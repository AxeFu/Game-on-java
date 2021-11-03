package Main;

import java.awt.*;

public class Meteorite {
    int scale;
    boolean inGame = false;
    double x, y, speed, rotate;
    Image image;
    public Meteorite(double x, double y, int speed, int scale, double rotate, Image image) {
        this.rotate = rotate;
        this.speed = speed;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.image = image;
    }
}
