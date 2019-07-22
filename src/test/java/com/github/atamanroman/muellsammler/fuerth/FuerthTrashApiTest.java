package com.github.atamanroman.muellsammler.fuerth;

import org.junit.Test;

public class FuerthTrashApiTest {

  @Test
  public void parseCsv() {
    var trashScheduleCsv = new FuerthTrashApi.TrashScheduleCsv(TestData.SCHEDULE_CSV);
    var trashSchedule = trashScheduleCsv.parse();
  }
}
