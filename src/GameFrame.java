import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); //fits all the components snugly around the frame
        this.setVisible(true);
        this.setLocationRelativeTo(null); //sets it at the center

    }
}