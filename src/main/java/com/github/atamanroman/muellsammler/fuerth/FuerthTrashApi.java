package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.PickUp;
import com.github.atamanroman.muellsammler.StreetName;
import com.github.atamanroman.muellsammler.TrashType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FuerthTrashApi {

  private static Logger log = LoggerFactory.getLogger(FuerthTrashApi.class);

  private String termineApi = "https://abfallwirtschaft.fuerth.eu/termine.php";
  private HttpClient httpClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofMillis(1000))
    .build();

  public HouseLocationsJson fetchLocationCodes(StreetName street) {
    var encodedStreet = URLEncoder.encode(street.getName(), StandardCharsets.UTF_8);
    var locationCodeUri = URI.create(termineApi + "?r=" + encodedStreet);
    log.info("GET locations from {}", locationCodeUri);
    var locationCodeRequest = HttpRequest.newBuilder(locationCodeUri).GET()
      .timeout(Duration.ofMillis(1000))
      .header("accept", "application/json")
      .build();
    try {
      var response = httpClient.send(locationCodeRequest, HttpResponse.BodyHandlers.ofString());
      return new HouseLocationsJson(response.body());
    } catch (IOException | InterruptedException e) {
      throw new FuerthTrashApiException(e);
    }
  }

  public TrashScheduleCsv fetchPickUps(Location location) {
    var scheduleUri = URI.create(termineApi + "?csvexport=" + location.getLocation());
    log.info("GET schedule from {}", scheduleUri);
    var scheduleRequest = HttpRequest.newBuilder(scheduleUri)
      .GET().timeout(Duration.ofMillis(1000))
      .build();
    try {
      var scheduleResponse = httpClient.send(scheduleRequest, HttpResponse.BodyHandlers.ofString());
      return new TrashScheduleCsv(scheduleResponse.body());
    } catch (IOException | InterruptedException e) {
      throw new FuerthTrashApiException(e);
    }
  }

  static class HouseLocationsJson {

    private static Gson gson = new GsonBuilder()
      .registerTypeAdapter(HouseLocations.HouseLocation.class, new HouseLocationTypeAdapter())
      .create();
    private final String json;

    public HouseLocationsJson(String json) {

      this.json = json;
    }

    public HouseLocations parse() {
      var parsed = gson.fromJson(json, HouseLocations.HouseLocation[].class);
      return new HouseLocations(Arrays.asList(parsed));
    }

    public String getJson() {
      return json;
    }
  }

  static class TrashScheduleCsv {

    static final String BIO_COL = "Bioabfall";
    static final String ALTPAPIER_COL = "Altpapier";
    static final String GELBER_SACK_COL = "Gelber Sack";
    static final String RESTMUELL_COL = "Restabfall";
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(
      "dd.MM.yy", Locale.GERMAN
    );

    private final String csv;

    TrashScheduleCsv(String csv) {
      this.csv = csv;
    }

    List<PickUp> parse() {
      var withoutComments = csv.lines()
        .filter(Predicate.not(String::isEmpty))
        .skip(2)
        .collect(Collectors.joining("\r\n"));

      try (
        CSVParser parser = CSVFormat.DEFAULT
          .withFirstRecordAsHeader()
          .withSkipHeaderRecord()
          .parse(new StringReader(withoutComments))
      ) {
        return parser.getRecords().stream()
          .flatMap(this::lineToPickUpStream)
          .collect(Collectors.toList());
      } catch (IOException e) {
        throw new IllegalStateException("Reading from a string can't cause an IOException");
      }
    }

    private Stream<PickUp> lineToPickUpStream(CSVRecord r) {
      return Stream.of(
        newPickUp(r.get(BIO_COL), TrashType.BIO),
        newPickUp(r.get(ALTPAPIER_COL), TrashType.ALTPAPIER),
        newPickUp(r.get(GELBER_SACK_COL), TrashType.GELBER_SACK),
        newPickUp(r.get(RESTMUELL_COL), TrashType.RESTMUELL)
      ).filter(Objects::nonNull);
    }

    private PickUp newPickUp(String date, TrashType type) {
      if (date.isBlank()) {
        return null;
      }
      return new PickUp(type, parseDate(date));
    }

    private LocalDate parseDate(String date) {
      return LocalDate.parse(date, DATE_FORMAT);
    }
  }

  static class HouseLocationTypeAdapter implements JsonDeserializer<HouseLocations.HouseLocation> {
    @Override
    public HouseLocations.HouseLocation deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context
    ) throws JsonParseException {
      var location = json.getAsJsonObject().getAsJsonPrimitive("i").getAsString();
      var number = json.getAsJsonObject().getAsJsonPrimitive("n").getAsString();
      return new HouseLocations.HouseLocation(location, number);
    }
  }
}
