package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle  {
    Obstacle[] obstacles;

    public Obstacle(int size){
        obstacles = new Obstacle[size];
        for (int i = 0; i < size - 5; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 20);
            obstacles[i].setY((Math.random() * 341) + 100);
        }
        for (int i = size - 5; i < size; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 100);
            obstacles[i].setY(Math.random() * 101);
        }
    }

    public Obstacle(double width, double height) {
        super(width,height, Color.RED);
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }
}
