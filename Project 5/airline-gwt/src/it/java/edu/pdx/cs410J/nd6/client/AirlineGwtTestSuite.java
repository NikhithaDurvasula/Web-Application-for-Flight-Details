package edu.pdx.cs410J.nd6.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import org.junit.Test;

public class AirlineGwtTestSuite {
  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite("Airline GWT Integration Tests");

    suite.addTestSuite(AirlineGwtIT.class);

    return (Test) suite;
  }

}
