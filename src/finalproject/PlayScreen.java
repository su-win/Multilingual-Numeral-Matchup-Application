package finalproject;
//sw5205

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.sql.*;
import javax.swing.*;


public class PlayScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JButton playButton, backButton, previousButton = null;
	private JLabel timerLabel, scoreLabel;
	private JPanel panel, topPanel, gameControlPanel, centerPanel, bottomPanel;
	private JButton[] buttonArray;
	private ImageIcon[] allOrgIcons;
	private Timer timer;
	private int timeLeft = 300;
	private double score = 0.0;
	private int firstImageIndex = -1, secondImageIndex = -1, matchedCount;
	private static final int timeLimit = 300;
	private long startTime, endTime;
	private boolean firstMove = true, selectingFirstImage = true;
    
    public PlayScreen() {
        initializeScreen();
        addPlayButtonListener();
    }

    private void initializeScreen() {
    	frame = new JFrame("Learn to Count!");
        scoreLabel = new JLabel("  Score: 0");
        timerLabel = new JLabel("Time: 05:00  ");
        playButton = new JButton("Play");
        backButton = new JButton("Back to Main Screen");
        backButton.setPreferredSize(new Dimension(200, 50)); //width, height
        buttonArray = new JButton[20];
        allOrgIcons = new ImageIcon[20];
        
        // Game Control Panel for play button
        gameControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playButton.setPreferredSize(new Dimension(100, 35));
        gameControlPanel.add(playButton);

        //Top Panel for game control panel, score and timer labels
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(scoreLabel, BorderLayout.WEST);
        topPanel.add(gameControlPanel, BorderLayout.CENTER);
        topPanel.add(timerLabel, BorderLayout.EAST);
        
        //Center Panel
        centerPanel = new JPanel(new FlowLayout());

        //Create arrays to store Burmese and English number images as ImageIcon object
        ImageIcon[] engNumIcons = new ImageIcon[10];
        ImageIcon[] burmeseNumIcons = new ImageIcon[10];

        // Read Burmese and English images from folders and store them in the respective arrays
        // We also stored images from both folders in array called allOrgIcons
        for (int i = 0; i < 10; i++) {
            engNumIcons[i] = new ImageIcon("images/englishNum/number" + i + ".png");
            burmeseNumIcons[i] = new ImageIcon("images/burmeseNum/number" + i + ".png");
            allOrgIcons[i] = engNumIcons[i];
            allOrgIcons[i + 10] = burmeseNumIcons[i];
        }

        // Shuffle all image icons using Collections.shuffle to get random order of icons
        Collections.shuffle(Arrays.asList(allOrgIcons));

        //Create 4 by 5 grid Button Panel to place all image icons 
        JPanel buttonPanel = new JPanel(new GridLayout(4, 5));
        
        //Retrieve all image icons from an array and place them on the button panel.
        for (int i = 0; i < 20; i++) {
            buttonArray[i] = new JButton(allOrgIcons[i]);
            buttonArray[i].setPreferredSize(new Dimension(104, 104));
            buttonArray[i].setVisible(false); //set button visibility to false
            buttonPanel.add(buttonArray[i]);
        }
        
        //Add button panel to the center panel
        centerPanel.add(buttonPanel, BorderLayout.CENTER); 
  
        //bottom Panel for back to main menu button
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
        
        panel = new JPanel(new BorderLayout());
        panel.add(topPanel, BorderLayout.NORTH);
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
            	if (timer != null && timer.isRunning()) { //user already play the game and click back to main menu
                    
                    timer.stop(); // Pause the timer

                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit play?\n(Game progess cannot be retrieved once you leave.)", "Confirm Exit Play",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        timer.stop();
                        frame.dispose();
                        new MainScreen();
                    } else {
                        timer.start(); //resume the timer if user does not exit
                    }
                }else {
	            	//user does not start play the game yet and leave the play screen. No exit confirmation.
	            	frame.dispose();
	                new MainScreen();
                }
            }
        });
    }

    private void addPlayButtonListener() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 20; i++) { 
                    buttonArray[i].setEnabled(true); //enable buttons
                    buttonArray[i].setVisible(true); // make the button visible
                }
              
                for (int i = 0; i < 20; i++) { //show all images
                    buttonArray[i].setIcon(allOrgIcons[i]);
                }
                
                playButton.setVisible(false);
                
                // Start the timer
                startTimer(300); // 5 minutes * 60 seconds/minute = 300 seconds
                
                performUserActions();
            }
        });
    }
    
    private void startTimer(int seconds) {
    	startTime = System.currentTimeMillis();
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                int minutes = timeLeft / 60;
                int seconds = timeLeft % 60;
                timerLabel.setText("Time: " + String.format("%02d:%02d", minutes, seconds) + "  ");
                if (timeLeft == 0) {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(frame, "Times up! Game over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        timer.start();
    }
    
    private void performUserActions() {
    	System.out.println("User clicked play");
    	matchedCount = 0;
    	JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        System.out.println("matchedCount: " + matchedCount);
    	for (int i = 0; i < 20; i++) { //add ActionListerer to all buttons
            buttonArray[i].addActionListener(new ActionListener() {
        
                @Override
                public void actionPerformed(ActionEvent e) {
                    int buttonIndex = -1;
                    
                    for (int i = 0; i < 20; i++) { //get the index of the clicked button
                        if (e.getSource() == buttonArray[i]) {
                            buttonIndex = i;
                            break;
                        }
                    }
                    if (selectingFirstImage) { //if user selects the first image, then set the index 
                        firstImageIndex = buttonIndex; //
                        System.out.println("First Button " + buttonIndex + " clicked.");
                        buttonArray[firstImageIndex].setIcon(allOrgIcons[firstImageIndex]);
                        previousButton = buttonArray[firstImageIndex]; //save the selected first image button as previous button
                        selectingFirstImage = false;
                    } else {
                		secondImageIndex = buttonIndex;
                		System.out.println("Second Button " + buttonIndex + " clicked.");
                		if (buttonIndex != firstImageIndex && buttonArray[secondImageIndex] != previousButton) {
	                		System.out.println("Not same button else " + buttonIndex + " clicked.");
	                        buttonArray[secondImageIndex].setIcon(allOrgIcons[secondImageIndex]);
	                        
	                        //Get the file name of the first image and second image
	                        String firstName = allOrgIcons[firstImageIndex].getDescription().substring(allOrgIcons[firstImageIndex].getDescription().lastIndexOf("/") + 1);
	                        String secondName = allOrgIcons[secondImageIndex].getDescription().substring(allOrgIcons[secondImageIndex].getDescription().lastIndexOf("/") + 1);
	                        //Compare the name of the first image and second image
	                        if (firstName.equals(secondName)) {
	                        	
	                            // if matched, increment the score by 10, increment matched count counter, set first move to false
	                        	buttonArray[firstImageIndex].setIcon(null);
	                            buttonArray[firstImageIndex].setEnabled(false);
	                            buttonArray[secondImageIndex].setIcon(null);
	                            buttonArray[secondImageIndex].setEnabled(false);
	                            score += 10;
	                        	scoreLabel.setText("  Score: " + score);
	                        	matchedCount += 1;
	                        	firstMove = false; //once item match, no longer first move
	                        	System.out.println("matchedCount: " + matchedCount);
	                        	System.out.println(firstMove);
	                        } else {// If file names are unmatched, deduct 0.5 from existing score
	                            // If any moves have been made yet, do not deduct points
	                        	if (!firstMove && matchedCount >= 1) {
	                                score -= 0.5;
	                                scoreLabel.setText("  Score: " + score);
	                                System.out.println("firstMove:" + firstMove);
	                                System.out.println("matchedCount: " + matchedCount);
	                                //During the game, users might performing several unmatched and the score might reach below zero.
	                                //So, if user score reached below, Game Over
	                                if (score <= 0) { 
	                                    timer.stop();
	                                    JOptionPane.showMessageDialog(frame, "Game Over! Your score went below zero.", "Lose", JOptionPane.ERROR_MESSAGE);
	                                }
	                            }
	                        	System.out.println("matchedCount: " + matchedCount);
	                            buttonArray[firstImageIndex].setIcon(allOrgIcons[firstImageIndex]);
	                            buttonArray[secondImageIndex].setIcon(allOrgIcons[secondImageIndex]);
	                        }
	             
	                        // If all items on the board are matched, show "win" message and save score to database
	                        if(matchedCount == 10) {
	                        	timer.stop();
	                        	endTime = System.currentTimeMillis();
	                        	int timeTaken = (int) ((endTime - startTime) / 1000);
	                        	System.out.println(timeTaken);
	                        	double scoreBasedonTime = (timeLimit - timeTaken) * 3;//calculate score based on time
	                        	System.out.println(scoreBasedonTime);
	
	                            score += (int) scoreBasedonTime;
	                            System.out.println(score);
	                            JOptionPane.showMessageDialog(frame, "Congratulations! You win!", "Win", JOptionPane.INFORMATION_MESSAGE);
	                            
	                            try { //save score to database
	                                Class.forName("org.sqlite.JDBC");
	                                Connection conn = DriverManager.getConnection("jdbc:sqlite:highscores.db");
	                                Statement statement = conn.createStatement();
	                                statement.execute("CREATE TABLE IF NOT EXISTS highscores (score INTEGER, time INTEGER)");
	                                statement.execute("INSERT INTO highscores (score, time) VALUES (" + score + ", " + timeTaken + ")");
	                                statement.close();
	                                conn.close();
	                            } catch (Exception ex) {
	                                ex.printStackTrace();
	                            }
	                        }
                		} else {
                			System.out.println("Same Button2 " + buttonIndex + " clicked.");
                	    	JOptionPane.showMessageDialog(dialog, "Please select a different image.");
                	    	selectingFirstImage = true;
                	        return; // Do not execute the rest of the code
                		}
                		selectingFirstImage = true;
                }//end of if (selectingFirstImage)'s else
                    previousButton = null;
                }//end of action performed
            });     
        }
    }
    
    
}

