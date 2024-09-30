import java.net.*;
import java.util.*;

class RPCServer {
    DatagramSocket ds;
    DatagramPacket dp;
    
    RPCServer() {
        try {
            ds = new DatagramSocket(1200);
            byte b[] = new byte[4096];
            System.out.println("RPC Server is running...");

            while (true) {
                dp = new DatagramPacket(b, b.length);
                ds.receive(dp);
                String str = new String(dp.getData(), 0, dp.getLength());

                if (str.equalsIgnoreCase("q")) {
                    System.out.println("Server shutting down...");
                    break; // Gracefully exit the loop
                }

                StringTokenizer st = new StringTokenizer(str, " ");
                if (st.countTokens() < 3) {
                    System.out.println("Invalid input format. Expected: <method> <val1> <val2>");
                    continue;
                }

                String methodName = st.nextToken();
                int val1 = Integer.parseInt(st.nextToken());
                int val2 = Integer.parseInt(st.nextToken());

                String result;
                switch (methodName.toLowerCase()) {
                    case "add":
                        result = String.valueOf(add(val1, val2));
                        break;
                    case "sub":
                        result = String.valueOf(sub(val1, val2));
                        break;
                    case "mul":
                        result = String.valueOf(mul(val1, val2));
                        break;
                    case "div":
                        if (val2 == 0) {
                            result = "Error: Division by zero";
                        } else {
                            result = String.valueOf(div(val1, val2));
                        }
                        break;
                    default:
                        result = "Unknown method: " + methodName;
                        break;
                }

                byte b1[] = result.getBytes();
                DatagramPacket dp1 = new DatagramPacket(b1, b1.length, dp.getAddress(), 1300);
                ds.send(dp1);
                System.out.println("Processed: " + str + " | Result: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null && !ds.isClosed()) {
                ds.close();
            }
        }
    }

    public int add(int val1, int val2) {
        return val1 + val2;
    }

    public int sub(int val1, int val2) {
        return val1 - val2;
    }

    public int mul(int val1, int val2) {
        return val1 * val2;
    }

    public int div(int val1, int val2) {
        return val1 / val2;
    }

    public static void main(String[] args) {
        new RPCServer();
    }
}

//===================================================
import java.io.*;
import java.net.*;

class RPCClient {
    RPCClient() {
        DatagramSocket ds = null;
        DatagramSocket ds1 = null;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ds = new DatagramSocket();
            ds1 = new DatagramSocket(1300);
            System.out.println("\nRPC Client\n");
            System.out.println("Enter method name and parameter (e.g., add 5 10), or type 'exit' to quit.");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str;

            while (true) {
                System.out.print("Input: ");
                str = br.readLine();
                
                // Exit condition
                if ("exit".equalsIgnoreCase(str)) {
                    System.out.println("Exiting...");
                    break;
                }

                byte[] b = str.getBytes();
                DatagramPacket dp = new DatagramPacket(b, b.length, ia, 1200);
                ds.send(dp);

                // Prepare to receive the response
                dp = new DatagramPacket(new byte[1024], 1024);
                ds1.receive(dp);
                String response = new String(dp.getData(), 0, dp.getLength());
                System.out.println("\nResult = " + response + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null && !ds.isClosed()) {
                ds.close();
            }
            if (ds1 != null && !ds1.isClosed()) {
                ds1.close();
            }
        }
    }

    public static void main(String[] args) {
        new RPCClient();
    }
}
