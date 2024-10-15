package finalproject;
//sw5205
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LearnScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
    private JLabel titleLabel;
    private JButton backButton;
    private JPanel panel, topPanel, centerPanel, bottomPanel;
	
	public LearnScreen() {
		frame = new JFrame("Learn to Count!");
	    titleLabel = new JLabel("<html><div style='text-align:center;'>Numerals<br>in<br>Burmese and English</html>", SwingConstants.CENTER);
	    titleLabel.setFont(new Font("Georgia", Font.BOLD, 21));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); 
        backButton = new JButton("Back to Main Screen");
        backButton.setPreferredSize(new Dimension(200, 50)); 
  
        
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(titleLabel);

        ImageIcon icon = new ImageIcon("images/learnNumerals.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(380, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        
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
                frame.dispose();
                new MainScreen();
            }
        });
    }
}

