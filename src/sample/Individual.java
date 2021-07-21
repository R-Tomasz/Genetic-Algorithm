package sample;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import sample.gui.gameView.GameViewModel;
import sample.gui.gameView.Player;

import java.util.Arrays;


public class Individual extends Player {
    double fitness = 0;
    private int topDistance;
    private int leftDistance;
    private int bottomDistance;
    private int rightDistance;

    private double distanceToPoint;

    int[][] genes = new int[16][8]; // tablica dwuwymiarowa osobnika z 16 ciągami bitów, po 8 bitów każdy

    String[] stringGenes = new String[16]; // ciąg bitów reprezentowany w formie tekstowej
    public double[] parameters = new double[16]; // wyliczone z decimal values parametry od -1 do 1


    double[] chancesForDirections = new double[4]; // "szanse" dla gracza na pójście w kierunkach kolejno WASD


    public Individual() {
        super(50, 50, GameViewModel.playerRadius);
        for (int i = 0; i < genes.length; i++) {
            for (int j = 0; j < genes[i].length; j++)
                genes[i][j] = (int) Math.round(Math.random());
        }
    }

    public void calcTopDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int y = (int) this.getCenterY(); y > 0; y--) {
            for (int x = (int) this.getCenterX() - (int) this.getRadius(); x <= (int) this.getCenterX() + (int) this.getRadius(); x++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setTopDistance((int) this.getCenterY() - y + 25 * -1);
                    break outer;
                }
                setTopDistance((int) getCenterY() - 25);
            }
        }
//        System.out.println("TOP:  " + topDistance);
    }

    public void calcLeftDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int x = (int) this.getCenterX(); x > 0; x--) {
            for (int y = (int) this.getCenterY() - (int) this.getRadius(); y <= (int) this.getCenterY() + (int) this.getRadius(); y++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setLeftDistance((int) this.getCenterX() - x + 25 * -1);
                    break outer;
                }
                setLeftDistance((int) getCenterX() - 25);
            }
        }
//        System.out.println("LEFT:  " + leftDistance);
    }

    public void calcBottomDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int y = (int) this.getCenterY(); y < img.getHeight(); y++) {
            for (int x = (int) this.getCenterX() - (int) this.getRadius(); x <= (int) this.getCenterX() + (int) this.getRadius(); x++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setBottomDistance(y - (int) this.getCenterY() - 25);
                    break outer;
                }
                setBottomDistance(GameViewModel.sceneHeight - (int) getCenterY() - 25);
            }
        }
//        System.out.println("BOTTOM:  " + bottomDistance);
    }

    public void calcRightDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int x = (int) this.getCenterX(); x < img.getWidth(); x++) {
            for (int y = (int) this.getCenterY() - (int) this.getRadius(); y <= (int) this.getCenterY() + (int) this.getRadius(); y++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setRightDistance(x - (int) this.getCenterX() - 25);
                    break outer;
                }
                setRightDistance(GameViewModel.sceneWidth - (int) getCenterX() - 25);
            }
        }
//        System.out.println("RIGHT:  " + rightDistance);
    }

    public void calcPointDistance() {
        setDistanceToPoint((Math.sqrt((GameViewModel.pointX - this.getCenterX()) * (GameViewModel.pointX - this.getCenterX()) + (GameViewModel.pointY - this.getCenterY()) * (GameViewModel.pointY - this.getCenterY()))) - 55);
    }

    public void calcDistancesToAllObstaclesAndPoint(Image img) {
        calcTopDistance(img);
        calcLeftDistance(img);
        calcBottomDistance(img);
        calcRightDistance(img);
        calcPointDistance();
    }

    public void moveSomewhere() {

        double min = chancesForDirections[0];

        int temp = 0;
        for (int i = 1; i < chancesForDirections.length; i++) {
            if (chancesForDirections[i] < min) {
                temp = i;
//                System.out.println("-------------");
//                System.out.println("GORA:  "+chancesForDirections[0]);
//                System.out.println("LEWO:  "+chancesForDirections[1]);
//                System.out.println("DOL:  "+chancesForDirections[2]);
//                System.out.println("PRAWO:  "+chancesForDirections[3]);
            }
        }
        switch (temp) {
            case (0) -> moveUp();
            case (1) -> moveLeft();
            case (2) -> moveDown();
            case (3) -> moveRight();
        }
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }


    public void geneIntToString() { //zamiana genow z tablicy typu int na tablice typu string w celu obliczenia wartosci dziesietnych
        for (int i = 0; i < genes.length; i++) {
            stringGenes[i] = "";
            for (int j = 0; j < genes[0].length; j++) {
                stringGenes[i] = stringGenes[i].concat(String.valueOf(genes[i][j])); // z tablicy np [1,0,0,1] tworzy się string "1001"
            }
        }
    }

    public void calculateValueFromGene() { // zamiana z ciągu binarnego na system dziesiętny oraz wyliczenie wartości dziesiętnych (parametrów)
        geneIntToString();
        int[] decimalValues = new int[16]; // ciągi bitów zamienione na system dziesiętny

        for (int i = 0; i < stringGenes.length; i++) {
            decimalValues[i] = Integer.parseInt(stringGenes[i], 2); // parsowanie z ciągu binarnego typu string na system dziesiętny
            parameters[i] = (double) decimalValues[i] / 128 - 1;
        }
    }

    public void calculateMove() {
        calculateValueFromGene();

        int[] distancesToObstacles = new int[]{getTopDistance(), getLeftDistance(), getBottomDistance(), getRightDistance()};

        double[] up = new double[4];
        double[] left = new double[4];
        double[] down = new double[4];
        double[] right = new double[4];
        int temp = 0;

        for (int i = 0; i < chancesForDirections.length; i++) {
            up[i] = (double) distancesToObstacles[0] * parameters[temp];
            left[i] = (double) distancesToObstacles[1] * parameters[temp + 1];
            down[i] = (double) distancesToObstacles[2] * parameters[temp + 2];
            right[i] = (double) distancesToObstacles[3] * parameters[temp + 3];
            chancesForDirections[i] = up[i] + left[i] + down[i] + right[i];
            temp += 4;
//            System.out.println("----------------------------");
//            System.out.println("TOP:  " + chancesForDirections[0]);
//            System.out.println("LEFT:  " + chancesForDirections[1]);
//            System.out.println("BOTTOM:  " + chancesForDirections[2]);
//            System.out.println("RIGHT:  " + chancesForDirections[3]);
            if (temp == 16) break;
        }
    }


    public void calculateFitness() {
        setFitness(0);
        setFitness(1002 - (int) distanceToPoint);
//
    }

    public int[][] getGenes() {
        return genes;
    }

    public void setGenes(int[][] genes) {
        this.genes = genes;
    }

    public void setSecondDimensionGenes(int chromosomePosition, int genePosition, int gene) {
        this.getGenes()[chromosomePosition][genePosition] = gene;
    }

    public void setTopDistance(int topDistance) {
        this.topDistance = topDistance;
    }

    public void setLeftDistance(int leftDistance) {
        this.leftDistance = leftDistance;
    }

    public void setBottomDistance(int bottomDistance) {
        this.bottomDistance = bottomDistance;
    }

    public void setRightDistance(int rightDistance) {
        this.rightDistance = rightDistance;
    }

    public void setDistanceToPoint(double distanceToPoint) {
        this.distanceToPoint = distanceToPoint;
    }

    public int getTopDistance() {
        return topDistance;
    }

    public int getLeftDistance() {
        return leftDistance;
    }

    public int getBottomDistance() {
        return bottomDistance;
    }

    public int getRightDistance() {
        return rightDistance;
    }

    public double getDistanceToPoint() {
        return distanceToPoint;
    }
}
