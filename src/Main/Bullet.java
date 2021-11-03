package Main;

public class Bullet {
    int x,y,speed;
    double rotate;
    public Bullet(int x, int y, int speed, double rotate) {
        this.rotate = rotate;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }
}
