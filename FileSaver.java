package packman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    private String filename;
    private BufferedWriter writer;
    private BufferedReader reader;
    private GameController gameController;
    private Game game;

    FileSaver() {
        this.filename="packmanFile.txt";
    }

    public void setGameController(GameController controller) {
        this.gameController=controller;
    }

    public void saveText(String text) throws IOException{
        try {
            reader = new BufferedReader(new FileReader(filename));
            writer = new BufferedWriter(new FileWriter(filename, true));

            //String oldText = readAllText();

            writer.write(text +". Points: " + game.getPoints() + "@");
        }
        catch (IOException e) {
            throw new IOException("En IO feil har skjedd i fillagring, melding: " + e.getMessage());
        }
    }
    public void setGame(Game game) {
        this.game=game;
    }

    public String readAllText() throws IOException{
        try {
            reader = new BufferedReader(new FileReader(filename));
            String text ="";
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                text+=line;
                line = reader.readLine(); //siste kommentar har alltid en \n bak seg
            }
            return text;
        }
        catch (IOException e) {
            throw new IOException("En IO feil har skjedd under fillesing, melding:" + e.getMessage());
        }
    }
    
    public void closeReader() throws IOException {
        reader.close();
    }
    public void closeWriter() throws IOException{
        writer.close();
    }
}
