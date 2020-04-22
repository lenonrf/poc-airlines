package br.com.bexs.airlines.api.domain.entity;

import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ROUTE")
public class Route {

    @Id
    @Column(name = "ID_ROUTE", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "AIRPORT_ORIGIN", nullable = false)
    @CsvBindByPosition(position = 0)
    private String airportOrigin;

    @Column(name = "AIRPORT_DESTINATION", nullable = false)
    @CsvBindByPosition(position = 1)
    private String airportDestination;

    @Column(name = "COST", nullable = false)
    @CsvBindByPosition(position = 2)
    private Double cost;

    public Route(){
        super();
    }

    public Route (String airportOrigin, String airportDestination) {

        this.airportOrigin = airportOrigin;
        this.airportDestination = airportDestination;
    }

    public Route (String route){

        if(route != null) {

            List<String> routeSplited = Arrays.asList(route.split("-"));

            this.airportOrigin = (routeSplited.size() >= 1 ) ? routeSplited.get(0) : "";
            this.airportDestination = (routeSplited.size() == 2) ? routeSplited.get(1) : "";
            this.cost = (routeSplited.size() >= 3) ? Double.valueOf(routeSplited.get(2)) : 0D;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportOrigin() {
        return airportOrigin;
    }

    public void setAirportOrigin(String airportOrigin) {
        this.airportOrigin = airportOrigin;
    }

    public String getAirportDestination() {
        return airportDestination;
    }

    public void setAirportDestination(String airportDestination) {
        this.airportDestination = airportDestination;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {

        return "\nAirport Origin: '" + airportOrigin +
                "\nAirport Destination: '" + airportDestination +
                "\nCost:" + cost;
    }
}
