import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private int numberOfAttempts;
    private int gameNumber;
    private LocalDateTime gameDateTime;
    private String secretNumber;
    private List<String> gameLog;

    public Game() {
        this.numberOfAttempts = 0;
        this.gameNumber = 0;
        this.secretNumber = "";
        this.gameLog = new ArrayList<String>();
    }
    ResultReader resultReader = new ResultReader();
    public void startGame() {
        this.gameNumber = resultReader.getLastGameNumber() + 1;
        this.gameDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String gameDateTimeString = this.gameDateTime.format(formatter);
        this.secretNumber = generateSecretNumber();
        this.gameLog.add(String.format("Game #%d %s Загаданная строка: %s", this.gameNumber, gameDateTimeString, this.secretNumber));
        System.out.println(String.format("Game #%d началась! Попытайся угадать четырехзначное число", this.gameNumber));
    }

    public int getNumberOfAttempts() {
        return this.numberOfAttempts;
    }

    public void guessNumber(String guess) {
        this.numberOfAttempts++;
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < 4; i++) {
            char c = guess.charAt(i);
            if (c == this.secretNumber.charAt(i)) {
                bulls++;
            } else if (this.secretNumber.indexOf(c) != -1) {
                cows++;
            }
        }
        String result = String.format("%d %s %d %s", bulls, (bulls == 1 ? "бык" : "быка"), cows,
                (cows == 1 ? "корова" : (cows >= 2 && cows <= 4 ? "коровы" : "коров")));

        this.gameLog.add(String.format("Запрос #%d: %s -> %s", this.numberOfAttempts, guess, result));
        System.out.println(result);
        if (bulls == 4) {
            this.endGame();
        }
    }

    private void endGame() {
        String resultGuess = String.format("Строка была угадана за %d %s.", this.numberOfAttempts,
                (this.numberOfAttempts == 1 ? "попытку" : (this.numberOfAttempts >= 2 && this.numberOfAttempts <= 4 ? "попытки" : "попыток")));

        System.out.println(String.format("Строка была угадана за %d попыток.", this.numberOfAttempts));
        System.out.println("Попоробуйте угадать новое число! Для выхода введите stop");
        this.gameLog.add(resultGuess);
        this.saveGameLogToFile();
    }

    private String generateSecretNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        List<Integer> digits = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(digits.size());
            sb.append(digits.get(index));
            digits.remove(index);
        }
        return sb.toString();
    }

    public void saveGameLogToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("game_log.txt", true));
            for (String log : this.gameLog) {
                writer.write(log);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}