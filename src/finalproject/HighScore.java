package finalproject;
//sw5205
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class HighScore {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel panel, scorePanel, centerPanel, bottomPanel;
    private JButton backButton;
    private HighScoreComponent scoreComponent;

    public HighScore() {
		frame = new JFrame("Learn to Count!");
        titleLabel = new JLabel("High Score", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 21));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); 
        backButton = new JButton("Back to Main Screen");
        backButton.setPreferredSize(new Dimension(200, 50)); //width, height


    	// Create the high score component and set its preferred size
        scoreComponent = new HighScoreComponent();
        scoreComponent.setPreferredSize(new Dimension(500, 300));

        // Create a panel to hold the score component
        scorePanel = new JPanel(new BorderLayout());
        scorePanel.add(scoreComponent, BorderLayout.CENTER);

        // Create a panel to hold the title and score panels
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(scorePanel);
        centerPanel.add(Box.createVerticalGlue());

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
        
        panel = new JPanel(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainScreen();
            }
        });
    	
    }

    //Function to retrieve score from database and display on the screen
    private class HighScoreComponent extends JPanel {
    	
        private static final long serialVersionUID = 1L;

		public HighScoreComponent() {
        	setLayout(new GridBagLayout()); //grid with one column
            setPreferredSize(new Dimension(200, 200)); //set the size of the component

            JLabel scoreLabel = new JLabel("Score", SwingConstants.CENTER); //Score Header
            scoreLabel.setFont(scoreLabel.getFont().deriveFont(Font.BOLD));
            
            JLabel timeLabel = new JLabel("Time", SwingConstants.CENTER); //Time Header
            timeLabel.setFont(timeLabel.getFont().deriveFont(Font.BOLD)); 

 
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(10, 10, 10, 10);
            add(scoreLabel, c);

            c.gridx = 1;
            add(timeLabel, c);

            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:highscores.db");
                Statement stmt = conn.createStatement();
                //select score and time from highscores and display them in descending order by score value
                ResultSet rs = stmt.executeQuery("SELECT score, time FROM highscores ORDER BY score DESC");
                int row = 1;
                while (rs.next()) {
                    int score = rs.getInt("score");
                    int time = rs.getInt("time");
                    String formattedTime = String.format("%d:%02d", time / 3600, time % 60);

                    JLabel scoreValueLabel = new JLabel(Integer.toString(score), SwingConstants.CENTER);
                    c.gridx = 0;
                    c.gridy = row;
                    c.insets = new Insets(5, 10, 5, 10);
                    add(scoreValueLabel, c);
                    
                    JLabel timeValueLabel = new JLabel(formattedTime, SwingConstants.CENTER);
                    c.gridx = 1;
                    add(timeValueLabel, c);

                    row++;
                }

                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}