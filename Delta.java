/**
 * Project 5 - Airport Manager
 *
 * @author Nicholas Dullam ndullam
 * @author Michael Taylor taylo874
 * @version 11-20-19
 */

public class Delta extends Airline {
    public Delta() {
        super("Delta", 200);
    }

    public Delta(int capacity) {
        super("Delta", capacity);
    }

    public String getDescription() {
        return "<html><div style='text-align: center;'>Delta Airlines is proud to be one of the five premier " +
                "Airlines at Purdue University" +
                ". <br>We are extremely exceptional services, with free limited WiFi for all customers.<br>" +
                "Passengers who use T-Mobile as a cell phone carrier get additional benefits.<br> We are also" +
                "happy to offer power outlets in each seat for passenger use. We hope you choose to fly Delta" +
                "as your next Airline.</div></html>";
    }
}
