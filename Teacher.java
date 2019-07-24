public class Teacher 
{
	private String name=null;
	private String duty=null;
	private int number;
	public Teacher()
	{
		
	}
	public Teacher(String name,String duty,int number)
	{
		this.name=name;
		this.duty=duty;
		this.number=number;
	}
	public String getName()
	{
		return this.name;
	}
	public String getDuty()
	{
		return this.duty;
	}
	public int getNumber()
	{
		return this.number;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setDuty(String duty)
	{
		this.duty=duty;
	}
	public void setNumber(int number)
	{
		this.number=number;
	}
}
