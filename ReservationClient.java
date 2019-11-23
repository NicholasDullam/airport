import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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
                while(hostName.equals("")) {
                    hostName = inputHostName();
                    if (hostName.equals(JOptionPane.CANCEL_OPTION)) {
                        System.exit(0);
                    }
                }
                System.out.println(hostName);
                while(portNum.equals("")) {
                    portNum = inputPortNum();
                    if (inputPortNum().equals(JOptionPane.CANCEL_OPTION)) {
                        System.exit(0);
                    }
                }
                welcome();
                bookFlight();

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
        String text = "Do you want to book a flight today?";

        JLabel text1 = new JLabel(text);
        text1.setFont(font1);
        JPanel welcomeText = new JPanel();
        welcomeText.add(text1);
        frame.add(text1, BorderLayout.NORTH);

        panel.add(exitButton);
        panel.add(bookFlightButton);

        frame.add(panel, BorderLayout.SOUTH);
    }

 /*   public static void purdueP() {
        //BufferedImage img = ImageIO.read("purdueP.png");
        ImageIcon icon = new ImageIcon("purdueP.png");
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200, 300);
        JLabel lbl = new JLabel(icon);
        //lbl.add(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/


}
