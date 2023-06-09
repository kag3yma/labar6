package network;
import commands.*;
import data.SpaceMarine;
import exceptions.NehvataetArgumentaException;
import requests.Request;
import utils.CommandHelper;
import utils.MarineAsker;
import data.User;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import utils.Console;
public class TCPClient {
    private String host = "localhost";
    private int port = 4444;
    private SocketChannel clientSocket;

    public boolean connectToServer() throws IOException {
        try{
            this.clientSocket = SocketChannel.open(new InetSocketAddress(host, port));
            return true;
        } catch (IOException ioe) {
            Console.println("Connection error: " + ioe.getMessage());
            return false;
        }
    }

    public void closeConnection() throws IOException {
        this.clientSocket.close();
    }

    public boolean sendRequest(String input, User user) throws IOException, InterruptedException {
        String [] tokens = input.split("\\s+");
        try{
        if ((tokens.length<2) || (tokens.length>3) ) throw  new NehvataetArgumentaException();} catch (NehvataetArgumentaException e){
            Console.printerror("Wrong number of Arguments");
            return false;
        }
        String command = tokens[0];
        String argument = tokens[1];

        if (!CommandHelper.commandList().containsKey(command)){
            Console.printerror("no such command");
            return false;
        }

        if (command.equals("exit")){
            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute(argument);
            return true;
        }

        if (command.equals("help")){
            HelpCommand helpCommand = new HelpCommand();
            helpCommand.execute(argument);
            return true;
        }

        if (command.equals("execute_script")){
            new ExecuteScriptCommand(CommandHelper.commandList(), this, user).execute(argument);
            return true;
        }

        if (!CommandHelper.argCheckMap().get(command).argCheck(argument)){
            return false;
        }

        if (connectToServer()){
            ObjectOutputStream objectOutput = new ObjectOutputStream(this.clientSocket.socket().getOutputStream());
            InputStream in = new BufferedInputStream(clientSocket.socket().getInputStream());
            if (command.equals("add") || command.equals("update") || command.equals("add_if_max")
            || command.equals("add_if_min") || command.equals("remove_lower")){
                objectOutput.writeObject(new Request(command, argument, new MarineAsker().MarineCreator(user), user));
            } else {
                objectOutput.writeObject(new Request(command, argument, null, user));
            }

            String str_in = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            Console.print(str_in);
            in.close();
            objectOutput.close();
            closeConnection();
        }
        return true;
    }
    public String sendRequest(Request request)throws IOException, InterruptedException{
        if (connectToServer()){
            ObjectOutput objectOutput = new ObjectOutputStream(this.clientSocket.socket().getOutputStream());
            InputStream in = clientSocket.socket().getInputStream();
            objectOutput.writeObject(request);
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            closeConnection();
            return (message.trim()+"\n");
        }
        return "";
    }
}