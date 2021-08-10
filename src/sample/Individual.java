package sample;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import sample.gui.gameView.GameViewModel;
import sample.gui.gameView.Player;

public class Individual extends Player {
    double fitness = 0;

    //odległości od przeszkód w 4 kierunkach
    private int topDistance;
    private int leftDistance;
    private int bottomDistance;
    private int rightDistance;

    //odległości od punktu w osiach X i Y
    private int xDistanceToPoint;
    private int yDistanceToPoint;

    //odległość od punktu
    private double distanceToPoint;

    // tablica dwuwymiarowa osobnika z 24 ciągami bitów, po 8 bitów każdy
    // wielkość 24 to 6 danych wejściowych, czyli 4 odległości od przeszkód i 2 odległości od punktu przemnożone przez 4 możliwości ruchu
    // tak więc (4+2)*4 = 24
    int[][] genes = new int[24][8];

    // ciąg bitów reprezentowany w formie tekstowej
    String[] stringGenes = new String[24];

    // wyliczone z ciągów bitów parametry od -1 do 1
    public double[] parameters = new double[24];

    // "szanse" dla gracza na pójście w kierunkach kolejno góra lewo dół prawo
    double[] chancesForDirections = new double[4];

    public Individual(int[][] genes) {
        super(50, 50, GameViewModel.playerRadius);

        //inicjalizacja osobnika gotowym zbiorem genów
        for (int i = 0; i < genes.length; i++) {
            System.arraycopy(genes[i], 0, this.genes[i], 0, genes[i].length);
        }
    }

    public Individual() {
        super(50, 50, GameViewModel.playerRadius);

        //wylosowanie ciągu 8 bitów dla 24 chromosomów osobnika
        for (int i = 0; i < genes.length; i++) {
            for (int j = 0; j < genes[i].length; j++)
                genes[i][j] = (int) Math.round(Math.random());
        }
    }

    public void calcTopDistance(Image img) {
        //snapshot wygenerowanej planszy
        final PixelReader pixelReader = img.getPixelReader();

        //sprawdzenie kolejnych pixeli w osi Y
        outer:
        for (int y = (int) this.getCenterY(); y > 0; y--) {

            //sprawdzanie wszerz w osi X kolejnych pixeli na wysokości y
            for (int x = (int) this.getCenterX() - (int) this.getRadius(); x <= (int) this.getCenterX() + (int) this.getRadius(); x++) {

                //jeżeli pixel o współrzędnych x y ma kolor czerwony (kolor przeszkody), oznacza to, że przeszkoda znajduje się w odległości y od gracza
                if (pixelReader.getColor(x, y).equals(Color.RED)) {

                    //ustalana jest odległość od gracza do najbliższej przeszkody na górze
                    //aktualna współrzędna Y gracza odjąć współrzędna, na której znaleziono przeszkodę dodać 15 (średnica gracza), pomnożone przez -1 aby wynik był dodatni
                    setTopDistance((int) this.getCenterY() - y + (int)this.getRadius() * -1);
                    break outer;
                }
                //jeżeli nie znaleziono przeszkody, ustal odległość od krawędzi planszy - średnica gracza
                setTopDistance((int) getCenterY() - 15);
            }
        }
    }

    public void calcLeftDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int x = (int) this.getCenterX(); x > 0; x--) {
            for (int y = (int) this.getCenterY() - (int) this.getRadius(); y <= (int) this.getCenterY() + (int) this.getRadius(); y++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setLeftDistance((int) this.getCenterX() - x + (int)this.getRadius() * -1);
                    break outer;
                }
                setLeftDistance((int) getCenterX() - 15);
            }
        }
    }

    public void calcBottomDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int y = (int) this.getCenterY(); y < img.getHeight(); y++) {
            for (int x = (int) this.getCenterX() - (int) this.getRadius(); x <= (int) this.getCenterX() + (int) this.getRadius(); x++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setBottomDistance(y - (int) this.getCenterY() - (int)this.getRadius());
                    break outer;
                }
                setBottomDistance(GameViewModel.sceneHeight - (int) getCenterY() - 15);
            }
        }
    }

    public void calcRightDistance(Image img) {
        final PixelReader pixelReader = img.getPixelReader();
        outer:
        for (int x = (int) this.getCenterX(); x < img.getWidth(); x++) {
            for (int y = (int) this.getCenterY() - (int) this.getRadius(); y <= (int) this.getCenterY() + (int) this.getRadius(); y++) {
                if (pixelReader.getColor(x, y).equals(Color.RED)) {
                    setRightDistance(x - (int) this.getCenterX() - (int)this.getRadius());
                    break outer;
                }
                setRightDistance(GameViewModel.sceneWidth - (int) getCenterX() - 15);
            }
        }
    }

    public void calcPointDistance() {
        //obliczenie odległości od punktu w lini prostej
        setDistanceToPoint((Math.sqrt((GameViewModel.pointX - this.getCenterX()) * (GameViewModel.pointX - this.getCenterX()) + (GameViewModel.pointY - this.getCenterY()) * (GameViewModel.pointY - this.getCenterY()))) - (GameViewModel.playerRadius + 30));
    }

    public void calcXYPointDistances() {
        //odległości od punktu w osiach X i Y
        this.setxDistanceToPoint(Math.abs((int) (GameViewModel.pointX - this.getCenterX())));
        this.setyDistanceToPoint(Math.abs((int) (GameViewModel.pointY - this.getCenterY())));
    }

    public void calcDistancesToAllObstaclesAndPoint(Image img) {
        calcTopDistance(img);
        calcLeftDistance(img);
        calcBottomDistance(img);
        calcRightDistance(img);
        calcXYPointDistances();
    }

    public void moveSomewhere() {
        double min = chancesForDirections[0];
        int temp = 0;
        for (int i = 1; i < chancesForDirections.length; i++) {
            if (chancesForDirections[i] < min) {
                temp = i;
                min = chancesForDirections[i];
            }
        }
        switch (temp) {
            case 0 -> moveUp();
            case 1 -> moveLeft();
            case 2 -> moveDown();
            case 3 -> moveRight();
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
        int[] decimalValues = new int[24]; // ciągi bitów zamienione na system dziesiętny

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
        double[] xToPoint = new double[4];
        double[] yToPoint = new double[4];
        int temp = 0;

        for (int i = 0; i < chancesForDirections.length; i++) {
            up[i] = (double) distancesToObstacles[0] * parameters[temp];
            left[i] = (double) distancesToObstacles[1] * parameters[temp + 1];
            down[i] = (double) distancesToObstacles[2] * parameters[temp + 2];
            right[i] = (double) distancesToObstacles[3] * parameters[temp + 3];
            xToPoint[i] = (double) getxDistanceToPoint() * (parameters[temp + 4]);
            yToPoint[i] = (double) getyDistanceToPoint() * (parameters[temp + 5]);
            chancesForDirections[i] = up[i] + left[i] + down[i] + right[i] + xToPoint[i] + yToPoint[i];
            temp += 6;
            if (temp == 24) break;
        }
    }


    public void calculateFitness() {
        setFitness(0);
        double multiplier = 1;

        //dodatkowy przelicznik dla osobników, które dotarły do punktu
        if (this.isPointReached()) multiplier = 1.3;

        //obliczanie efektywności każdego ruchu w celu zapobiegnięcia zapętleniu ruchów góra-dół lub prawo-lewo
        //im mniej ruchów osobnik wynika aby dojść do celu, tym lepszy uzyska wynik
        int distanceFromSpawnToPoint = (int) Math.sqrt((GameViewModel.pointX - 50) * (GameViewModel.pointX - 50) + (GameViewModel.pointY - 50) * (GameViewModel.pointY - 50));
        int distanceFromPlayerToPoint = (int) Math.sqrt((GameViewModel.pointX - this.getCenterX()) * (GameViewModel.pointX - this.getCenterX()) + (GameViewModel.pointY - this.getCenterY()) * (GameViewModel.pointY - this.getCenterY()));
        int effectiveMove = (distanceFromSpawnToPoint - distanceFromPlayerToPoint) / this.getIndividualMovesCounter() * 5;

        //przystosowanie jest obliczane poprzez odjęcie od nieosiągalnej odległości od punktu (1002px)
        // aktualnej (czyli w chwili śmierci) odległości osobnika i dodaniu współczynnika efektywności ruchu
        //jeżeli osobnik dotarł do celu, całość jest mnożona dodatkowo przez 1.3 aby go wyróżnić
        setFitness((1020 - (int) distanceToPoint + effectiveMove) * multiplier);
    }

    public int[][] getGenes() {
        return genes;
    }

    public void setSecondDimensionGenes(int chromosomePosition, int genePosition, int gene) {
        //ustalenie genu na określonej pozycji - potrzebne przy mutacji
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

    public int getxDistanceToPoint() {
        return xDistanceToPoint;
    }

    public int getyDistanceToPoint() {
        return yDistanceToPoint;
    }

    public void setxDistanceToPoint(int xDistanceToPoint) {
        this.xDistanceToPoint = xDistanceToPoint;
    }

    public void setyDistanceToPoint(int yDistanceToPoint) {
        this.yDistanceToPoint = yDistanceToPoint;
    }

}
