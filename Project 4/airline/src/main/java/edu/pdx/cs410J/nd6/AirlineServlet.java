package edu.pdx.cs410J.nd6;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class AirlineServlet extends HttpServlet {
    private final Map<String, String> data = new HashMap<String, String>();
    private final Map<String, Airline> airlineMap = new HashMap<String, Airline>();
    private Airline airline = null;

    @Override
    /**
     * get the content from the servlet
     * base on the matching keyword
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter( "name", request );
        String src = getParameter("src", request);
        String dest = getParameter("dest", request);



        if(airlineMap.get(name) == null){
            PrintWriter pw = response.getWriter();
            pw.println("The airline " + name + " doesn't exist");
            System.err.println("The airline " + name + " doesn't exist");
            return;
        }


        if(name != null && src == null && dest == null) {
//        writeAllMappings(response);
            writeAirline(name, response);
        }

        if(name != null && src != null && dest != null){
            writeSearch(name, src, dest, response);
        }
    }


    private String prettyPrintFlightsBetween(String source, String destination) {
        StringBuilder sb = new StringBuilder();
        sb.append("Flights between ").append(source).append(" and ").append(destination).append(":\n");
        this.airline.getFlights().stream()
                .filter(f -> f.getSource().equals(source) && f.getDestination().equals(destination))
                .forEach(flight -> sb.append("  ").append(flight).append("\n"));

        return sb.toString();
    }

    @Override
    /**
     * post new data to the servlet
     * it can post new airline to the servlet
     * and it can add more flights to the exist airline
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String flightNumber = getParameter("flightNumber", request);
        if (flightNumber == null){
            missingRequiredParameter(response, flightNumber);
            return;
        }


        String src = getParameter("src", request);
        if (src == null){
            missingRequiredParameter(response, src);
            return;
        }

        String departTime = getParameter("departTime", request);
        if (departTime == null){
            missingRequiredParameter(response, departTime);
            return;
        }

        String dest = getParameter("dest", request);
        if (dest == null){
            missingRequiredParameter(response, dest);
            return;
        }

        String arriveTime = getParameter("arriveTime", request);
        if (arriveTime == null){
            missingRequiredParameter(response, arriveTime);
            return;
        }

        Flight flight = new Flight(flightNumber, src, departTime, dest, arriveTime);

        String name = getParameter("name", request);
        if (name == null){
            missingRequiredParameter(response, name);
            return;
        }

        if (this.airlineMap.get(name) == null){

            Airline airline1 = new Airline(name);
            this.airline = airline1;
            this.airline.addFlight(flight);
            this.airlineMap.put(name, this.airline);
        }
        else{
            this.airline.addFlight(flight);
            this.airlineMap.put(name, this.airline);
        }

//        PrintWriter pw = response.getWriter();
//        pw.println(Messages.mappedKeyValue(flightNumber, name));
//        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }


    private void nonMatchingAirlineName(String airlineName, HttpServletResponse response) throws IOException {
        String message = "Airline not named " + airlineName;
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    private boolean createOrValidateAirlineWithName(String airlineName) {
        if (this.airline != null) {
            return this.airline.getName().equals(airlineName);

        } else {
            this.airline = new Airline(airlineName);
            return true;
        }
    }

    private void invalidFlightNumber(String numberAsString, HttpServletResponse response) throws IOException {
        String message = "Invalid flight number" + numberAsString;
        response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, message);
    }

    /**
     * Handles an HTTP DELETE request by removing all key/value pairs.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.airline = null;

        response.setStatus(HttpServletResponse.SC_OK);

    }


    /**
     * David's method
     * @param response
     * @param key
     * @throws IOException
     */
    private void missingRequiredParameter( HttpServletResponse response, String key )
            throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * David's method
     * @param key
     * @param response
     * @throws IOException
     */
    private void writeValue( String key, HttpServletResponse response ) throws IOException
    {
        String value = this.data.get(key);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        pw.println(Messages.formatKeyValuePair( key, value ));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * David's method
     * @param response
     * @throws IOException
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingAirlineCount( airlineMap.size() ));
//        pw.println(Messages.getMappingCount( data.size() ));

//        for (Map.Entry<String, String> entry : this.data.entrySet()) {
//            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue()));
//        }
        for (Map.Entry<String, Airline> entry : this.airlineMap.entrySet()) {
            pw.println(entry.getValue().print());
//            pw.println();
//            Airline airline2 = entry.getValue();
//            System.out.println(airline2.print());

        }
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * write all the content of flights of airline to the servlet
     * @param name : of the airline
     * @param response
     * @throws IOException
     */
    private void writeAirline(String name, HttpServletResponse response) throws IOException{
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingAirlineCount( airlineMap.size() ));

        Airline airline1 = this.airlineMap.get(name);
        pw.println(airline1.print());

        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * write the matching of flight of airline to the servlet
     * @param name
     * @param src
     * @param dest
     * @param response
     * @throws IOException
     */
    private void writeSearch(String name, String src, String dest, HttpServletResponse response) throws IOException{
        PrintWriter pw = response.getWriter();

        Airline airline1 = this.airlineMap.get(name);
        Collection flights = airline1.getFlights();
        boolean found = false;

        for(Object obj:flights){
            if(src.equals(((Flight)obj).getSource()) && dest.equals(((Flight)obj).getDestination())){
                pw.println(((Flight) obj).print());
                found = true;
            }
        }

        if(!found){
            pw.println("There is no flight from " + src + " to " + dest);
        }
    }


    /**
     * get the value of parameter from the client
     * @param name : of the airline
     * @param request
     * @return
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    Airline getAirline() {
        return airline;
    }
}