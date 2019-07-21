package com.github.atamanroman.muellsammler;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

public class PickUp {

	private TrashType type;
	private LocalDate date;
	@Nullable
	private LocalTime time;

	public PickUp(TrashType type, LocalDate date, LocalTime time) {
		this.type = type;
		this.date = date;
		this.time = time;
	}

	public PickUp(TrashType type, LocalDate date) {
		this.type = type;
		this.date = date;
	}
}
