import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25; //change this to adjust the size of the grid in draw() function
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=200;
    final int x[]= new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten;
    int appleX; //x co-ordinate of position of apple
    int appleY; //y co-ordinate of position of apple
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;
    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(130, 244, 123));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){
        if(running) {
            /*

            //to print a matrix
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);


            } */
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //draw the head of the snake and the body
            //for loop to iterate through the body
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor((new Color(35, 138, 30)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(15, 153,50));
                    /*
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); //changes the colour of the snake randomly */
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            //to display the score
            g.setColor(Color.black);
            g.setFont(new Font("Helvetica", Font.BOLD, 30));
            FontMetrics metrics=getFontMetrics(g.getFont()); //aligns the font at the center of the string
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        } else{
            gameOver(g);
        }

    }

    public void newApple(){ //generates the co-ordinates of the apple
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;



    }

    public void move(){
        for(int i=bodyParts; i>0; i--){ //shift through the body parts of the snake
            x[i]=x[i-1];//shifting all the co-ordinates by one
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break; //makes the snake go up and y[0] denotes the head of the snake
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }

    }

    public void checkApple(){ //checks if snake eats the apple
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions(){
        //check if head of the snake collides with the body
        //iterate through the body parts
        for(int i=bodyParts; i>0; i--){
            if((x[0]==x[i]) && (y[0]==y[i])){
                running=false;
            }
        }

        //check if head touches left border
        if(x[0]<0){
            running=false;
        }

        //check if head touches right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }

        //check if head touches top border
        if(y[0]<0){
            running=false;
        }

        //check if head touches bottom border
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }

        if(!running){
            timer.stop();
        }


    }

    public void gameOver(Graphics g){
        //to display the score after game is over
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.BOLD, 30));
        FontMetrics metrics1=getFontMetrics(g.getFont()); //aligns the font at the center of the string
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //set the game over text
        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.BOLD, 75));
        FontMetrics metrics2=getFontMetrics(g.getFont()); //aligns the font at the center of the string
        g.drawString("Game Over", (SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();//check if game is moving
            checkApple();//check if we run into an apple
            checkCollisions();

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                //to denote for each key for each directions
                //we don't want the user to create a 180 degree turn because that will make them collide on themselves
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
            }

        }
    }
}
