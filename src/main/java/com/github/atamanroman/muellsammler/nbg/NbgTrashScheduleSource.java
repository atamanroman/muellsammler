package com.github.atamanroman.muellsammler.nbg;

import com.github.atamanroman.muellsammler.Address;
import com.github.atamanroman.muellsammler.City;
import com.github.atamanroman.muellsammler.TrashSchedule;
import com.github.atamanroman.muellsammler.TrashScheduleSource;
import com.github.atamanroman.muellsammler.TrashType;
import java.util.Set;

/**
 * Sources:
 * Gelb:
 * https://www.ana-abfallentsorgung-nuernberg.de/cms/Abfuhrtermine.html (GELB)
 * Papier:
 * https://www.nuernberg.de/imperia/md/asn/dokumente/abfuhrplan_dsd_nuernberg_2017-2019_final.pdf
 */
public class NbgTrashScheduleSource implements TrashScheduleSource {

  private final NbgGelberSackApi gelberSackApi;

  public NbgTrashScheduleSource(NbgGelberSackApi gelberSackApi) {
    this.gelberSackApi = gelberSackApi;
  }

  @Override
  public TrashSchedule read(Address address) {
    var gelberSackPdf = gelberSackApi.fetch();
    // TODO needs another format for street lookup with complex rules (depending on the house#)
    var pickUps = gelberSackPdf.parse();
    return null;
  }

  @Override
  public City city() {
    return new City("Nürnberg");
  }

  @Override
  public Set<TrashType> supports() {
    return Set.of(TrashType.GELBER_SACK);
  }
}
