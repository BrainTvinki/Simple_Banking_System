package banking;

import java.sql.SQLException;

public class Main {
    static String pathToDB = "card.s3db";

    public static void main(String[] args) {
        if (args.length > 0) {
            if(args[0].equals("-fileName")) {
                pathToDB = args[1];
            }
        }
        int command;
        Account currentAccount = null;
        Menu menu = new Menu();
        DataBaser.connectByStart();
        while (true) {
            menu.displayMainMenu();
            command =  menu.getCommand();
            if (command == 0) {
                menu.exit();
            }
            if (command == 1) {
                Account.createNewAccount();
            }
            if (command == 2) {
                try {
                    currentAccount = Account.logInAccount();
                } catch (NullPointerException e) {
                    System.out.println("There's no such account");
                }
            }
            while (currentAccount != null) {
                menu.displayLoggedMenu();
                command =  menu.getCommand();
                if (command == 0) {
                    menu.exit();
                }
                if (command == 1) {
                    currentAccount.showBalance();
                }
                if (command == 2) {
                    currentAccount.addIncome(currentAccount);
                }
                if (command == 3) {
                    currentAccount.transfer(currentAccount);
                }
                if (command == 4) {
                    currentAccount.deleteAccount(currentAccount);
                }
                if (command == 5) {
                    currentAccount = null;
                    System.out.println("You have successfully logged out!");
                }
            }
        }
    }
}