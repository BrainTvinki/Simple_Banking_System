package banking;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void testForGit() {
        System.out.println("And now it new branch");
    }

    public void displayMainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public void displayLoggedMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }

    public int getCommand() throws InputMismatchException, IllegalArgumentException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the command:");
            try {
                int command = scanner.nextInt();
                if(command < 0 || command > 2) {
                    throw new IllegalArgumentException();
                }
                return command;
            } catch (Exception e) {
                System.out.println("Incorrect command, try again");
                return -1;
            }
    }
    public void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }


}

