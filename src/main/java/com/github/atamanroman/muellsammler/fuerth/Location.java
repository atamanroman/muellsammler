package com.github.atamanroman.muellsammler.fuerth;

import java.util.Objects;

public class Location {

	private final int location;

	public Location(int location) {
		if (location < 0)
			throw new IllegalArgumentException("location must be > 0");
		this.location = location;
	}

	public int getLocation() {
		return location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location1 = (Location) o;
		return location == location1.location;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location);
	}

	@Override
	public String toString() {
		return "Location=" + location;
	}
}
