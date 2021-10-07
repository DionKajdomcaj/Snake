package snake;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private double velx;
    private double vely;
    ArrayList<SnakePart>snake;

    public Snake(SnakePart head,SnakePart body) {

        snake=new ArrayList<>();
        snake.add(head);
        snake.add(body);
    }

    public void move() {
        if(velx != 0 || vely != 0){
            for(int i=snake.size()-1;i>0;i--){
                snake.get(i).x=snake.get(i-1).x;
                snake.get(i).y=snake.get(i-1).y;
            }
            snake.get(0).x+=velx;
            snake.get(0).y+=vely;
        }

    }

    public double getVelx() {
        return velx;
    }

    public void setVelx(double velx) {
        this.velx = velx;
    }

    public void setVely(double vely) {
        this.vely = vely;
    }
    public void draw(Graphics g){
        for(SnakePart s : snake){
            g.drawImage(s.image,s.x,s.y,s.width,s.height,null);
        }

    }
}
