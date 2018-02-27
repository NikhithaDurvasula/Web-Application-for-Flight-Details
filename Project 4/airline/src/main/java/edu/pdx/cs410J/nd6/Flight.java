package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractFlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight extends AbstractFlight {
    private String number;
    private String source;
    private Date departure;
    private String depart;
    private String destination;
    private Date arrival;
    private String arrive;

    public Flight(String number, String source, Date departure, String destination, Date arrival) {
        this.number = number;
        this.source = source;
        this.departure = departure;
        this.destination = destination;
        this.arrival = arrival;
    }

    public Flight(String number, String source, String departure, String destination, String arrival) {
        this.number = number;
        this.source = source;
        this.depart = departure;
        this.destination = destination;
        this.arrive = arrival;
    }

    /**
     * get number of the flight and make sure this is valid number
     */
    public int getNumber() {
        int num = 0;
        try {
            num =  Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.err.println("Flight number isn't an integer");
            System.exit(1);
        }
        return num;
    }

    /**
     *  get the three letter code of departure airline
     */
    public String getSource() {
        if(source.length() != 3){
            System.err.println("Invalid departure airport");
            System.exit(1);
        }
        return source;
    }

    /**
     *  get the time and day of departure
     */
    public String getDepartureString() {
//        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(departure);
        return depart;
    }

    /**
     *  get 3 letter code of destination airport
     */
    public String getDestination() {
        if (destination.length() != 3){
            System.err.println("Invalid arrival airport");
            System.exit(1);
        }
        return destination;
    }

    /**
     *  get arrival's day and time
     */
    public String getArrivalString() {
//        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(arrival);
        return arrive;
    }

    /**
     * return type date of departure
     */
    public Date getDeparture() {
        return getDeparture();
    }

    /**
     * return type date of arrival
     */
    public Date getArrival() {
        return getArrival();
    }

    /**
     *
     */
    public int compareTo(Flight o) {
        if(this.getSource().compareTo(o.getSource()) != 0){
            return this.getSource().compareTo(o.getSource());
        }
        else{
            return this.departure.toString().compareTo(o.departure.toString());
//            return this.getDepartureString().compareTo(o.getDepartureString());
        }
    }

    public long durationTime(){
        Date arrival1 = dateAndTimeFormat(arrive);
        Date depature1 = dateAndTimeFormat(depart);

        long different = (arrival1.getTime() - depature1.getTime())/6000;
        return different;
    }

    public static Date dateAndTimeFormat(String date){
        Date date1 = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm a");
        try{
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {

            System.err.println("date and time are malformed " + date);
            System.exit(1);
        }
        return date1;
    }

    public String print(){
        return this.toString() +  ". And it takes " + this.durationTime() + " in minutes.\n";
    }

}


