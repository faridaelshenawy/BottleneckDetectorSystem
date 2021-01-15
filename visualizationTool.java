import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public class GUI extends JFrame implements ActionListener{
	
	public GUI(){
		super();
		JFrame frame= new JFrame();
		JPanel panel= new JPanel();
		JLabel label= new JLabel();
		label.setLayout(null);
		label.setBounds(0,0,400,400);
		
		
		panel.setLayout(null);
		
		JLabel l= new JLabel("Please select one of the choices below");
		l.setLayout(null);
		l.setBounds(75,25,650,100);
		l.setFont(new Font(Font.MONOSPACED,Font.BOLD,25));
		l.setForeground(Color.black);
		label.add(l);
		
		JButton button1= new JButton("Highest Execution Time Technique");
		button1.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
		button1.setForeground(Color.BLACK);
		button1.setBackground(Color.white);
		button1.setBounds(75,100,650,50);
		button1.setActionCommand("ReportA");
		button1.addActionListener(this);
		label.add(button1);
		
		JButton button2= new JButton("Highest Number of Queuing Processes Technique");
		button2.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
		button2.setForeground(Color.BLACK);
		button2.setBackground(Color.white);
		button2.setBounds(75,200,650,50);
		button2.setActionCommand("ReportB");
		button2.addActionListener(this);
		label.add(button2);
		
		frame.setSize(400, 400);
		frame.getContentPane().add(label);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ReportA")){
			File file = new File("src/finalResultA.csv");

	        //first check if Desktop is supported by Platform or not
	        if(!Desktop.isDesktopSupported()){
	            System.out.println("Desktop is not supported");
	            return;
	        }

	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists())
				try {
					desktop.open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}
		if(e.getActionCommand().equals("ReportB")){
			File file = new File("src/finalResultB.csv");

	        //first check if Desktop is supported by Platform or not
	        if(!Desktop.isDesktopSupported()){
	            System.out.println("Desktop is not supported");
	            return;
	        }

	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists())
				try {
					desktop.open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
