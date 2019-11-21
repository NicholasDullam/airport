import java.net.*;
import java.io.*;

public class ReservationServer {

    private ServerSocket socket;

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
