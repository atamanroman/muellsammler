package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.StreetName;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

public class FuertTrashApi {

	public static final Type STRING_STRING_MAP = new TypeToken<Map<String, String>>() {
	}.getType();
	private static Logger log = LoggerFactory.getLogger(FuertTrashApi.class);
	private String termineApi = "https://abfallwirtschaft.fuerth.eu/termine.php";
	private HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(1000)).build();

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

	public TrashScheduleCsv fetchTrashSchedule(Location location) {
		var scheduleUri = URI.create(termineApi + "?csvexport=" + location.getLocation());
		log.info("GET schedule from {}", scheduleUri);
		var scheduleRequest = HttpRequest.newBuilder(scheduleUri).GET().timeout(Duration.ofMillis(1000)).build();
		try {
			var scheduleResponse = httpClient.send(scheduleRequest, HttpResponse.BodyHandlers.ofString());
			return new TrashScheduleCsv(scheduleResponse.body());
		} catch (IOException | InterruptedException e) {
			throw new FuerthTrashApiException(e);
		}
	}
}
