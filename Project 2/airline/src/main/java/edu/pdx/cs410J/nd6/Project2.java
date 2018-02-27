package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {
    public static int flightNumber;
    private static String name;
    public static String src;
    public static String dest;
    public static String arriveTime;
    public static String departTime;

    public static void main(String[] args) {

        String fileName;
        AbstractAirline<Flight> airline = null;
        AbstractAirline airline1 ;
        Flight al ;
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
                System.out.println("\nREADME FILE - PROJECT 2 - NIKHITHA DURVASULA\n" +
                        "This project is used to create airline including flight details entered through commandline.\n" +
                        "and optionally creating text file for that airline\n" +
                        "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
                        "args are (n this order):\n" +
                        "name                   The name of the airline\n" +
                        "flightNumber           The flight number\n" +
                        "src                    Three-letter code of departure airport\n" +
                        "departTime             Departure date and time (24-hour time)\n" +
                        "dest                   Three-letter code of arrival airport\n" +
                        "arriveTime             Arrival date and time (24-hour time)\n" +
                        "options are (options may appear in any order):\n" +
                        "-print Prints a description of the new flight\n" +
                        "-README Prints a README for this project and exits\n" +
                        "-textFile file creates text file for airline\n" +
                        "Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
                        "Description should not be empty\n" +
                        "If -print and flight details are specified it prints flight description");
                System.exit(1);
            } else {

                if ((args.length == 11) && (args[0].contentEquals("-textFile")) && !(args[1].startsWith("-")) && (args[2].contentEquals("-print")))
                {
                    fileName = args[1];

                    //to check the format of date and time
                    if(!args[6].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!args[9].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                        throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
                    if(!args[7].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!args[10].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                        throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");

                    //to check if airline name consists of any special characters
                    Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(args[3]);
                    boolean b = m.find();
                    if (b) {
                        System.out.println("There should be no special characters in the name");
                    }

                    //to check number of letters in the name of the source airport 
                    int counter1=0;
                    for (int i = 0; i < args[5].length(); i++)
                    {
                        if (Character.isLetter(args[5].charAt(i)))
                            counter1++;
                    }
                    if (counter1!=3)
                        System.out.println("Source airport should be given in 3 Alphabets only");

                    //to check number of letters in the name of the destination airport 
                    int counter2=0;
                    for (int i = 0; i < args[8].length(); i++)
                    {
                        if (Character.isLetter(args[8].charAt(i)))
                            counter2++;
                    }
                    if (counter2!=3)
                        System.out.println("Destination airport should be given in 3 Alphabets only");

                    if (true) {
                        try {
                            File file = new File(fileName);

                            if (file.exists()) {
                                System.out.println("File exists");
                                name = args[3];
                                tparser.getFileName(fileName, name);
                                airline = tparser.parse();
                                al = new Flight(Integer.parseInt(args[4]), args[5], args[6] + " " + args[7], args[8] ,args[9] + " " + args[10]);
                                airline.addFlight(al);
                                tdumper.getFileName(fileName);
                                tdumper.dump(airline);
                                System.out.println(al);

                            } else {
                                file.createNewFile();
                                System.out.println("Created new file");
                                airline = new Airline(args[3]);
                                //name = args[3];
                                al = new Flight(Integer.parseInt(args[4]), args[5], args[6] + " " + args[7], args[8] ,args[9] + " " + args[10]);
                                airline.addFlight(al);
                                System.out.println(airline);
                                tdumper.getFileName(fileName);
                                tdumper.dump(airline);
                                System.out.println(al);
                            }
                        } catch (FileNotFoundException filename) {
                            System.out.println("File not found");
                        } catch (ParserException e) {
                            System.out.println("Parsing Exception");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                    }

                }

                if ((args.length == 11) && (args[0].contentEquals("-print") && (args[1].contentEquals("-textFile")) && !(args[2].startsWith("-"))))
                {
                    fileName = args[2];

                    //to check the format of date and time
                    if(!args[6].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!args[9].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                        throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
                    if(!args[7].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!args[10].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                        throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");

                    //to check if airline name consists of any special characters
                    Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(args[3]);
                    boolean b = m.find();
                    if (b) {
                        System.out.println("There should be no special characters in the name");
                    }

                    //to check number of letters in the name of the source airport 
                    int counter1=0;
                    for (int i = 0; i < args[5].length(); i++)
                    {
                        if (Character.isLetter(args[5].charAt(i)))
                            counter1++;
                    }
                    if (counter1!=3)
                        System.out.println("Source airport should be given in 3 Alphabets only");

                    //to check number of letters in the name of the destination airport 
                    int counter2=0;
                    for (int i = 0; i < args[8].length(); i++)
                    {
                        if (Character.isLetter(args[8].charAt(i)))
                            counter2++;
                    }
                    if (counter2!=3)
                        System.out.println("Destination airport should be given in 3 Alphabets only");

                    if (true) {
                        try {
                            File file = new File(fileName);

                            if (file.exists()) {
                                System.out.println("File exists");
                                name = args[3];
                                tparser.getFileName(fileName, name);
                                airline = tparser.parse();
                                al = new Flight(Integer.parseInt(args[4]), args[5], args[6] + " " + args[7], args[8] ,args[9] + " " + args[10]);
                                airline.addFlight(al);
                                tdumper.getFileName(fileName);
                                tdumper.dump(airline);
                                System.out.println(al);

                            } else {
                                file.createNewFile();
                                System.out.println("Created new file");
                                airline = new Airline(args[3]);
                                //name = args[3];
                                al = new Flight(Integer.parseInt(args[4]), args[5], args[6] + " " + args[7], args[8] ,args[9] + " " + args[10]);
                                airline.addFlight(al);
                                System.out.println(airline);
                                tdumper.getFileName(fileName);
                                tdumper.dump(airline);
                                System.out.println(al);
                            }
                        } catch (FileNotFoundException filename) {
                            System.out.println("File not found");
                        } catch (ParserException e) {
                            System.out.println("Parsing Exception");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                    }

                }

                else if((args.length == 10)  && (args[0].contentEquals("-textFile")) && (!(args[1].startsWith("-")))) {


                    fileName = args[1];

                    //to check the format of date and time
                    if (!args[5].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)") || !args[8].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                        throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
                    if (!args[6].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || !args[9].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                        throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");

                    //to check if airline name consists of any special characters
                    Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(args[2]);
                    boolean b = m.find();
                    if (b) {
                        System.out.println("There should be no special characters in the name");
                    }

                    //to check number of letters in the name of the source airport 
                    int counter1 = 0;
                    for (int i = 0; i < args[4].length(); i++) {
                        if (Character.isLetter(args[4].charAt(i)))
                            counter1++;
                    }
                    if (counter1 != 3)
                        System.out.println("Source airport should be given in 3 Alphabets only");

                    //to check number of letters in the name of the destination airport 
                    int counter2 = 0;
                    for (int i = 0; i < args[7].length(); i++) {
                        if (Character.isLetter(args[7].charAt(i)))
                            counter2++;
                    }
                    if (counter2 != 3)
                        System.out.println("Destination airport should be given in 3 Alphabets only");

                    if (true) {
                        try {
                            File file = new File(fileName);

                            if (file.exists()) {
                                System.out.println("File exits");
                                name = args[2];
                                tparser.getFileName(fileName, name);
                                airline = tparser.parse();
                                al = new Flight(Integer.parseInt(args[3]), args[4], args[5] + " " + args[6], args[7], args[8] + " " + args[9]);
                                airline.addFlight(al);
                                tdumper.getFileName(fileName);
                                tdumper.dump(airline);

                            } else {
                                file.createNewFile();
                                System.out.println("Created new file");
                                airline = new Airline(args[2]);
                                airline.addFlight(new Flight(Integer.parseInt(args[3]), args[4], args[5] + " " + args[6], args[7], args[8] + " " + args[9]));
                                tdumper.getFileName(fileName);
                                tdumper.dump(airline);
                            }
                        } catch (FileNotFoundException filename) {
                            System.out.println("file not found");
                        } catch (ParserException e) {
                            System.out.println("Parsing Exception");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                    }
                }

                else if((args.length == 9)  && (args[0].equals("-print"))){

                    //to check the format of date and time
                    if(!args[4].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!args[7].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                        throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
                    if(!args[5].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!args[8].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                        throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");

                    //to check if airline name consists of any special characters
                    Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(args[1]);
                    boolean b = m.find();
                    if (b) {
                        System.out.println("There should be no special characters in the name");
                    }

                    //to check number of letters in the name of the source airport 
                    int counter1=0;
                    for (int i = 0; i < args[3].length(); i++)
                    {
                        if (Character.isLetter(args[3].charAt(i)))
                            counter1++;
                    }
                    if (counter1!=3)
                        System.out.println("Source airport should be given in 3 Alphabets only");

                    //to check number of letters in the name of the destination airport 
                    int counter2=0;
                    for (int i = 0; i < args[6].length(); i++)
                    {
                        if (Character.isLetter(args[6].charAt(i)))
                            counter2++;
                    }
                    if (counter2!=3)
                        System.out.println("Destination airport should be given in 3 Alphabets only");

                    if (true) {
                        airline = new Airline(args[1]);
                        al = new Flight(Integer.parseInt(args[2]), args[3], args[4] + " " + args[5], args[6] ,args[7] + " " + args[8]);
                        airline.addFlight(al);
                        if (args[0].contentEquals("-print")) {
                            System.out.println("Airline name : " + airline.getName());
                            System.out.println(al.toString());
                        }
                    }
                }

                else if(args.length==8 ){
                    for (String arg : args) {
                        System.out.println(arg);
                    }
                    System.out.println("No options given. Available options are -README and -print.");
                }

                else if(args.length<8)
                {
                    System.out.println("Not enough args... Please refer the README file and try again.");
                }
            }
        }  catch (IllegalArgumentException e) {
            System.out.println("Invalid Argument.\n" + e.getMessage());
            System.exit(1);
        }
        System.exit(1);
    }

}
