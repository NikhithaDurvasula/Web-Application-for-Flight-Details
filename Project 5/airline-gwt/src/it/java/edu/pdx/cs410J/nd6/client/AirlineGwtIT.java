package edu.pdx.cs410J.nd6.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * An integration test for the airline GWT UI.  Remember that GWTTestCase is JUnit 3 style.
 * So, test methods names must begin with "test".
 * And since this test code is compiled into JavaScript, you can't use hamcrest matchers.  :(
 */
public class AirlineGwtIT extends GWTTestCase {
  @Override
  public String getModuleName() {
    return "edu.pdx.cs410J.nd6.AirlineIntegrationTests";
  }
//
//  @Test
//  public void testClickingShowAirlineButtonAlertsWithAirlineInformation() {
//    final CapturingAlerter alerter = new CapturingAlerter();
//
//    final AirlineGwt ui = new AirlineGwt(alerter);
//    ui.onModuleLoad();
//
//    // Wait for UI widgets to be created
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        click(ui.showAirlineButton);
//      }
//    });
//
//    // Wait for the RPC call to return
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        String message = alerter.getMessage();
//        assertNotNull(message);
//        assertTrue(message, message.contains("Air CS410J with 1 flights"));
//        finishTest();
//      }
//    });
//
//    delayTestFinish(1000);
//  }
//
//  @Test
//  public void testClickingShowUndeclaredExceptionButtonAlertsWithExpectedMessage() {
//    final CapturingAlerter alerter = new CapturingAlerter();
//
//    final AirlineGwt ui = new AirlineGwt(alerter);
//    ui.onModuleLoad();
//
//    // Wait for UI widgets to be created
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        click(ui.showUndeclaredExceptionButton);
//      }
//    });
//
//    // Wait for the RPC call to return
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        String message = alerter.getMessage();
//        assertNotNull(message);
//        assertTrue(message, message.contains("StatusCodeException: 500 Server Error"));
//        finishTest();
//      }
//    });
//
//    // Wait up to 1000 milliseconds for the validation to complete
//    delayTestFinish(1000);
//  }
//
//  @Test
//  public void testClickingShowDeclaredExceptionButtonAlertsWithExpectedMessage() {
//    final CapturingAlerter alerter = new CapturingAlerter();
//
//    final AirlineGwt ui = new AirlineGwt(alerter);
//    ui.onModuleLoad();
//
//    // Wait for UI widgets to be created
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        click(ui.showDeclaredExceptionButton);
//      }
//    });
//
//    // Wait for the RPC call to return
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        String message = alerter.getMessage();
//        assertNotNull(message);
//        assertTrue(message, message.contains("IllegalStateException: Expected declared exception"));
//        finishTest();
//      }
//    });
//
//    // Wait up to 1000 milliseconds for the validation to complete
//    delayTestFinish(1000);
//  }
//
//  @Test
//  public void testClickingShowClientSideExceptionButtonAlertsWithExpectedMessage() {
//    final CapturingAlerter alerter = new CapturingAlerter();
//
//    final AirlineGwt ui = new AirlineGwt(alerter);
//    ui.onModuleLoad();
//
//    // Wait for UI widgets to be created
//    waitBeforeRunning(500, new Runnable() {
//      @Override
//      public void run() {
//        try {
//          click(ui.showClientSideExceptionButton);
//          fail("Should have thrown an UmbrellaException");
//
//        } catch (UmbrellaException ex) {
//          Throwable cause = ex.getCause();
//          assertTrue(cause instanceof IllegalStateException);
//          IllegalStateException ise = (IllegalStateException) cause;
//          assertTrue(ise.getMessage().contains("Expected exception on the client side"));
//          finishTest();
//        }
//      }
//    });
//
//    // Wait up to 1000 milliseconds for the validation to complete
//    delayTestFinish(1000);
//  }
//
//  private void waitBeforeRunning(int delayMillis, final Runnable operation) {
//    Timer click = new Timer() {
//      @Override
//      public void run() {
//        operation.run();
//      }
//    };
//    click.schedule(delayMillis);
//  }
//
//  /**
//   * Clicks a <code>Button</code>
//   *
//   * One would think that you could testing clicking a button with Button.click(), but it looks
//   * like you need to fire the native event instead.  Lame.
//   *
//   * @param button
//   *        The button to click
//   */
//  private void click(final Button button) {
//    assertNotNull("Button is null", button);
//    NativeEvent event = Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
//    DomEvent.fireNativeEvent(event, button);
//  }
//
//  private class CapturingAlerter implements AirlineGwt.Alerter {
//    private String message;
//
//    @Override
//    public void alert(String message) {
//      this.message = message;
//    }
//
//    public String getMessage() {
//      return message;
//    }
//  }
}