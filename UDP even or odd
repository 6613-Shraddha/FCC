import java.net.*;
import java.io.*;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(2000);
            // Size is 1024 bytes
            byte[] buffer = new byte[1024];

            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            ds.receive(receivePacket);

            // The data received in the packet is converted from bytes to a string
            String receiveStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + receiveStr);

            int number = Integer.parseInt(receiveStr);
            String responseMessage;

            // Check if the number is even or odd
            if (number % 2 == 0) {
                responseMessage = "The number is even";
            } else {
                responseMessage = "The number is odd";
            }

            // Preparing the Response Packet
            byte[] responseBytes = responseMessage.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                responseBytes,
                responseBytes.length,
                receivePacket.getAddress(),
                receivePacket.getPort()
            );

            ds.send(responsePacket);
            System.out.println("Response sent: " + responseMessage);

            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//======================================================================

import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String[] args) {
        try {
            
            DatagramSocket ds = new DatagramSocket();
            BufferedReader br = new BufferedReader(new                                InputStreamReader(System.in));

            System.out.print("Enter a number: ");
            String num = br.readLine();

            // Convert the input number to bytes
            byte[] sendBuffer = num.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getLocalHost(), 2000);
            ds.send(sendPacket);  // Send the packet to the server

            // Prepare to receive the server's response
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            ds.receive(receivePacket);  // Wait for a response from the server
            
            // Convert the received bytes to a string
            String responseStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server Response: " + responseStr);

            // Close the DatagramSocket
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
}
