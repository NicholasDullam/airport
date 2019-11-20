import java.io.Serializable;

/**
 *  Project 5 - Airport Manager
 *
 *  @author Nicholas Dullam ndullam
 *
 *  @version 11-20-19
 *
 */

public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private BoardingPass boardingPass;
    private int age;

    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public void setBoardingPass(Airline airline) {
        this.boardingPass = new BoardingPass(this, airline);
    }
}