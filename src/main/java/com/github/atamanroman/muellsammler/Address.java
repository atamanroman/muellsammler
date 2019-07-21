package com.github.atamanroman.muellsammler;

import java.util.Objects;

public final class Address {

	private final StreetName street;
	private final HouseNumber number;
	private final String zip;

	public Address(StreetName street, HouseNumber number, String zip) {
		this.street = street;
		this.number = number;
		this.zip = zip.trim();
	}

	public StreetName getStreetName() {
		return street;
	}

	public HouseNumber getHouseNumber() {
		return number;
	}

	public String getZip() {
		return zip;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return street.equals(address.street) &&
			number.equals(address.number) &&
			zip.equals(address.zip);
	}

	@Override
	public int hashCode() {
		return Objects.hash(street, number, zip);
	}

	@Override
	public String toString() {
		return "Address{" +
			"street=" + street +
			", number=" + number +
			", zip='" + zip + '\'' +
			'}';
	}
}
