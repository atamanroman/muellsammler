package com.github.atamanroman.muellsammler;

import java.util.Objects;

public final class StreetName {

	private final String streetName;

	public StreetName(String streetName) {
		this.streetName = streetName.trim();
	}

	public String getName() {
		return streetName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StreetName that = (StreetName) o;
		return streetName.equals(that.streetName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(streetName);
	}

	@Override
	public String toString() {
		return "StreetName='" + streetName + "'";
	}
}
