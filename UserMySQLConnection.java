import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
public class UserMySQLConnection
{
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private Student student=new Student();
	private Teacher teacher=new Teacher();
	private Apartment apartment=new Apartment();
	public boolean connect(User user)
	{
		try
		{
			//注意:非root用户的登陆方式，url加入user和password，第二和第三个参数分别是需要登陆的用户的user和password,不是root用户
			DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306?useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&user="+user.getAccount()+"&password="+String.valueOf(user.getPassword()),user.getAccount(),String.valueOf(user.getPassword()));//char[]转换为String类型使用静态String.valueof()
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public Connection getConnection(User user)
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306?useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&user="+user.getAccount()+"&password="+String.valueOf(user.getPassword()),user.getAccount(),String.valueOf(user.getPassword()));
		}
		catch(SQLException exception)
		{
			return null;
		}
	}
	public int administrator(User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("select update_priv from mysql.user where user=?;");//?表示参数化的mysql语句
			preparedStatement.setString(1,user.getAccount());//为第一个参数根据属性的类型赋值
			resultSet=preparedStatement.executeQuery();//返回ResultSet类，mysql结果集的数据表，抛出SQLException异常
			while(resultSet.next())//从第一行开始判断每一行是否为空
				switch(resultSet.getString("update_priv"))//从数据表的制定列中得到该列的值
				{
				case "Y":return 1;
				case "N":return 2;
				default:break;
				}
			return 0;
		}
		catch(SQLException exception)
		{
			return 0;
		}
	}
	public boolean exist(int studentNumber,User user)
	{
		try
	 	{
			preparedStatement=getConnection(user).prepareStatement("select number from Apartment.student where number=?;");
			preparedStatement.setInt(1,studentNumber);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
				return true;
			return false;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public boolean exist(String teacherNumber,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("select number from Apartment.teacher where number=?;");
			preparedStatement.setInt(1,Integer.parseInt(teacherNumber));//将字符串型转换为整型，使用静态的Integer.parseInt()
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
				return true;
			return false;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public Student searchStudent(int studentNumber,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("select * from Apartment.student where number=?;");
			preparedStatement.setInt(1,studentNumber);
			resultSet=preparedStatement.executeQuery();
			student.setNumber(studentNumber);
			while(resultSet.next())
			{
				student.setName(resultSet.getString("name"));
				student.setFaculty(resultSet.getString("faculty"));
				student.setSpecialty(resultSet.getString("specialty"));
				student.setClas(resultSet.getInt("class"));
				student.setYear(resultSet.getInt("year"));
			}
			return student;	
		}
		catch(SQLException exception)
		{
			return null;
		}
	}
	public Teacher searchTeacher(int studentNumber,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("select teacherNumber from Apartment.student where number=?;");
			preparedStatement.setInt(1,studentNumber);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
				teacher.setNumber(resultSet.getInt("teacherNumber"));
			preparedStatement.clearParameters();
			preparedStatement=getConnection(user).prepareStatement("select name,duty from Apartment.teacher where number=?;");
			preparedStatement.setInt(1,teacher.getNumber());
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				teacher.setName(resultSet.getString("name"));
				teacher.setDuty(resultSet.getString("duty"));
			}
			return teacher;	
		}
		catch(SQLException exception)
		{
			return null;
		}
	}
	public Apartment searchApartment(int studentNumber,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("select apartment,bedroomNumber,bedNumber from Apartment.apartment where studentNumber=?;");
			preparedStatement.setInt(1,studentNumber);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				apartment.setApartment(resultSet.getString("apartment"));
				apartment.setBedroomNumber(resultSet.getInt("bedroomNumber"));
				apartment.setBedNumber(resultSet.getInt("bedNumber"));
			}
			return apartment;	
		}
		catch(SQLException exception)
		{
			return null;
		}
	}
	public boolean studentInsert(Student student,int teacherNumber,Apartment apartment,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("insert into Apartment.student values(?,?,?,?,?,?,?);");
			preparedStatement.setInt(1,student.getNumber());
			preparedStatement.setString(2,student.getName());
			preparedStatement.setString(3,student.getFaculty());
			preparedStatement.setString(4,student.getSpecialty());
			preparedStatement.setInt(5,student.getYear());
			preparedStatement.setInt(6,student.getClas());
			preparedStatement.setInt(7,teacherNumber);
			preparedStatement.executeUpdate();
			preparedStatement.clearParameters();
			preparedStatement.clearBatch();
			preparedStatement=getConnection(user).prepareStatement("insert into Apartment.apartment values(?,?,?,?);");
			preparedStatement.setString(1,apartment.getApartment());
			preparedStatement.setInt(2,apartment.getBedroomNumber());
			preparedStatement.setInt(3,apartment.getBedNumber());
			preparedStatement.setInt(4,student.getNumber());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
			return false;
		}
	}
	public boolean teacherInsert(Teacher teacher,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("insert into Apartment.teacher values(?,?,?);");
			preparedStatement.setInt(1,teacher.getNumber());
			preparedStatement.setString(2,teacher.getName());
			preparedStatement.setString(3,teacher.getDuty());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public boolean delete(int studentNumber,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("delete from Apartment.apartment where studentNumber=?;");
			preparedStatement.setInt(1,studentNumber);
			preparedStatement.executeUpdate();
			preparedStatement.clearParameters();
			preparedStatement.clearBatch();
			preparedStatement=getConnection(user).prepareStatement("delete from Apartment.student where number=?;");
			preparedStatement.setInt(1,studentNumber);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public boolean update(int studentNumber,Apartment apartment,User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("Update Apartment.apartment set apartment=?,bedroomNumber=?,bedNumber=? where studentNumber=?;");
			preparedStatement.setString(1,apartment.getApartment());
			preparedStatement.setInt(2,apartment.getBedroomNumber());
			preparedStatement.setInt(3,apartment.getBedNumber());
			preparedStatement.setInt(4,studentNumber);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public int totalStudent(User user)
	{
		try
		{
			preparedStatement=getConnection(user).prepareStatement("select * from Apartment.Total;");
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
				return resultSet.getInt("count(number)");
			return -1;
		}
		catch(SQLException exception)
		{
			return -1;
		}
	}
}
