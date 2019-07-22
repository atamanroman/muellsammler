package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.HouseNumber;
import com.github.atamanroman.muellsammler.StreetName;
import com.github.atamanroman.muellsammler.TrashType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FuerthTrashScheduleSourceTest {

  @Test
  public void read() {
    var address = new Address(
      new StreetName("Peter-Hannweg-Straße"),
      new HouseNumber("38"),
      "90768"
    );
    var response = new FuerthTrashScheduleSource(new FakeFuertTrashApi()).read(address);
    assertThat(response.getAddress(), is(address));
//    assertThat(response.getDates().get(TrashType.GELBER_SACK), hasItem(PICK_UP_ONE));
    assertThat(response.getDates().get(TrashType.BIO).size(), is(3));
    assertThat(response.getDates().get(TrashType.RESTMUELL).size(), is(3));
    assertThat(response.getDates().get(TrashType.ALTPAPIER).size(), is(2));
    assertThat(response.getDates().get(TrashType.GELBER_SACK).size(), is(2));
  }

  static class FakeFuertTrashApi extends FuerthTrashApi {
    @Override
    public HouseLocationsJson fetchLocationCodes(StreetName street) {
      return new HouseLocationsJson(TestData.HOUSENUMBERS_JSON);
    }

    @Override
    public TrashScheduleCsv fetchPickUps(Location location) {
      return new TrashScheduleCsv(TestData.SCHEDULE_CSV);
    }
  }
}
