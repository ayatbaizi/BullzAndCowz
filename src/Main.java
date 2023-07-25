import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean playing = true;
        Game game = new Game();
        game.startGame();
        Scanner scanner = new Scanner(System.in);
        while (playing) {
            System.out.print("Введите ваш запрос: ");
            String guess = scanner.nextLine();
            if (guess.equalsIgnoreCase("stop")) {
                playing = false;
            } else {
                game.guessNumber(guess);
            }
        }
    }
}