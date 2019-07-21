package com.github.atamanroman.muellsammler;

import java.util.Map;
import java.util.Set;

public class TrashSchedule {
	private Address address;
	private Map<TrashType, Set<TrashSchedule>> dates;
}
