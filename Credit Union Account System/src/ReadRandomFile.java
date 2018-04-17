///////////////////////////////////
/////  David Mulhall          /////
/////  Assignment 4           /////
/////  B00107874              /////
/////  17/04/2018             /////
///////////////////////////////////

//ReadRandomFile.java
//this program read a random access file sequentally and
//displays the content one record at a time in text fields

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ReadRandomFile extends JFrame implements ActionListener
{
//create GUI components
private JTextField accountNumber, firstNameField, lastNameField, balance, overdraft;
private JButton next, done;
private RandomAccessFile input, output;
private Record data;

public ReadRandomFile()
{
super ("View / Maintain Account Menu");

//Open the file

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


//set out layout
setSize( 299, 481 );
getContentPane().setLayout(new GridLayout(7, 2, 0, 0));

getContentPane().add( new JLabel( "Account Number" ) );
accountNumber = new JTextField(15);
accountNumber.setEditable( false );
getContentPane().add( accountNumber );

getContentPane().add( new JLabel( "First Name" ) );
firstNameField = new JTextField(15);
firstNameField.setEditable( false );
getContentPane().add( firstNameField );

getContentPane().add( new JLabel( "Last Name" ) );
lastNameField = new JTextField(15);
lastNameField.setEditable( false );
getContentPane().add( lastNameField );

getContentPane().add( new JLabel( "Balance" ) );
balance = new JTextField(15);
balance.setEditable( false );
getContentPane().add( balance );

getContentPane().add( new JLabel( "overdraft" ) );
overdraft = new JTextField(15);
overdraft.setEditable( false );
getContentPane().add( overdraft );

next = new JButton ("Next");
next.addActionListener(this);
getContentPane().add (next);

done = new JButton ("Done");
done.addActionListener(this);
getContentPane().add (done);

setVisible( true );
}

//set up handler for various actionlisteners
public void actionPerformed( ActionEvent e )
{
  //read account details when account number entered
  if (e.getSource() == next)
  {
   readRecord();
  }
else
  closeFile();

}//ends actionperformed method

//READ RECORD METHOD
public void readRecord()
 {
   DecimalFormat twoDigits = new DecimalFormat( "0.00" );

    try
    {
     do {
		 data.read(input);
	 }

	 while (data.getAccount() == 0);

	accountNumber.setText(String.valueOf( data.getAccount() ) );
    firstNameField.setText( data.getFirstName() );
    lastNameField.setText( data.getLastName() );
    balance.setText( String.valueOf(twoDigits.format( data.getBalance() ) ) );
    overdraft.setText( String.valueOf(twoDigits.format( data.getOverdraft() ) ) );
    }
	catch (EOFException eof )
     {
     closeFile();     }
     catch (IOException e )
     {
     System.err.println("Error during read from file\n " + e.toString() );
     System.exit( 1 );
     }
  }

//method to closefile for exceptions
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
}

}//end class
