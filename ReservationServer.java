import java.net.*;
import java.io.*;
import java.util.*;

public class ReservationServer {

    private static ServerSocket socket;
    private static Airline[] airlines = null;
    private static BufferedReader bfr = null;
    private static PrintWriter pwr = null;
    private static ArrayList<String> file = null;

    public ReservationServer() {
        read();
        try {
            socket = new ServerSocket(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            pwr = new PrintWriter("reservations.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Airline airline : airlines) {
            pwr.print(airline.getName().toUpperCase() + "\n" +
                (airline.getCapacity() - airline.getCapacityLeft()) + "/" + airline.getCapacity() + "\n" +
                airline.getName() + " passenger list" + "\n" + "\n");
            for (Passenger passenger : airline.getPassengers()) {
                if (passenger == null) {
                    break;
                }
                pwr.print(passenger.getFirstName().substring(0, 1).toUpperCase() + ". " + passenger.getLastName().toUpperCase() + ", " + passenger.getAge() +
                    "\n-----------------" + airline.getName().toUpperCase() + "\n" + "\n");
            }
        }

        pwr.print("EOF");

        try {
            pwr.flush();
            pwr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        read();
    }

    public static void read() {

        airlines = new Airline[3];
        airlines[0] = new Delta();
        airlines[1] = new Alaska();
        airlines[2] = new Southwest();

        try {
            bfr = new BufferedReader(new FileReader("reservations.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> file = new ArrayList<String>();
        try {
            String line = "";
            while (true) {
                line = bfr.readLine();
                file.add(line);
                if (line == null || line.equals("EOF")) {
                    break;
                }
            }

            String airline = "";
            boolean passengers = false;
            for (int i = 0; i < file.size(); i++) {
                if (file.get(i) == null || file.get(i).equals("")) {
                    continue;
                }
                if (file.get(i).equals("EOF")) {
                    break;
                }

                if (file.get(i).equals("DELTA") || file.get(i).equals("SOUTHWEST") || file.get(i).equals("ALASKA")) {
                    passengers = false;
                    airline = file.get(i);
                } else if (!airline.equals("") && file.get(i).contains("passenger list")) {
                    passengers = true;
                } else if (passengers == true && !(file.get(i).contains("DELTA") || file.get(i).contains("ALASKA") || file.get(i).contains("SOUTHWEST"))) {
                    System.out.println(i);
                    String[] query = file.get(i).split(" ");
                    switch (airline) {
                        case "DELTA" :
                            airlines[0].addPassenger(new Passenger(query[0].replaceAll("[.]", ""), query[1].replaceAll("[,]", ""), query[2]));
                            break;
                        case "ALASKA" :
                            airlines[1].addPassenger(new Passenger(query[0].replaceAll("[.]", ""), query[1].replaceAll("[,]", ""), query[2]));
                            break;
                        case "SOUTHWEST" :
                            airlines[2].addPassenger(new Passenger(query[0].replaceAll("[.]", ""), query[1].replaceAll("[,]", ""), query[2]));
                            break;
                    }
                } else if (passengers == false) {
                    try {
                        String[] capacity = file.get(i).split("/");
                        switch (airline) {
                            case "DELTA" :
                                airlines[0] = new Delta(Integer.parseInt(capacity[1]));
                                break;
                            case "ALASKA" :
                                airlines[1] = new Alaska(Integer.parseInt(capacity[1]));
                                break;
                            case "SOUTHWEST" :
                                airlines[2] = new Southwest(Integer.parseInt(capacity[1]));
                                break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            bfr.close();
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
                ReservationServer.read();
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
                                case "AIR" :
                                    netoos.writeObject(ReservationServer.airlines);
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
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
