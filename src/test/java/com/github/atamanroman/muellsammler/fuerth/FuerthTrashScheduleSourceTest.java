package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.HouseNumber;
import com.github.atamanroman.muellsammler.StreetName;
import org.junit.Test;

public class FuerthTrashScheduleSourceTest {

	@Test
	public void read() {
		var address = new Address(new StreetName("Peter-Hannweg-Straße"), new HouseNumber("38"), "90768");
		var response = new FuerthTrashScheduleSource(new FuertTrashApi()).read(address);
	}
}
