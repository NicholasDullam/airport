/**
 *  Project 5 - Airport Manager
 *
 *  @author Nicholas Dullam ndullam
 *
 *  @version 11-20-19
 *
 */

public class Alaska extends Airline {
    public Alaska() {
        super("Alaska", 100);
    }

    public Alaska(int capacity) {
        super("Alaska", capacity);
    }

    public String getDescription() {
        return "<html><div style='text-align: center;'>Alaskan Airlines is proud to serve the strong " +
                "and <br>knowledgeable Boilermakers from Purdue University.<br>" +
                " We primarily fly westward, and often have stops in Alaska and " +
                "California.<br>We have first class amenities, even in coach class.<br>We provide fun snacks, " +
                "such as pretzels and goldfish.<br>We also have comfortable seats, and free WiFi.<br>We hope " +
                "you choose Alaska Airlines for your next itinerary!</div></html>";
    }
}
