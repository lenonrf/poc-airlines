package br.com.bexs.airlines.api.endpoint;


import br.com.bexs.airlines.api.domain.entity.Route;
import br.com.bexs.airlines.api.domain.RouteFullRepresentation;
import br.com.bexs.airlines.api.service.FileReaderRouteService;
import br.com.bexs.airlines.api.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class RouteEndpoint {

    @Autowired
    private FileReaderRouteService fileReaderRouteService;

    @Autowired
    private RouteService routeService;

    @PostMapping("/route/file-upload")
    public ResponseEntity<List<Route>> upload(@RequestParam("file") MultipartFile file) throws Exception{

        List<Route> routeList = fileReaderRouteService.readRoutesFromFile(file);

        routeService.save(routeList);
        return new ResponseEntity<>(routeList, OK);
    }


    @GetMapping("/route")
    public ResponseEntity<List<Route>> get(){

        List<Route> routeList = routeService.findAll();
        return new ResponseEntity<>(routeList, OK);
    }

    @GetMapping("/route{filterMatrix}")
    public ResponseEntity<RouteFullRepresentation> get(
            @MatrixVariable(pathVar = "filterMatrix", value = "airport-origin", required = false) String airportOrigin,
            @MatrixVariable(pathVar = "filterMatrix", value = "airport-destination", required = false) String airportDestination) throws Exception{

        RouteFullRepresentation routeStopOver =
                routeService.getBestRouteWithFullDescription(airportOrigin, airportDestination);

        if(routeStopOver != null){
            return new ResponseEntity<>(routeStopOver, OK);

        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
