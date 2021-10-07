package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class GameEngine extends JPanel {
    private final double Movement = 15;


    private boolean paused = false;
    private Image background;
    private int levelNum = 1;
    private Level level;
    private Food food;
    private Snake snk;
    private JLabel timeLabel;
    private Timer newFrameTimer;
    private Timer timer;
    private long startTime;
    ArrayList<Food> foods=new ArrayList<>();

    public GameEngine() {
        super();
        background = new ImageIcon("data/images/safari.jpg").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snk.setVelx(-Movement);
                snk.setVely(0);
               // snk.move();
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snk.setVelx(Movement);
                snk.setVely(0);
                //snk.move();
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snk.setVely(Movement);
                snk.setVelx(0);
                //snk.move();
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snk.setVely(-Movement);
                snk.setVelx(0);
                //snk.move();
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(60, new NewFrameListener());
        newFrameTimer.start();
        timeLabel=new JLabel(" ");
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText(elapsedTime() + " ms");
            }
        });
        startTime = System.currentTimeMillis();
        timer.start();
;
    }
    public JLabel getBoardPanel() {
        return timeLabel;
    }
    public long elapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void restart() {


        try {

            level = new Level("data/levels/level"+levelNum+".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image head = new ImageIcon("data/images/head.png").getImage();
        Image body = new ImageIcon("data/images/body.png").getImage();
        snk=new Snake(new SnakePart(350,300,15,15,head),new SnakePart(350+15,300,15,15,body));
        Random r = new Random();
        Image foodimg = new ImageIcon("data/images/food.png").getImage();
        food=new Food(r.nextInt(26)*30,r.nextInt(20)*30, 30, 30, foodimg);
        //foods.add(food);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 600, null);
        level.draw(grphcs);
        snk.draw(grphcs);
        food.draw(grphcs);
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                snk.move();

                if(snk.snake.get(0).collides(food)){
                    foods.add(food);

                    int index = snk.snake.size()-1;
                    Image newImg = new ImageIcon("data/images/body.png").getImage();
                    snk.snake.add(new SnakePart(snk.snake.get(index).x+15,snk.snake.get(index).y+15,15,15,newImg));
                    Image foodimg = new ImageIcon("data/images/food.png").getImage();
                    Random r= new Random();
                    food = new Food(r.nextInt((int) 800/30)*30,r.nextInt((int) 600/30)*30, 30, 30, foodimg);
                }


            }
            for(Rock b : level.rocks){
                if(snk.snake.get(0).collides(b)){
                    timer.stop();
                    JFrame frame = new JFrame();
                    String result = JOptionPane.showInputDialog(frame,"Enter the name");
                    int finalscore = level.score;
                    timer.restart();
                    restart();
                }
            }

            for(int i=1;i<snk.snake.size();i++){
                if(snk.snake.get(0).collides(snk.snake.get(i))){
                    timer.stop();
                    JFrame frame = new JFrame();
                    String result = JOptionPane.showInputDialog(frame,"Enter the name");
                    int finalscore = level.score;
                    long time =elapsedTime();
                    timer.restart();
                    restart();
                }
            }



            if(snk.snake.get(0).x+15>= 800 || snk.snake.get(0).x+15<=0 || snk.snake.get(0).y+15 >=600 ||  snk.snake.get(0).y+15<=0){
                timer.stop();
                JFrame frame = new JFrame();
                String result = JOptionPane.showInputDialog(frame,"Enter the name");
                //int finalscore = score;
                long time =elapsedTime();
                timer.restart();
                restart();
            }
            if(foods.size()==14){
                //Level finishes
                levelNum = (levelNum+1) % 2;
                timer.stop();
                JFrame frame = new JFrame();
                String result = JOptionPane.showInputDialog(frame,"Enter the name");
                //int finalscore = score;
                long time =elapsedTime();
                restart();

            }

            repaint();
        }

    }
}