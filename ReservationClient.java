import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ReservationClient extends JFrame{

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String hostName = "";
                String portNum = "";
                String thisAirline = "";


                while(hostName.equals("")) {
                    hostName = inputHostName();
                    if (hostName.equals(JOptionPane.CANCEL_OPTION)) {
                        System.exit(0);
                    }
                }
                System.out.println(hostName);
                while(portNum.equals("")) {
                    portNum = inputPortNum();
                    if (portNum.equals(JOptionPane.CANCEL_OPTION)) {
                        System.exit(0);
                    }
                }
                welcome();
                //flightChoice();
                //bookFlight();
            //flightChoice();
            }
        });

    }
    private static String inputHostName() {
        return JOptionPane.showInputDialog(null,
                "What is the hostname you'd like to connect to?", "Hostname?",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static String inputPortNum() {
        return JOptionPane.showInputDialog(null,
                "What is the port you'd like to connect to?", "Port?",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void welcome() {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600,400);
        frame.setVisible(true);


        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        JButton exitButton = new JButton("Exit");
        JButton bookFlightButton = new JButton("Book a Flight");
        bookFlightButton.addActionListener(e -> {
            bookFlight();
            frame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        panel.add(exitButton);
        panel.add(bookFlightButton);
        frame.add(panel, BorderLayout.SOUTH);

        String text = "Welcome to the Purdue University Airline Reservation Management System!";

        JLabel text1 = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
        text1.setFont(font1);
        JPanel welcomeText = new JPanel();
        welcomeText.add(text1);
        frame.add(text1, BorderLayout.NORTH);

        //frame.setLocationRelativeTo(null);
    }

    public static void bookFlight() {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600,400);
        frame.setVisible(true);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        JButton bookFlightButton = new JButton("Yes, I want to book a Flight.");
        bookFlightButton.addActionListener(e -> {
            flightChoice();
        });

        String text = "Do you want to book a flight today?";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);
        JPanel welcomeText = new JPanel();
        welcomeText.add(text1);
        frame.add(text1, BorderLayout.NORTH);

        panel.add(exitButton);
        panel.add(bookFlightButton);

        frame.add(panel, BorderLayout.SOUTH);
    }

    public static void flightChoice() {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(new BorderLayout(100,100));
        frame.setSize(600,400);
        frame.setVisible(true);
        int airlineChoice;

        JPanel chooseText = new JPanel();
        JPanel airlineText = new JPanel();
        JPanel panel = new JPanel();

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        String text = "Choose a flight from the drop down menu.";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);
        chooseText.add(text1, BorderLayout.NORTH);

        JComboBox<String> comboBox = new JComboBox();
        comboBox.addItem("Alaska");
        comboBox.addItem("Delta");
        comboBox.addItem("Southwest");

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        JButton chooseButton = new JButton("Choose this flight");

        chooseButton.addActionListener(e -> {
            //airlineChoice = comboBox.getSelectedIndex();

        });

        JLabel textBox = new JLabel("test");

        comboBox.addItemListener(e-> {
            int selectedIndex = comboBox.getSelectedIndex();
            System.out.println("here");
            switch (selectedIndex) {
            case 0 :
                textBox.setText("Alaskan Airlines is proud to serve the strong and knoledgable Boilermakers from " +
                        "Purdue University.\n We primarily fly westward, and often have stops in Alaska and " +
                        "California.\nWe have first class amenities, even in coach class.\nWe provide fun snacks, " +
                        "such as pretzels and goldfish.\nWe also have comfortable seats, and free WiFi.\nWe hope " +
                        "you choose Alaska Airlines for your next itinerary!");
                break;
            case 1 :
                textBox.setText("Delta Airlines is proud to be one of the five premier Airlines at Purdue University" +
                        ". \nWe are extremely exceptional services, with free limited WiFi for all customers.\n" +
                        "Passengers who use T-Mobile as a cell phone carrier get additional benefits.\n We are also" +
                        "happy to offer power outlets in each seat for passenger use. We hope you choose to fly Delta" +
                        "as your next Airline.");
                break;
            case 2 :
                textBox.setText("Southwest Airlines is proud to offer flights to Purdue University.\n We are happy " +
                        "to offer free in flight WiFi, as well as our amazing snacks.\n In addition, we offer flights" +
                        "for much cheaper than other airlines, and offer two free checked bags.\nWe hope you choose " +
                        "Southwest for your next flight.");
                //panel.add(textBox);
        }
        });

        airlineText.add(textBox);
        //airlineText.add(comboBox);
        chooseText.add(comboBox, BorderLayout.SOUTH);
        panel.add(exitButton);
        panel.add(chooseButton);


        frame.add(chooseText, BorderLayout.NORTH);
        //frame.add(comboBox, BorderLayout.NORTH);
        frame.add(airlineText, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

    }



}
