package utils;

import data.User;
import network.TCPClient;
import requests.Request;

import java.io.IOException;
import java.util.Scanner;

public class UserHelper {
    TCPClient client;
    public User user;

    public UserHelper(TCPClient client){
        this.client = client;
    }

    public String sendRegister(User user){
        try {
            return client.sendRequest(new Request("register","placeholderArg", null, user));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "Error while getting response";
    }
    public String sendLogin(User user){
        try {
            return client.sendRequest(new Request("login","placeholderArg",null,user));
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        } return "Error while getting response";
    }
    public User createUser(Scanner scanner){
        System.out.println("Enter login:");
        String login = scanner.nextLine();
        System.out.println("Enter password:");
        String pwd = scanner.nextLine();
        if (login.trim().isBlank()){
            Console.printerror("Login cannot be empty");
            return createUser(scanner);
        }
        if(pwd.trim().isBlank()){
            Console.printerror("Password cannot be empty");
            return createUser(scanner);
        }return new User(login, pwd);
    }

    public boolean ask(Scanner scanner){
        Console.println("Register/Login[R/L]");
        String input = scanner.nextLine();
        if (input.equals("R")){
            user = createUser(scanner);
            String register = sendRegister(user);
            Console.println(register);
            if (register.trim().equals("User successfully registered".trim())){
                return true;
            }else {
                ask(scanner);
            }
        }
        if (input.equals("L")){
            user = createUser(scanner);
            String login = sendLogin(user);
            Console.println(login);
            if (login.trim().equals("Now you are able to use all commands. Use help to see all available commands".trim())){
                return true;
            } else {
                ask(scanner);
            }
        }return ask(scanner);
    }

}
