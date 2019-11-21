import java.net.*;
import java.io.*;

public class ReservationServer {

    private static ServerSocket socket;
    private static Airline[] airlines;

    public ReservationServer() throws IOException {
        socket = new ServerSocket(0);
    }

    public void startServer() {
        Socket clientSocket = null;
        Thread handlerThread;

        while (true) {
            try {
                clientSocket = this.socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            handlerThread = new Thread(new Handler(clientSocket));
            handlerThread.start();
        }
    }

    public int getCapacityLeft(Airline airline) {
        return airline.getCapacityLeft();
    }

    public Passenger[] getPassengers(Airline airline) {
        return airline.getPassengers();
    }

    public Airline getAirline(String name) {
        for (Airline airline : airlines) {
            if (airline.getName().toUpperCase().equals(name)) {
                return airline;
            }
        }

        return null;
    }

    private class Handler extends Thread {
        private Socket clientSocket;

        public Handler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            System.out.println("running");
        }
    }
}
