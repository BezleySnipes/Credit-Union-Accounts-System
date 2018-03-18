//this class will allow the user to read data for a particular account
//and delete the account
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class CloseAccount extends JFrame implements ActionListener
{
//create GUI components
private JTextField accountField, firstNameField, lastNameField, balanceField;
private JButton closeBut, exitBut;
private RandomAccessFile input, output;
private Record data;

public CloseAccount()
 {
  super ("Close Account");

try
  {
  //set up files for read & write
  input = new RandomAccessFile( "credit.dat", "rw" );
  output = new RandomAccessFile( "credit.dat", "rw" );
  }
  catch ( IOException e )
  {
   System.err.println(e.toString() );
   System.exit(1);
  }

data = new Record();

//create the components of the Frame
setSize( 320, 150 );
setLayout( new GridLayout(5, 2) );


add( new JLabel( "Account Number (1 - 100)" ) );
accountField = new JTextField(15);
accountField.setEditable( true );
add( accountField );
accountField.addActionListener(this);

add( new JLabel( "First Name" ) );
firstNameField = new JTextField(15);
firstNameField.setEditable( false );
add( firstNameField );

add( new JLabel( "Last Name" ) );
lastNameField = new JTextField(15);
lastNameField.setEditable( false );
add( lastNameField );

add( new JLabel( "Account Balance" ) );
balanceField = new JTextField(15);
balanceField.setEditable( false );
add( balanceField );


closeBut = new JButton( "Close Account");
closeBut.addActionListener( this );
add(closeBut);
closeBut.setEnabled(false);

exitBut = new JButton( "Exit" );
exitBut.addActionListener( this );
add(exitBut);

setVisible( true );
}

public void actionPerformed( ActionEvent e )
{
  //read account details when account number entered
  if (! accountField.getText().equals(""))
  {
  	readRecord(); //calling readRecord method
  	closeBut.setEnabled(true);
  }
  if (e.getSource() == exitBut)   //exit maintenance menu
  {
   	setVisible(false);
  }
  if (e.getSource() == closeBut)
  	{
   try
      {
   	 int accountNumber = Integer.parseInt( accountField.getText() );

	  data.setAccount( 0 );
	  data.setFirstName( null );
	  data.setLastName( null);
	  data.setBalance( 0);

	  output.seek( (long) ( accountNumber-1 ) * Record.size() );
	  data.write( output );

	  JOptionPane.showMessageDialog(this, "Account has been deleted");
	  accountField.setText("");
	  firstNameField.setText("");
	  lastNameField.setText("");
	  balanceField.setText("");
	  }//end try
	 catch (NumberFormatException nfe )
   	  	 {
   	  	  System.err.println("You must enter an integer account number");
   	  	 }
   	 catch (IOException io)
   	  	 {
   	  	  System.err.println("error during write to file\n" + io.toString() );
   	      }
     }
 } //end actionPerformed

public void readRecord()
{
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	try
	{
		int accountNumber = Integer.parseInt(accountField.getText());

		if (accountNumber < 1 || accountNumber > 100)
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
       			balanceField.setText( String.valueOf(
       			twoDigits.format( data.getBalance() ) ) );
       		}
       		if (data.getAccount() == 0)
       		{
				JOptionPane.showMessageDialog(this, "Account does not exist");
				accountField.setText("");
			}
 	}//end try statement
 	catch (EOFException eof )
 	{
       closeFile();
    }
    catch (IOException e )
    {
       System.err.println("Error during read from file\n " + e.toString() );
       System.exit( 1 );
    }
  } // end readRecord method

private void closeFile()
{
	try
    {
    input.close();
    System.exit( 0 );
    }
    catch( IOException e)
    {
    System.err.println( "Error closing file \n" + e.toString() );
    }
}// end closeFile method

public static void main(String [] args)
    {
     new CloseAccount();
   }

}