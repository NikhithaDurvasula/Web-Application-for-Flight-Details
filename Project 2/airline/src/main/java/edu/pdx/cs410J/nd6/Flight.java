package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {
    public int flightNumber;
    public String src;
    public String dest;
    public String arriveTime;
    public String departTime;

    //default Flight constructor and constructor for values returning from Project2
    Flight(int flightNumber, String src, String departTime, String dest, String arriveTime){
        this.flightNumber = Integer.parseInt(String.valueOf(flightNumber));
        this.src = src;
        this.departTime = departTime;
        this.dest = dest;
        this.arriveTime = arriveTime;
    }

//    Flight(int flightNumber, String src, String departTime, String dest, String arriveTime){
//        this.flightNumber = this.flightNumber;
//        this.src = this.src;
//        this.departTime = this.departTime;
//        this.dest = this.dest;
//        this.arriveTime = this.arriveTime;
//    }


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

    //To get departure time and date
    @Override
    public String getDepartureString() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return departTime;
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
        return arriveTime;
    }
}
