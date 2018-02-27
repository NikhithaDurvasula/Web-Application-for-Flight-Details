package edu.pdx.cs410J.nd6;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all keys and values from the server
     */
    public Response getAllKeysAndValues() throws IOException
    {
        return get(this.url );
    }

    /**
     * Returns all values for the given key
     */
    public Response getValues( String key ) throws IOException
    {
        return get(this.url, "key", key);
    }

    /**
     * David's function
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    public Response addKeyValuePair( String key, String value ) throws IOException
    {
        return post( this.url, "key", key, "value", value );
    }

//    public Response printTheFlight(String airline, String print) throws IOException {
//        return get(this.url, "name", airline, "print", print);
//    }

    /**
     *
     * @param airline : name of the airline
     * @param src : original terminal
     * @param dest : departure terminal
     * @return get matching flight from the servlet
     * @throws IOException
     */
    public Response searchFlight(String airline, String src, String dest) throws IOException{
        return get(this.url, "name", airline, "src", src, "dest", dest);
    }


    /**
     *
     * @param airline : name of the airline
     * @param flightNumber : the 3 digits number of flight
     * @param src : original terminal
     * @param departTime : departure time at original terminal
     * @param dest : departure terminal
     * @param arriveTime : arrive time at departure terminal
     * @return
     * @throws IOException
     */
    public Response addFlight(String airline, String flightNumber, String src, String departTime, String dest, String arriveTime) throws IOException{
        return post(this.url, "name", airline, "flightNumber", flightNumber, "src", src, "departTime", departTime,
                "dest", dest, "arriveTime", arriveTime);
    }


    @VisibleForTesting
    Response postToMyURL(String... keysAndValues) throws IOException {
        return post(this.url, keysAndValues);
    }

    public void removeAirline() throws IOException {
        Response response = delete(this.url);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        String message = response.getContent();
        if (code != HTTP_OK) {
            throw new AppointmentBookRestException(code, message);
        }
        return response;
    }

    private class AppointmentBookRestException extends RuntimeException {
        public AppointmentBookRestException(int httpStatusCode, String message) {
            super("Got an HTTP Status Code of " + httpStatusCode + ": " + message);
        }
    }
}