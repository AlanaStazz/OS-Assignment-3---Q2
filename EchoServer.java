//OS Ass 2 - Q1 - Server
//Alana Staszczyszyn & Elijah Tavenor
//October 19, 2017

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static class HandleEcho implements Runnable{
        BufferedInputStream inStream;
        PrintWriter pout;
        String msg;
        int data;
        Socket client;

        public HandleEcho(Socket client) {
            try {
                this.client = client;

                //Create client input stream
                this.inStream =  new BufferedInputStream(client.getInputStream());

                //Create output stream
                this.pout = new PrintWriter(client.getOutputStream(), true);

                //Print server prompt
                this.msg = "\nServer: ";

                System.out.println(msg);

            } catch(IOException e){
                System.out.println(e);
            }
        }

        public void exe(){
            try {
                //Grab data from the input stream
                data = inStream.read();

                //While it's not a newline (i.e. not end of data stream)
                while (data != 10) {
                    //Keep appending, reading next data, and printing
                    System.out.println(data + " " + (char) data);
                    msg += ((char) data);
                    data = inStream.read();
                }

                //Print the data to the output stream
                pout.println(msg);

                //End the connection
                client.close();
            } catch(IOException e){
                System.out.println(e);
            }
        }


        public void run() {
            exe();
        }
    }

    public static void main(String args[]){
        try{
            Boolean on = true;
            //Make new socket
            ServerSocket sock = new ServerSocket(666);
            //Create runnable object
            HandleEcho serv;

            //While listening...
            while(on){
                System.out.println("LOOPING");
                //Listen for attempted connection
                Socket client = sock.accept();

                //Construct thread using socket and run
                serv = new HandleEcho(client);
                serv.run();


            }
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }
}
