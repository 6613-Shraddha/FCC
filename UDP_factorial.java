import java.io.*;
import java.net.*;

public class FactServer
{
	private static String s;
	public static void main(String args[])
	{
		try
		{
			DatagramSocket ds = new DatagramSocket(2000);
 			byte b[] = new byte[1024];
			DatagramPacket dp = new DatagramPacket(b,b.length); 
			ds.receive(dp);
			String str = new String(dp.getData(),0,dp.getLength());
 			System.out.println(str);
			int a = Integer.parseInt(str);
			//String s= new String();
			if (a == 0)
				s = "Factorial of 0 is 1";
			else
				fact(a);

			byte b1[] = new byte[1024];
			b1 = s.getBytes();
			DatagramPacket dp1 = new DatagramPacket(b1,b1.length,InetAddress.getLocalHost(),1000);
			ds.send(dp1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void fact(int a)
	{
		int x = 1;
		for(int i = a; i>0; i--)
		{
			x = x*i;
		}
		s = "Factorial of "+a+" is "+x;
	}
}

//=====================================================


import java.net.*;
import java.io.*;

public class FactClient {
    
    public static void main(String[] args) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(1000);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a number: ");
            String num = br.readLine();

            int number;

            try {
                number = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                return; // Exit the program if input is invalid
            }

            // Convert the number to a byte array
            byte[] b = num.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 2000);
            ds.send(dp);

            // Prepare to receive the response
            byte[] b1 = new byte[1024];
            DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
            ds.receive(dp1);
            String str = new String(dp1.getData(), 0, dp1.getLength());
            System.out.println("Factorial: " + str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null && !ds.isClosed()) {
                ds.close();
            }
        }
    }
}
