package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.TrashSchedule;
import com.github.atamanroman.muellsammler.TrashScheduleSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuerthTrashScheduleSource implements TrashScheduleSource {

	private static Logger log = LoggerFactory.getLogger(FuerthTrashScheduleSource.class);

	private FuertTrashApi trashApi;

	public FuerthTrashScheduleSource(FuertTrashApi trashApi) {
		this.trashApi = trashApi;
	}

	@Override
	public TrashSchedule read(Address address) {
		var locationCodesJson = trashApi.fetchLocationCodes(address.getStreetName());
		var locationCodes = HouseLocations.fromJsonString(locationCodesJson.getJson());
		var optionalLocation = locationCodes.locationForHouseNumber(address.getHouseNumber());
		var optionalTrashScheduleCsv = optionalLocation.map(trashApi::fetchTrashSchedule);
		return null;
	}
}
