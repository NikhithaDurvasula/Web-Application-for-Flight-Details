package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;
import java.util.List;

import java.util.Collection;

public class Airline extends AbstractAirline
{
    private String name;
    private List<AbstractFlight> flightList;

    //Constructor to connect Airline and Flight
    Airline(String name)
    {
        this. name = name;
        flightList = new ArrayList<AbstractFlight>();
        //addFlight((AbstractFlight) flightList);
    }

    //gets the name of the airline
    @Override
    public String getName() {
        return name;
    }

    //adds the details of the flight like flightNumber, src, departTime, dest, arriveTime to flightList
    @Override
    public void addFlight(AbstractFlight abstractFlight) {
        flightList.add(abstractFlight);
    }

    //gets the data in the flightList
    @Override
    public Collection getFlights() {
        return flightList;
    }
}
