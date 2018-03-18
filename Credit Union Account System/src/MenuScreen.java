//this class contains button that when click calls
//another class  delete records.
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class MenuScreen extends JFrame
{
	private JButton deleteAccount;
	//private JPanel menuPanel;

	public MenuScreen()
	{
        super( "Menu Panel" );

  		deleteAccount = new JButton(  "Delete Account" );

		deleteAccount.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				new CloseAccount(); //calling the CloseAccount class
			}
		}
	);
	add( deleteAccount, BorderLayout.NORTH );

    //add( menuPanel);

    setSize(300,100);
    setVisible( true );
    }

//main method
public static void main(String[] args )
	{
	new MenuScreen();
	}
}
