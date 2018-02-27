package edu.pdx.cs410J.nd6.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAirline;

/**
 * The client-side interface to the airline service
 */
public interface AirlineServiceAsync {

  /**
   * Return an airline created on the server
   */
  void getAirline(AsyncCallback<Airline> async);

  /**
   * Always throws an exception so that we can see how to handle uncaught
   * exceptions in GWT.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);

  /**
   *
   * @param airlineName
   * @param number
   * @param source
   * @param depart
   * @param destination
   * @param arrive
   * @param async
   */
  void addFlight(String airlineName, String number, String source,
                 String depart, String destination, String arrive, AsyncCallback<AbstractAirline> async);

  /**
   *
   * @param name
   * @param src
   * @param dest
   * @param async
   */
  void searchFlight(String name, String src, String dest, AsyncCallback<AbstractAirline> async);
}