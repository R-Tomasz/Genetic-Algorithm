package sample;

import javafx.scene.Node;
import sample.gui.gameView.GameViewModel;
import sample.gui.gameView.Player;

import java.util.Arrays;

public class Individual extends Player{
    double fitness = 0;
    int[][] genes = new int[16][8];

    public Individual() {
        super((double)GameViewModel.sceneWidth/2, (double)GameViewModel.sceneHeight/2, GameViewModel.playerRadius);
        for (int i = 0; i < genes.length; i++) {
            for(int j = 0 ; j < genes[i].length ; j++)
                genes[i][j] = (int) Math.round(Math.random());
        }
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

//    public double calculateFitness() {
//        fitness = 0;
//        for (int i = 0; i < genes.length; i++) {
//            if (genes[i] == 1)
//                ++fitness;
//        }
//        return fitness;
//    }

    @Override
    public String toString() {
        return "Individual{" +
                "genes=" + Arrays.toString(genes) +
                "}\n";
    }

    public int[][] getGenes() {
        return genes;
    }
}
