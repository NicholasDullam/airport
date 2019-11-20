import java.io.Serializable;

public class BoardingPass implements Serializable {
    private Passenger passenger;
    private Airline airline;

    public BoardingPass(Passenger passenger, Airline airline) {
        this.passenger = passenger;
        this.airline = airline;
    }

    public String toString() {
        return String.format("BoardingPass[%s, %s, %s, %d, %s]", this.airline.getName(), this.passenger.getFirstName(),
            this.passenger.getLastName(), this.passenger.getAge(), this.airline.getGate());
    }
}
