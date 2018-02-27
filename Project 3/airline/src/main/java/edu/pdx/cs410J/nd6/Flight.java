package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractFlight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.exit;

public class Flight extends AbstractFlight implements Comparable<Flight>{
    public int flightNumber;
    public String src;
    public String dest;
    public Date arriveTime;
    public Date departTime;
    private DateFormat ShortDateFormat;


    //default Flight constructor for values returning from Project3
    Flight(int flightNumber, String src, String departTime, String dest, String arriveTime){
        ShortDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
        try{
        this.flightNumber = Integer.parseInt(String.valueOf(flightNumber));
        this.src = src;
        this.departTime = ShortDateFormat.parse(departTime);
        this.dest = dest;
        this.arriveTime = ShortDateFormat.parse(arriveTime);
        }
        catch (ParseException e)
        {
            System.out.println("Error parsing the date and time,enter the time as per given format. mm/dd/yyyy hh:mm am/pm" + e.getMessage());
            exit(1);
        }
    }

    //to get flightNumber
    @Override
    public int getNumber() {
        return flightNumber;
    }

    //To get source airport name
    @Override
    public String getSource() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return src;
    }

    @Override
    public Date getDeparture() {
        return departTime;
    }

    @Override
    public Date getArrival() {
        return arriveTime;
    }

    //To get departure time and date
    @Override
    public String getDepartureString() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return (ShortDateFormat.format(departTime));
    }

    //To get destination airport name
    @Override
    public String getDestination() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return dest;
    }

    //To get arrival time and date
    @Override
    public String getArrivalString() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return (ShortDateFormat.format(arriveTime));
    }

    /**
     * This method is used to sort the airlines based on source in Alphabetical order
     * Departure times in Chronological order for flights departing from the same source
     * and to consider
     * Flights with same source and departure time to be equal
     * @param o of type Flight
     * @return 0 or 1
     *
     */
    @Override
    public int compareTo(Flight o) {

        if(this.src.compareToIgnoreCase(o.src)==0){
            return this.getDeparture().compareTo(o.getDeparture());
        }
        return this.src.compareToIgnoreCase(o.src);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + src.hashCode();
        result = 31 * result + departTime.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Flight){
            Flight f=(Flight)obj;
            if (this.getSource().equalsIgnoreCase(f.getSource()) && this.getDeparture().equals(f.getDeparture()))
                return true;
        }
        return false;
    }

}

