package com.github.atamanroman.muellsammler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrashSchedule {
  private Address address;
  private Map<TrashType, Set<PickUp>> dates;

  public TrashSchedule(Address address, Map<TrashType, Set<PickUp>> dates) {
    this.address = address;
    this.dates = dates;
  }

  public TrashSchedule(Address address, Collection<PickUp> pickups) {
    this.address = address;

    var bio = pickups.stream()
      .filter(p -> p.getType().equals(TrashType.BIO))
      .collect(Collectors.toUnmodifiableSet());
    var gelb = pickups.stream()
      .filter(p -> p.getType().equals(TrashType.GELBER_SACK))
      .collect(Collectors.toUnmodifiableSet());
    var rest = pickups.stream()
      .filter(p -> p.getType().equals(TrashType.RESTMUELL))
      .collect(Collectors.toUnmodifiableSet());
    var altpapier = pickups.stream()
      .filter(p -> p.getType().equals(TrashType.ALTPAPIER))
      .collect(Collectors.toUnmodifiableSet());

    var tmp = new HashMap<TrashType, Set<PickUp>>();
    tmp.put(TrashType.BIO, bio);
    tmp.put(TrashType.GELBER_SACK, gelb);
    tmp.put(TrashType.RESTMUELL, rest);
    tmp.put(TrashType.ALTPAPIER, altpapier);
    this.dates = Collections.unmodifiableMap(tmp);
  }

  public Address getAddress() {
    return address;
  }

  public Map<TrashType, Set<PickUp>> getDates() {
    return dates;
  }
}
