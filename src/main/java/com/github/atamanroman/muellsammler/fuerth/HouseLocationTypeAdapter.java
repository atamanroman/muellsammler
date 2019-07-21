package com.github.atamanroman.muellsammler.fuerth;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class HouseLocationTypeAdapter implements JsonDeserializer<HouseLocations.HouseLocation> {
	@Override
	public HouseLocations.HouseLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		var location = json.getAsJsonObject().getAsJsonPrimitive("i").getAsString();
		var number = json.getAsJsonObject().getAsJsonPrimitive("n").getAsString();
		return new HouseLocations.HouseLocation(location, number);
	}
}
