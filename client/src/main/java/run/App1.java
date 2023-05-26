package run;

import java.io.IOException;
import java.util.Scanner;

import commands.Command;
import commands.HelpCommand;
import utils.*;
import network.TCPClient;

public class App1 {


    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner userScanner = new Scanner(System.in);
        TCPClient client = new TCPClient();
        Command help = new HelpCommand();
        while (true){
            Console.print(PS1);
            String input = userScanner.nextLine();
            input = input + " placeholderArg";
            client.sendRequest(input);
        }
    }
}
//MarineAsker marineAsker = new MarineAsker(userScanner);
//FileManager fileManager = new FileManager(nameFile);
//CollectionHandler collectionHandler = new CollectionHandler(fileManager);
//CommandManager commandManager = new CommandManager(
//         new HelpCommand(),
//        new InfoCommand(collectionHandler),
//        new ShowCommand(collectionHandler),
//        new AddCommand(collectionHandler, marineAsker),
//        new UpdateCommand(collectionHandler, marineAsker),
//        new RemoveByIdCommand(collectionHandler),
//        new ClearCommand(collectionHandler),
//        new ExitCommand(),
//        new ExecuteScriptCommand(),
//       new AddIfMinCommand(collectionHandler, marineAsker),
//       new AddIfMaxCommand(collectionHandler, marineAsker),
//       new RemoveLowerCommand(collectionHandler, marineAsker),
//       new AverageOfHealthCommand(collectionHandler),
//       new FilterStartsWithNameCommand(collectionHandler),
//       new CountGreaterThanMeleeWeaponCommand(collectionHandler)
//);
// Console console = new Console(commandManager, userScanner, marineAsker);
// Console console = new Console(commandManager, userScanner);
//console.interactiveMode();