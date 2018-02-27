package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses data from text file and creates Airline
 */

public class TextParser implements AirlineParser{

    private String filename;
    private String name;
    public Airline airline;

/*
This function gets the arguments of filename and the ownername
 */
    void getFileName(String fname, String name) {
        this.filename = fname;
        this.name = name;
    }

    /*
    This method reads the contents of the created text file if the file exists and creates a new airline after validating the date format and description
     */
    @Override
    public AbstractAirline parse() throws ParserException {
        System.out.println("Reading the file and adding flight details.");
        try {
            File file= new File(filename);
            FileReader fileReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            if(file.exists()) {
                String name = reader.readLine();
                airline = new Airline(name);

                if (name.equalsIgnoreCase(name)) {
                    while ((line = reader.readLine()) != null)
                    {
                        String[] newVar = line.split(",");
                        String num = newVar[0];
                        String source = newVar[1];
                        String depart = newVar[2];
                        String [] dT = depart.split(" ");
                        String destination = newVar[3];
                        String arrive = newVar[4];
                        String [] aT = arrive.split(" ");

                        checkAirlineName(name);
                        checkAirportNames(source);
                        checkAirportNames(destination);
                        checkDateTime(dT[0] + " " + dT[1]);
                        checkAmPm(dT[2]);
                        checkDateTime(aT[0] + " " + aT[1]);
                        checkAmPm(aT[2]);

                        Flight al = new Flight(Integer.parseInt(num), source, depart, destination, arrive);
                        airline.addFlight(al);
                    }
                    reader.close();
                }else {
                    System.out.println(filename + " has been modified. The owner name doesn't match");
                    System.exit(1);
                }
            }else
            {
                System.out.println("File not created yet");
            }
        }catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            filename + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + filename + "'");

        } catch (ParseException e) {
            System.out.println("Text file has been modified. Invalid Date Format. The date and time format must be : mm/dd/yyyy hh:mm in text file");
        } catch (Exception e) {
            System.out.println("Text file has been modified.");
        }
        //  System.exit(1);
        return airline;
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

    /**
     * This method checks the format of the name of the airline
     * @param airlineName
     * takes name of the airline as parameter
     * @return airlineName
     * returns airlineName if valid
     * Prints error message if not valid
     */
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

    /**
     * This method checks the format of the name of the source and destination airport names
     * @param airportName
     * takes the source or destination aiport names as parameter
     * @return airportName
     * returns airlineName if valid
     * Prints error message if not valid
     */
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

}