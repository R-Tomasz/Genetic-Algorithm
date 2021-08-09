package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle  {
    Obstacle[] obstacles;

    public Obstacle(int size){
        obstacles = new Obstacle[size];
        for (int i = 0; i < size - 10; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 900) + 15);
            obstacles[i].setY((Math.random() * 300) + 100);
        }
        for (int i = size - 10; i < size-5; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 900) + 100);
            obstacles[i].setY(Math.random() * 100);
        }
        for(int i = size - 5; i< size;i++){
            obstacles[i] =new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 875) + 15);
            obstacles[i].setY((Math.random() * 55) + 500);
        }
    }

    public Obstacle(double width, double height) {
        super(width,height, Color.RED);
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }
}
