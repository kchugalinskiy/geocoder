/**
 * Copyright 2023
 * @author programmer1337
 */

package com.dmnine.geocoder.client;

import com.dmnine.geocoder.dto.NominatimPlace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/** FeignClient. */
@FeignClient(name = "nominatim", url = "https://nominatim.openstreetmap.org")
public interface NominatimClient {
  String JSON_FORMAT = "json";

  @RequestMapping(method = RequestMethod.GET, value = "/search", consumes = "application/json")
  List<NominatimPlace> search(@RequestParam("q") String query,
                              @RequestParam("format") String format);
  /**
   * Поиск объекта на карте по адресной строке в свободном параметре.
   * В случаее нескольких подходящий объектов будет возвращен самый релевантный.
   *
   * @param query строка поиска
   * @return объект адреса
   */
  default Optional<NominatimPlace> search(final String query) {
    try {
      return Optional.of(search(query, JSON_FORMAT).get(0));
    } catch (Exception ex) {
      return Optional.empty();
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/reverse", consumes = "application/json")
  NominatimPlace reverse(@RequestParam("lat") Double lat,
                         @RequestParam("lon") Double lon,
                         @RequestParam("format") String format);
  /**
   * Поиск объекта по его координатам.
   * Возвращается ближайший к запрашиваемым координатам объект.
   *
   * @param lat широта
   * @param lon долгота
   * @return возвращает найденный объект
   */
  default Optional<NominatimPlace> reverse(final Double lat, final Double lon) {
    try {
      return Optional.of(reverse(lat, lon, JSON_FORMAT));
    } catch (Exception ex) {
      return Optional.empty();
    }
  }
}
