package com.github.atamanroman.muellsammler.nbg;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.HouseNumber;
import com.github.atamanroman.muellsammler.StreetName;
import org.junit.Test;

public class NbgTrashScheduleSourceTest {

  private static class FakeNuernbergGelberSackApi extends NbgGelberSackApi {
    @Override
    GelberSackPdf fetch() {
      return new GelberSackPdf(TestData.GELBER_SACK_PDF);
    }
  }

  private NbgTrashScheduleSource cut = new NbgTrashScheduleSource(new FakeNuernbergGelberSackApi());

  @Test
  public void readStreetFromApiWithoutSpecialCase() {
    var address = new Address(new StreetName("Aachener Str."), new HouseNumber("42"), "90489");
    var trashSchedule = cut.read(address);
  }
}
