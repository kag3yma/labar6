package run;

import commands.Command;
import commands.HelpCommand;
import network.TCPClient;
import utils.Console;
import utils.UserHelper;

import java.io.IOException;
import java.util.Scanner;

public class App1 {


    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        TCPClient client = new TCPClient();
        Command help = new HelpCommand();
        UserHelper userHelper = new UserHelper(client);
        boolean logged_in = false;
        while (!logged_in){
            logged_in = userHelper.ask(scanner);
        }
        while (true){
            Console.print(PS1);
            String input = scanner.nextLine();
            Console.print(client.sendRequest(input + " placeholderArg", userHelper.user));
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