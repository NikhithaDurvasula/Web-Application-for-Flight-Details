package edu.pdx.cs410J.nd6;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class parses data from text file and creates Airline
 */

public class TextParser implements AirlineParser{

    private String filename;
    private String name;
    //List<String> list = new ArrayList<String>();
    public Airline airline;

/*
This function gets the arguments of filename and the ownername
 */

    void getFileName(String fname, String name) {

        this.filename = fname;
        this.name = name;

    }

    /*
    This method reads the contents of the created text file if the file exists and creates a new appoinment book after validating the date format and description
     */
    @Override
    public AbstractAirline parse() throws ParserException {
        System.out.println("Reading the file and adding appointments.");
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

                        //For checking date and time format
                        if(!dT[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!aT[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                            throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
                        if(!dT[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!aT[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                            throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");

                        //to check if airline name consists of any special characters
                        Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(name);
                        boolean b = m.find();
                        if (b) {
                            System.out.println("There should be no special characters in the name");
                        }

                        //to check number of letters in the name of the source airport 
                        int counter1=0;
                        for (int i = 0; i < source.length(); i++)
                        {
                            if (Character.isLetter(source.charAt(i)))
                                counter1++;
                        }
                        if (counter1!=3)
                            System.out.println("Source airport should be given in 3 Alphabets only");

                        //to check number of letters in the name of the destination airport 
                        int counter2=0;
                        for (int i = 0; i < destination.length(); i++)
                        {
                            if (Character.isLetter(destination.charAt(i)))
                                counter2++;
                        }
                        if (counter2!=3)
                            System.out.println("Destination airport should be given in 3 Alphabets only");

                        checkNullDescription(num);
                        Flight al = new Flight(Integer.parseInt(num), source, depart, destination, arrive);
                        airline.addFlight(al);
                    }

                    reader.close();

                }else {
                    System.out.println(filename + " has been modified. The owner name doesn't match");
                    System.exit(1);
                }  // return null;
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
            System.out.println("Text file has been modified. Invalid description in text file");
        }
        //  System.exit(1);
        return airline;

    }

    /**
     * This method checks whether the appointment has description or not
     * @param description
     * takes the parameter as description
     * @return true
     * return true
     * @throws Exception
     * throws exception if there is no description
     */
    private static boolean checkNullDescription(String description) throws Exception {
        if(!description.trim().isEmpty()){
            return true;
        }else{
            throw new Exception("Empty string in text file description");

        }
    }



}