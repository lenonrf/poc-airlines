package br.com.bexs.airlines.api.service;

import br.com.bexs.airlines.api.domain.entity.Route;
import br.com.bexs.airlines.api.domain.RouteFullRepresentation;
import br.com.bexs.airlines.api.repository.RouteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Route> save(List<Route> routeList) {

        if (routeList != null) {

            for (Route item : routeList) {
                routeRepository.save(item);
            }
        }

        return routeList;
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public RouteFullRepresentation getBestRouteWithFullDescription(String airportOrigin, String airportDestination){

        List<RouteFullRepresentation> routeStopOverList = new ArrayList<>();
        Route mainRoute = new Route(airportOrigin, airportDestination);

        List<Route> airportOriginRouteList = routeRepository.findAllByAirportOrigin(airportOrigin);

        if(airportOriginRouteList.size() == 0) {
            return null;
        }

        for(Route item: airportOriginRouteList){

            RouteFullRepresentation currentStopOverDescription =
                    this.getRouteDescriptionWithStopOver(mainRoute, item,
                            new RouteFullRepresentation(airportOrigin, 0D));

            if(currentStopOverDescription != null){
                routeStopOverList.add(currentStopOverDescription);
            }
        }

        return routeStopOverList.stream()
                .min(Comparator.comparing(RouteFullRepresentation::getTotalCost))
                .get();
    }



    public RouteFullRepresentation getRouteDescriptionWithStopOver(Route mainRoute, Route stopOverRoute,
                                                                   RouteFullRepresentation stopOverDescription){

        stopOverDescription.addRouteInDescription(stopOverRoute.getAirportDestination());
        stopOverDescription.addRouteCost(stopOverRoute.getCost());

        if(mainRoute.getAirportDestination().equals(stopOverRoute.getAirportDestination())){
            return stopOverDescription;
        }

        Route currentRoute =
                routeRepository.findByAirportOrigin(stopOverRoute.getAirportDestination());

        if(currentRoute == null){
            return null;
        }

        return this.getRouteDescriptionWithStopOver(mainRoute, currentRoute, stopOverDescription);
    }
}
