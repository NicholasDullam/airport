import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ReservationClient extends JFrame {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String hostName = "";
                String portNum = "";
                String thisAirline = "";


                /*while (hostName.equals("")) {
                    hostName = inputHostName();
                    if (hostName.equals(JOptionPane.CANCEL_OPTION)) {
                        System.exit(0);
                    }
                }
                System.out.println(hostName);
                while (portNum.equals("")) {
                    portNum = inputPortNum();
                    if (portNum.equals(JOptionPane.CANCEL_OPTION)) {
                        System.exit(0);
                    }
                }*/
                //welcome();
                flightChoice();

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
        frame.setSize(600, 400);
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

        String text = "<html><div style='text-align: center;'>Welcome to the Purdue University Airline Reservation " +
                "Management System!</div></html>";

        JLabel text1 = new JLabel(text);
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
        frame.setSize(600, 400);
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

        JButton listenerButton = new JButton();
        listenerButton.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                    System.out.println("Enter has been pressed!");
                }
            }
        });

        JComboBox<String> comboBox = new JComboBox();
        comboBox.addItem("Alaska");
        comboBox.addItem("Delta");
        comboBox.addItem("Southwest");
        comboBox.addItemListener(listener -> {
            String choice;
            JComboBox getSelection = (JComboBox) listener.getSource();
            choice = (String) getSelection.getSelectedItem();
            //System.out.println(choice);
            airlineChoice.replaceAll("", choice);
            System.out.println(airlineChoice);

        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        JButton chooseButton = new JButton("Choose this flight");
        chooseButton.addActionListener(e -> {
            int choice = comboBox.getSelectedIndex();
            frame.setVisible(false);
            confirmAirline(airlines[choice]);
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

    public static void confirmAirline(String airline) {
        JFrame frame = new JFrame("Purdue University Flight Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        String text = "<html>Are you sure that you want to book a flight on <br>" + airline + " Airlines?</html>";
        JLabel text1 = new JLabel(text, SwingConstants.CENTER);
        text1.setFont(font1);
        frame.add(text1, BorderLayout.NORTH);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        JButton yesButton = new JButton("Yes, I want this flight.");
        yesButton.addActionListener(e -> {

        });
        JButton noButton = new JButton("No, I want a different flight.");
        noButton.addActionListener(e -> {
            frame.setVisible(false);
            flightChoice();
        });
        JPanel panel = new JPanel();
        panel.add(exitButton, BorderLayout.SOUTH);
        panel.add(yesButton, BorderLayout.SOUTH);
        panel.add(noButton, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.SOUTH);
    }

    public void askInfo(){
        
    }


}
