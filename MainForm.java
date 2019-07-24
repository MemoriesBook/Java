import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
public class MainForm extends LoginForm
{
	private JTextField searchMethodTextField=new JTextField();
	private JButton searchButton=new JButton("Search");
	private JPanel studentPanel=new JPanel();
	private JLabel studentNumberLabel=new JLabel("Number:",JLabel.CENTER);
	private JLabel specialtyLabel=new JLabel("Specialty:",JLabel.CENTER);
	private JLabel classLabel=new JLabel("Class:",JLabel.CENTER);
	private JLabel yearLabel=new JLabel("Year:",JLabel.CENTER);
	private JTextField studentNameTextField=new JTextField();
	private JTextField facultyTextField=new JTextField();
	private JTextField studentNumberTextField=new JTextField();
	private JTextField specialtyTextField=new JTextField();
	private JTextField classTextField=new JTextField();
	private JTextField yearTextField=new JTextField();
	private JPanel teacherPanel=new JPanel();
	private JLabel teacherNumberLabel=new JLabel("Number:",JLabel.CENTER);
	private JTextField teacherNumberTextField=new JTextField();
	private JTextField teacherNameTextField=new JTextField();
	private JTextField dutyTextField=new JTextField();
	private JPanel apartmentPanel=new JPanel();
	private JTextField apartmentTextField=new JTextField();
	private JTextField bedroomNumberTextField=new JTextField();
	private JTextField bedNumberTextField=new JTextField();
	private JLabel moreLabel=new JLabel("more",JLabel.CENTER);
	private JButton insertButton=new JButton("Insert");
	private JButton deleteButton=new JButton("Delete");
	private JButton modifyButton=new JButton("Modify");
	private Student student=new Student();
	private Teacher teacher=new Teacher();
	private Apartment apartment=new Apartment();
	public MainForm()
	{
		super();
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		headPanel.removeAll();
		bottomPanel.removeAll();
		headPanel.setLayout(new GridLayout(1,3));
		headPanel.add(tipsLabel);
		headPanel.add(searchMethodTextField);
		headPanel.add(searchButton);
		bottomPanel.setLayout(new GridLayout(1,5));
		bottomPanel.add(insertButton);
		bottomPanel.add(deleteButton);
		bottomPanel.add(modifyButton);
		bottomPanel.add(resetButton);
		bottomPanel.add(cancelButton);
		mainFrame.setLayout(new GridLayout(10,1));
		mainFrame.add(headPanel);
		mainFrame.add(new JSeparator());
		mainFrame.add(new JLabel("Student Information:",JLabel.LEFT));
		mainFrame.add(studentPanel);
		mainFrame.add(new JLabel("Teacher Information:",JLabel.LEFT));
		mainFrame.add(teacherPanel);
		mainFrame.add(new JLabel("Apartment Information:",JLabel.LEFT));
		mainFrame.add(apartmentPanel);
		mainFrame.add(moreLabel);
		mainFrame.add(bottomPanel);
	}
	public void initialization(User user)
	{
		tipsLabel.setText("Please enter the number of student:");
		studentPanel.setLayout(new GridLayout(2,6));
		studentPanel.add(new JLabel("Name:",JLabel.CENTER));
		studentPanel.add(studentNameTextField);
		studentPanel.add(new JLabel("Faculty:",JLabel.CENTER));
		studentPanel.add(facultyTextField);
		studentPanel.add(studentNumberLabel);
		studentPanel.add(studentNumberTextField);
		studentPanel.add(specialtyLabel);
		studentPanel.add(specialtyTextField);
		studentPanel.add(classLabel);
		studentPanel.add(classTextField);
		studentPanel.add(yearLabel);
		studentPanel.add(yearTextField);
		teacherPanel.setLayout(new GridLayout(1,4));
		teacherPanel.add(new JLabel("Name:",JLabel.CENTER));
		teacherPanel.add(teacherNameTextField);
		teacherPanel.add(new JLabel("Duty:",JLabel.CENTER));
		teacherPanel.add(dutyTextField);
		teacherPanel.add(teacherNumberLabel);
		teacherPanel.add(teacherNumberTextField);
		apartmentPanel.setLayout(new GridLayout(1,6));
		apartmentPanel.add(new JLabel("Apartment:",JLabel.CENTER));
		apartmentPanel.add(apartmentTextField);
		apartmentPanel.add(new JLabel("Bedroom Number:",JLabel.CENTER));
		apartmentPanel.add(bedroomNumberTextField);
		apartmentPanel.add(new JLabel("Bed Number:",JLabel.CENTER));
		apartmentPanel.add(bedNumberTextField);
		searchButton.addActionListener(this);
		insertButton.addActionListener(this);
		deleteButton.addActionListener(this);
		modifyButton.addActionListener(this);
		if(userMySQLConnection.administrator(user)==2)
		{
			user.setAccount(accountTextField.getText());
			studentNumberLabel.setVisible(false);
			studentNumberTextField.setVisible(false);
			specialtyLabel.setVisible(false);
			specialtyTextField.setVisible(false);
			classLabel.setVisible(false);
			classTextField.setVisible(false);
			yearLabel.setVisible(false);
			yearTextField.setVisible(false);
			teacherNumberLabel.setVisible(false);
			teacherNumberTextField.setVisible(false);
			moreLabel.setForeground(Color.gray);
			bottomPanel.setVisible(false);
		}
		else if(userMySQLConnection.administrator(user)==0)
			JOptionPane.showMessageDialog(mainFrame,"User does not exist","Warning",JOptionPane.WARNING_MESSAGE,null);
	}
	public void actionPerformed(ActionEvent event)
	{
		switch(event.getActionCommand())
		{
		case "Search":
			if(searchMethodTextField.getText().equals(""))//判断字符串是否为空不能直接两个等号等于""
				JOptionPane.showMessageDialog(mainFrame,"Please input the student number!","Warning",JOptionPane.WARNING_MESSAGE);
			else if(userMySQLConnection.exist(Integer.parseInt(searchMethodTextField.getText()),user)==false)
				JOptionPane.showMessageDialog(mainFrame,"Student don't exist!","Warning",JOptionPane.WARNING_MESSAGE);
			else
			{
				student=userMySQLConnection.searchStudent(Integer.parseInt(searchMethodTextField.getText()),user);
				studentNumberTextField.setText(Integer.toString(student.getNumber()));
				facultyTextField.setText(student.getFaculty());
				studentNameTextField.setText(student.getName());
				specialtyTextField.setText(student.getSpecialty());
				classTextField.setText(Integer.toString(student.getClas()));
				yearTextField.setText(Integer.toString(student.getYear()));
				teacher=userMySQLConnection.searchTeacher(Integer.parseInt(searchMethodTextField.getText()),user);
				teacherNumberTextField.setText(Integer.toString(teacher.getNumber()));
				teacherNameTextField.setText(teacher.getName());
				dutyTextField.setText(teacher.getDuty());
				apartment=userMySQLConnection.searchApartment(Integer.parseInt(searchMethodTextField.getText()),user);
				apartmentTextField.setText(apartment.getApartment());
				bedroomNumberTextField.setText(Integer.toString(apartment.getBedroomNumber()));
				bedNumberTextField.setText(Integer.toString(apartment.getBedNumber()));
			}
			break;
		case "Reset":
			searchMethodTextField.setText(null);
			studentNumberTextField.setText(null);
			facultyTextField.setText(null);
			studentNameTextField.setText(null);
			specialtyTextField.setText(null);
			classTextField.setText(null);
			yearTextField.setText(null);
			teacherNumberTextField.setText(null);
			dutyTextField.setText(null);
			teacherNameTextField.setText(null);
			apartmentTextField.setText(null);
			bedroomNumberTextField.setText(null);
			bedNumberTextField.setText(null);
			break;
		case "Cancel":System.exit(0);break;
		case "Insert":
			if(userMySQLConnection.exist(teacherNumberTextField.getText(),user)==false)
			{
				teacher=new Teacher(teacherNameTextField.getText(),dutyTextField.getText(),Integer.parseInt(teacherNumberTextField.getText()));
				if(userMySQLConnection.teacherInsert(teacher,user)==false)
					JOptionPane.showMessageDialog(mainFrame,"Insert Error!","Error",JOptionPane.ERROR_MESSAGE);
			}
			if(userMySQLConnection.exist(Integer.parseInt(studentNumberTextField.getText()),user))
			{
				JOptionPane.showMessageDialog(mainFrame,"Student exists!","Warning",JOptionPane.WARNING_MESSAGE);
				break;
			}
			else 
			{
				try
				{
					student=new Student(studentNameTextField.getText(),facultyTextField.getText(),Integer.parseInt(studentNumberTextField.getText()),specialtyTextField.getText(),Integer.parseInt(classTextField.getText()),Integer.parseInt(yearTextField.getText()));
					apartment=new Apartment(apartmentTextField.getText(),Integer.parseInt(bedroomNumberTextField.getText()),Integer.parseInt(bedNumberTextField.getText()));
					if(JOptionPane.showConfirmDialog(mainFrame,"Are you sure to insert?")!=0)
						break;
					else if(userMySQLConnection.studentInsert(student,Integer.parseInt(teacherNumberTextField.getText()),apartment,user))
						JOptionPane.showMessageDialog(mainFrame,"Insert successfully!");
					else
						JOptionPane.showMessageDialog(mainFrame,"Insert unsuccessfully!","Error",JOptionPane.ERROR_MESSAGE);
				}
				catch(HeadlessException exception)
				{
					JOptionPane.showMessageDialog(mainFrame,"Insert Error!","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case "Delete":
			if(userMySQLConnection.exist(Integer.parseInt(studentNumberTextField.getText()),user)==false)
				JOptionPane.showMessageDialog(mainFrame,"Student don't exist!","Warning",JOptionPane.WARNING_MESSAGE);
			else
			{
				try
				{
					if(JOptionPane.showConfirmDialog(mainFrame,"Are you sure to delete?")!=0)
						break;
					else if(userMySQLConnection.delete(Integer.parseInt(studentNumberTextField.getText()),user))
						JOptionPane.showMessageDialog(mainFrame,"Delete successfully!");
					else
						JOptionPane.showMessageDialog(mainFrame,"Delete unsuccessfully!","Error",JOptionPane.ERROR_MESSAGE);
				}
				catch(HeadlessException exception)
				{
					JOptionPane.showMessageDialog(mainFrame,"Delete Error!","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case "Modify":
			if(userMySQLConnection.exist(Integer.parseInt(studentNumberTextField.getText()),user)==false)
				JOptionPane.showMessageDialog(mainFrame,"Student don't exist!","Warning",JOptionPane.WARNING_MESSAGE);
			else
			{
				apartment=new Apartment(apartmentTextField.getText(),Integer.parseInt(bedroomNumberTextField.getText()),Integer.parseInt(bedNumberTextField.getText()));
				try
				{
					if(JOptionPane.showConfirmDialog(mainFrame,"Are you sure to Modify?")!=0)
						break;
					else if(userMySQLConnection.update(Integer.parseInt(studentNumberTextField.getText()),apartment,user))
						JOptionPane.showMessageDialog(mainFrame,"Modify successfully!");
					else
						JOptionPane.showMessageDialog(mainFrame,"Modify unsuccessfully!","Error",JOptionPane.ERROR_MESSAGE);
				}
				catch(HeadlessException exception)
				{
					JOptionPane.showMessageDialog(mainFrame,"Modify Error!","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		default:break;
		}
	}
	public void windowOpened(WindowEvent event)
	{
		if(userMySQLConnection.totalStudent(user)>=0)
			JOptionPane.showMessageDialog(mainFrame,"Current total student = "+userMySQLConnection.totalStudent(user));
		else
			JOptionPane.showMessageDialog(mainFrame,"Total student error","Error",JOptionPane.ERROR_MESSAGE);
	}
}
