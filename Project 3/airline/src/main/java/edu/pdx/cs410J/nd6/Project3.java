package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J airline Project
 */
public class Project3 {
    public static int flightNumber;
    public static String name;
    public static String src;
    public static String dest;
    public static String arriveTime;
    public static String departTime;

    public static void main(String[] args) {
        String fileName;
        String fileName1;
        String fileName2;
        boolean flag = false;
        String Name;
        AbstractAirline airline ;
        List<String> ex1 = new ArrayList<>();
        List<String> ex2 = new ArrayList<>();
        AbstractFlight al ;
        TextDumper tdumper = new TextDumper();
        TextParser tparser = new TextParser();


        boolean a = false;
        try {

            for (String arg : args) {

                if (arg.contains("-README")) {
                    a = true;
                }
            }

            if (a == true)
            {
                System.out.println("\nREADME FILE - \n \nProject1 by Nikhitha Durvasula creates an airline for each flight with the respective flights list.\n" +
                        "usage: java edu.pdx.cs410J.nd6.Project3 [options] <args>\n" +
                        "args are (in this order):\n" +
                        "name                   The name of the airline\n" +
                        "flightNumber           The flight number\n" +
                        "src                    Three-letter code of departure airport\n" +
                        "departTime             Departure date and time (12-hour time)\n" +
                        "dest                   Three-letter code of arrival airport\n" +
                        "arriveTime             Arrival date and time (12-hour time)\n" +
                        "\noptions (options may appear in any order):\n" +
                        "-pretty file Pretty print the airline details to\n" +
                        "             a text file or standard out (file -)\n"+
                        "-print : Prints a description of the new flight\n" +
                        "-README : Prints a README for this project and exits\n" +
                        "-textFile file creates text file for airline\n"+
                        "Date and time should be in this format: mm/dd/yyyy hh:mm\n" +
                        "Description should not be empty");
                System.exit(1);
            } else {
                if((args.length == 12) &&  (!(args[1].startsWith("-")) && args[0].contentEquals("-pretty") )){

                    fileName = args[1];

                    checkAirlineName(args[2]);
                    checkAirportNames(args[4]);
                    checkDateTime(args[5] + " " + args[6]);
                    checkAmPm(args[7]);
                    checkAirportNames(args[8]);
                    checkDateTime(args[9] + " " + args[10] );
                    checkAmPm(args[11]);

                    try {
                        File file = new File(fileName);

                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[2];
                            tparser.getFileName(fileName, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[3]), args[4],args[5]+" " + args[6]+ " " +args[7], args[8],args[9] + " " + args[10] + " " +args [11]);
                            airline.addFlight(al);
                            //System.out.println(airline);
                            PrettyPrinter pp = new PrettyPrinter(file, flag );
                            pp.dump(airline);
                            //System.out.println(al);

                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            PrettyPrinter pp = new PrettyPrinter(file, flag );
                            airline = new Airline(args[2]);
                            al = new Flight(Integer.parseInt(args[3]), args[4],args[5]+" " + args[6]+ " " +args[7], args[8],args[9] + " " + args[10] + " " +args [11]);
                            airline.addFlight(al);
                            //System.out.println(airline);
                            pp.dump(airline);
                            //System.out.println(al);
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found");
                    }
                }

                else if((args.length == 12) &&  ((args[1].startsWith("-")) && args[0].contentEquals("-pretty") )){

                    fileName = args[1];

                    checkAirlineName(args[2]);
                    checkAirportNames(args[4]);
                    checkDateTime(args[5] + " " + args[6]);
                    checkAmPm(args[7]);
                    checkAirportNames(args[8]);
                    checkDateTime(args[9] + " " + args[10] );
                    checkAmPm(args[11]);

                    try {
                        File file = new File(fileName);

                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[2];
                            tparser.getFileName(fileName, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[3]), args[4],args[5]+" " + args[6]+ " " +args[7], args[8],args[9] + " " + args[10] + " " +args [11]);
                            airline.addFlight(al);
                            Date arrival = al.getArrival();
                            Date departure = al.getDeparture();
                            System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) );
                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            PrettyPrinter pp = new PrettyPrinter(file, flag );
                            airline = new Airline(args[2]);
                            al = new Flight(Integer.parseInt(args[3]), args[4],args[5]+" " + args[6]+ " " +args[7], args[8],args[9] + " " + args[10] + " " +args [11]);
                            airline.addFlight(al);
                            Date arrival = al.getArrival();
                            Date departure = al.getDeparture();
                            System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) );
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found");
                    }
                }

                else if ((args.length == 13) && (args[0].contentEquals("-textFile")) && !(args[1].startsWith("-") && (args[2].contentEquals("-print"))) ) {

                    fileName = args[1];
                    checkAirlineName(args[3]);
                    checkAirportNames(args[5]);
                    checkDateTime(args[6] + " " + args[7]);
                    checkAmPm(args[8]);
                    checkAirportNames(args[9]);
                    checkDateTime(args[10] + " " + args[11] );
                    checkAmPm(args[12]);
                    try {
                        File file = new File(fileName);

                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[3];
                            tparser.getFileName(fileName, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName);
                            tdumper.dump(airline);
                            System.out.println(al);

                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            airline = new Airline(args[3]);
                            al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName);
                            tdumper.dump(airline);
                            System.out.println(al);




                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found. ");
                    }
                }

                else if ((args.length == 13) && (args[0].contentEquals("-print")) && (args[1].contentEquals("-textFile") && !(args[2].startsWith("-"))) ) {

                    fileName = args[2];
                    checkAirlineName(args[3]);
                    checkAirportNames(args[5]);
                    checkDateTime(args[6] + " " + args[7]);
                    checkAmPm(args[8]);
                    checkAirportNames(args[9]);
                    checkDateTime(args[10] + " " + args[11] );
                    checkAmPm(args[12]);

                    try {
                        File file = new File(fileName);
                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[3];
                            tparser.getFileName(fileName, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName);
                            tdumper.dump(airline);
                            System.out.println(al);

                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            airline = new Airline(args[3]);
                            al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName);
                            tdumper.dump(airline);
                            System.out.println(al);
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found");
                    }
                }

                else if((args.length == 13) && args[0].startsWith("-")) {

                    int pindex = -1;

                    checkAirlineName(args[3]);
                    checkAirportNames(args[5]);
                    checkDateTime(args[6] + " " + args[7]);
                    checkAmPm(args[8]);
                    checkAirportNames(args[9]);
                    checkDateTime(args[10] + " " + args[11]);
                    checkAmPm(args[12]);

                    for (int i = 0; i < 3; i++) {
                        if (args[i].equals("-pretty")) {
                            pindex = i;
                            if (!(args[pindex + 1].startsWith("-")) && !(args[pindex + 1].contentEquals("-"))) {
                                fileName1 = args[pindex + 1];

                                try {
                                    File file1 = new File(fileName1);

                                    if (file1.exists()) {
                                        System.out.println("File exists");
                                        name = args[3];
                                        tparser.getFileName(fileName1, name);
                                        airline = tparser.parse();
                                        al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                                        airline.addFlight(al);
                                        System.out.println(airline);
                                        PrettyPrinter pp = new PrettyPrinter(file1, flag);
                                        pp.dump(airline);
                                    } else {
                                        file1.createNewFile();
                                        System.out.println("Created new file");
                                        PrettyPrinter pp = new PrettyPrinter(file1, flag);
                                        airline = new Airline(args[3]);
                                        al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                                        airline.addFlight(al);
                                        pp.dump(airline);
                                    }

                                } catch (FileNotFoundException filename) {
                                    System.out.println("file not found");
                                }

                            } else if( (args[pindex + 1].startsWith("-")) ){
                                flag = true;

                                try {
                                    System.out.println("Printing flight details");
                                    PrettyPrinter pp = new PrettyPrinter(flag);
                                    airline = new Airline(args[3]);
                                    al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                                    airline.addFlight(al);
                                    Date arrival = al.getArrival();
                                    Date departure = al.getDeparture();
                                    System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure));
                                } catch ( Exception e) {
                                    System.out.println("Cannot Pretty print");
                                }
                            }

                            else {
                                System.out.println("Please see readme file");
                            }
                        }

                        if((args[i].equals("-print"))){
                            airline = new Airline(args[3]);
                            al = new Flight(Integer.parseInt(args[4]), args[5],args[6]+" " + args[7]+ " " +args[8], args[9],args[10] + " " + args[11] + " " +args [12]);
                            airline.addFlight(al);
                            System.out.println(al);
                        }
                    }
                }

                else if((args.length == 14) && (args[0].contentEquals("-pretty")) && !(args[1].startsWith("-")) && !(args[1].endsWith("-")) &&(args[2].contentEquals("-textFile") ) && !(args[3].startsWith("-")) ){

                    fileName1 = args[1];
                    fileName2 = args[3];

                    checkAirlineName(args[4]);
                    checkAirportNames(args[6]);
                    checkDateTime(args[7] + " " + args[8]);
                    checkAmPm(args[9]);
                    checkAirportNames(args[10]);
                    checkDateTime(args[11] + " " + args[12] );
                    checkAmPm(args[13]);

                    try {
                        File file1 = new File(fileName1);
                        File file = new File(fileName2);
                        if (file.exists()) {
                            System.out.println("TextFile exists");
                            if(file1.exists())
                                System.out.println("Parser file exists");
                            else
                                System.out.println("Created new Parser file");
                            name = args[4];
                            tparser.getFileName(fileName2, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            //System.out.println(airline);
                            tdumper.getFileName(fileName2);
                            tdumper.dump(airline);

                            PrettyPrinter pp = new PrettyPrinter(file1, flag );
                            pp.dump(airline);

                        } else if (!file.exists()){
                            file.createNewFile();
                            System.out.println("Created new Textfile");
                            if(file1.exists())
                                System.out.println("Parser file exists");
                            else
                                System.out.println("Created new Parser file");
                            airline = new Airline(args[4]);
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            //System.out.println(airline);
                            tdumper.getFileName(fileName2);
                            tdumper.dump(airline);
                            PrettyPrinter pp = new PrettyPrinter(file1, flag );
                            pp.dump(airline);
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found. ");
                    }
                }

                else if((args.length == 14) && (args[0].contentEquals("-pretty")) && (args[1].startsWith("-")) &&(args[2].contentEquals("-textFile") ) && !(args[3].startsWith("-")) ){
                    fileName2 = args[3];
                    flag = true;

                    checkAirlineName(args[4]);
                    checkAirportNames(args[6]);
                    checkDateTime(args[7] + " " + args[8]);
                    checkAmPm(args[9]);
                    checkAirportNames(args[10]);
                    checkDateTime(args[11] + " " + args[12] );
                    checkAmPm(args[13]);

                    try {
                        File file = new File(fileName2);
                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[4];
                            tparser.getFileName(fileName2, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName2);
                            tdumper.dump(airline);
                            Date arrival = al.getArrival();
                            Date departure = al.getDeparture();
                            System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure));
                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            airline = new Airline(args[4]);
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName2);
                            tdumper.dump(airline);
                            Date arrival = al.getArrival();
                            Date departure = al.getDeparture();
                            System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure));
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found. ");
                    }
                }

                else if((args.length == 14) && (args[0].contentEquals("-textFile")) && !(args[1].startsWith("-")) && (args[2].contentEquals("-pretty") ) && !(args[3].startsWith("-")) && !(args[3].endsWith("-")) ){

                    fileName1 = args[1];
                    fileName2 = args[3];

                    checkAirlineName(args[4]);
                    checkAirportNames(args[6]);
                    checkDateTime(args[7] + " " + args[8]);
                    checkAmPm(args[9]);
                    checkAirportNames(args[10]);
                    checkDateTime(args[11] + " " + args[12] );
                    checkAmPm(args[13]);
                    try {
                        File file = new File(fileName1);
                        File file1 = new File(fileName2);
                        if (file.exists()) {
                            System.out.println("File exists");
                            if(file1.exists())
                                System.out.println("Parser file exists");
                            else
                                System.out.println("Created new Parser file");
                            name = args[4];
                            tparser.getFileName(fileName1, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName1);
                            tdumper.dump(airline);
                            PrettyPrinter pp = new PrettyPrinter(file1, flag );
                            pp.dump(airline);
                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            if(file1.exists())
                                System.out.println("Parser file exists");
                            else
                                System.out.println("Created new Parser file");
                            airline = new Airline(args[4]);
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName1);
                            tdumper.dump(airline);
                            PrettyPrinter pp = new PrettyPrinter(file1, flag );
                            pp.dump(airline);
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found");
                    }
                }

                else if((args.length == 14) && (args[0].contentEquals("-textFile")) && !(args[1].startsWith("-")) && (args[2].contentEquals("-pretty") )  && (args[3].startsWith("-")) ){

                    fileName1 = args[1];
                    flag = true;

                    checkAirlineName(args[4]);
                    checkAirportNames(args[6]);
                    checkDateTime(args[7] + " " + args[8]);
                    checkAmPm(args[9]);
                    checkAirportNames(args[10]);
                    checkDateTime(args[11] + " " + args[12] );
                    checkAmPm(args[13]);

                    try {
                        File file = new File(fileName1);
                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[4];
                            tparser.getFileName(fileName1, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName1);
                            tdumper.dump(airline);
                            Date arrival = al.getArrival();
                            Date departure = al.getDeparture();
                            System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) + "\n");

                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            airline = new Airline(args[4]);
                            al = new Flight(Integer.parseInt(args[5]), args[6],args[7]+" " + args[8]+ " " +args[9], args[10],args[11] + " " + args[12] + " " +args [13]);
                            airline.addFlight(al);
                            System.out.println(airline);
                            tdumper.getFileName(fileName1);
                            tdumper.dump(airline);
                            Date arrival = al.getArrival();
                            Date departure = al.getDeparture();
                            System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) + "\n");

                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found");
                    }
                }


                else if ((args.length == 15) && (args[0].startsWith("-"))) {
                    int pindex = -1;
                    int tindex = -1;

                    checkAirlineName(args[5]);
                    checkAirportNames(args[7]);
                    checkDateTime(args[8] + " " + args[9]);
                    checkAmPm(args[10]);
                    checkAirportNames(args[11]);
                    checkDateTime(args[12] + " " + args[13]);
                    checkAmPm(args[14]);

                    for (int i = 0; i < 5; i++) {

                        if (args[i].equals("-textFile")) {
                            tindex = i;
                            if (!(args[tindex + 1].startsWith("-"))) {
                                fileName1 = args[tindex + 1];
                                try {
                                    File file = new File(fileName1);

                                    if (file.exists()) {
                                        System.out.println("File exists");
                                        name = args[5];
                                        tparser.getFileName(fileName1, name);
                                        airline = tparser.parse();
                                        al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                                        airline.addFlight(al);
                                        System.out.println(airline);
                                        tdumper.getFileName(fileName1);
                                        tdumper.dump(airline);

                                    } else {
                                        file.createNewFile();
                                        System.out.println("Created new file");
                                        airline = new Airline(args[5]);
                                        al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                                        airline.addFlight(al);
                                        System.out.println(airline);
                                        tdumper.getFileName(fileName1);
                                        tdumper.dump(airline);
                                    }
                                } catch (FileNotFoundException filename) {
                                    System.out.println("file not found. ");
                                }
                            } else {
                                System.out.println("Refer Readme for the order of arguments");
                            }
                        }

                        if (args[i].equals("-pretty")) {
                            pindex = i;
                            if (!(args[pindex + 1].startsWith("-")) && !(args[pindex+1].endsWith("-"))) {
                                fileName1 = args[pindex + 1];
                                try {
                                    File file1 = new File(fileName1);
                                    if (file1.exists()) {
                                        System.out.println("File exists");
                                        name = args[5];
                                        tparser.getFileName(fileName1, name);
                                        airline = tparser.parse();
                                        al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                                        airline.addFlight(al);
                                        System.out.println(airline);
                                        PrettyPrinter pp = new PrettyPrinter(file1, flag);
                                        pp.dump(airline);

                                    } else {
                                        file1.createNewFile();
                                        System.out.println("Created new file");
                                        PrettyPrinter pp = new PrettyPrinter(file1, flag);
                                        airline = new Airline(args[5]);
                                        al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                                        airline.addFlight(al);
                                        pp.dump(airline);
                                    }

                                } catch (FileNotFoundException filename) {
                                    System.out.println("file not found");
                                }
                            }

                            else if ((args[pindex+1].startsWith("-"))) {
                                fileName1 = args[tindex + 1];
                                flag = true;

                                try {
                                    File file1 = new File(fileName1);

                                    if (file1.exists()) {
                                        System.out.println("File exists");
                                        name = args[5];
                                        tparser.getFileName(fileName1, name);
                                        airline = tparser.parse();
                                        al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                                        airline.addFlight(al);
                                        System.out.println(airline);
                                        PrettyPrinter pp = new PrettyPrinter(file1, flag);
                                        pp.dump(airline);
                                        Date arrival = al.getArrival();
                                        Date departure = al.getDeparture();
                                        System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure));

                                    } else {
                                        file1.createNewFile();
                                        System.out.println("Created new file");
                                        PrettyPrinter pp = new PrettyPrinter(file1, flag);
                                        airline = new Airline(args[5]);
                                        al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                                        airline.addFlight(al);
                                        pp.dump(airline);
                                        Date arrival = al.getArrival();
                                        Date departure = al.getDeparture();
                                        System.out.println(airline.getName() + "\t\t" + al.getNumber() + "\t\t" + AirportNames.getName(al.getSource()) + "\t\t" + al.getDepartureString() + "\t\t"+ AirportNames.getName(al.getDestination()) + "\t\t"+ al.getArrivalString() + "\t\t" + getTimeDiff(arrival,departure) );
                                    }
                                } catch (FileNotFoundException filename) {
                                    System.out.println("file not found");
                                }
                            }

                            else {
                                System.out.println("Please see readme file");
                            }
                        }
                        if(args[i].equals("-print"))
                        {
                            airline = new Airline(args[5]);
                            al = new Flight(Integer.parseInt(args[6]), args[7],args[8]+" " + args[9]+ " " +args[10], args[11],args[12] + " " + args[13] + " " +args [14]);
                            airline.addFlight(al);
                            System.out.println(airline.getName()+" "+al.getNumber()+" "+al.getSource()+" "+al.getDepartureString()+" "+al.getDestination()+" "+al.getArrivalString());
                        }
                    }
                }

                else if((args.length == 12)  && (args[0].contentEquals("-textFile")) && !(args[1].startsWith("-"))){

                    fileName = args[1];
                    checkAirlineName(args[2]);
                    Integer.parseInt(args[3]);
                    checkAirportNames(args[4]);
                    checkDateTime(args[5] + " " + args[6]);
                    checkAmPm(args[7]);
                    checkAirportNames(args[8]);
                    checkDateTime(args[9] + " " + args[10] );
                    checkAmPm(args[11]);

                    try {
                        File file = new File(fileName);
                        if (file.exists()) {
                            System.out.println("File exists");
                            name = args[2];
                            tparser.getFileName(fileName, name);
                            airline = tparser.parse();
                            al = new Flight(Integer.parseInt(args[3]), args[4],args[5]+" " + args[6]+ " " +args[7], args[8],args[9] + " " + args[10] + " " +args [11]);
                            airline.addFlight(al);
                            //System.out.println(airline);
                            tdumper.getFileName(fileName);
                            tdumper.dump(airline);

                        } else {
                            file.createNewFile();
                            System.out.println("Created new file");
                            airline = new Airline(args[2]);
                            name = args[4];
                            al = new Flight(Integer.parseInt(args[3]), args[4],args[5]+" " + args[6]+ " " +args[7], args[8],args[9] + " " + args[10] + " " +args [11]);
                            airline.addFlight(al);
                            //System.out.println(airline);
                            tdumper.getFileName(fileName);
                            tdumper.dump(airline);
                        }
                    } catch (FileNotFoundException filename) {
                        System.out.println("file not found");
                    }
                }

                else if((args.length == 11)  && (args[0].contentEquals("-print")) ){

                    checkAirlineName(args[1]);
                    checkAirportNames(args[3]);
                    checkDateTime(args[4] + " " + args[5]);
                    checkAmPm(args[6]);
                    checkAirportNames(args[7]);
                    checkDateTime(args[8] + " " + args[9]);
                    checkAmPm(args[10]);

                    airline = new Airline(args[1]);
                    al = new Flight(Integer.parseInt(args[2]), args[3],args[4]+" " + args[5]+ " " +args[6], args[7],args[8] + " " + args[9] + " " +args [10]);
                    System.out.println(al.toString());
                }
                else if((args.length==10 && !(args[0].startsWith("-")))){

                    checkAirlineName(args[0]);
                    checkAirportNames(args[2]);
                    checkDateTime(args[3] + " " + args[4]);
                    checkAmPm(args[5]);
                    checkAirportNames(args[6]);
                    checkDateTime(args[7] + " " + args[8]);
                    checkAmPm(args[9]);

                    airline = new Airline(args[0]);
                    al = new Flight(Integer.parseInt(args[1]), args[2],args[3]+" " + args[4]+ " " +args[5], args[6],args[7] + " " + args[8] + " " +args [9]);
                    airline.addFlight(al);
                    System.out.println(al.toString());
                    System.out.println(airline.toString());
                }
                else {
                    throw new Exception("Invalid number of arguments");
                }
            }

        }catch (ParseException e) {
            System.out.println("Invalid Date Format. The date and time format must be : mm/dd/yyyy hh:mm" + e.getMessage());
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Empty string in description")) {
                System.out.println(e.getMessage());
            } else {
                System.out.println("Invalid no of arguments. Please refer the README file and try again");
            }
        }
        System.exit(1);
    }

    /**
     * This method helps in validating the am/pm part of the date
     * @param ampm input
     * @return ampm valid if accurate
     * @throws Exception
     * throwa exception if the format is not as specified.
     */
    public static String checkAmPm(String ampm) throws Exception{
        if(ampm.equalsIgnoreCase("am") || ampm.equalsIgnoreCase("pm"))
        {

        }
        else {
            System.out.println("The date and time format must be : mm/dd/yyyy hh:mm am/pm");
            System.exit(1);
        }
        return ampm;
    }

    /**
     * This method checks the date and time format of the airline arrival and departure date and time
     * @param dateTime
     * takes dateTime as parameter
     * @return dateTime
     * returns dateTime if valid
     * @throws ParseException
     * throws exception if not valid
     */
    public static String checkDateTime(String dateTime)throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //To make strict date format validation
        formatter.setLenient(false);
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateTime);

        } catch (ParseException e) {
            //Handle exception
            System.out.println("The date and time format must be : mm/dd/yyyy hh:mm am/pm");
        }
        return dateTime;
    }

    public static String checkAirlineName(String airlineName){
        //to check if airline name consists of any special characters
        Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(airlineName);
        boolean b = m.find();
        if (b) {
            System.out.println("There should be no special characters in the name");
        }
        return airlineName;
    }

    public static String checkAirportNames(String airportName){
        if(AirportNames.getName(airportName)!=null) {
            int counter1 = 0;
            for (int i = 0; i < airportName.length(); i++) {
                if (Character.isLetter(airportName.charAt(i)))
                    counter1++;
            }
            if (counter1 != 3)
                System.out.println("Source airport should be given in 3 Alphabets only");
            return airportName;
        }
        else
            System.out.println("Please enter a valid Airport code!!!");
        System.exit(1);
        return airportName;
    }

    /**
     * This method is used to print the difference between the departure and arrival date and time in minutes
     * @param departure,arrival
     * takes the departure and arrival date and time of the flights
     * @return diff
     */
    public static String getTimeDiff(Date dateOne, Date dateTwo)
    {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format(" %d mins", ( TimeUnit.MILLISECONDS.toHours(timeDiff) * 60) + (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff))));
        return diff;
    }

}
