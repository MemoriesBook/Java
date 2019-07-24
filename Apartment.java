public class Apartment
{
	private String apartment=null;
	private int bedroomNumber;
	private int bedNumber;
	public Apartment()
	{
		
	}
	public Apartment(String apartment,int bedroomNumber,int bedNumber)
	{
		this.apartment=apartment;
		this.bedroomNumber=bedroomNumber;
		this.bedNumber=bedNumber;
	}
	public String getApartment()
	{
		return this.apartment;
	}
	public int getBedroomNumber()
	{
		return this.bedroomNumber;
	}
	public int getBedNumber()
	{
		return this.bedNumber;
	}
	public void setApartment(String apartment)
	{
		this.apartment=apartment;
	}
	public void setBedroomNumber(int bedroomNumber)
	{
		this.bedroomNumber=bedroomNumber;
	}
	public void setBedNumber(int bedNumber)
	{
		this.bedNumber=bedNumber;
	}
}
