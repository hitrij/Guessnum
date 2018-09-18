package guessnum;

import jdk.dynalink.beans.StaticClass;

import javax.swing.text.DefaultEditorKit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static Random rand = new Random();
    static Scanner scan = new Scanner(System.in);
    static ArrayList<GameResult> results = new ArrayList<>();
    private static GameResult gameResult;

    public static void main(String[] args) {
        loadResults();
        String playAgain;
        String userName;
        Long time1;
        Long time2;

        do {
            do {
                System.out.println("Please enter your name:");
                userName = scan.next();
            } while (userName == "");

            int myNum = rand.nextInt(100) + 1;
            int numberOfTries = 5;
            System.out.println(myNum);
            System.out.println(userName + ", guess the number from 1 to 100!");
            System.out.println("===============================");
            boolean looser = true;
            time1 = System.currentTimeMillis();
            for (int i = 0; i < numberOfTries; i++) {
                System.out.println("You have " + (numberOfTries - i) + " tries left.");

                //int userNum = scan.nextInt();
                int userNum = askNum();
                if (myNum > userNum) {
                    System.out.println("The number is greater.");
                    System.out.println("-------------------------------");
                } else if (myNum < userNum) {
                    System.out.println("The number is smaller.");
                    System.out.println("-------------------------------");
                } else {
                    System.out.println("____________________________");
                    System.out.println("BINGO, " + userName + "!!!");
                    looser = false;

                    GameResult r = new GameResult();
                    r.name = userName;
                    r.triesCount = i + 1; // herotenj!!!
                    time2 = System.currentTimeMillis();
                    Long timeSpent = (time2 - time1);
                    r.time = timeSpent;
                    results.add(r);
                    results.sort(Comparator.<GameResult>comparingInt(r0 -> r0.triesCount)
                            .thenComparingLong(r0 -> r0.time));
                    System.out.println("Time spent in seconds: " + timeSpent / 1000);
                    break;
                }

            }
            if (looser) {
                System.out.println("You loose!");
                System.out.println("The number is " + myNum);
                GameResult r = new GameResult();
                r.name = userName;
                r.triesCount = 9999; // herotenj!!!
                time2 = System.currentTimeMillis();
                Long timeSpent = (time2 - time1);
                r.time = timeSpent;
                results.add(r);
                results.sort(Comparator.<GameResult>comparingInt(r0 -> r0.triesCount)
                        .thenComparingLong(r0 -> r0.time));
                System.out.println("Time spent in seconds: " + timeSpent / 1000);
            }

            System.out.println("");
            System.out.println("______________________________");
            System.out.println("Do you want to play again? y/n");
            playAgain = askYN();
        } while (playAgain.equalsIgnoreCase("y"));

        showResults();
        saveResults();

        System.out.println("BYE");
    }

    private static void loadResults() {
        File file = new File("top_scores.txt");
        try (Scanner in = new Scanner(file)) {
            while (in.hasNext()) {
                GameResult result = new GameResult();
                result.name = in.next();
                result.triesCount = in.nextInt();
                result.time = in.nextLong();
                results.add(result);
            }
        } catch (IOException e) {
            System.out.println("Cannot save to File");
        }

    }

    private static void saveResults() {

        File file = new File("top_scores.txt");

        try (PrintWriter out = new PrintWriter(file)) {
            for (GameResult r : results) {
                out.printf("%s %d %d\n", r.name, r.triesCount, r.time);
            }
        } catch (IOException e) {
            System.out.println("Cannot save to File");
        }

    }

    private static void showResults() {
//        int i=0;
//        for (GameResult r : results) {
//            System.out.printf("%s tried %d times in %dsec\n", r.name, r.triesCount, r.time/1000);
//            i ++;
//            if (i==3) break;
//        }
    results.stream()
            .sorted(Comparator.<GameResult>comparingInt(r -> r.triesCount)
                    .thenComparingLong(r -> r.time))
            .limit(3)
            .forEach(r -> {
        System.out.printf("%s tried %d times in %.2f sec\n", r.name, r.triesCount, r.time/1000.0);
    });
    }

    static String askYN() {
        String answer;
        do {
            answer = scan.next();
            if (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.println("Answer y or n!");
                continue;
            } else {
                return answer;
            }
        } while (true);
    }

    static int askNum() {
        System.out.println("Please try to guess the number:");
        int userNum;
        do {
            try {
                userNum = scan.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Please enter the number");
                scan.next();
                continue;
            }
            if (userNum > 0 && userNum < 101) {
                return userNum;
            } else {
                System.out.println("The number should from 1 to 100. Try again:");
                continue;
            }
        } while (true);
    }
}