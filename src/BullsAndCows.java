import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BullsAndCows {
    private int numberOfAttempts;
    private int gameNumber;
    private LocalDateTime gameDateTime;
    private String secretNumber;
    private List<String> gameLog;

    public BullsAndCows() {
        this.numberOfAttempts = 0;
        this.gameNumber = 0;
        this.secretNumber = "";
        this.gameLog = new ArrayList<String>();
    }

    public void startGame() {
        this.gameNumber = getLastGameNumber() + 1;
        this.gameDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String gameDateTimeString = this.gameDateTime.format(formatter);
        this.secretNumber = generateSecretNumber();
        this.gameLog.add(String.format("Game #%d %s Secret number: %s", this.gameNumber, gameDateTimeString, this.secretNumber));
        System.out.println(String.format("Game #%d started! Try to guess the secret number consisting of 4 different digits.", this.gameNumber));
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
        String result = String.format("%d быки %d коровы", bulls, cows);
        this.gameLog.add(String.format("Guess #%d: %s -> %s", this.numberOfAttempts, guess, result));
        System.out.println(result);
        if (bulls == 4) {
            this.endGame();
        }
    }

    private void endGame() {
        System.out.println(String.format("Строка была угадана за %d попыток.", this.numberOfAttempts));
        this.saveGameLogToFile();
    }

    private int getLastGameNumber() {
        int lastGameNumber = 0;
        try {
            java.io.File file = new java.io.File("game_log.txt");
            if (file.exists()) {
                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Game #")) {
                        lastGameNumber = Integer.parseInt(line.split("#")[1].split(" ")[0]);
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastGameNumber;
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

    private void saveGameLogToFile() {
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