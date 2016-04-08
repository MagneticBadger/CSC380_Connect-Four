import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mjs3607 on 4/7/2016.
 */
public class Efficiency {
    private ArrayList<Double> time = new ArrayList<Double>();
    private ArrayList<Double> space = new ArrayList<Double>();
    private ArrayList<Double> numberOfMoves = new ArrayList<Double>();
    private ArrayList<Double> numberOfWins = new ArrayList<Double>();

    private final double WIN_WEIGHT = 0.4;
    private final double TIME_WEIGHT = 0.25;
    private final double SPACE_WEIGHT = 0.25;
    private final double MOVE_WEIGHT = 0.1;

    double total;
    String algorithmName;

    public Efficiency(String name) throws IOException {
        this.algorithmName = name;
        calculateEfficiency();
        efficientWriter();
    }

    private void calculateEfficiency() {
        double runningSum = 0.0;

        for (int i = 0; i <= time.size(); i++) {
            runningSum += ((WIN_WEIGHT * numberOfWins.get(i)) +
                    (TIME_WEIGHT * (1 / time.get(i))) +
                    (SPACE_WEIGHT * (1 / space.get(i))) +
                    (MOVE_WEIGHT * numberOfMoves.get(i)));

        }

        total = runningSum / time.size();
    }

    private void readerIn() throws IOException {

        Scanner in = new Scanner(new File("runtimeData.csv"));
        String line;

        // csv values are: numberOfWins, time, space, numberOfMove
        while (in.hasNext()) {
            String[] s = in.nextLine().split(", ");

            numberOfWins.add(Double.parseDouble(s[0]));
            time.add(Double.parseDouble(s[1]));
            space.add(Double.parseDouble(s[2]));
            numberOfMoves.add(Double.parseDouble(s[3]));
        }


    }

    public void efficientWriter() throws IOException {
        File file = new File("finalTotal.csv");

        if (!file.createNewFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file, true);

        writer.append(algorithmName + ": \t" + total + "\n");
        writer.flush();
        writer.close();
    }


}


