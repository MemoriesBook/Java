import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public class LoginForm implements ActionListener,WindowListener
{
	JFrame mainFrame=new JFrame("Student Apartment Information Management System");
	JLabel accountLabel=new JLabel("Account:",JLabel.CENTER);
	JLabel passwordLabel=new JLabel("Password:",JLabel.CENTER);
	JLabel tipsLabel=new JLabel();
	JTextField accountTextField=new JTextField(null);
	JPasswordField passwordField=new JPasswordField(null);
	JButton loginButton=new JButton("Login");
	JButton registerButton=new JButton("Register");
	JButton resetButton=new JButton("Reset");
	JButton cancelButton=new JButton("Cancel");
	JPanel headPanel=new JPanel();
	JPanel bottomPanel=new JPanel();
	private MySQLConnection mysqlConnection=new MySQLConnection();
	UserMySQLConnection userMySQLConnection=new UserMySQLConnection();
	static User user=new User();
	public LoginForm()
	{
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(400,145);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(this);
		mainFrame.setLayout(new BorderLayout());
		headPanel.setLayout(new GridLayout(2,2));
		headPanel.add(accountLabel);
		headPanel.add(accountTextField);
		headPanel.add(passwordLabel);
		headPanel.add(passwordField);
		bottomPanel.setLayout(new GridLayout(2,2));
		bottomPanel.add(loginButton);
		bottomPanel.add(registerButton);
		bottomPanel.add(resetButton);
		bottomPanel.add(cancelButton);
		registerButton.addActionListener(this);
		resetButton.addActionListener(this);
		cancelButton.addActionListener(this);
		mainFrame.add("North",headPanel);
		mainFrame.add("Center",tipsLabel);
		tipsLabel.setForeground(Color.red);
		tipsLabel.setHorizontalAlignment(0);
		mainFrame.add("South",bottomPanel);
		mainFrame.setVisible(true);
	}
	public void initialization()
	{
		loginButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent event)
	{
		switch(event.getActionCommand())
		{
		case "Login":
			user.setAccount(accountTextField.getText());
			user.setPassword(passwordField.getPassword());
			if(user.getAccount().equals("")||String.valueOf(user.getPassword()).equals(""))
				tipsLabel.setText("Account or password cannot be empty");
			else if(userMySQLConnection.connect(user)==false)
				tipsLabel.setText("Account or password is wrong");
			else
			{
				JOptionPane.showMessageDialog(mainFrame,"Successfully Logining");
				mainFrame.dispose();MainForm mainForm=new MainForm();mainForm.initialization(user);
			}
			break;
		case "Register":mainFrame.dispose();RegisterForm registerForm=new RegisterForm();registerForm.initialization();break;
		case "Reset":accountTextField.setText(null);passwordField.setText(null);break;
		case "Cancel":System.exit(0);break;
			default:break;
		}
	}
	public void windowOpened(WindowEvent event)
	{
		mysqlConnection.databaseInitialization();
	}
	public void windowClosed(WindowEvent event)
	{
		
	}
	public void windowClosing(WindowEvent event)
	{
		
	}
	public void windowActivated(WindowEvent event)
	{
		
	}
	public void windowDeactivated(WindowEvent event)
	{
		
	}
	public void windowDeiconified(WindowEvent event)
	{
		
	}
	public void windowIconified(WindowEvent event)
	{
		
	}
	public static void main(String[] args)
	{
		LoginForm loginForm=new LoginForm();
		loginForm.initialization();
	}
}

