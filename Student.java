public class Student
{
	private String name=null;
	private String faculty=null;
	private int number;
	private String specialty=null;
	private int clas;
	private int year;
	public Student()
	{
		
	}
	public Student(String name,String faculty,int number,String specialty,int clas,int year)
	{
		this.name=name;
		this.faculty=faculty;
		this.number=number;
		this.specialty=specialty;
		this.clas=clas;
		this.year=year;
	}
	public String getName()
	{
		return this.name;
	}
	public String getFaculty()
	{
		return this.faculty;
	}
	public int getNumber()
	{
		return this.number;
	}
	public String getSpecialty()
	{
		return this.specialty;
	}
	public int getClas()
	{
		return this.clas;
	}
	public int getYear()
	{
		return this.year;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setFaculty(String faculty)
	{
		this.faculty=faculty;
	}
	public void setNumber(int number)
	{
		this.number=number;
	}
	public void setSpecialty(String specialty)
	{
		this.specialty=specialty;
	}
	public void setClas(int clas)
	{
		this.clas=clas;
	}
	public void setYear(int year)
	{
		this.year=year;
	}
}
