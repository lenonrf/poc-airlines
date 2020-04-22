package br.com.bexs.airlines.batch.gateway;

import br.com.bexs.airlines.api.domain.entity.Route;
import br.com.bexs.airlines.api.domain.RouteFullRepresentation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class RouteGateway {

    public static final String RESOURCE = "http://localhost:8080/route";

    public void upload(String fileName){

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(fileName));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String url = RESOURCE + "/file-upload";

        ResponseEntity<String> response = new RestTemplate().exchange(url,
                        HttpMethod.POST, new HttpEntity<>(bodyMap, headers), String.class);
    }


    public RouteFullRepresentation getExpandedRouteWithStopOver(Route route) throws Exception{

        try {

            String url = RESOURCE + ";airport-origin="+route.getAirportOrigin()
                    +";airport-destination=" + route.getAirportDestination();

            ResponseEntity<String> response =
                    new RestTemplate().getForEntity(url, String.class);

            return new ObjectMapper().readValue(response.getBody(), RouteFullRepresentation.class);

        } catch (HttpStatusCodeException exception) {
                return null;
        }
    }


    public List<Route> getAllRoutes() throws Exception{

        ResponseEntity<String> response =
                new RestTemplate().getForEntity(RESOURCE, String.class);

        return new ObjectMapper().readValue(response.getBody(), new TypeReference<List<Route>>() {});
    }
}
