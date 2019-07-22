package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.City;
import com.github.atamanroman.muellsammler.TrashSchedule;
import com.github.atamanroman.muellsammler.TrashScheduleSource;
import com.github.atamanroman.muellsammler.fuerth.FuerthTrashApi.TrashScheduleCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuerthTrashScheduleSource implements TrashScheduleSource {

  private static Logger log = LoggerFactory.getLogger(FuerthTrashScheduleSource.class);

  private FuerthTrashApi trashApi;

  public FuerthTrashScheduleSource(FuerthTrashApi trashApi) {
    this.trashApi = trashApi;
  }

  @Override
  public TrashSchedule read(Address address) {
    log.info("Read TrashSchedule for {}", city());
    var locationCodes = trashApi.fetchLocationCodes(address.getStreetName()).parse();
    var optionalLocation = locationCodes.locationForHouseNumber(address.getHouseNumber());
    var optionalTrashSchedule = optionalLocation.map(trashApi::fetchPickUps);
    return optionalTrashSchedule
      .map(TrashScheduleCsv::parse)
      .map(pickUps -> new TrashSchedule(address, pickUps))
      .orElseThrow(() -> new FuertTrashException("Could not fetch trash schedule"));
  }

  @Override
  public City city() {
    return new City("Fürth");
  }
}
