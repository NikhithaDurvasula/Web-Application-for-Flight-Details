package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements AirlineDumper{
    File fileName;
    boolean flag = false;

    /**
     * This takes in filename and flag as args
     * @param fileName name of the file
     * @param flag flag value
     *             assigns values
     */
    PrettyPrinter(File fileName, boolean flag){
        this.flag = flag;
        this.fileName = fileName;
    }

    /**
     * This just takes flag value
     * @param flag flag value
     *             returns flag value
     */
    PrettyPrinter(boolean flag){

        this.flag = flag;
    }


    /**
     * This method is used to print the difference between the departure and arrival date and time in minutes
     * @return diff
     */
    public String getTimeDiff(Date dateOne, Date dateTwo)
    {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format(" %d mins", (TimeUnit.MILLISECONDS.toHours(timeDiff) * 60) + (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff))));
        return diff;
    }

    /**
     * The dump function is used to put all the information in a format
     * @param airline of type AbstractAirline
     * @throws IOException
     * Throws an exception if file is not found
     */
    @Override
    public void dump(AbstractAirline airline) throws IOException {
        if(airline == null)
        {
            airline = new Airline("",new Flight(Integer.parseInt(""),"","","",""));
        }

        if (flag){
            airline.getFlights().toString();
            for (Object o : airline.getFlights())
            {
                Flight al = (Flight) o;
                Date arrival = al.getArrival();
                Date departure = al.getDeparture();
                System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) + "\n");
            }
        }

        else {
            SortedSet<Flight> flights= new TreeSet<>(airline.getFlights());

            System.out.println("Flight details are added to airline's file. File updated with the flight details");
            PrintWriter pw;
            if (fileName!=null){
                pw=new PrintWriter(new FileWriter(fileName));
            }
            else {
                pw=new PrintWriter(System.out);
            }

            try {
                pw.write(airline.getName());
                Iterator it = flights.iterator();
                int i=0;
                pw.append("\n");
                while (it.hasNext()){
                    Flight al = (Flight)it.next();
                    Date arrival = al.getArrival();
                    Date departure = al.getDeparture();
                    pw.write(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) + "\n" );
                }
                pw.flush();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

