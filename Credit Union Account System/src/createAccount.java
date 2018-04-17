///////////////////////////////////
/////  David Mulhall          /////
/////  Assignment 4           /////
/////  B00107874              /////
/////  17/04/2018             /////
///////////////////////////////////

//this class will allow the user to write data for each account and save to file
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class createAccount extends JFrame implements ActionListener
{
  //create GUI componements
  private JTextField accountField, firstNameField, lastNameField, balanceField, overdraft;
  private JButton enter;  //send record to file; //exit program
  private RandomAccessFile output, input; 	//file for input & output
  private Record data;

  //contructor -- initalises the Frame

  public createAccount()
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
  
  	//block of could to find center of screen and offset Jpanel to stay in the center of the screen
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	int screenHeight = Integer.parseInt(String.format("%.0f",(screenSize.getHeight())));
	int screenWidth = Integer.parseInt(String.format("%.0f",(screenSize.getWidth())));
	int frameWidth = 586;
	int frameHeight = 446;
	
	setBounds((screenWidth/2)-(frameWidth/2),(screenHeight/2)-(frameHeight/2), frameWidth, frameHeight);
	
   //set out layout
	setSize( 586, 446 );
	getContentPane().setLayout( new GridLayout(7, 2) );
	
	getContentPane().add( new JLabel( "Account Number (1 - 1000)" ) );
	accountField = new JTextField(15);
	accountField.setEditable( true );
	getContentPane().add( accountField );
	accountField.addActionListener(this);

	getContentPane().add( new JLabel( "First Name" ) );
	firstNameField = new JTextField(15);
	firstNameField.setEditable( true );
	getContentPane().add( firstNameField );

	getContentPane().add( new JLabel( "Last Name" ) );
	lastNameField = new JTextField(15);
	lastNameField.setEditable( true );
	getContentPane().add( lastNameField );

	getContentPane().add( new JLabel( "Account Balance" ) );
	balanceField = new JTextField(15);
	balanceField.setEditable( true );
	getContentPane().add( balanceField );

	getContentPane().add( new JLabel( "Overdraft amount" ) );
	overdraft = new JTextField(15);
	overdraft.setEditable( true );
	getContentPane().add( overdraft );
		
	enter = new JButton ("Add New Account");
	enter.setBounds(18, 317, 310, 68);
	enter.addActionListener(this);
	getContentPane().add (enter);

	setVisible( true );
}

//create method for adding records to file
public void addRecord()
{
 int IDNumber = 0;
 Double zBalance,zOverdraft;

 if ( ! accountField.getText().equals( "" ) );
 {

 try
  {
   IDNumber = Integer.parseInt( accountField.getText() );
   zBalance = Double.parseDouble( balanceField.getText() );
   zOverdraft = Double.parseDouble( overdraft.getText() );

    if (IDNumber < 1 || IDNumber > 1000)  //validate ID number is in range
     {
	  JOptionPane.showMessageDialog(this, "Account number must be between 1 & 1000");
     }

    else if (IDNumber > 0 && IDNumber <= 1000 ) {

	   //read file to check if account number already exists.
	   output.seek((long) (IDNumber - 1) * Record.size());
	   data.read(output);

	  if (data.getAccount() == IDNumber)  //if a/c exists,display dialog box to user
	    {
	    JOptionPane.showMessageDialog(this,"Account already exists! Please try a different account number");
	    accountField.setText("");   // clear account textfield
        }

	      else   //once conditions are met, data is written to file.
           {
            data.setAccount( IDNumber );
            data.setFirstName( firstNameField.getText() );
            data.setLastName( lastNameField.getText() );
            zBalance = new Double( balanceField.getText() );
            data.setBalance( zBalance.doubleValue() );
            data.setOverdraft( zOverdraft.doubleValue() );
            output.seek( (long) ( IDNumber-1 ) * Record.size() );
            data.write( output );
	        JOptionPane.showMessageDialog(this, "New account succesfully created!");
	        
           }
    }
            //reset textfields
            accountField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            balanceField.setText("");
            overdraft.setText("");

  }//end try statement
           catch (NumberFormatException nfe )
           {
        	   JOptionPane.showMessageDialog(this,"You must enter an integer ID number");
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

 if (e.getSource() == enter)
 {
  try
  {
   output.close();
   dispose();
   
  }
  catch (IOException io)
  {
   System.err.println( "File not closed properly\n" + io.toString() );
  }
   setVisible(false);
 }
}

}


