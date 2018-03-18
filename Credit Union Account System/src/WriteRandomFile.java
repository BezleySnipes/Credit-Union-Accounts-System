//this class will allow the user to write data for each account and save to file
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WriteRandomFile extends JFrame implements ActionListener
{
  //create GUI componements
  private JTextField IDField, firstNameField, lastNameField, payField;
  private JButton enter,  //send record to file
  					done; //exit program
  private RandomAccessFile output, input; 	//file for input & output
  private Record data;

  //contructor -- initalises the Frame

  public WriteRandomFile()
   {
    super("Add new account");

    //open file
    data = new Record();

   //access record
  try
   {
    output = new RandomAccessFile( "credit.dat", "rw" );
   }
  catch ( IOException e)
  {
   System.err.println( e.toString() );
   System.exit(1);
  }
   //set out layout
   setSize( 300, 150 );
   setLayout( new GridLayout(5,2) );

   //add components to GUI

	add( new JLabel("ID Number") );
	IDField = new JTextField();
	add( IDField );

	add( new JLabel("First Name") );
	firstNameField = new JTextField();
	add( firstNameField );

	add( new JLabel("Last Name") );
	lastNameField = new JTextField();
	add( lastNameField );

	add( new JLabel("Rate of Pay") );
	payField = new JTextField();
	add( payField );

	enter = new JButton ("Add Employee");
	enter.addActionListener(this);
	add (enter);

	done = new JButton ("Done");
	done.addActionListener(this);
	add (done);

	setVisible( true );
}

//create method for adding records to file
public void addRecord()
{
 int IDNumber = 0;
 Double pay;

 if ( ! IDField.getText().equals( "" ) );
 {

 try
  {
   IDNumber = Integer.parseInt( IDField.getText() );
   pay = Double.parseDouble( payField.getText() );

    if (IDNumber < 1 || IDNumber > 100)  //validate ID number is in range
     {
	  JOptionPane.showMessageDialog(this, "ID number must be between 1 & 100");
     }

    else if (IDNumber > 0 && IDNumber <= 100 ) {

	   //read file to check if account number already exists.
	   output.seek((long) (IDNumber - 1) * Record.size());
	   data.read(output);

	  if (data.getAccount() == IDNumber)  //if a/c exists,display dialog box to user
	    {
	    JOptionPane.showMessageDialog(this,"Account already exists! Please try a different ID number");
	    IDField.setText("");   // clear account textfield
        }

	      else   //once conditions are met, data is written to file.
           {
            data.setAccount( IDNumber );
            data.setFirstName( firstNameField.getText() );
            data.setLastName( lastNameField.getText() );
            pay = new Double( payField.getText() );
            data.setBalance( pay.doubleValue() );
            output.seek( (long) ( IDNumber-1 ) * Record.size() );
            data.write( output );
	        JOptionPane.showMessageDialog(this, "Employee Details Saved");
           }
    }
            //reset textfields
            IDField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            payField.setText("");

  }//end try statement
           catch (NumberFormatException nfe )
           {
             System.err.println("You must enter an integer ID number");
           }
           catch (IOException io)
           {
           System.err.println("error during write to file\n" + io.toString() );
          }

  }//end initial if statement
} //end addRecord method


public void actionPerformed (ActionEvent e)  //add actionperformed for exit button
{

 addRecord();

 if (e.getSource() == done)
 {
  try
  {
   output.close();
  }
  catch (IOException io)
  {
   System.err.println( "File not closed properly\n" + io.toString() );
  }
   setVisible(false);
 }
}

//Instantiate a WriteRandonFile object and start the program

    public static void main(String [] args )
    {
     new WriteRandomFile();
    }
}


