import java.net.*;
import java.io.*;

class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8000);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String msg;
            System.out.println("To stop chatting with the server, type 'STOP'");
            
            while (true) {
                System.out.print("Client Says: ");
                msg = userInput.readLine();

                out.writeBytes(msg + "\n");

                if (msg.equalsIgnoreCase("STOP")) {
                    break;
                }

                String serverMsg = in.readLine();
                System.out.println("Server Says: " + serverMsg);
            }
        } catch (IOException e) {
            System.out.println("Communication error: " + e.getMessage());
        }
    }
}

//==============================================================================================

import java.net.*;
import java.io.*;

class ChatServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Waiting for client to connect...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String receive, send;
                while ((receive = in.readLine()) != null) {
                    if (receive.equalsIgnoreCase("STOP")) {
                        System.out.println("Client has stopped the chat.");
                        break;
                    }
                    System.out.println("Client Says: " + receive);
                    System.out.print("Server Says: ");
                    send = userInput.readLine();
                    out.writeBytes(send + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
