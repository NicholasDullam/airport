import java.net.*;
import java.io.*;

public class ReservationServer {

    private static ServerSocket socket;
    private static Airline[] airlines = null;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;

    public ReservationServer() {
        try {
            ois = new ObjectInputStream(new FileInputStream("reservations.txt"));
            airlines = (Airline[]) ois.readObject();
        } catch (EOFException e) {
            System.out.println("Reformatting reservations.txt");
            airlines = new Airline[3];
            airlines[0] = new Delta();
            airlines[1] = new Alaska();
            airlines[2] = new Southwest();
            save();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) ois.close();
            } catch (Exception j) {
                j.printStackTrace();
            }
        }

        try {
            socket = new ServerSocket(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream("reservations.txt"));
            oos.writeObject(airlines);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer() {

        Socket clientSocket;
        Thread handlerThread;

        System.out.printf("Server Port: %d", this.socket.getLocalPort());

        while (true) {
            try {
                clientSocket = this.socket.accept();
                handlerThread = new Thread(new Handler(clientSocket));
                handlerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getCapacityLeft(String name) {
        return getAirline(name).getCapacityLeft();
    }

    public static int getCapacity(String name) {
        return getAirline(name).getCapacity();
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

    public static void addPassenger(String name, Passenger passenger) {
        getAirline(name).addPassenger(passenger);
    }

    public static void main(String[] args) {
        ReservationServer rs = new ReservationServer();
        try {
            rs.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Handler extends Thread {
        private Socket clientSocket;
        private ObjectInputStream netois;
        private ObjectOutputStream netoos;

        public Handler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                netoos = new ObjectOutputStream(clientSocket.getOutputStream());
                netois = new ObjectInputStream(clientSocket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            System.out.println("Thread" + this.getName());
            while (true) {
                try {
                    String queue = (String) netois.readObject();
                    System.out.println("found");
                    String[] query = queue.split("!");
                    switch (query[0]) {
                        case "GET" :
                            switch(query[1]) {
                                case "CPCL" :
                                    System.out.println(ReservationServer.getCapacityLeft(query[2]));
                                    netoos.writeObject(ReservationServer.getCapacityLeft(query[2]));
                                    break;
                                case "PASS" :
                                    netoos.writeObject(ReservationServer.getPassengers(query[2]));
                                    System.out.println(ReservationServer.getPassengers(query[2]));
                                    break;
                                case "CPC" :
                                    netoos.writeObject(ReservationServer.getCapacity(query[2]));
                                    break;
                            }
                            break;
                        case "POST" :
                            switch(query[1]) {
                                case "PASS" :
                                    try {
                                        Passenger passenger = (Passenger) netois.readObject();
                                        ReservationServer.addPassenger(query[2], passenger);
                                        ReservationServer.save();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                    }
                } catch (EOFException e) {
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
