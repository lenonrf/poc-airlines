package br.com.bexs.airlines.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteFullRepresentation {

    @JsonProperty("description")
    private String description;

    @JsonProperty("totalCost")
    private Double totalCost;

    public RouteFullRepresentation(){
        super();
    }

    public RouteFullRepresentation(String description, Double totalCost){
        this.description = description;
        this.totalCost = totalCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void addRouteInDescription(String routeName){
        this.description += " - "+routeName;
    }

    public void addRouteCost(Double routeCost){
        this.totalCost += routeCost;
    }

    @Override
    public String toString() {

        return "\nRoute With Stop Overs: '" + description +
                "\nCost:" + totalCost;
    }
}
