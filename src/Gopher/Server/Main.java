package Gopher.Server;

import Gopher.Server.GopherServer;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        int portNumber = 8070;
        System.out.println("Creating Server on port " + portNumber);
        GopherServer GopherServer = new GopherServer(portNumber);
        GopherServer.CreateServer();

        //Listens indefinitely for client sockets.
        while(true){

            GopherServer.ConnectToClient();
            GopherServer.StartUpMessage();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(GopherServer.getClientSocket().getInputStream()));

            String string = bufferedReader.readLine();
            GopherServer.ProcessRequest(string);


            GopherServer.CloseClient();
        }




    }
}
