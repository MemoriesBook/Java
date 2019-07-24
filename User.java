public class User
{
	private String account=null;
	private char[] password=null;
	private boolean administrator=false;
	public User()
	{
		
	}
	public User(String account,char[] password,boolean administrator)
	{
		this.account=account;
		this.password=password;
		this.administrator=administrator;
	}
	public User(String account,char[] password)
	{
		this.account=account;
		this.password=password;
	}
	public String getAccount()
	{
		return this.account;
	}
	public char[] getPassword()
	{
		return this.password;
	}
	public boolean getAdministrator()
	{
		return this.administrator;
	}
	public void setAccount(String account)
	{
		this.account=account;
	}
	public void setPassword(char[] password)
	{
		this.password=password;
	}
	public void setAdministrator(boolean administrator)
	{
		this.administrator=administrator;
	}
}
