package CoreClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jasmine on 29/01/2017.
 */

public class HighScores {

    public ArrayList<String> playerName = new ArrayList<>();
    public ArrayList<Long> scores = new ArrayList<>();


    public HighScores() {
        try {
            Scanner settingsReader = new Scanner(new File("data/highscores.txt"));
            String line;
            String name;
            while (settingsReader.hasNextLine()) {
                line = settingsReader.nextLine();
                int pos;
                for (pos = 0; line.charAt(pos) != '_' ; ++pos) ;
                name = line.substring(0, pos);
                playerName.add(name);
                scores.add(Long.valueOf(line.substring(pos + 1, line.indexOf(';'))));
            }
            settingsReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addHighScore(String name, long awesomeness){
        int i;
        for(i = playerName.size() - 1; i >= 0; --i){
            if(awesomeness >= scores.get(i)){
                scores.add(i + 1, scores.get(i));
            }
            else{
                break;
            }
        }
        scores.add(i + 1, awesomeness);
        playerName.add(i + 1, name);
        try(FileWriter writer = new FileWriter("data/highscores.txt", false)) {
            for (int j = 0; j < playerName.size(); ++j) {
                writer.write(playerName.get(j));
                writer.write('_');
                writer.write(String.valueOf(scores.get(j)));
                writer.write(";\n");
            }
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
}