package Main;

import Sounds.Sound;
import Textures.Textures;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class Screen extends JPanel implements Runnable {

    public static int radius;
    public static int FPS = 60;
    public static int rotate = 0;
    public static int WIDTH, HEIGHT;
    public static int killCount = 0;
    public static int buttonMenu = 4;
    public static int difficulty = 1;
        public static boolean fire = true;
    public static boolean pause = true;
    public static int movementSpeed = 10;
    public static int moveSpeedMeteorite = 10;
    public static boolean runnable = false;
    public static int bulletDifficulty = 1;

    public static Point p;
    public Sound sound;
    public Meteorite[] meteorite;
    public Thread thread;
    public Player player;
    public Bullet[] bullet;
    public Textures textures;


    public Screen(JFrame frame) {
        WIDTH = frame.getWidth();
        HEIGHT = frame.getHeight();
        radius = WIDTH;

        addMouseMotionListener(new Mouse());
        addKeyListener(new KeyBoard());
        addMouseListener(new Mouse());
        setFocusable(true);

        textures = new Textures();
        bullet = new Bullet[20];
        meteorite = new Meteorite[100];
        player = new Player(WIDTH/2, HEIGHT/2, 50);
        try {sound = new Sound('M');} catch (Exception e) {System.out.println("Проблема с музыкой в главном меню!\n" + e);}
        running();
    }

    public void running() {
        if (runnable) return;
        runnable = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double time = System.nanoTime();
        double dif = 1;
        double now;
        while (true) {
            dif --;
            try {
                while (true) {
                    now = System.nanoTime();
                    dif += (now - time) * FPS / 1000000000;
                    time = now;
                    if (dif >= 1) {
                        if (!pause) action();
                        repaint();
                        break;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    public boolean hitBocksChecker(Bullet bullet, Meteorite meteorite) {
        if (meteorite != null) {
            long scale = meteorite.scale / 2;
            return bullet != null && (
                    (
                            bullet.x - 10 > meteorite.x - scale &&
                            bullet.x - 10 < meteorite.x + scale &&
                            bullet.y - 10 > meteorite.y - scale &&
                            bullet.y - 10 < meteorite.y + scale
                    ) || (
                            bullet.x + 10 > meteorite.x - scale &&
                            bullet.x + 10 < meteorite.x + scale &&
                            bullet.y + 10 > meteorite.y - scale &&
                            bullet.y + 10 < meteorite.y + scale
                    ) || (
                            bullet.x + 10 > meteorite.x - scale &&
                            bullet.x + 10 < meteorite.x + scale &&
                            bullet.y - 10 > meteorite.y - scale &&
                            bullet.y - 10 < meteorite.y + scale
                    ) || (
                            bullet.x - 10 > meteorite.x - scale &&
                            bullet.x - 10 < meteorite.x + scale &&
                            bullet.y + 10 > meteorite.y - scale &&
                            bullet.y + 10 < meteorite.y + scale
                    ));
        }
        return false;
    }

    public boolean hitBocksChecker(Player player, Meteorite meteorite) {
        if (meteorite != null) {
            long scale = meteorite.scale / 2;
            long scaleP = player.scale / 2;
            return (
                    player.x - scaleP > meteorite.x - scale &&
                    player.x - scaleP < meteorite.x + scale &&
                    player.y - scaleP > meteorite.y - scale &&
                    player.y - scaleP < meteorite.y + scale
            ) || (
                    player.x + scaleP > meteorite.x - scale &&
                    player.x + scaleP < meteorite.x + scale &&
                    player.y + scaleP > meteorite.y - scale &&
                    player.y + scaleP < meteorite.y + scale
            ) || (
                    player.x + scaleP > meteorite.x - scale &&
                    player.x + scaleP < meteorite.x + scale &&
                    player.y - scaleP > meteorite.y - scale &&
                    player.y - scaleP < meteorite.y + scale
            ) || (
                    player.x - scaleP > meteorite.x - scale &&
                    player.x - scaleP < meteorite.x + scale &&
                    player.y + scaleP > meteorite.y - scale &&
                    player.y + scaleP < meteorite.y + scale
            );
        }
        return false;
    }

    public void action() {
        if (KeyBoard.X && fire) {
            int freeBullets = 0;
            for (int i = 0; i < bulletDifficulty; i++)
                if (bullet[i] == null) freeBullets = i;
            if (bullet[freeBullets] == null) {
                bullet[freeBullets] = new Bullet(player.x, player.y, movementSpeed * 4, Math.atan2((player.y - p.y), (player.x - p.x)));
                try {sound = new Sound('P');} catch (Exception e) {e.printStackTrace();}
            }
            fire = false;
        }
        for (int i = 0; i < bulletDifficulty; i++) {
            for (int j = 0; j < difficulty; j++) {
                if (hitBocksChecker(bullet[i], meteorite[j])) {
                    meteorite[j] = null;
                    bullet[i] = null;
                    killCount++;
                    if (killCount == difficulty) {
                        difficulty ++;
                        killCount = 0;
                        Arrays.fill(meteorite, null);
                    }
                }
            }
            if (bullet[i] != null) {
                bullet[i].x -= bullet[i].speed*Math.cos(bullet[i].rotate);
                bullet[i].y -= bullet[i].speed*Math.sin(bullet[i].rotate);
                if (bullet[i].x > WIDTH || bullet[i].x < 0 || bullet[i].y > HEIGHT || bullet[i].y < 0) bullet[i] = null;
            }
        }
        for (int i = 0; i < difficulty; i++) {
            if (hitBocksChecker(player,meteorite[i])) {
                pause = true;
                player.x = WIDTH / 2;
                player.y = HEIGHT / 2;
                difficulty = 1;
                killCount = 0;
                for (int j = 0; j < difficulty; j++) {
                    meteorite[j] = null;
                }
                Arrays.fill(bullet, null);
            }
            if (meteorite[i] != null) {
                meteorite[i].x -= Math.floor(meteorite[i].speed*Math.cos(meteorite[i].rotate));
                meteorite[i].y -= Math.floor(meteorite[i].speed*Math.sin(meteorite[i].rotate));
                if (
                    meteorite[i].x < WIDTH + meteorite[i].scale &&
                    meteorite[i].x > -meteorite[i].scale &&
                    meteorite[i].y < HEIGHT+meteorite[i].scale &&
                    meteorite[i].y > -meteorite[i].scale
                ) {
                    meteorite[i].inGame = true;
                } else if (meteorite[i].inGame) meteorite[i] = null;
            } else {
                double a = Math.toRadians(Math.random()*360);
                int scale = (int) (50 + Math.random()*20);
                int speed = (int) (movementSpeed-movementSpeed/2 + moveSpeedMeteorite*Math.random());
                int image = (int) (Math.random()*2);
                meteorite[i] = new Meteorite(player.x + radius*Math.cos(a), player.y + radius*Math.sin(a), speed, scale, a, textures.met[image]);
            }
        }


        if (KeyBoard.UP && player.y - movementSpeed-25 >= 0)  player.y -= movementSpeed;
        if (KeyBoard.LEFT && player.x - movementSpeed-25 >= 0) player.x -= movementSpeed;
        if (KeyBoard.RIGHT && player.x + movementSpeed+25 <= WIDTH) player.x += movementSpeed;
        if (KeyBoard.DOWN && player.y + movementSpeed+25 <= HEIGHT) player.y += movementSpeed;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, WIDTH, HEIGHT);

        if (p != null && pause) {
            buttonMenu = 3;
            g.drawImage(textures.main,0,0,WIDTH,HEIGHT,null);
            for (int i = 0; i < 3; i++) {
                int var = HEIGHT*2/3 + i*60;
                g.setColor(Color.GREEN);
                if (p.y >= var && p.y <= var + 60) {
                    buttonMenu = i;
                    g.setColor(Color.RED);
                    g.drawImage(textures.met[0],350, var - 17, 100,75,null);
                }
                g.setFont(textures.buttonsFont);
                g.drawString(textures.buttons[i],20,var + 60);
            }
        }

        if (!pause) {
            g.drawImage(textures.ship[0],player.x - 2 * player.scale - player.scale/4, player.y - player.scale - player.scale/2,4 * player.scale + player.scale/2,3*player.scale,null);

            for (Bullet value : bullet)
                if (value != null)
                    g.drawImage(textures.bullets,value.x - 40, value.y - 40, 80, 80,null);

            for (int i = 0; i < difficulty; i++)
                if (meteorite[i] != null)
                    g.drawImage(meteorite[i].image, (int) (meteorite[i].x - meteorite[i].scale + meteorite[i].scale/4), (int) (meteorite[i].y - meteorite[i].scale + meteorite[i].scale/4), meteorite[i].scale + meteorite[i].scale/2, meteorite[i].scale+meteorite[i].scale/2, null);
            g.setColor(Color.RED);
            Font font = new Font("Cooper", Font.BOLD, 100);
            g.setFont(font);
            g.drawString("LvL: " + difficulty, 5,100);
        }
    }
}
