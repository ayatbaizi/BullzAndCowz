import java.io.IOException;

public class ResultReader {
    public int getLastGameNumber() {
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
}
