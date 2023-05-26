package network;


import requests.Request;
import utils.*;
import commands.Command;

import java.util.HashMap;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TCPServer{
    private ServerSocketChannel serverSocketChannel;
    private int port = 4444;
    protected SocketChannel clientSocket;
    public void start(HashMap<String, Command> map, CollectionHandler collectionHandler){
        openServerSocket();
        while(serverSocketChannel!=null){
            System.out.println("waiting for connection...");
            try{
                this.clientSocket = serverSocketChannel.accept();
                System.out.println("successfully connected");
                processRequest(map);
            } catch (IOException ioe) {
                System.out.println("Connection error"+ioe.getMessage());
            } catch (ClassNotFoundException ioe) {
                System.out.println("Request error"+ioe.getMessage());
            }
        }
        closeServerSocket();
    }

    private void openServerSocket() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        } catch (IOException e) {
            System.out.println("Port error"+ e.getMessage());
        }
    }

    private void closeServerSocket() {
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            System.out.println("Closing Port error");
        }
    }

    private boolean processRequest(HashMap<String, Command> map) throws IOException, ClassNotFoundException {
        ObjectInput objectInput = new ObjectInputStream(clientSocket.socket().getInputStream());
        Request request = (Request) objectInput.readObject();
        if(map.containsKey(request.getCommandName())){
            map.get(request.getCommandName()).execute(request);
        } else {
            System.out.println("Unknown command");
        }
        objectInput.close();
        return true;
    }

    public Socket getClientSocket(){
        return clientSocket.socket();
    }
}