package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.Iterator;

public class TextDumper implements AirlineDumper{
    private String filename;

    /*
    This function gets the filename from arguments from main
     */

    public void getFileName(String fname){
        this.filename = fname;

    }

    /*
        This method dumps the contents of the arguments into a file
         */
    @Override
    public void dump(AbstractAirline book) throws IOException {

        File file = new File(filename);
        FileWriter fw =new FileWriter(filename,false);
        try {
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            fw.write(book.getName());
            Iterator i = book.getFlights().iterator();

            while (i.hasNext()){

                Flight flight = (Flight)i.next();
                fw.write("\n");
                fw.write(flight.flightNumber+","+flight.src+","+flight.departTime+","+flight.dest+","+flight.arriveTime);
            }
            fw.write("\n");
            fw.flush();
            fw.close();

        } catch (IOException x) {
            System.err.println(x.getMessage());
            System.err.println("Please provide file name");
            System.exit(1);
        }
    }
}


