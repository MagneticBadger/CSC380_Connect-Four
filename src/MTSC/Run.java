package MTSC;

import java.io.*;
import java.util.Arrays;

/**
 * Created by mjs3607 on 4/18/2016.
 */
public class Run
{
    static int scorePlayer0 = 0;
    static int scorePlayer1 = 0;
    static int draws = 0;
    static double[] scr;

    public static void run(int games, int it, double exp, boolean bounds, double pess, double opti) {
        MCTS player = new MCTS();
        player.setExplorationConstant(exp);
        player.setOptimisticBias(opti);
        player.setPessimisticBias(pess);
        player.setTimeDisplay(true);
        player.setScoreBounds(bounds);

        scorePlayer0 = 0;
        scorePlayer1 = 0;
        draws = 0;
        scr = new double[2];

        for (int i = 0; i < games; i++) {
            ConnectFour cf = new ConnectFour();
            while (true) {
                Move m = player.runMCTS(cf, it);
                cf.makeMove(m);
                cf.print();

                if (cf.gameOver()) {
                    scr = cf.getScore();
                    if (scr[0] == 1.0) {
                        scorePlayer0++;
                    } else if (scr[1] == 1.0) {
                        scorePlayer1++;
                    } else {
                        draws ++;
                    }

                    System.out.println("Score Vector: " + Arrays.toString(scr));
                    System.out.println(i + ": Scores: " + scorePlayer0 + " / " + scorePlayer1 + " Draws: " + draws);
                    break;
                }
            }

        }

        System.out.println("Iterations: " + it + " Exp. Con.: " + exp);
        System.out.println("pessBias: " + pess + " optiBias: " + opti);
        System.out.println("Score for this run: " + scorePlayer0 + " / " + scorePlayer1 + " Draws: " + draws);

        try {
            FileOutputStream fos = new FileOutputStream("newRuns_5.txt", true);
            Writer writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            writer.write("Iterations: " + it + " Exp. Con.: " + exp + ", Bounds = " + bounds + "\n");
            writer.write("pessBias: " + pess + " optiBias: " + opti + "\n");
            writer.write("Score for this run: " + scorePlayer0 + " / " + scorePlayer1 + " Draws: " + draws + "\n");
            writer.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
