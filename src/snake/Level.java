package snake;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {
    public int score;
    private final int BRICK_WIDTH = 20;
    private final int BRICK_HEIGHT = 40;
    ArrayList<Rock> rocks;
    public Level(String levelPath) throws IOException {
        score=0;
        loadlevel(levelPath);
    }
    public void loadlevel(String levelPath) throws FileNotFoundException, IOException{
        BufferedReader br= new BufferedReader(new FileReader(levelPath));
        rocks = new ArrayList<>();
        int y = 0;
        String line;
        while((line=br.readLine()) != null){
            int x =0;
            for(char blockType : line.toCharArray()){
                if(Character.isDigit(blockType)){
                    Image image = new ImageIcon("data/images/rock.png").getImage();
                    rocks.add(new Rock(x * BRICK_WIDTH, y * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT, image));
                }
                x++;
            }
            y++;

        }

    }
    public boolean collides(Food food){
        Rock collidedWith = null ;
        for(Rock brick : rocks){
            if(food.collides(brick)){
                collidedWith=brick;
                break;
            }

        }
        if(collidedWith != null){
            rocks.remove(collidedWith);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isOver(){
        return score ==60;
    }
    public void draw(Graphics g) {
        for (Rock rock : rocks) {
            rock.draw(g);
        }
    }






























}
