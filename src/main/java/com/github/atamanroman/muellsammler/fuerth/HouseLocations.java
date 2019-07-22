package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.HouseNumber;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class HouseLocations {

  private Map<HouseNumber, Location> locationCodes = new HashMap<>();

  public HouseLocations(List<HouseLocation> houseLocations) {
    if (houseLocations.isEmpty()) {
      throw new IllegalArgumentException("houseLocations must not be empty");
    }
    houseLocations.forEach(houseLocation -> this.locationCodes.put(
      houseLocation.getHouseNumber(), houseLocation.getLocation()
    ));
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

