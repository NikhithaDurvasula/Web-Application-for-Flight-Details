package edu.pdx.cs410J.nd6.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import javafx.collections.transformation.SortedList;

import java.util.*;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {


  @Override
  public  void onModuleLoad() {
    final FlexTable airlineinfotable = new FlexTable();
    airlineinfotable.setText(0, 0, "Airline Name ");
    airlineinfotable.setText(0, 1, "Flight Number ");
    airlineinfotable.setText(0, 2, "Source Airport Code ");
    airlineinfotable.setText(0, 3, "Departure Date & Time ");
    airlineinfotable.setText(0, 4, "Destination Airport Code ");
    airlineinfotable.setText(0, 5, "Arrival Date & Time ");
    airlineinfotable.setText(0, 6, "Flight Duration in Minutes ");

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(new Label("Airline Name"));
    final TextBox airlineName = new TextBox();
    verticalPanel.add(airlineName);

    verticalPanel.add(new Label("Flight Number"));
    final TextBox flightNumber = new TextBox();
    verticalPanel.add(flightNumber);

    verticalPanel.add(new Label("Source Airport Code"));
    final TextBox source = new TextBox();
    verticalPanel.add(source);

    verticalPanel.add(new Label("Departure Date & Time"));
    final TextBox depart = new TextBox();
    verticalPanel.add(depart);

    verticalPanel.add(new Label("Destination Airport Code"));
    final TextBox dest = new TextBox();
    verticalPanel.add(dest);

    verticalPanel.add(new Label("Arrival Date & Time"));
    final TextBox arrive = new TextBox();
    verticalPanel.add(arrive);

    Button addFlight = new Button("Add a new Flight");
    addFlight.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        AirlineServiceAsync async = GWT.create(AirlineService.class);
        final String airline = airlineName.getText();
        final String flightnumber = flightNumber.getText();
        if(validateFlightNumebr(flightnumber)){
        }else{
          throw new IllegalArgumentException("Please enter valid flightNumber");
        }
        final String src = source.getText().toUpperCase();
        validatesourceandDestination(src);
        final String departturedatetime = depart.getText();
        validateDate(departturedatetime, "departTime");
        final String destination=dest.getText().toUpperCase();
        validatesourceandDestination(destination);
        final String arrivaldatetime=arrive.getText();
        validateDate(arrivaldatetime,"arriveTime");

        async.addFlight(airline, flightnumber, src, departturedatetime, destination, arrivaldatetime, new AsyncCallback<AbstractAirline>() {
          @Override
          public void onFailure(Throwable caught) {
            Window.alert(caught.toString());
          }

          @Override
          public void onSuccess(AbstractAirline result) {
            airlineinfotable.removeAllRows();
            airlineinfotable.setText(0, 0, "Airline Name ");
            airlineinfotable.setText(0, 1, "Flight Number ");
            airlineinfotable.setText(0, 2, "Source Airport Code ");
            airlineinfotable.setText(0, 3, "Departure Date & Time ");
            airlineinfotable.setText(0, 4, "Destination Airport Code ");
            airlineinfotable.setText(0, 5, "Arrival Date & Time ");
            airlineinfotable.setText(0, 6, "Flight Duration in Minutes ");
            SortedSet<Flight> flightslist=new TreeSet<>();
            Collection flights = result.getFlights();
            flightslist.addAll(flights);
            
            for(AbstractFlight flight:flightslist){
              int row = airlineinfotable.getRowCount() + 1;
              Flight temp = (Flight)flight;
              airlineinfotable.setText(row, 0, result.getName());
              airlineinfotable.setText(row, 1, Integer.toString(flight.getNumber()));
              airlineinfotable.setText(row, 2, flight.getSource());
              airlineinfotable.setText(row, 3, String.valueOf(flight.getDepartureString()));
              airlineinfotable.setText(row, 4, flight.getDestination());
              airlineinfotable.setText(row, 5, String.valueOf(flight.getArrivalString()));
              airlineinfotable.setText(row, 6, String.valueOf(temp.durationTime()));
            }
          }
        });
      }
    });
    verticalPanel.add(addFlight);
    VerticalPanel verticalPanel1 = new VerticalPanel();
    verticalPanel1.add(new Label("Airline Name"));
    final TextBox seachAirlineName = new TextBox();
    verticalPanel1.add(seachAirlineName);
    verticalPanel1.add(new Label("Source Airport Code"));
    final TextBox searchSrc = new TextBox();
    verticalPanel1.add(searchSrc);
    verticalPanel1.add(new Label("Destination Airport Code"));
    final TextBox searchDest = new TextBox();
    verticalPanel1.add(searchDest);
    Button search = new Button("Search");
    search.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        AirlineServiceAsync async = GWT.create(AirlineService.class);
        String name = seachAirlineName.getText();
        String src1 = searchSrc.getText().toUpperCase();
        if (src1== null) {
          Window.alert("Source "+ src1 + " is not valid. Please enter a valid airport code...");
          return;
        }
        String dest1 = searchDest.getText().toUpperCase();
        if (dest1== null) {
          Window.alert("Destination "+ dest1 + " is not valid. Please enter a valid airport code...");
          return;
        }
        async.searchFlight(name, src1, dest1, new AsyncCallback<AbstractAirline>() {
          @Override
          public void onFailure(Throwable caught) {
            Window.alert(caught.toString());
          }

            @Override
          public void onSuccess(AbstractAirline result) {
            if(result == null){
              Window.alert("No flight exist in server");
            }
            else {
              if (result.getFlights().size() == 0) {
                Window.alert("No flight exist in server");
              } else {
                StringBuilder sb = new StringBuilder(result.toString());
                List<Flight> flightslist=new ArrayList<Flight>();
                Collection flights = result.getFlights();
                flightslist.addAll(flights);
                Collections.sort(flightslist, new Comparator<Flight>(){
                  @Override
                  public int compare(Flight o1, Flight o2) {
                    if(o1.getSource().compareTo(o2.getSource()) != 0){
                      return o1.getSource().compareTo(o2.getSource());
                    }
                    else if(o1.getSource().compareTo(o2.getSource()) == 0 && o1.getDepartureString().compareTo(o2.getDepartureString())==0)
                    {
                      return 0;
                    }
                    else
                      return o1.getDepartureString().compareTo(o2.getDepartureString());
                  }
                  });

                for (AbstractFlight flight : flightslist) {
                  sb.append("\n"+flight);
                  sb.append("\n");
                }
                Window.alert(sb.toString());
              }
            }
          }
        });
      }
    });
    verticalPanel1.add(search);
    Button readMe = new Button("READ ME");
    readMe.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        Window.setTitle("Read Me");
        Window.alert(Readme());
      }
    });
    DecoratorPanel decoratorPanelForAddFlight = new DecoratorPanel();
    decoratorPanelForAddFlight.add(verticalPanel);
    DecoratorPanel decoratorPanel1 = new DecoratorPanel();
    decoratorPanel1.add(verticalPanel1);
    DecoratorPanel decoratorPanel2 = new DecoratorPanel();
    decoratorPanel2.add(airlineinfotable);

    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(readMe, 605, 20);
    rootPanel.add(decoratorPanelForAddFlight, 300, Window.getClientHeight()/10);
    rootPanel.add(decoratorPanel1, 800 , Window.getClientHeight()/10);
    rootPanel.add(decoratorPanel2, 150, 350);
  }

  public String Readme () {
    StringBuffer sb=new StringBuffer();
    sb.append("Readme about the CS410J Project5\n");
    sb.append("Name : Nikhitha Durvasula             \n");
    sb.append("Description : To design an Airline Application  \n");
    sb.append("Project5 of this application is a Rich Internet Application which stores the details of the flight on server.\n");
    sb.append("The project will create airline and flight if user enter \n" +
            " Airline Name:                    the name of the airline \n" +
            " Flight Number:                  the flight number\n" +
            " Source Airport Code:          three-letter code of departure airport\n" +
            " Departure Date & Time:       departure date and time (24-hour time)\n" +
            " Destination Airport Code:    three-letter code of arrival airport\n" +
            " Arrival Date & Time:            arrival date and time (24-hour time)\n" +
            "and click on add flight button\n" +
            "the project will search the flight for user if they enter\n" +
            " Airline Name:                    the name of the airline \n" +
            " Source Airport Code:          three-letter code of departure airport\n" +
            " Destination Airport Code:    three-letter code of arrival airport\n" +
            "and click on search button");
    return sb.toString();
  }

  /**
   * This method validate the Date input arguments in the mm/dd/yyyy format.
   * it returns true if the format is correct otherwise return false if the format is not correct.
   *
   * @param date
   * @return true
   */
  public Boolean validateDate (String date, String time){
    String dateformat = "MM/dd/yyyy hh:mm a";
    // Boolean isDate=false;
    char[] val = date.toCharArray();
    for (int i = 6; i < 10; i++) {
      if (Character.isDigit(val[i])) {
      } else {
        Window.alert("Please provide date and time in correct format. MM/dd/yyyy HH:mm a");
        throw new IllegalArgumentException("Please provide date and time in correct format. MM/dd/yyyy HH:mm a");
      }
    }

    if(val.length==18) {
        if (Integer.parseInt(String.valueOf(val[11])) < 13) {
        } else {
            Window.alert("Please provide time in a valid format... MM/dd/yyyy HH:mm a");
            throw new IllegalArgumentException("Please provide time in a valid format... MM/dd/yyyy HH:mm a");
        }
    }
    if(val.length==19) {
        int i1 = Integer.parseInt(String.valueOf(val[11]));
        int i2 = Integer.parseInt(String.valueOf(val[12]));
        if (!(i1 == 0)) {
            int i3 = i1 + i2;
            if (i3 < 4) {
            } else {
                Window.alert("Please provide time in a valid format... MM/dd/yyyy HH:mm a");
                throw new IllegalArgumentException("Please provide time in a valid format... MM/dd/yyyy HH:mm a");
            }
        } else if (i2 < 13) {

        } else {
            Window.alert("Please provide time in a valid format... MM/dd/yyyy HH:mm a");
            throw new IllegalArgumentException("Please provide time in a valid format... MM/dd/yyyy HH:mm a");
        }
    }
    
    if (date == null) {
      if (time.equalsIgnoreCase("departTime")) {

        Window.alert("Please provide " + time + " as command line argument");
        throw new IllegalArgumentException("Please provide " + time + " as command line argument");
      } else if (time.equalsIgnoreCase("arriveTime")) {
        Window.alert("Please provide " + time + " as command line argument");
        throw new IllegalArgumentException("Please provide " + time + " as command line argument");
      }
    }
    DateTimeFormat sdf;
    sdf = DateTimeFormat.getFormat(dateformat);
    //   sdf.setLenient(false);
    try {
      Date d = sdf.parse(date);
      //  System.out.println(d);
    } catch (Exception e) {

      Window.alert("Date and time is malformatted .please enter the correct date and time");
      throw new IllegalArgumentException(e);
      // return false;
    }
    return true;
  }

  public  Boolean validatesourceandDestination(String str) {
    if (str != null && str.length() == 3) {
      char[] ch = str.toCharArray();
      for (char c : ch) {
        if (Character.isLetter(c)) {
        } else {
          Window.alert("Please enter only letter as an argument for source and destination");
          throw new IllegalArgumentException("Please enter only letter as an argument for source and destination");
        }
      }
    } else {
      Window.alert("Please enter 3 letter arguments for source and destination");
      throw new IllegalArgumentException("Please enter 3 letter arguments for source and destination");
    }
    return true;
  }

  public  Boolean validateFlightNumebr(String str) {
    char[] ch = str.toCharArray();
    if (str != null) {
      for (char c : ch) {
        if (Character.isDigit(c)) {
        } else {
          Window.alert("Please enter only digit for flightNumber");
          throw new IllegalArgumentException("Please enter only digit for flightNumber");
        }
      }
    } else {
      return false;
    }
    return true;
  }
}