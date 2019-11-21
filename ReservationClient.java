import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ReservationClient extends JFrame{

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String hostName = inputHostName();
                String portNum = inputPortNum();

                JPanel panel = new JPanel();
                JFrame window = new JFrame("Purdue University Flight Reservation System");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLayout(new BorderLayout());
                window.setSize(600,400);

                Font font1 = new Font("SansSerif", Font.BOLD, 20);
                JButton exitButton = new JButton("Exit");

                JButton bookFlightButton = new JButton("Book a Flight");
                String text = "Welcome to the Purdue University Airline Reservation Management System!";

                JLabel text1 = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
                text1.setFont(font1);
                window.add(text1, BorderLayout.NORTH);

                panel.add(exitButton);
                panel.add(bookFlightButton);

                window.setContentPane(panel);
                window.setVisible(true);
                window.setLocationRelativeTo(null);

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


}
