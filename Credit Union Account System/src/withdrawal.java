///////////////////////////////////
/////  David Mulhall          /////
/////  Assignment 4           /////
/////  B00107874              /////
/////  17/04/2018             /////
///////////////////////////////////

//this class will allow the user to write data for each account and save to file
import java.io.*;
import java.text.DecimalFormat;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class withdrawal extends JFrame implements ActionListener
{
	private JTextField accountField, withdrawal, balanceField, overdraft;
	private JButton exitBut;
	private RandomAccessFile input, output;
	private Record data;
	private JButton btnUpdateOverdraft;

	public withdrawal()
	 {
	  super ("Make Withdrawal");

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

	getContentPane().add( new JLabel( "Account Balance" ) );
	balanceField = new JTextField(15);
	balanceField.setEditable( false );
	getContentPane().add( balanceField );

	getContentPane().add( new JLabel( "Overdraft amount" ) );
	overdraft = new JTextField(15);
	overdraft.setEditable( false );
	getContentPane().add( overdraft );
	
	getContentPane().add( new JLabel( "Withdrawal amount" ) );
	withdrawal = new JTextField(15);
	withdrawal.setEditable( false );
	getContentPane().add( withdrawal );

	exitBut = new JButton( "Exit" );
	exitBut.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			closeFile();
			dispose();
		}
	});
	getContentPane().add(exitBut);
	
	btnUpdateOverdraft = new JButton("Withdraw");
	btnUpdateOverdraft.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			updateRecord();
		}
	});
	getContentPane().add(btnUpdateOverdraft);
	btnUpdateOverdraft.setVisible(false);

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
	       			balanceField.setText( String.valueOf(twoDigits.format( data.getBalance() ) ) );
	       			overdraft.setText( String.valueOf(twoDigits.format( data.getOverdraft() ) ) );
	       			withdrawal.setEditable( true );
	       			btnUpdateOverdraft.setVisible(true);
	       			validate();
	       			
	       		}
	       		if (data.getAccount() == 0)
	       		{
					JOptionPane.showMessageDialog(this, "Account does not exist");
					accountField.setText("");
					balanceField.setText("");
					overdraft.setText("");
					withdrawal.setEditable( false );
					btnUpdateOverdraft.setVisible(false);
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
	
	public void updateRecord()
	{
	 int IDNumber;
	 Double zwithdrawal,zBalance,zAvailable;
	 
	 try
	  {
	   IDNumber = Integer.parseInt( accountField.getText() );
	   zwithdrawal = Double.parseDouble( withdrawal.getText() );
	   zBalance = data.getBalance();
	   zAvailable = (zBalance + data.getOverdraft());
	   output.seek((long) (IDNumber - 1) * Record.size());
	   data.read(output);
	   if (zwithdrawal>0 && zwithdrawal<=zAvailable) {
       data.setBalance(zBalance- zwithdrawal.doubleValue() );
       output.seek( (long) ( IDNumber-1 ) * Record.size() );
       data.write( output );
       JOptionPane.showMessageDialog(this, "Withdrawal successfully");
       closeFile();}
	   else {
		   JOptionPane.showMessageDialog(this, "insufficient funds");
	   }

	  }//end try statement
	           catch (NumberFormatException nfe )
	           {
	             System.err.println("You must enter an integer ID number");
	           }
	           catch (IOException io)
	           {
	           System.err.println("error during write to file\n" + io.toString() );
	          }
	} //end addRecord method

	private void closeFile()
	{
		
			
		try
	    {
	    input.close();
	    dispose();
	    }
	    catch( IOException e)
	    {
	    System.err.println( "Error closing file \n" + e.toString() );
	    }
		
	}// end closeFile method

}
