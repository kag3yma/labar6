package run;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import commands.*;
import requests.Request;
import utils.Console;
import utils.*;
import network.TCPServer;

public class App {


    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CollectionHandler collectionHandler = new CollectionHandler();
        MarineAsker marineAsker = new MarineAsker(scanner);
        DatabaseHandler databaseHandler = new DatabaseHandler();
        try{
            collectionHandler.setCollection(databaseHandler.getAllSpaceMarines());
        }catch (SQLException e){
            e.printStackTrace();
        }
        TCPServer server = new TCPServer();

        Command info = new InfoCommand(collectionHandler);
        Command show = new ShowCommand(collectionHandler);
        Command add = new AddCommand(collectionHandler, databaseHandler);
        Command update = new UpdateCommand(collectionHandler, databaseHandler);
        Command removeById = new RemoveByIdCommand(collectionHandler, databaseHandler);
        Command clear = new ClearCommand(collectionHandler);
        Command save = new SaveCommand(collectionHandler,databaseHandler);
        Command exit = new ExitCommand();
        Command addIfMin = new AddIfMinCommand(collectionHandler, databaseHandler);
        Command addIfMax = new AddIfMaxCommand(collectionHandler, databaseHandler);
        Command removeLower = new RemoveLowerCommand(collectionHandler, databaseHandler);
        Command averageOfHealth = new AverageOfHealthCommand(collectionHandler, databaseHandler);
        Command filterStartsWithName = new FilterStartsWithNameCommand(collectionHandler);
        Command countGreaterThanMeleeWeapon = new CountGreaterThanMeleeWeaponCommand(collectionHandler);
        Command register = new RegisterCommand(databaseHandler);
        Command login = new LoginCommand(databaseHandler);
        Command load = new LoadCommand(collectionHandler);


        HashMap<String, Command> map = new HashMap<String, Command>();
        map.put(info.getName(), info);
        map.put(show.getName(), show);
        map.put(add.getName(), add);
        map.put(removeById.getName(), removeById);
        map.put(update.getName(), update);
        map.put(clear.getName(), clear);
        map.put(filterStartsWithName.getName(), filterStartsWithName);
        map.put(addIfMin.getName(), addIfMin);
        map.put(addIfMax.getName(), addIfMax);
        map.put(removeLower.getName(),removeLower);
        map.put(averageOfHealth.getName(), averageOfHealth);
        map.put(countGreaterThanMeleeWeapon.getName(), countGreaterThanMeleeWeapon);
        map.put(register.getName(), register);
        map.put(login.getName(), login);
        map.put(load.getName(), load);

        Command executeScriptCommand = new ExecuteScriptCommand(map);
        map.put(executeScriptCommand.getName(), executeScriptCommand);

        new Thread(() ->{
            while(true){
                String command = scanner.nextLine();
                if(command.trim().equals(exit.getName())){
                    scanner.close();
                    save.execute(new Request("save", "placeholderArg",null,null));
                    exit.execute(new Request("exit", "placeholderArg",null,null));
                }
                if(command.trim().equals(save.getName())){
                    save.execute(new Request("save", "placeholderArg",null,null));
                } else {
                    Console.printerror("No Such Command, server can run only save and exit commands");
                }
            }
        }).start();

        new Thread(() -> server.start(map,collectionHandler)).start();
    }
}