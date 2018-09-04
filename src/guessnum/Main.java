package guessnum;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Random iRand = new Random();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String answer;
        do {
            int myNum = iRand.nextInt(100);
            int a;
            a = 5;
            System.out.println(myNum);
            System.out.println("You have " + a + " chooses");

            int userNum = askNum();
            int i;
            for (i = 1; i < a; i++) {
                if (userNum < myNum) {
                    System.out.println("Your number is less. " + (a - i) + " tries left");
                    userNum = askNum();
                } else if (userNum > myNum) {
                    System.out.println("Your number is bigger. " + (a - i) + " tries left");
                    userNum = askNum();
                } else {
                    System.out.println("You are the best");
                    break;
                }
            }
            if (i == a) System.out.println("You are the LOOSER");

            System.out.println("Do you want to play once again?");
            answer = askYN();
        } while (answer.equals("y"));
        System.out.println("Good bye!");
    }

    static String askYN() {
        String answer;
        do {
            answer = scan.next();
            if (!answer.equals("y") && !answer.equals("n")) System.out.println("Please choose: y or n");
        } while (!answer.equals("y") && !answer.equals("n"));
        return answer;

    }

    static int askNum() {
        int aNum;
        do {
            try {
                aNum = scan.nextInt();
            }catch (InputMismatchException e) {
                System.out.println("Please enter number");
                scan.next();
                aNum = -1;
                continue;
            }

            if (aNum > 100 || aNum < 0) System.out.println("Please enter Number from [0;100]");
        } while (aNum > 100 || aNum < 0);
        return aNum;
    }
}

