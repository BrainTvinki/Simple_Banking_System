package banking;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Account {



    boolean isLogged = false;
    long cardNumber;
    int pinCode;
    long balance = 0L;

    //final long CARD_NUMBER = 4000_0049_3832_0895L;
    //final int PIN_CODE = 6826;


    Account() throws SQLException {
        this.cardNumber = generateCardNumber();
        this.pinCode = (int) (Math.random() * 9000 + 1000);
        DataBaser.addNewAccountToDB(this.cardNumber,this.pinCode);
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    /*public long getCARD_NUMBER() {
        return CARD_NUMBER;
    }

     */

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }


    public static Account createNewAccount() throws SQLException {
        Account account = new Account();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPinCode());
        System.out.println();
        return account;
    }

    public static boolean logInAccount(Account account) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long cardNumber = scanner.nextLong();
        System.out.println("Enter your PIN:");
        int pinCode = scanner.nextInt();
        if(account.getCardNumber() == cardNumber && account.getPinCode() == pinCode) {
            System.out.println("You have successfully logged in!");
            return true;
        } else {
            System.out.println("Wrong card number or PIN!");
            return false;
        }
    }

    public boolean logOutAccount() {
        System.out.println("You have successfully logged out!");
        return false;
    }

    public void showBalance() {
        System.out.println("Balance: " + this.getBalance());
    }

    private long generateCardNumber() {
        int[] cardNumber = {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for(int i = 6; i < cardNumber.length - 1; i++) {
            cardNumber[i] = (int) (Math.random() * 10);
        }
        int[] algorithm = Arrays.copyOf(cardNumber, 16);
        int sum = 0;
        for(int i = 0; i < algorithm.length - 1; i++) {
            if(i % 2 == 0) {
                algorithm[i] = (algorithm[i] * 2);
            }
            if(algorithm[i] > 9) {
                algorithm[i] = algorithm[i] - 9;
            }
            sum += algorithm[i];
        }
        if(sum % 10 == 0) {
            cardNumber[15] = 0;
        } else {
            cardNumber[15] = 10 - (sum % 10);
        }
        System.out.println(Arrays.toString(cardNumber));
        StringBuilder cardNumberAsString = new StringBuilder("");
        for(int number : cardNumber) {
            cardNumberAsString.append(number);
        }
        return Long.parseLong(cardNumberAsString.toString());
    }

}
