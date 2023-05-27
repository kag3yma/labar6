package network;


import org.slf4j.LoggerFactory;
import requests.Request;
import utils.*;
import commands.Command;

import java.util.HashMap;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import ch.qos.logback.classic.*;
public class TCPServer{
    private ServerSocketChannel serverSocketChannel;
    private int port = 4444;
    protected SocketChannel clientSocket;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(TCPServer.class);
    public void start(HashMap<String, Command> map, CollectionHandler collectionHandler){
        openServerSocket();
        logger.info("waiting for connection...");
        while(serverSocketChannel!=null){
            try{
                this.clientSocket = serverSocketChannel.accept();
                logger.info("successfully connected");
                processRequest(map);
            } catch (IOException ioe) {
               logger.error("Connection error", ioe.getMessage());
            } catch (ClassNotFoundException ioe) {
                logger.error("Request error",ioe.getMessage());
            }
        }
        closeServerSocket();
    }

    private void openServerSocket() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        } catch (IOException e) {
            logger.error("Port error", e.getMessage());
        }
    }

    private void closeServerSocket() {
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            logger.error("Closing Port error", e.getMessage());
        }
    }

    private boolean processRequest(HashMap<String, Command> map) throws IOException, ClassNotFoundException {
        ObjectInput objectInput = new ObjectInputStream(clientSocket.socket().getInputStream());
        Request request = (Request) objectInput.readObject();
        if(map.containsKey(request.getCommandName())){
            logger.info("Requested command: "+request.getCommandName());
            map.get(request.getCommandName()).execute(request);
        } else {
            logger.error("Unknown command");
        }
        objectInput.close();
        return true;
    }

    public Socket getClientSocket(){
        return clientSocket.socket();
    }
}