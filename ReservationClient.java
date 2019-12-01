import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.*;

import static java.lang.Integer.parseInt;

public class ReservationClient extends JFrame {

    public static Socket server;
    public static ObjectOutputStream netoos;
    public static ObjectInputStream netois;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boolean auth = false;
                Passenger x = new Passenger();

                //edge-case adjustment for server auth
                while (!auth) {
                    String hostName = "";
                    String portNum = "";
                    while (hostName.equals("")) {
                        hostName = inputHostName();
                        if (hostName.equals(JOptionPane.CANCEL_OPTION)) {
                            System.exit(0);
                        }
                    }
                    while (portNum.equals("")) {
                        portNum = inputPortNum();
                        if (portNum.equals(JOptionPane.CANCEL_OPTION)) {
                            System.exit(0);
                        }
                    }

                    //implemented the setServer method to create the static socket variable
                    auth = setServer(hostName, portNum);
                }

                welcome(x);
                //*/
            }
        });
    }

    private static String inputHostName() {
        return JOptionPane.showInputDialog(null,
                "What is the hostname you'd like to connect to?", "Hostname?",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //added the setServer method to avoid the previous issues of final or effectively final reference
    private static boolean setServer(String hostName, String portNum) {
        try {
            server = new Socket(hostName, Integer.parseInt(portNum));
            netoos = new ObjectOutputStream(server.getOutputStream());
            netois = new ObjectInputStream(server.getInputStream());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String inputPortNum() {
        return JOptionPane.showInputDialog(null,
                "What is the port you'd like to connect to?", "Port?",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void welcome(Passenger x) {
        // TODO: 11/30/2019 add image
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setVisible(true);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        JButton exitButton = new JButton("Exit");
        JButton bookFlightButton = new JButton("Book a Flight");
        bookFlightButton.addActionListener(e -> {
            bookFlight(x);
            frame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        panel.add(exitButton);
        panel.add(bookFlightButton);
        frame.add(panel, BorderLayout.SOUTH);

        String text = "<html><div style='text-align: center;'>Welcome to the Purdue University Airline Reservation " +
                "Management System!</div></html>";

        JLabel text1 = new JLabel(text);
        text1.setFont(font1);
        JPanel welcomeText = new JPanel();
        welcomeText.add(text1);
        frame.add(text1, BorderLayout.NORTH);
    }


    public static void bookFlight(Passenger x) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setVisible(true);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        JButton bookFlightButton = new JButton("Yes, I want to book a Flight.");
        bookFlightButton.addActionListener(e -> {
            frame.setVisible(false);
            flightChoice(x);
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

    public static void flightChoice(Passenger x) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
        final String airlineChoice = "";
        String[] airlines = {"Alaska", "Delta", "Southwest"};

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
        JButton listenerButton = new JButton();
        listenerButton.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                    // TODO: 11/30/2019 fix listener work on 2nd try
                    int choice = comboBox.getSelectedIndex();
                    System.out.println("Enter has been pressed!");
                    displayManifest(choice);
                    System.out.println("Enter has been pressed!");
                }
            }
        });

        //TODO: replace the new airline constructors with those from the server
        JButton chooseButton = new JButton("Choose this flight");
        chooseButton.addActionListener(e -> {
            int choice = comboBox.getSelectedIndex();
            switch (choice) {
                case 0:
                    x.setBoardingPass(new BoardingPass(new Alaska()));
                    break;
                case 1:
                    x.setBoardingPass(new BoardingPass(new Delta()));
                    break;
                case 2:
                    x.setBoardingPass(new BoardingPass(new Southwest()));
                    break;
            }

            frame.setVisible(false);
            confirmAirline(x);
        });

        String alaskanText = "<html>Alaskan Airlines is proud to serve the strong and knowledgeable Boilermakers " +
                "from Purdue University.<br>" +
                " We primarily fly westward, and often have stops in Alaska and " +
                "California.<br>We have first class amenities, even in coach class.<br>We provide fun snacks, " +
                "such as pretzels and goldfish.<br>We also have comfortable seats, and free WiFi.<br>We hope " +
                "you choose Alaska Airlines for your next itinerary!</html>";
        JLabel textBox = new JLabel(alaskanText);
        textBox.setPreferredSize(new Dimension(500, 300));
        comboBox.addItemListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    textBox.setText(alaskanText);

                    break;
                case 1:
                    textBox.setText("<html>Delta Airlines is proud to be one of the five premier Airlines at Purdue University" +
                            ". <br>We are extremely exceptional services, with free limited WiFi for all customers.<br>" +
                            "Passengers who use T-Mobile as a cell phone carrier get additional benefits.<br> We are also" +
                            "happy to offer power outlets in each seat for passenger use. We hope you choose to fly Delta" +
                            "as your next Airline.</html>");
                    break;
                case 2:
                    textBox.setText("<html>Southwest Airlines is proud to offer flights to Purdue University.<br> We are happy " +
                            "to offer free in flight WiFi, as well as our amazing snacks.<br> In addition, we offer flights" +
                            "for much cheaper than other airlines, and offer two free checked bags.<br>We hope you choose " +
                            "Southwest for your next flight.</html>");
            }
        });

        airlineText.add(textBox);
        chooseText.add(comboBox, BorderLayout.SOUTH);
        panel.add(exitButton);
        panel.add(chooseButton);
        frame.add(listenerButton);
        frame.add(chooseText, BorderLayout.NORTH);
        frame.add(airlineText, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

    }

    public static void confirmAirline(Passenger x) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        String text = "<html>Are you sure that you want to book a flight on <br>" + x.getBoardingPass().getAirlineString()
                + " Airlines?</html>";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);
        frame.add(text1, BorderLayout.NORTH);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        JButton yesButton = new JButton("Yes, I want this flight.");
        yesButton.addActionListener(e -> {
            frame.setVisible(false);
            askInfo(x);
        });
        JButton noButton = new JButton("No, I want a different flight.");
        noButton.addActionListener(e -> {
            frame.setVisible(false);
            flightChoice(x);
        });
        JPanel panel = new JPanel();
        panel.add(exitButton, BorderLayout.SOUTH);
        panel.add(yesButton, BorderLayout.SOUTH);
        panel.add(noButton, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.SOUTH);
    }

    public static void askInfo(Passenger x) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        Font font2 = new Font("SansSerif", Font.PLAIN, 15);
        String text = "Please input your information below.";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);
        frame.add(text1, BorderLayout.NORTH);

        JLabel text2 = new JLabel("What is your first name?");
        text2.setFont(font2);

        JLabel text3 = new JLabel("What is your last name?");
        text3.setFont(font2);

        JLabel text4 = new JLabel("What is your age?");
        text4.setFont(font2);

        JTextArea textFieldFN = new JTextArea(4, 50);
        JTextArea textFieldLN = new JTextArea(4, 50);
        JTextArea textFieldAGE = new JTextArea(4, 50);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (textFieldFN.getText().equals("") || textFieldLN.getText().equals("") || textFieldAGE.getText().equals("")) {
                JOptionPane.showConfirmDialog(null, "Please fill out all required fields then press \"Next\" to continue", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!confirmInfo(textFieldFN.getText(), textFieldLN.getText(), textFieldAGE.getText())) {

                } else {
                    frame.setVisible(false);
                    String firstName = textFieldFN.getText();
                    String lastName = textFieldLN.getText();
                    String age = textFieldAGE.getText();
                    x.setFirstName(firstName);
                    x.setLastName(lastName);
                    x.setAge(age);
                    x.getBoardingPass().setPassenger(x);
                    try {
                        netoos.writeObject(String.format("POST!PASS!%s", x.getBoardingPass().getAirlineString().toUpperCase()));
                        netoos.writeObject(x);
                    } catch (Exception j) {
                        j.printStackTrace();
                    }
                    x.toString();
                    flightData(x);
                }
            }
        });

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.add(text2);
        panel1.add(textFieldFN);
        panel1.add(text3);
        panel1.add(textFieldLN);
        panel1.add(text4);
        panel1.add(textFieldAGE);
        panel2.add(exitButton);
        panel2.add(nextButton);
        frame.add(panel1);
        frame.add(panel2, BorderLayout.SOUTH);
    }

    public static boolean confirmInfo(String firstN, String lastN, String age) {
        int choice = -1;
        String message = "<html>Are all the details you entered correct?<br>" +
                "The passenger's name is " + firstN + " " + lastN + " and their age is " + age + ".<br>" +
                "If all the information shown is correct, select the Yes<br> button below, otherwise, " +
                "select the No button.</html>";
        choice = JOptionPane.showConfirmDialog(null, message, "Confirm Info",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (choice == JOptionPane.NO_OPTION) {
            return false;
        }
        if (choice == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return true;
        }

    }

    public static void flightData(Passenger x) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
        JPanel panel = new JPanel();

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        JButton refreshButton = new JButton("Refresh Flight Status");
        refreshButton.addActionListener(e -> {
            // TODO: 11/30/2019 refresh
        });

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        String text = "<html>Flight data displaying for " + x.getBoardingPass().getAirlineString() + " Airlines<br>" +
                "Enjoy your flight!<br>Flight is now boarding at Gate A16</html>";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);
        frame.add(text1, BorderLayout.NORTH);

        Font font2 = new Font("SansSerif", Font.PLAIN, 15);
        String text3 = "<html>--------------------------------------------------------------------------------------------------------------------------------------<br>" +
                "BOARDING PASS FOR FLIGHT 18000 WITH " + x.getBoardingPass().getAirlineString() + " Airlines<br>" +
                "PASSENGER FIRST NAME: " + x.getFirstName() + "<br>" +
                "PASSENGER LAST NAME: " + x.getLastName() + "<br>" +
                "PASSENGER AGE: " + x.getAge() + "<br>" +
                "You can now begin boarding at gate " + x.getBoardingPass().getAirline().getGate() + "<br>" +
                "--------------------------------------------------------------------------------------------------------------------------------------</html>";
        // TODO: 11/30/2019


        // TODO: make scroll layout vertical and center capacity label
        Passenger[] passengers = null;
        int capacityLeft = 0;
        int capacity = 0;

        try {
            netoos.writeObject(String.format("GET!PASS!%s", x.getBoardingPass().getAirlineString().toUpperCase()));
            passengers = (Passenger[]) netois.readObject();
            netoos.writeObject(String.format("GET!CPCL!%s", x.getBoardingPass().getAirlineString().toUpperCase()));
            capacityLeft = (int) netois.readObject();
            netoos.writeObject(String.format("GET!CPC!%s", x.getBoardingPass().getAirlineString().toUpperCase()));
            capacity = (int) netois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel scrollPanel = new JPanel();

        scrollPanel.add(new JLabel(capacityLeft + "/" + capacity));
        for (Passenger passenger: passengers) {
            if (passenger == null) {
                break;
            }
            String textFill = "<html>" + passenger.getFirstName().substring(0,1).toUpperCase() + ". " +
                passenger.getLastName().toUpperCase() + ", " +
                passenger.getAge() + "</html>";
            JLabel readFromServer = new JLabel(textFill);
            scrollPanel.add(readFromServer);
        }
        //
        JScrollPane jsp = new JScrollPane(scrollPanel);
        frame.add(jsp, BorderLayout.CENTER);

        JLabel text4 = new JLabel(text3);
        text1.setFont(font1);
        panel.add(text4, BorderLayout.NORTH);
        panel.add(exitButton, BorderLayout.SOUTH);
        panel.add(refreshButton, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.SOUTH);

    }

    public static void displayManifest(int choice) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        JPanel panel = new JPanel();

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            frame.setVisible(false);
        });

        String air = "";
        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        switch (choice) {
            case 0:
                air = "Alaska";
                break;
            case 1:
                air = "Delta";
                break;
            case 2:
                air = "Southwest";
                break;
        }

        String text = air + " Airlines.";

        // TODO: 11/30/2019

        // TODO: Same as the flightData, need to implement vertical scrolling

        Passenger[] passengers = null;
        int capacityLeft = 0;
        int capacity = 0;

        try {
            netoos.writeObject(String.format("GET!PASS!%s", air.toUpperCase()));
            passengers = (Passenger[]) netois.readObject();
            netoos.writeObject(String.format("GET!CPCL!%s", air.toUpperCase()));
            capacityLeft = (int) netois.readObject();
            netoos.writeObject(String.format("GET!CPC!%s", air.toUpperCase()));
            capacity = (int) netois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String textCap = capacityLeft + ":" + capacity;

        JPanel scrollPanel = new JPanel();

        for (Passenger passenger: passengers) {
            if(passenger == null) {
                break;
            }
            String textFill = "<html>" + passenger.getFirstName().substring(0,1).toUpperCase() + ". " +
                passenger.getLastName().toUpperCase() + ", " +
                passenger.getAge() + "</html>";
            JLabel readFromServer = new JLabel(textFill);
            scrollPanel.add(readFromServer);
        }

        JScrollPane jsp = new JScrollPane(scrollPanel);
        frame.add(jsp, BorderLayout.CENTER);

        JLabel text1 = new JLabel(text);
        text1.setFont(font1);
        frame.add(text1, BorderLayout.NORTH);
        frame.add(exitButton, BorderLayout.SOUTH);
    }

}
