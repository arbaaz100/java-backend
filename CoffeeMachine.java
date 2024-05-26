package machine;

import java.util.Scanner;
import java.lang.Math;

public class CoffeeMachine {
    static int waterAvailable = 400, milkAvailable = 540, beansAvailable = 120, cupsAvailable = 9, money = 550;

    private static final int ESPRESSO_WATER = 250;
    private static final int ESPRESSO_MILK = 0;
    private static final int ESPRESSO_BEANS = 16;
    private static final int ESPRESSO_PRICE = 4;
    private static final int LATTE_WATER = 350;
    private static final int LATTE_MILK = 75;
    private static final int LATTE_BEANS = 20;
    private static final int LATTE_PRICE = 7;
    private static final int CAPPUCCINO_WATER = 200;
    private static final int CAPPUCCINO_MILK = 100;
    private static final int CAPPUCCINO_BEANS = 12;
    private static final int CAPPUCCINO_PRICE = 6;


    enum UserState {
        REMAINING, FILL, TAKE, EXIT, BUY
    }

    public void processInput(UserState userState, String input) {
        switch (userState) {
            case REMAINING:
                showRemainingIngredients();
                break;
            case BUY:
                buyCoffee(input);
                break;
            case FILL:
                fillMachineContents();
                break;
            case TAKE:
                takeMoneyFromMachine();
                break;
            default:
                break;
        }
    }

    private void takeMoneyFromMachine() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    private void fillMachineContents() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        waterAvailable += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milkAvailable += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        beansAvailable += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        cupsAvailable += scanner.nextInt();
    }

    private void buyCoffee(String choice) {
        switch (choice) {
            case "1":
                makeCoffee(ESPRESSO_WATER, ESPRESSO_MILK, ESPRESSO_BEANS, ESPRESSO_PRICE);
                break;
            case "2":
                makeCoffee(LATTE_WATER, LATTE_MILK, LATTE_BEANS, LATTE_PRICE);
                break;
            case "3":
                makeCoffee(CAPPUCCINO_WATER, CAPPUCCINO_MILK, CAPPUCCINO_BEANS, CAPPUCCINO_PRICE);
                break;
            default:
                break;
        }
    }


    private void makeCoffee(int water, int milk, int beans, int price) {
        if (waterAvailable >= water && milkAvailable >= milk && beansAvailable >= beans && cupsAvailable > 0) {
            System.out.println("I have enough resources, making you a coffee!");
            waterAvailable -= water;
            milkAvailable -= milk;
            beansAvailable -= beans;
            money += price;
            cupsAvailable -= 1;
        } else {
            if (waterAvailable < water)
                System.out.println("Sorry, not enough water!");
            if (milkAvailable < milk)
                System.out.println("Sorry, not enough milk!");
            if (beansAvailable < beans)
                System.out.println("Sorry, not enough coffee beans!");
        }
    }

    private void showRemainingIngredients() {
        System.out.println("The coffee machine has:\n" +
                waterAvailable + " ml of water\n" +
                milkAvailable + " ml of milk\n" +
                beansAvailable + " g of coffee beans\n" +
                cupsAvailable + " disposable cups\n" +
                "$" + money + " of money");
    }

}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String input = scanner.next();
            CoffeeMachine.UserState userState = CoffeeMachine.UserState.valueOf(input.toUpperCase());
            if (userState == CoffeeMachine.UserState.EXIT) break;
            else if (userState == CoffeeMachine.UserState.BUY) {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                input = scanner.next();
            }
            coffeeMachine.processInput(userState, input);
        }
    }
}
