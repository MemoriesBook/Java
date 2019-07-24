import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
public class RegisterForm extends LoginForm implements ItemListener
{
	private JRadioButton userRadioButton=new JRadioButton("User",true);
	private JRadioButton administratorRadioButton=new JRadioButton("Administrator",false);
	private ButtonGroup buttonGroup=new ButtonGroup();
	private User user=new User();
	private JButton deleteButton=new JButton("Delete");
	private JButton modifyButton=new JButton("Modify");
	private JTextField codeTextField=new JTextField(null);
	private MySQLConnection mysqlConnection=new MySQLConnection();
	private UserMySQLConnection userMySQLConnection=new UserMySQLConnection();
	public RegisterForm()
	{
		super();
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setSize(400,200);
		headPanel.setLayout(new GridLayout(4,2));		
		bottomPanel.remove(loginButton);
	}
	public void initialization()
	{
		passwordField.setEchoChar((char)0);
		buttonGroup.add(userRadioButton);
		userRadioButton.addItemListener(this);
		buttonGroup.add(administratorRadioButton);
		administratorRadioButton.addItemListener(this);
		headPanel.add(new JLabel("Register code:",JLabel.CENTER));
		headPanel.add(codeTextField);
		headPanel.add(userRadioButton);
		headPanel.add(administratorRadioButton);
		bottomPanel.setLayout(new GridLayout(2,3));
		bottomPanel.add(registerButton);
		bottomPanel.add(deleteButton);
		bottomPanel.add(modifyButton);
		bottomPanel.add(resetButton);
		bottomPanel.add(cancelButton);
		deleteButton.addActionListener(this);
		modifyButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent event)
	{
		switch(event.getActionCommand())
		{
		case "Register":
			user.setAccount(accountTextField.getText());
			user.setPassword(passwordField.getPassword());
			if(user.getAccount().equals("")||String.valueOf(user.getPassword()).equals("")||codeTextField.getText().equals(""))
				tipsLabel.setText("Cannot be empty");
			else if(!codeTextField.getText().equals("river"))
				tipsLabel.setText("Registration code is wrong");
			else if(mysqlConnection.grant(user))
				JOptionPane.showMessageDialog(mainFrame,"Register successfully");
			else
				JOptionPane.showMessageDialog(mainFrame,"Register unsuccessfully","Error",0);
			break;
		case "Reset":accountTextField.setText(null);passwordField.setText(null);codeTextField.setText(null);break;
		case "Cancel":mainFrame.dispose();break;
		case "Delete":
			user.setAccount(accountTextField.getText());
			user.setPassword(passwordField.getPassword());
			if(user.getAccount().equals("")||String.valueOf(user.getPassword()).equals("")||codeTextField.getText().equals(""))
				tipsLabel.setText("Cannot be empty");
			else if(!codeTextField.getText().equals("river"))
				tipsLabel.setText("Registration code is wrong");
			else if(userMySQLConnection.connect(user)==false)
				tipsLabel.setText("Account or password is wrong");
			else if(userMySQLConnection.administrator(user)==1&&user.getAdministrator()==false)
				tipsLabel.setText("Permission is wrong");
			else if(userMySQLConnection.administrator(user)==2&&user.getAdministrator()==true)
				tipsLabel.setText("Permission is wrong");
			else if(userMySQLConnection.administrator(user)==0)
				tipsLabel.setText("User does not exist");
			else if(mysqlConnection.drop(user))
				JOptionPane.showMessageDialog(mainFrame,"Delete successfully");
			else
				JOptionPane.showMessageDialog(mainFrame,"Delete unsuccessfully","Error",JOptionPane.ERROR_MESSAGE);
			break;
		case "Modify":
			user.setAccount(accountTextField.getText());
			user.setPassword(passwordField.getPassword());
			if(user.getAccount().equals("")||String.valueOf(user.getPassword()).equals("")||codeTextField.getText().equals(""))
				tipsLabel.setText("Cannot be empty");
			else if(!codeTextField.getText().equals("river"))
				tipsLabel.setText("Registration code is wrong");
			else if(mysqlConnection.exist(user)==false)
				tipsLabel.setText("Account does not exist");
			else if(mysqlConnection.update(user))
				JOptionPane.showMessageDialog(mainFrame,"Modify successfully");
			else
				JOptionPane.showMessageDialog(mainFrame,"Modify unsuccessfully","Error",JOptionPane.ERROR_MESSAGE);
			break;
		default:break;
		}
	}
	public void itemStateChanged(ItemEvent event)
	{
		if(administratorRadioButton.isSelected())
			user.setAdministrator(true);
		else if(userRadioButton.isSelected())
			user.setAdministrator(false);
		else
			user.setAdministrator(false);
	}
	public void windowOpened(WindowEvent event)
	{
		
	}
	public void windowClosed(WindowEvent event)
	{
		LoginForm loginForm=new LoginForm();
		loginForm.initialization();
	}
}
