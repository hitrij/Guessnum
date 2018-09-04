package guessnum;

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

            int userNum = scan.nextInt();
            int i;
            for (i = 1; i < a; i++) {
                if (userNum < myNum) {
                    System.out.println("Your number is less. " + (a - i) + " tries left");
                    userNum = scan.nextInt();
                } else if (userNum > myNum) {
                    System.out.println("Your number is bigger. " + (a - i) + " tries left");
                    userNum = scan.nextInt();
                } else {
                    System.out.println("You are the best");
                    break;
                }
            }
            if (i == a) System.out.println("You are the LOOSER");

            System.out.println("Do you want to play once again?");
            answer = scan.next();
        } while (answer.equals("y"));
        System.out.println("Good bye!");
    }
}
