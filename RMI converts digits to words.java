import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterConvert extends Remote {
    String convertDigit(String no) throws RemoteException;
}

//==========================================================

import java.rmi.*;
import java.rmi.server.*;

public class ServerConvert extends UnicastRemoteObject implements InterConvert {
    
    public ServerConvert() throws RemoteException {
        super();
    }

    public String convertDigit(String no) throws RemoteException {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < no.length(); i++) {
            int digit = Character.getNumericValue(no.charAt(i));
            switch (digit) {
                case 0: str.append("zero "); break;
                case 1: str.append("one "); break;
                case 2: str.append("two "); break;
                case 3: str.append("three "); break;
                case 4: str.append("four "); break;
                case 5: str.append("five "); break;
                case 6: str.append("six "); break;
                case 7: str.append("seven "); break;
                case 8: str.append("eight "); break;
                case 9: str.append("nine "); break;
                default: str.append(" "); // For non-digit characters, add a space
            }
        }
        return str.toString().trim(); // Trim to remove any trailing space
    }

    public static void main(String[] args) {
        try {
            ServerConvert s1 = new ServerConvert();
            Naming.rebind("rmi://localhost/Wrd", s1); // Use rebind for replacing
            System.out.println("Object registered....");
        } catch (RemoteException e) {
            System.err.println("Remote exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//===============================================

import java.rmi.*;
import java.io.*;

public class ClientConvert {
    public static void main(String[] args) {
        try {
            // Look up the remote object by its name
            InterConvert h1 = (InterConvert) Naming.lookup("rmi://localhost/Wrd");

            // Create a BufferedReader to read input from the console
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a number: ");
            
            // Read the input number
            String no = br.readLine();
            
            // Validate input
            if (!no.matches("\\d+")) {
                System.out.println("Please enter only digits.");
                return;
            }
            
            // Call the remote method to convert the digit
            String ans = h1.convertDigit(no);
            
            // Print the result
            System.out.println("The word representation of the entered digit is: " + ans);
        } catch (NotBoundException e) {
            System.err.println("The specified name is not currently bound: " + e.getMessage());
        } catch (RemoteException e) {
            System.err.println("Remote exception: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
