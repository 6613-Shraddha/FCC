import java.net.*;
import java.io.*;

class TcpServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8002)) {
            System.out.println("Server Started...............");

            while (true) {
                Socket socket = serverSocket.accept();
                try (DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                     
                    int x = in.readInt();
                    String result = isPrime(x) ? x + " is Prime" : x + " is not Prime";
                    out.writeUTF(result);
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true; // 2 and 3 are prime
        if (num % 2 == 0 || num % 3 == 0) return false; // Exclude multiples of 2 and 3

        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false; // Check for factors
        }
        return true;
    }
}

//=================================================================

import java.net.*;
import java.io.*;

class TcpClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8002);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            System.out.print("Enter a number: ");
            int number = Integer.parseInt(userInput.readLine());

            // Send the number to the server
            out.writeInt(number);

            // Read and print the server's response
            String response = in.readUTF();
            System.out.println(response);
        } catch (IOException e) {
            System.out.println("Communication error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    }
}
