
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BullsAndCows game = new BullsAndCows();
        game.startGame();
        Scanner scanner = new Scanner(System.in);
        while (game.getNumberOfAttempts() < 10) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine();
            game.guessNumber(guess);
        }
        scanner.close();
    }
}