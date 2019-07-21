package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.HouseNumber;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class HouseLocations {

	private static Gson gson = new GsonBuilder()
		.registerTypeAdapter(HouseLocation.class, new HouseLocationTypeAdapter())
		.create();
	private Map<HouseNumber, Location> locationCodes = new HashMap<>();

	public HouseLocations(List<HouseLocation> houseLocations) {
		if (houseLocations.isEmpty())
			throw new IllegalArgumentException("houseLocations must not be empty");
		houseLocations.forEach(houseLocation -> this.locationCodes.put(
			houseLocation.getHouseNumber(), houseLocation.getLocation()
		));
	}

	public static HouseLocations fromJsonString(String json) {
		var parsed = gson.fromJson(json, HouseLocation[].class);
		return new HouseLocations(Arrays.asList(parsed));
	}

	public Optional<Location> locationForHouseNumber(HouseNumber number) {
		return Optional.ofNullable(this.locationCodes.get(number));
	}


	public static class HouseLocation {
		@SerializedName("i")
		private final String location;
		@SerializedName("n")
		private final String houseNumber;

		public HouseLocation(String location, String houseNumber) {
			this.location = location.trim();
			this.houseNumber = houseNumber.replaceAll(" ", "").trim();
		}

		public Location getLocation() {
			return new Location(Integer.parseInt(this.location));
		}

		public HouseNumber getHouseNumber() {
			return new HouseNumber(this.houseNumber);
		}
	}

}

