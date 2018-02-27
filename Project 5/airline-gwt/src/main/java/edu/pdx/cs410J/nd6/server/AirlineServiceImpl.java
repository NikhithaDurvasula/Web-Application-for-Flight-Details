package edu.pdx.cs410J.nd6.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.nd6.client.Airline;
import edu.pdx.cs410J.nd6.client.Flight;
import edu.pdx.cs410J.nd6.client.AirlineService;

import java.util.*;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  private Map<String, Airline> airlineHashMap = new HashMap<>();

  @Override
  public Airline getAirline() {
    Airline airline = new Airline();
    airline.addFlight(new Flight());
    return airline;
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  @Override
  public AbstractAirline addFlight(String airlineName, String number, String source, String depart, String destination, String arrive) {
    Airline airline = new Airline(airlineName);
    Flight flight = new Flight(number, source, depart, destination, arrive);

    if (airlineHashMap.get(airlineName) == null){
      airline.addFlight(flight);
      airlineHashMap.put(airlineName, airline);
    }
    else{
      airlineHashMap.get(airlineName).addFlight(flight);
    }
    return airlineHashMap.get(airlineName);
  }

  @Override
  public AbstractAirline searchFlight(String name, String src, String dest) {
    if(airlineHashMap.get(name) == null){
      return null;
    }
    else {
      Airline airlineFound = new Airline(name);
      Airline airline = airlineHashMap.get(name);
      Collection flights = airline.getFlights();
      List<Flight> flightslist=new ArrayList<Flight>();
      flightslist.addAll(flights);
      Collections.sort(flightslist, new Comparator<Flight>(){
        @Override
        public int compare(Flight o1, Flight o2) {
          if(o1.getSource().compareTo(o2.getSource()) != 0){
            return o1.getSource().compareTo(o2.getSource());
          }
          else if(o1.getSource().compareTo(o2.getSource()) == 0 && o1.getDepartureString().compareTo(o2.getDepartureString())==0)
          {
            return 0;
          }
          else

            return o1.getDepartureString().compareTo(o2.getDepartureString());
        }});


      for (Object obj : flightslist) {

        if (src.equals(((Flight) obj).getSource()) && dest.equals(((Flight) obj).getDestination())) {
          airlineFound.addFlight((Flight) obj);
        }

      }
      return airlineFound;
    }

  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}