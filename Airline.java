import java.io.Serializable;

public abstract class Airline implements Serializable {

    private String name;
    private Gate gate;
    private int flightNumber;
    private int capacity;
    private Passenger[] passengers;

    public Airline(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.flightNumber = 18000;
        this.passengers = new Passenger[capacity];
        this.gate = new Gate(this);
    }

    public String getName() {
        return this.name;
    }

    public Gate getGate() {
        return this.gate;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public void addPassenger(Passenger passenger) {
        for (int i = 0; i < this.passengers.length; i++) {
            if (this.passengers[i] == null) {
                this.passengers[i] = passenger;
                break;
            }
        }
    }
}
