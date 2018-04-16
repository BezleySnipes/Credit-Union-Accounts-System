//this class will allow the user to read data for a particular account
//and delete the account
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class balance extends JFrame implements ActionListener
{
//create GUI components
private JTextField accountField, firstNameField, lastNameField, balanceField, overdraft;
private JButton exitBut;
private RandomAccessFile input;
private Record data;

public balance()
 {
  super ("View Account Balance");

try
  {
  //set up files for read & write
  input = new RandomAccessFile( "credit.dat", "rw" );
  }
  catch ( IOException e )
  {
   System.err.println(e.toString() );
   System.exit(1);
  }

data = new Record();

	//block of could to find center of screen and offset Jpanel to stay in the center of the screen
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	int screenHeight = Integer.parseInt(String.format("%.0f",(screenSize.getHeight())));
	int screenWidth = Integer.parseInt(String.format("%.0f",(screenSize.getWidth())));
	int frameWidth = 586;
	int frameHeight = 446;
	
	setBounds((screenWidth/2)-(frameWidth/2),(screenHeight/2)-(frameHeight/2), frameWidth, frameHeight);
	


//create the components of the Frame
setSize( 586, 446 );
getContentPane().setLayout( new GridLayout(7, 2) );


getContentPane().add( new JLabel( "Account Number (1 - 1000)" ) );
accountField = new JTextField(15);
accountField.setEditable( true );
getContentPane().add( accountField );
accountField.addActionListener(this);

getContentPane().add( new JLabel( "First Name" ) );
firstNameField = new JTextField(15);
firstNameField.setEditable( false );
getContentPane().add( firstNameField );

getContentPane().add( new JLabel( "Last Name" ) );
lastNameField = new JTextField(15);
lastNameField.setEditable( false );
getContentPane().add( lastNameField );

getContentPane().add( new JLabel( "Account Balance" ) );
balanceField = new JTextField(15);
balanceField.setEditable( false );
getContentPane().add( balanceField );

getContentPane().add( new JLabel( "Overdraft amount" ) );
overdraft = new JTextField(15);
overdraft.setEditable( false );
getContentPane().add( overdraft );

exitBut = new JButton( "Exit" );
exitBut.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		closeFile();
		dispose();
	}
});
getContentPane().add(exitBut);

setVisible( true );
}

public void actionPerformed( ActionEvent e )
{
  //read account details when account number entered
  if (! accountField.getText().equals(""))
  {
  	readRecord();
  	}
}
public void readRecord()
{
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	try
	{
		int accountNumber = Integer.parseInt(accountField.getText());

		if (accountNumber < 1 || accountNumber > 1000)
			{
			JOptionPane.showMessageDialog(this, "Account does not exist");
			
			}
			else
			{
				input.seek( (accountNumber - 1)*Record.size());
       			data.read(input);

       			accountField.setText(String.valueOf( data.getAccount() ) );
       			firstNameField.setText( data.getFirstName() );
       			lastNameField.setText( data.getLastName() );
       			balanceField.setText( String.valueOf(twoDigits.format( data.getBalance() ) ) );
       			overdraft.setText( String.valueOf(twoDigits.format( data.getOverdraft() ) ) );
       		}
       		if (data.getAccount() == 0)
       		{
				JOptionPane.showMessageDialog(this, "Account does not exist");
				accountField.setText("");
				balanceField.setText("");
				overdraft.setText("");
				validate();
			}
 	}//end try statement
 	catch (EOFException eof )
 	{
       closeFile();
    }
    catch (IOException e )
    {
       System.err.println("Error during read from file\n " + e.toString() );
       System.exit( 0 );
    }
  } // end readRecord method

private void closeFile()
{
	
		
	try
    {
    input.close();
   // System.exit( 0 );
    }
    catch( IOException e)
    {
    System.err.println( "Error closing file \n" + e.toString() );
    }
	
}// end closeFile method

}