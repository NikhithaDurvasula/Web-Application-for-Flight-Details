package edu.pdx.cs410J.nd6;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

//  @Test
//  public void test0RemoveAirline() throws IOException {
//    AirlineRestClient client = newAirlineRestClient();
//    client.removeAirline();
//  }
//
//  @Test
//  public void test1AddOneFlight() throws IOException {
//    AirlineRestClient client = newAirlineRestClient();
//
//    String airlineName = "My Airline";
//    Flight flight = new Flight("PDX", "LAX", 123);
//    client.addFlight(airlineName, flight);
//  }
//
//  @Test
//  public void test2AddMoreFlights() throws IOException {
//    AirlineRestClient client = newAirlineRestClient();
//
//    String airlineName = "My Airline";
//    client.addFlight(airlineName, new Flight("PDX", "LAS", 234));
//    client.addFlight(airlineName, new Flight("PDX", "LAX", 345));
//  }

}