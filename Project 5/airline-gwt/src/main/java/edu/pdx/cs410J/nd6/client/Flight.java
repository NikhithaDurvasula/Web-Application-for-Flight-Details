package edu.pdx.cs410J.nd6.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Date;

public class Flight extends AbstractFlight implements Comparable<Flight>

        /**
         * In order for GWT to serialize this class (so that it can be sent between
         * the client and the server), it must have a zero-argument constructor.
         */
{
  private String number;
  private String source;
  private Date departure;
  private String depart;
  private String destination;
  private Date arrival;
  private String arrive;

  /**
   * Parameterized constructor taking below parameters to create a flight object
   * @param number flight nu,ber
   * @param source source of flight
   * @param depart departure time
   * @param destination  destination of flight
   * @param arrive arrival time of flight
   */
  public Flight(String number, String source, String depart, String destination, String arrive) {
    this.number = number;
    this.source = source;
    this.depart = depart;
    this.destination = destination;
    this.arrive = arrive;
  }


  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public Flight() {

  }

  /**
   * get number of the flight and make sure this is valid number
   */
  @Override
  public int getNumber() {
    int num = 0;
    try {
      num =  Integer.parseInt(number);
    } catch (NumberFormatException e) {
    }
    return num;
  }

  /**
   * returns source
   * @return source of flight
   */
  @Override
  public String getSource() {
    return source;
  }

  /**
   * return type date of departure
   */
  @Override
  public Date getDeparture() {
    DateTimeFormat dateTimeFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a") {};
    Date date = dateTimeFormat.parse(depart);
    return date;
  }

  /**
   * get the time and day of departure in string format
   * @return daparture time and date
   */
  public String getDepartureString() {
    return  depart;
  }

  /**
   * returns destination
   * @return destination of flight
   */
  public String getDestination() {
    return destination;
  }

  /**
   * returns arrival time and date as date object
   * @return arrival date
   */
  public Date getArrival() {
    DateTimeFormat dateTimeFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a") {};
    Date date = dateTimeFormat.parse(arrive);
    return date;
  }

  /**
   * Date and time as string
   * @return arrival date string
   */
  public String getArrivalString() {
    return arrive;
  }

  /**
   *  returns duration time of flight
   * @return duration time
   */
  public long durationTime(){
    long different = (this.getArrival().getTime() - this.getDeparture().getTime())/6000;
    return different;
  }

    /**
     * @param flight is to compare the instance of calling flight to all flights in the class.
     * It is called implicitly when the set gets elements inserted in it
     * */
    @Override
    public int compareTo(Flight flight) {
        int difference;
        difference = this.getSource().compareTo(flight.getSource());

        if(difference == 0){
            difference = this.getDepartureString().compareTo(flight.getDepartureString());
        }

        return difference;

    }

    /**
     * Saying whether the object is an instance of the Flight class or not
     * @return boolean
     */
    public boolean equals(Object o){
        if(o instanceof Flight){
            return true;
        }
        return false;
    }

    /**
     * Returns departure time hashed
     * @return int with the hashcode
     */
    public int hashCode(){
        return this.getDeparture().hashCode();
    }

}
