import java.io.Serializable;

/**
 *  Project 5 - Airport Manager
 *
 *  @author Nicholas Dullam ndullam
 *
 *  @version 11-20-19
 *
 */

public class BoardingPass implements Serializable {
    private Passenger passenger;
    private Airline airline;

    public BoardingPass(Passenger passenger, Airline airline) {
        this.passenger = passenger;
        this.airline = airline;
    }

    public BoardingPass(Airline airline) {
        this.airline = airline;
    }

    public BoardingPass() {

    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airline getAirline() {
        return airline;
    }

    public String getAirlineString() {
        if(this.getAirline() instanceof Alaska) {
            return "Alaska";
        }
        else if(this.getAirline() instanceof Delta) {
            return "Delta";
        }
        else if(this.getAirline() instanceof Southwest) {
            return "Southwest";
        }
        else {return null;}
    }

    public String toString() {
        return String.format("BoardingPass[%s, %s, %s, %s, %s]", this.airline.getName(), this.passenger.getFirstName(),
                this.passenger.getLastName(), this.passenger.getAge(), this.airline.getGate());
    }
}
