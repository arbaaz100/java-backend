package bullscows;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static int turn = 1;
    static boolean flag = true;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        int num = 0;

        try {
            num = Integer.parseInt(input);
            if (num == 0) {
                System.out.println("Error: the secret code length should be atleast one.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int totalNum = scanner.nextInt();

        if (totalNum < num) {
            System.out.println("Error: it's not possible to generate a code with a length of " + num + " with " + totalNum + " unique symbols.");
            return;
        }

        String secretCode = "";

        if (totalNum > 36) {
            System.out.println("Error: Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        secretCode = Main.generateRandomNumber(num, (totalNum - 10));

        System.out.println("The random secret number is " + secretCode + ".");
        System.out.println("Okay, let's start a game!");
        while (flag) {
            Main.method(secretCode);
        }
    }

    public static String generateRandomNumber(int len, int letterLen) {
        Random random = new Random();

        HashSet<Character> set = new LinkedHashSet<>();
        StringBuilder randomNumber = new StringBuilder();
        StringBuilder randomLetter = new StringBuilder();
        StringBuilder starPattern = new StringBuilder();
        StringBuilder alphabetsRange = new StringBuilder("");

        for (int i = 0; i < 10; i++) { //0123456789
            randomNumber = randomNumber.append(i);
        }

        StringBuilder randomSecretCode = new StringBuilder();
        int limit = ((letterLen + 10) > 10) ? len - 1 : len;

        while (set.size() < limit) {
            int indexToFetchNumber = random.nextInt(0, 10);
            set.add(randomNumber.charAt(indexToFetchNumber));
            System.out.println(set);
            starPattern = starPattern.append('*');
        }

        for (char ch : set) {
            randomSecretCode = randomSecretCode.append(ch);
        }

        if (letterLen > 0) {
            for (char ch = 'a'; ch < 'a' + letterLen; ch++) { //abcdef..
                randomLetter = randomLetter.append(ch);
            }

            alphabetsRange = alphabetsRange.append(randomLetter.charAt(0)).append('-').append(randomLetter.charAt(letterLen - 1));

            char randomChar = randomLetter.charAt(random.nextInt(0, letterLen));
            randomSecretCode = randomSecretCode.insert(random.nextInt(0, len), randomChar);
            starPattern = starPattern.append('*');
        }

        System.out.println("The secret is prepared: " + starPattern + " (0-9, " + alphabetsRange + ").");

        return randomSecretCode.toString();
    }

    public static void method(String pseudoRandomNumber) {
        System.out.println("Turn " + turn + ":");
        int len = pseudoRandomNumber.length();
        Scanner scanner = new Scanner(System.in);
        String codeEntered = scanner.next();
        String secretCode = pseudoRandomNumber;

        int cows = 0;
        int bulls = 0;

        for (int i = 0; i < codeEntered.length(); i++) {
            char ch = codeEntered.charAt(i);
            if (secretCode.contains(ch + "")) {
                cows += 1;
                if (ch == secretCode.charAt(i)) {
                    bulls += 1;
                    cows -= 1;
                }
            }
        }

        if (cows == 0 && bulls == 0) {
            System.out.println("Grade: None. The secret code is " + secretCode + ".");
        } else if (cows == 0 && bulls > 0) {
            if (bulls == len) {
                System.out.println("Grade: " + len + " bulls\n" +
                        "Congratulations! You guessed the secret code.");
                flag = false;
                return;
            }
            String str = (bulls > 1) ? "bulls" : "bull";
            System.out.println("Grade: " + bulls + " " + str);
        } else if (cows > 0 && bulls == 0) {
            String str = (cows > 1) ? "cows" : "cow";
            System.out.println("Grade: " + cows + " " + str);
        } else {
            String str1 = (bulls > 1) ? "bulls" : "bull";
            String str2 = (cows > 1) ? "cows" : "cow";
            System.out.println("Grade: " + bulls + " " + str1 + " and " + cows + " " + str2);
        }

        turn += 1;
    }
}
