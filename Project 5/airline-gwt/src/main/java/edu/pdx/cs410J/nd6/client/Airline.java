package edu.pdx.cs410J.nd6.client;


import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Airline extends AbstractAirline<Flight>
{
  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */

  private String name;
  public List<Flight> flight;

  public Airline() {

  }
  public Airline(String name) {
    this.name = name;
    this.flight = new ArrayList<>();
  }
  private Collection<Flight> flights = new ArrayList<>();

  @Override
  public String getName() {

    return name;
  }

  @Override
  public void addFlight(Flight flight) {
    this.flights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return this.flights;
  }
}
