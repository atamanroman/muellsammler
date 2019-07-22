package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.HouseNumber;
import com.github.atamanroman.muellsammler.StreetName;
import java.util.Arrays;
import org.junit.Test;

public class FuerthTrashScheduleSourceTest {


  @Test
  public void read() {
    var address = new Address(
      new StreetName("Peter-Hannweg-Straße"),
      new HouseNumber("38"),
      "90768"
    );
    var response = new FuerthTrashScheduleSource(new FakeFuertTrashApi()).read(address);
  }

  static class FakeFuertTrashApi extends FuerthTrashApi {
    @Override
    public HouseLocations fetchLocationCodes(StreetName street) {

      return new HouseLocations(
        Arrays.asList(
          new HouseLocations.HouseLocation("123456", "1"),
          new HouseLocations.HouseLocation("123457", "2")
        )
      );
    }

    @Override
    public TrashScheduleCsv fetchTrashSchedule(Location location) {
      return null; // TODO
    }
  }
}
