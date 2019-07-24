import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
public class MySQLConnection
{
	private PreparedStatement preparedStatement=null;
	public MySQLConnection()
	{
		try
		{
			Connection connection=null;
			Class.forName("com.mysql.cj.jdbc.Driver");//加载mysql驱动包，驱动包版本必须与mysql的版本一致，抛出ClassNotFoundException异常
			connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306?useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8","root","12345678");//连接mysql，建立SSL连接，设置时区、字符编码，mysql用户root密码
			connection.close();//Connection使用时抛出SQLException异常
		}
		catch(SQLException exception)
		{
			JOptionPane.showMessageDialog(null,"MySQL connection failed","Error",JOptionPane.WARNING_MESSAGE,null);
			System.exit(0);//系统退出
		}
		catch(ClassNotFoundException exception)
		{
			JOptionPane.showMessageDialog(null,"Drive load failed","Error",JOptionPane.WARNING_MESSAGE,null);
			System.exit(0);
		}
	}
	public Connection getConnection()
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306?useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8","root","12345678");
		}
		catch(SQLException exception)
		{
			JOptionPane.showMessageDialog(new LoginForm().mainFrame,"MySQL connection failed","Error",2,null);
			return null;
		}
	}
	public void databaseInitialization()
	{
		try
		{
			preparedStatement=getConnection().prepareStatement("create database if not exists Apartment;");//对mysql进行操作时需要此时已经与mysql建立连接
			preparedStatement.execute();//此处execute()和executeUpdate()都可以用,executeUpdate()相对使用较多不容出错
			preparedStatement.executeUpdate("use Apartment;");
			preparedStatement.executeUpdate("create table if not exists teacher(number int(11) primary key not null unique,name varchar(11) not null,duty varchar(11) not null,unique index teacherIndex(number));");
			preparedStatement.executeUpdate("create table if not exists student(number int(11) primary key not null unique,name varchar(11) not null,faculty varchar(11) not null,specialty varchar(11) not null,year int(4) not null,class int(4) not null,teacherNumber int(11) not null,constraint foreign key(teacherNumber)references teacher(number),unique index studentIndex(number));");
			preparedStatement.executeUpdate("create table if not exists apartment(apartment varchar(11) not null,bedroomNumber int(4) not null,bedNumber int(4) not null,studentNumber int(11) not null unique,constraint foreign key(studentNumber)references student(number),constraint primary key(apartment,bedroomNumber,bedNumber),unique index apartmentIndex(studentNumber));");
			preparedStatement.executeUpdate("drop view if exists Total;");
			preparedStatement.executeUpdate("create view Total as select count(number) from Apartment.student;");
			preparedStatement.close();//PreparedStatement使用时抛出SQLException类
		}
		catch(SQLException exception)
		{
			JOptionPane.showMessageDialog(null,"Database or table initialization Error","Error",JOptionPane.WARNING_MESSAGE,null);
		}
	}
	public boolean exist(User user)
	{
		try
	 	{
			ResultSet resultSet=null;
			preparedStatement=getConnection().prepareStatement("select user from mysql.user where user=?;");
			preparedStatement.setString(1,user.getAccount());
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
	public boolean grant(User user)
	{
		try
		{
			//参数化sql语句好像只能用这种形式
			preparedStatement=getConnection().prepareStatement("create user ?@localhost identified by ?;");
			preparedStatement.setString(1,user.getAccount());
			preparedStatement.setString(2,String.valueOf(user.getPassword()));
			preparedStatement.execute();
			if(user.getAdministrator())
				preparedStatement=getConnection().prepareStatement("grant select,insert,delete,update on *.* to ?@localhost;");
			else
				preparedStatement=getConnection().prepareStatement("grant select on *.* to ?@localhost;");
			preparedStatement.setString(1,user.getAccount());
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public boolean drop(User user)
	{
		try
		{
			preparedStatement=getConnection().prepareStatement("drop user ?@localhost;");
			preparedStatement.setString(1,user.getAccount());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
	public boolean update(User user)
	{
		try
		{
			preparedStatement=getConnection().prepareStatement("alter user ?@localhost identified with mysql_native_password by ?;");
			preparedStatement.setString(1,user.getAccount());
			preparedStatement.setString(2,String.valueOf(user.getPassword()));
			preparedStatement.executeUpdate();
			preparedStatement.clearParameters();
			preparedStatement=getConnection().prepareStatement("revoke all on *.* from ?@localhost;");
			preparedStatement.setString(1,user.getAccount());
			preparedStatement.executeUpdate();
			preparedStatement.clearParameters();
			if(user.getAdministrator())
				preparedStatement=getConnection().prepareStatement("grant select,insert,delete,update on *.* to ?@localhost;");
			else
				preparedStatement=getConnection().prepareStatement("grant select on *.* to ?@localhost;");
			preparedStatement.setString(1,user.getAccount());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		catch(SQLException exception)
		{
			return false;
		}
	}
}
