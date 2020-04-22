package br.com.bexs.airlines.api.repository;

import br.com.bexs.airlines.api.domain.entity.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RouteRepository extends CrudRepository<Route, Long> {

    @Override
    List<Route> findAll();

    List<Route> findAllByAirportOrigin(String airportOrigin);

    Route findByAirportOrigin(String airportOrigin);
}
