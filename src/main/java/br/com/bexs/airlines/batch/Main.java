package br.com.bexs.airlines.batch;

import br.com.bexs.airlines.api.domain.entity.Route;
import br.com.bexs.airlines.api.domain.RouteFullRepresentation;
import br.com.bexs.airlines.batch.gateway.RouteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class Main {

    @Autowired
    private RouteGateway routeFileGateway;

    @Autowired
    private RouteGateway routeGateway;

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void init(String[] args) throws Exception {

        if(args.length == 0){
            System.out.println("File not found");
            System.exit(0);
        }

        String fileName = args[0];

        System.out.println("IMPORTING DATA FROM FILE: "+fileName);
        this.routeFileGateway.upload(fileName);

        while (true) {

            System.out.println("\n\n---------------------------------------------------");
            System.out.println(" MENU ");
            System.out.println("---------------------------------------------------\n");
            System.out.println("[1] - Show all Routes");
            System.out.println("[2] - Find a best Route by Origin and Destination");
            System.out.println("[3] - Quit");

            System.out.print("Choose: ");
            Integer choice = Integer.valueOf(br.readLine().trim());

            switch (choice) {

                case 1:
                    this.showAllRoutes();
                    break;

                case 2:
                    this.findBestRoute();
                    break;

                case 3:
                    System.exit(0);
                    break;
            }

            System.out.print("\n[Press any key to continue]");
            br.readLine();

        }
    }


    public void findBestRoute() throws Exception {

        System.out.print("\nPlease enter the route: ");
        String routeInput = br.readLine().toUpperCase();

        if(routeInput.matches("[A-Z]+-[A-Z]+")){

            Route route = new Route(routeInput);

            RouteFullRepresentation routeStopOver =
                    routeGateway.getExpandedRouteWithStopOver(route);

            if(routeStopOver != null){
                System.out.println("\nThe best route is: " + routeStopOver.getDescription());
                System.out.println("Total Cost: " + routeStopOver.getTotalCost());

            }else{
                System.out.println("\nRoute not found.");
            }

        }else{

            System.out.println("\nInvalid Input. ");
            System.out.println("Example: GRU-ORL");
        }


    }


    public void showAllRoutes() throws Exception {

        List<Route> routeList = routeGateway.getAllRoutes();

        for(Route item : routeList){
            System.out.println("\nAirport Origin: " + item.getAirportOrigin());
            System.out.println("Airport Destination: " + item.getAirportDestination());
            System.out.println("Cost: " + item.getCost());
        }
    }
}