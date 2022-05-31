package banking;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Account {

    int id;
    boolean isLogged = false;
    long cardNumber;
    int pinCode;
    long balance = 0L;

    Account() {
        this.cardNumber = generateCardNumber();
        this.pinCode = (int) (Math.random() * 9000 + 1000);
        DataBaser.addNewAccountToDB(this.cardNumber,this.pinCode);
    }
    Account(int cardId) {
        this.id = cardId;
        this.isLogged = true;
        this.cardNumber = DataBaser.getCardNumber(cardId);
        this.pinCode = DataBaser.getCardPin(cardId);
        this.balance = DataBaser.getBalance(cardId);

    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public long getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public static void createNewAccount(){
        Account account = new Account();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPinCode());
        System.out.println();
        //return account;
    }

    public static Account logInAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long cardNumber = scanner.nextLong();
        System.out.println("Enter your PIN:");
        int pinCode = scanner.nextInt();
        int idLoggedAc = DataBaser.loggInDB(cardNumber, pinCode);
        if(idLoggedAc > 0) {
            System.out.println("You have successfully logged in!");
            System.out.println("Your id " + idLoggedAc);
            return new Account(idLoggedAc);
        } else {
            System.out.println("Wrong login/password pair!");
            return null;
        }
    }

    public boolean addIncome(Account account){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter income:");
        long addMoney = scanner.nextLong();
        if(DataBaser.setBalance( account.getBalance() + addMoney, account.getId())) {
            System.out.println("Income was added!");
            return true;
        } else {
            System.out.println("Something goes wrong");
            return false;
        }
    }

    public boolean transfer(Account account){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number:");
        String cardNumberForTransfering = scanner.nextLine();
        long cardNumberForTransferingAsLong = Long.parseLong(cardNumberForTransfering);
        if(!checkCardNumber(cardNumberForTransfering)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return false;
        }
        if(DataBaser.getIdByCardNumber(cardNumberForTransferingAsLong) == 0) {
            System.out.println("Such a card does not exist.");
            return  false;
        }
        System.out.println("Enter how much money you want to transfer:");
        long moneyForTransfering = scanner.nextLong();
        if(DataBaser.getBalance(account.getId()) >= moneyForTransfering) {
            DataBaser.setBalance(DataBaser.getBalance(account.getId()) - moneyForTransfering, account.getId());
            DataBaser.setBalance(DataBaser.getBalance(DataBaser.getIdByCardNumber(cardNumberForTransferingAsLong)) +
                    moneyForTransfering,DataBaser.getIdByCardNumber(cardNumberForTransferingAsLong));
            System.out.println("Success!");
            return true;
        } else {
            System.out.println("Not enough money!");
            return false;
        }
    }

    public boolean deleteAccount (Account account){
        if(DataBaser.deleteAccount( account.getId())) {
            System.out.println("The account has been closed!");
            return true;
        } else {
            System.out.println("Something goes wrong");
            return false;
        }
    }

    public void showBalance() {

        System.out.println("Balance: " + DataBaser.getBalance(this.id));
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
        StringBuilder cardNumberAsString = new StringBuilder();
        for(int number : cardNumber) {
            cardNumberAsString.append(number);
        }
        return Long.parseLong(cardNumberAsString.toString());
    }


    private static boolean checkCardNumber(String value) {
        int sum = Character.getNumericValue(value.charAt(value.length() - 1));
        int parity = value.length() % 2;
        for (int i = value.length() - 2; i >= 0; i--) {
            int summand = Character.getNumericValue(value.charAt(i));
            if (i % 2 == parity) {
                int product = summand * 2;
                summand = (product > 9) ? (product - 9) : product;
            }
            sum += summand;
        }
        return (sum % 10) == 0;
    }

}
