import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterDate extends Remote {

public String display() throws RemoteException;
}



//============================================================

import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;

public class ServerDate extends UnicastRemoteObject implements InterDate {
    
    public ServerDate() throws RemoteException {
        // Constructor can be empty; it must declare RemoteException.
    }

    public String display() throws RemoteException {
        Date d = new Date();
        return d.toString();
    }

    public static void main(String[] args) {
        try {
            ServerDate s1 = new ServerDate();
            Naming.rebind("rmi://localhost/DS", s1); // Use rebind to avoid AlreadyBoundException
            System.out.println("Date Server is running and object registered as 'DS'.");
        } catch (RemoteException e) {
            System.err.println("Remote exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//===========================================================================================


import java.rmi.*;
import java.io.*;
public class ClientDate
{
public static void main(String args[]) throws
Exception {
String s1;
InterDate h1 = (InterDate)Naming.lookup("DS");
s1 = h1.display();
System.out.println(s1);}}
