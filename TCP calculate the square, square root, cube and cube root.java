import java.net.*;
import java.io.*; 

public class ClientCode {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8001);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            System.out.print("Enter a number: ");
            String input = userInput.readLine();
            int number;

            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }

            out.writeInt(number);
            System.out.println("Server Response: " + in.readUTF());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


//==========================================================================

import java.net.*;
import java.io.*;

public class ServerCode {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8001)) {
            System.out.println("Server Started");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                     DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

                    int x = in.readInt();
                    int square = x * x;
                    double squareRoot = Math.sqrt(x);
                    int cube = x * x * x;
                    double cubeRoot = Math.cbrt(x);

                    out.writeUTF("Square of " + x + " is " + square +
                                 "\nSquare root of " + x + " is " + squareRoot +
                                 "\nCube of " + x + " is " + cube +
                                 "\nCube root of " + x + " is " + cubeRoot);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
