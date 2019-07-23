package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.HouseNumber;
import com.github.atamanroman.muellsammler.PickUp;
import com.github.atamanroman.muellsammler.StreetName;
import com.github.atamanroman.muellsammler.TrashType;
import java.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FuerthTrashScheduleSourceTest {

  private FuerthTrashScheduleSource cut = new FuerthTrashScheduleSource(new FakeFuertTrashApi());

  static class FakeFuertTrashApi extends FuerthTrashApi {
    @Override
    public HouseLocationsJson fetchHouseLocations(StreetName street) {
      return new HouseLocationsJson(TestData.HOUSENUMBERS_JSON);
    }

    @Override
    public TrashScheduleCsv fetchTrashSchedule(Location location) {
      return new TrashScheduleCsv(TestData.SCHEDULE_CSV);
    }
  }

  @Test
  public void readEverythingFromApi() {
    var address = new Address(
      new StreetName("Peter-Hannweg-Straße"),
      new HouseNumber("38"),
      "90768"
    );
    var response = cut.read(address);
    assertThat(response.getAddress(), is(address));
    assertThat(response.getDates().get(TrashType.BIO).size(), is(3));
    assertThat(response.getDates().get(TrashType.BIO), hasItems(
      new PickUp(TrashType.BIO, LocalDate.of(2019, 7, 10)),
      new PickUp(TrashType.BIO, LocalDate.of(2019, 7, 24)),
      new PickUp(TrashType.BIO, LocalDate.of(2019, 8, 7))
    ));
    assertThat(response.getDates().get(TrashType.RESTMUELL).size(), is(3));
    assertThat(response.getDates().get(TrashType.RESTMUELL), hasItems(
      new PickUp(TrashType.RESTMUELL, LocalDate.of(2019, 7, 10)),
      new PickUp(TrashType.RESTMUELL, LocalDate.of(2019, 7, 24)),
      new PickUp(TrashType.RESTMUELL, LocalDate.of(2019, 8, 7))
    ));
    assertThat(response.getDates().get(TrashType.ALTPAPIER).size(), is(2));
    assertThat(response.getDates().get(TrashType.ALTPAPIER), hasItems(
      new PickUp(TrashType.ALTPAPIER, LocalDate.of(2019, 8, 1)),
      new PickUp(TrashType.ALTPAPIER, LocalDate.of(2019, 8, 29))
    ));
    assertThat(response.getDates().get(TrashType.GELBER_SACK).size(), is(2));
    assertThat(response.getDates().get(TrashType.GELBER_SACK), hasItems(
      new PickUp(TrashType.GELBER_SACK, LocalDate.of(2019, 7, 15)),
      new PickUp(TrashType.GELBER_SACK, LocalDate.of(2019, 8, 12))
    ));
  }

  @Test
  public void supportsAll() {
    assertThat(cut.supports(), hasItems(TrashType.values()));
  }
}
