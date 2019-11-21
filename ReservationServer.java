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

    public static int getCapacityLeft(String name) {
        return getAirline(name).getCapacityLeft();
    }

    public static Passenger[] getPassengers(String name) {
        return getAirline(name).getPassengers();
    }

    public static Airline getAirline(String name) {
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
            while (true) {
                BufferedWriter bfw = null;
                BufferedReader bfr = null;

                try {
                    bfw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    bfr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String[] query = bfr.readLine().split("$");

                    switch (query[1]) {
                        case "CAP" :
                            bfw.write(ReservationServer.getCapacityLeft(query[0]));
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
