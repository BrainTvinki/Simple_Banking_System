package banking;

import java.sql.SQLException;
import java.util.Scanner;



public class Main {
    static String pathToDB = "card.s3db";

    public static void main(String[] args) throws SQLException {
        if (args.length > 0) {
            if(args[0].equals("-fileName")) {
                pathToDB = args[1];
            }
        }
        int command;
        boolean isLogged = false;
        Account currentAccount = null;
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        DataBaser.connectByStart();
        while (true) {

            menu.displayMainMenu();
            command =  menu.getCommand();
            if (command == 0) {
                menu.exit();
            }
            if (command == 1) {
                currentAccount = Account.createNewAccount();
            }
            if (command == 2) {
                try {
                    currentAccount.setLogged(Account.logInAccount(currentAccount));
                } catch (NullPointerException e) {
                    System.out.println("There's no such account");
                }
            }
            while (currentAccount.isLogged()) {
                menu.displayLoggedMenu();
                command =  menu.getCommand();
                if (command == 0) {
                    menu.exit();
                }
                if (command == 1) {
                    currentAccount.showBalance();
                }
                if (command == 2) {
                    currentAccount.setLogged(currentAccount.logOutAccount());
                }
            }

        }



    }
}