package edu.pdx.cs410J.nd6;

import java.util.LinkedList;
import java.util.List;

public class Airline {
    private String name;
    private List<Flight> flights;

    /**
     *
     * @param name :  the name of this airline
     */
    public Airline(String name) {
        this.name = name;
        this.flights = new LinkedList<>();
    }

    /**
     * get name of the airline
     */
    public String getName() {
        return name;
    }

    /**
     * add the info of new flight to the collection of flight
     * @param flight
     */
    public void addFlight(Flight flight) {
        flights.add((Flight)flight);
//        Collections.sort(flights);
    }

    /**
     * return the collection of the flight
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     *
     * @return print out the collection of flight for pretty print
     */
    public String print(){
        String printOutAirlineInfo = "Airline name: " + name + " has : \n";

        for(int i=0; i<flights.size(); ++i){
            printOutAirlineInfo += "\t" + flights.get(i).toString()  + ". And it takes " + flights.get(i).durationTime() + " in minutes.\n";
        }
        System.out.println();
        return printOutAirlineInfo;
    }

    /**
     *
     * @return print out the collection of flights for text dumper
     */
    public String printFlight(){
        String printOutAirlineFlight = "";

        for (int i=0; i<flights.size(); ++i){
            Flight printFLight = (Flight)flights.get(i);
            printOutAirlineFlight += name + " " + printFLight.toString();
        }

        return printOutAirlineFlight;
    }
}