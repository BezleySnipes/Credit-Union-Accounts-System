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
private JTextField IDField, firstNameField, lastNameField, payField;
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
setSize( 300, 150 );
setLayout( new GridLayout(5,2) );

add( new JLabel( "ID Number" ) );
IDField = new JTextField(15);
IDField.setEditable( false );
add( IDField );

add( new JLabel( "First Name" ) );
firstNameField = new JTextField(15);
firstNameField.setEditable( false );
add( firstNameField );

add( new JLabel( "Last Name" ) );
lastNameField = new JTextField(15);
lastNameField.setEditable( false );
add( lastNameField );

add( new JLabel( "Rate of Pay" ) );
payField = new JTextField(15);
payField.setEditable( false );
add( payField );

next = new JButton ("Next");
next.addActionListener(this);
add (next);

done = new JButton ("Done");
done.addActionListener(this);
add (done);

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

	IDField.setText(String.valueOf( data.getAccount() ) );
    firstNameField.setText( data.getFirstName() );
    lastNameField.setText( data.getLastName() );
    payField.setText( String.valueOf(
    twoDigits.format( data.getBalance() ) ) );
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
//Instantiate a ReadRandomFile object and start the program

  public static void main(String [] args)
   {
    new ReadRandomFile();
   }

}//end class
