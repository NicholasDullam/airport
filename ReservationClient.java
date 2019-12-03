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

import static javax.swing.GroupLayout.Alignment.*;

import java.awt.Component;


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


                //welcome(x);
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
            JOptionPane.showMessageDialog(null, "Incorrect hostname or port number.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static String inputPortNum() {
        return JOptionPane.showInputDialog(null,
                "What is the port you'd like to connect to?", "Port?",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void welcome(Passenger x) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setVisible(true);

        ImageIcon imageIcon = new ImageIcon("purdueP.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(400, 260, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        JLabel imagepa = new JLabel(imageIcon);
        frame.add(imagepa);

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
        Airline[] airlineObjects = null;

        try {
            netoos.writeObject("GET!AIR!ALL");
            airlineObjects = (Airline[]) netois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel chooseText = new JPanel();
        JPanel airlineText = new JPanel();
        JPanel panel = new JPanel();

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        String text = "Choose a flight from the drop down menu.";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);


        JComboBox<String> comboBox = new JComboBox();

        for (int i = 0; i < airlineObjects.length; i++) {
            if (airlineObjects[i].getCapacityLeft() != 0) {
                comboBox.addItem(airlineObjects[i].getName());
            }
        }

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });


        //TODO: replace the new airline constructors with those from the server
        JButton chooseButton = new JButton("<html><div style='text-align: center;'>Choose this flight</div></html>");
        chooseButton.addActionListener(e -> {
            Airline[] airlineObjectsE = null;

            try {
                netoos.writeObject("GET!AIR!ALL");
                airlineObjectsE = (Airline[]) netois.readObject();
            } catch (Exception j) {
                j.printStackTrace();
            }

            String choice = (String) comboBox.getSelectedItem();

            for (int i = 0; i < airlineObjectsE.length; i++) {
                if (choice.equals(airlineObjectsE[i].getName())) {
                    x.setBoardingPass(new BoardingPass(airlineObjectsE[i]));
                }
            }

            frame.setVisible(false);
            confirmAirline(x);
        });

        String first = "";

        for (int i = 0; i < airlineObjects.length; i++) {
            if (airlineObjects[i].getCapacityLeft() != 0) {
                first = airlineObjects[i].getDescription();
                break;
            }
        }

        JLabel textBox = new JLabel(first);
        textBox.setPreferredSize(new Dimension(500, 300));

        comboBox.addItemListener(e -> {
            Airline[] airlineObjectsE = null;
            try {
                netoos.writeObject("GET!AIR!ALL");
                airlineObjectsE = (Airline[]) netois.readObject();
            } catch (Exception j) {
                j.printStackTrace();
            }

            String choice = (String) comboBox.getSelectedItem();
            for (int i = 0; i < airlineObjectsE.length; i++) {
                if (choice.equals(airlineObjectsE[i].getName())) {
                    textBox.setText(airlineObjectsE[i].getDescription());
                }
            }
        });

        JTextField paneledTextField = new JTextField(0);
        paneledTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                    System.out.println("here");
                    displayManifest((String) comboBox.getSelectedItem());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        comboBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                    System.out.println("here");
                    displayManifest((String) comboBox.getSelectedItem());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        chooseText.add(text1, BorderLayout.NORTH);
        airlineText.add(comboBox, BorderLayout.NORTH);
        airlineText.add(textBox, BorderLayout.SOUTH);
        panel.add(exitButton);
        panel.add(chooseButton);
        frame.add(paneledTextField);
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
        String text = "<html><div style='text-align: center;'>Are you sure that you want to book a flight on <br>" + x.getBoardingPass().getAirlineString()
                + " Airlines?</div></html>";
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

        JLabel text2 = new JLabel("What is your first name?", SwingConstants.LEFT);
        text2.setFont(font2);

        JLabel text3 = new JLabel("What is your last name?", SwingConstants.LEFT);
        text3.setFont(font2);

        JLabel text4 = new JLabel("What is your age?", SwingConstants.LEFT);
        text4.setFont(font2);

        JTextArea textFieldFN = new JTextArea(4, 50);
        JTextArea textFieldLN = new JTextArea(4, 50);
        JTextArea textFieldAGE = new JTextArea(4, 50);
        JButton nextButton = new JButton("Next");

        textFieldFN.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    textFieldFN.transferFocus();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        textFieldLN.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    textFieldLN.transferFocus();

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        textFieldAGE.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    textFieldAGE.transferFocus();

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    nextButton.transferFocus();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        nextButton.addActionListener(e -> {
            if (textFieldFN.getText().equals("") || textFieldLN.getText().equals("") || !isNumeric(textFieldAGE.getText())) {
                JOptionPane.showMessageDialog(null, "Please fill out all required fields correctly" +
                        " then press \"Next\" to continue", "ERROR", JOptionPane.ERROR_MESSAGE);
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
                "select the \"No\" button.</html>";
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
        frame.setSize(850, 700);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        JPanel southPanel = new JPanel();
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        JButton refreshButton = new JButton("Refresh Flight Status");
        refreshButton.addActionListener(e -> {
            frame.setVisible(false);
            flightData(x);
        });

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        String text = "<html><div style='text-align: center;'>Flight data displaying for " + x.getBoardingPass().getAirlineString() + " Airlines<br>" +
                "Enjoy your flight!<br>Flight is now boarding at Gate " + x.getBoardingPass().getAirline().getGate() + "</div></html>";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);

        Font font2 = new Font("SansSerif", Font.PLAIN, 10);
        String text3 = "<html>------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------<br>" +
                "BOARDING PASS FOR FLIGHT 18000 WITH " + x.getBoardingPass().getAirlineString() + " Airlines<br>" +
                "PASSENGER FIRST NAME: " + x.getFirstName() + "<br>" +
                "PASSENGER LAST NAME: " + x.getLastName() + "<br>" +
                "PASSENGER AGE: " + x.getAge() + "<br>" +
                "You can now begin boarding at gate " + x.getBoardingPass().getAirline().getGate() + "<br>" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</html>";

        Passenger[] passengers = null;
        int capacityLeft = 0;
        int capacity = 0;
        int c2 = 0;

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

        c2 = capacity - capacityLeft;
        String c3 = "" + c2 + ":" + capacity;
        JLabel limitedCapacity = new JLabel(c3, SwingConstants.CENTER);
        Font fontx = new Font("SansSerif", Font.TYPE1_FONT, 17);
        limitedCapacity.setFont(fontx);

        String textFill = "";
        for (Passenger passenger : passengers) {
            if (passenger == null) {
                break;
            }
            textFill = textFill + passenger.getFirstName().substring(0, 1).toUpperCase() + ". " +
                    passenger.getLastName().toUpperCase() + ", " +
                    passenger.getAge() + "<br>";
        }
        textFill = c3 + "<br>" + textFill;
        JLabel readFromServer = new JLabel("<html><br>" + textFill + "</html>");

        JLabel text4 = new JLabel(text3);
        JScrollPane jsp = new JScrollPane(readFromServer);
        jsp.setPreferredSize(new Dimension(850, 400));
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        northPanel.add(text1, BorderLayout.NORTH);
        northPanel.add(jsp, BorderLayout.SOUTH);

        //text1.setFont(font1);
        centerPanel.add(text4);
        southPanel.add(exitButton);
        southPanel.add(refreshButton);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

    }

    public static void displayManifest(String choice) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        JPanel panel = new JPanel();

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            frame.setVisible(false);
        });

        String air = "";
        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        air = choice;

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

        String text = String.format(air + " Airlines." + " %d : %d", (capacity - capacityLeft), capacity);

        String textCap = capacityLeft + ":" + capacity;

        JPanel scrollPanel = new JPanel();

        String textFill = "";
        for (Passenger passenger : passengers) {
            if (passenger == null) {
                break;
            }
            textFill = textFill + passenger.getFirstName().substring(0, 1).toUpperCase() + ". " +
                    passenger.getLastName().toUpperCase() + ", " +
                    passenger.getAge() + "<br>";
        }
        JLabel readFromServer = new JLabel("<html><div style='text-align: left;'>" + textFill + "</div></html>");
        scrollPanel.add(readFromServer);

        JScrollPane jsp = new JScrollPane(scrollPanel);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(jsp, BorderLayout.CENTER);

        JLabel text1 = new JLabel(text);
        text1.setFont(font1);
        frame.add(text1, BorderLayout.NORTH);
        frame.add(exitButton, BorderLayout.SOUTH);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
