package finalproject;
//sw5205

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel, topPanel, buttonPanel;
    private JLabel titleLabel;
    private JButton playbutton, learnbutton, highscorebutton, exitbutton;
    
    public MainScreen() {

	        // create the label and buttons
	        frame = new JFrame("Learn to Count!");
	        titleLabel = new JLabel("<html><div style='text-align:center;'>Learn to Count<br><br>in<br><br>Burmese and English</html>", SwingConstants.CENTER);
	        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
	        playbutton = new JButton("Play");
	        learnbutton = new JButton("Learn Numerals");
	        highscorebutton = new JButton("High Score");
	        exitbutton = new JButton("Exit");
	
	        //add action listener to all buttons
	        playbutton.addActionListener(new ButtonHandle());
	        learnbutton.addActionListener(new ButtonHandle());
	        highscorebutton.addActionListener(new ButtonHandle());
	        exitbutton.addActionListener(new ButtonHandle());
	        
	        //Top Panel
	        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        topPanel.add(titleLabel);
	        
	        //Center Panel to place navigation buttons
	        buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10)); //4 rows, 1 column, 10 horizontal gap, 10 vertical gap
	        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); //Padding: top 10, left 50, bottom 10, right 50
	        buttonPanel.add(playbutton);
	        buttonPanel.add(learnbutton);
	        buttonPanel.add(highscorebutton);
	        buttonPanel.add(exitbutton);
	        
	        panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	        panel.add(Box.createVerticalGlue());
	        panel.add(topPanel);
	        panel.add(Box.createRigidArea(new Dimension(0, 30)));
	        panel.add(buttonPanel);
	        panel.add(Box.createVerticalGlue());
	        
	        frame.add(panel);
	        frame.setSize(800, 600);
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setResizable(false);
	        frame.setVisible(true);
    }
    
    private class ButtonHandle implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == learnbutton) { //if user click learn button, go to learn screen
                frame.dispose();
                new LearnScreen();
            } else if (e.getSource() == playbutton) { //if user click play button, go to learn screen
                frame.dispose();
                new PlayScreen();
            } else if (e.getSource() == highscorebutton) { //if user click high score button, go to high score screen
                frame.dispose();
                new HighScore();
            //if user click exit button on main screen, Confirm Exit dialog box appears
            } else if (e.getSource() == exitbutton) { 
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) { //If user choose "Yes" exit the program.
                	System.exit(0);
                }
            }
        }
    }
 
    public static void main(String[] args) {
        new MainScreen();
    }
}
