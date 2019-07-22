package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.StreetName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FuerthTrashApi {

  public static final Type STRING_STRING_MAP = new TypeToken<Map<String, String>>() {
  }.getType();
  private static Logger log = LoggerFactory.getLogger(FuerthTrashApi.class);
  private String termineApi = "https://abfallwirtschaft.fuerth.eu/termine.php";
  private HttpClient httpClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofMillis(1000))
    .build();

  public HouseLocations fetchLocationCodes(StreetName street) {
    var encodedStreet = URLEncoder.encode(street.getName(), StandardCharsets.UTF_8);
    var locationCodeUri = URI.create(termineApi + "?r=" + encodedStreet);
    log.info("GET locations from {}", locationCodeUri);
    var locationCodeRequest = HttpRequest.newBuilder(locationCodeUri).GET()
      .timeout(Duration.ofMillis(1000))
      .header("accept", "application/json")
      .build();
    try {
      var response = httpClient.send(locationCodeRequest, HttpResponse.BodyHandlers.ofString());
      return new HouseLocationsJson(response.body()).parse();
    } catch (IOException | InterruptedException e) {
      throw new FuerthTrashApiException(e);
    }
  }

  public TrashScheduleCsv fetchTrashSchedule(Location location) {
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
    private final String csv;

    public TrashScheduleCsv(String csv) {
      this.csv = csv;
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
