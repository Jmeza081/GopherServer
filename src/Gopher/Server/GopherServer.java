package Gopher.Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Gopher.Server.Constants.*;


/**
 * Created by jmeza on 2/6/16.
 */
public class GopherServer {
    int portNumber = 0;
    ServerSocket mServerSocket;
    Socket ClientSocket;

    HashMap startingdir = new HashMap();
    HashMap sampleTextDir = new HashMap();


    public GopherServer(int PortNumber){
        portNumber = PortNumber;
        startingdir.put("About this server",Constants.text);
        startingdir.put("More sample text...",Constants.directory);
        sampleTextDir.put("More About Me", Constants.text);
        sampleTextDir.put("What I've been up to",Constants.text);
        sampleTextDir.put("Sample Book",Constants.text);
        sampleTextDir.put("More text", Constants.text);

    }

    public void CreateServer() throws IOException{
         mServerSocket = new ServerSocket(portNumber);
    }

    public void ConnectToClient() throws IOException{
        ClientSocket = mServerSocket.accept();
        System.out.println("Server started on port 8070");
    }

    public void StartUpMessage() throws IOException{
        OutputStream outputStream = ClientSocket.getOutputStream();
        PrintWriter outputWriter = new PrintWriter(outputStream,true);
        outputWriter.println("Welcome to Jesse Meza's Gopher Server Implementation!");
    }

    public void ProcessRequest(String UserInput) throws IOException{
        System.out.println(startingdir.keySet());
        if(UserInput.isEmpty()){
            respond(startingdir);
        }
        else if(startingdir.containsKey(UserInput)){
            readFromFile(UserInput+".txt","About/");
        }
        else if(UserInput.contentEquals("More sample text")){
            respond(sampleTextDir);
        }
        else if(sampleTextDir.containsKey(UserInput)){
            readFromFile(UserInput+".txt","MoreText/");
        }
        else{
            System.out.println("Your Resquest: " + UserInput + " could not be processed" );
            respond(startingdir);
        }


    }

    public void respond(HashMap map) throws IOException{
        String returnString = "";
        OutputStream outputStream = ClientSocket.getOutputStream();
        PrintWriter outputWriter = new PrintWriter(outputStream,true);
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry i = (Map.Entry)iterator.next();
            returnString += i.getValue().toString() + i.getKey().toString() +
                    "\t" + "fake" + "\t" + "Null" + "\t" + "0" + "\n";
        }
        returnString+=".";

        outputWriter.println(returnString);
        outputStream.close();
    }

    public void CloseClient() throws IOException{
        ClientSocket.close();
    }

    public Socket getClientSocket(){
        return ClientSocket;
    }

    private void readFromFile(String Filename, String Path) throws IOException{
        OutputStream outputStream = ClientSocket.getOutputStream();
        PrintWriter outputWriter = new PrintWriter(outputStream,true);
        FileReader textReader = new FileReader(Path+Filename);
        BufferedReader bufferedReader = new BufferedReader(textReader);
        String outPut;
        while ((outPut = bufferedReader.readLine()) != null){
            outputWriter.println(outPut);
        }
        textReader.close();
        bufferedReader.close();
    }

}
