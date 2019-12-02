/**
 *  Project 5 - Airport Manager
 *
 *  @author Nicholas Dullam ndullam
 *
 *  @version 11-20-19
 *
 */

public class Southwest extends Airline {
    public Southwest() {
        super("Southwest", 100);
    }

    public Southwest(int capacity) {
        super("Southwest", capacity);
    }

    public String getDescription() {
        return "<html><div style='text-align: center;'>Southwest Airlines is proud to offer flights to Purdue University.<br> We are happy " +
                "to offer free in flight WiFi, as well as our amazing snacks.<br> In addition, we offer flights" +
                "for much cheaper than other airlines, and offer two free checked bags.<br>We hope you choose " +
                "Southwest for your next flight.</div></html>";
    }
}
