///////////////////////////////////
/////  David Mulhall          /////
/////  Assignment 4           /////
/////  B00107874              /////
/////  17/04/2018             /////
///////////////////////////////////

//this class contains button that when click calls
//another class  delete records.
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.io.File;
import javax.swing.*;

public class MenuScreen extends JFrame
{
	//private JPanel menuPanel;

	public MenuScreen()
	{
        super( "Menu Panel" );
        
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	int screenHeight = Integer.parseInt(String.format("%.0f",(screenSize.getHeight())));
	int screenWidth = Integer.parseInt(String.format("%.0f",(screenSize.getWidth())));
	int frameWidth = 756;
	int frameHeight = 600;
	
	//block of could to find center of screen and offset Jpanel to stay in the center of the screen
	setBounds((screenWidth/2)-(frameWidth/2),(screenHeight/2)-(frameHeight/2), frameWidth, frameHeight);
	
	JPanel headerPanel = new JPanel();
	headerPanel.setBackground(Color.WHITE);
	getContentPane().add(headerPanel, BorderLayout.NORTH);
	headerPanel.setLayout(new BorderLayout(0, 0));
	
	JLabel logo = new JLabel("");
	logo.setHorizontalAlignment(SwingConstants.CENTER);
	headerPanel.add(logo, BorderLayout.CENTER);
	logo.setIcon(new ImageIcon("src/credit-union-logo.jpg"));
	
	JPanel menuPanel = new JPanel();
	getContentPane().add(menuPanel, BorderLayout.CENTER);
	menuPanel.setLayout(new BorderLayout(0, 0));
	
	JPanel buttonPanel = new JPanel();
	menuPanel.add(buttonPanel, BorderLayout.CENTER);
	buttonPanel.setLayout(null);
	
	JButton btnOpenAccount = new JButton("Open Account");
	btnOpenAccount.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new createAccount();
			}
	});
	btnOpenAccount.setBounds(0, 0, 246, 125);
	buttonPanel.add(btnOpenAccount);
	
	JButton btnCloseAccount = new JButton("Close Account");
	btnCloseAccount.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new CloseAccount();
		}
	});
	btnCloseAccount.setBounds(247, 0, 246, 125);
	buttonPanel.add(btnCloseAccount);
	
	JButton btnLodgement = new JButton("Lodgement");
	btnLodgement.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new lodgement();
		}
	});
	btnLodgement.setBounds(0, 136, 246, 125);
	buttonPanel.add(btnLodgement);
	
	JButton btnWithdrawal = new JButton("Withdrawal");
	btnWithdrawal.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new withdrawal();
		}
	});
	btnWithdrawal.setBounds(247, 136, 246, 125);
	buttonPanel.add(btnWithdrawal);
	
	JButton btnOverdraft = new JButton("Overdraft");
	btnOverdraft.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Overdraft();
		}
	});
	btnOverdraft.setBounds(494, 136, 246, 125);
	buttonPanel.add(btnOverdraft);
	
	JButton btnBalance = new JButton("Balance");
	btnBalance.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new balance();
		}
	});
	btnBalance.setBounds(494, 0, 246, 125);
	buttonPanel.add(btnBalance);

    //add( menuPanel);

    setSize(756,487);
    setVisible( true );
    }

//main method
public static void main(String[] args )
	{
	File f = new File("C:\\Users\\Dave\\OneDrive - Institute of Technology Blanchardstown\\Java\\Assignments\\Assignment 4\\Credit Union Account System\\credit.dat"); 
	  if(f.exists() && f.isFile()) {new MenuScreen();}
	  else {new CreateRandomFile();}
	
	}
}
