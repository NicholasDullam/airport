import java.io.Serializable;
import java.util.Random;

public class Gate implements Serializable {

    String[] terminals = {"A", "B", "C"};
    String terminal;
    int terminalGate;

    public Gate(Airline airline) {
        Random rand = new Random();

        switch(airline.getName()) {
            case "Alaska" :
                this.terminal = terminals[0];
                break;
            case "Delta" :
                this.terminal = terminals[1];
                break;
            case "Southwest" :
                this.terminal = terminals[2];
                break;
        }

        terminalGate = rand.nextInt(19);
    }

    public String toString() {
        return this.terminal + this.terminalGate;
    }
}
