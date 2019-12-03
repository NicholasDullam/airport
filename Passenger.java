import java.io.Serializable;

/**
 * Project 5 - Airport Manager
 *
 * @author Nicholas Dullam ndullam
 * @author Michael Taylor taylo874
 * @version 11-20-19
 */

public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private BoardingPass boardingPass;
    private String age;

    public Passenger(String firstName, String lastName, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Passenger() {
        this.firstName = "";
        this.lastName = "";
        this.age = "";
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAge() {
        return this.age;
    }

    public BoardingPass getBoardingPass() {
        return boardingPass;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBoardingPass(Airline airline) {
        this.boardingPass = new BoardingPass(this, airline);
    }

    public void setBoardingPass(BoardingPass boardingPass) {
        this.boardingPass = boardingPass;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", boardingPass=" + boardingPass +
                ", age=" + age +
                '}';
    }
}
