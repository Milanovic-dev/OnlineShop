package helpers;

public class DealFilter extends Filter
{
	public String minPrice;
	public String maxPrice;
	public String minLikes;
	public String maxLikes;
	public String minDate;
	public String maxDate;
	public String status;
	public String category;
	
	public DealFilter()
	{
		super();
	}
	
	public String toString()
	{
		return "Filter:"+this.name + ", " + this.city + ", " + this.minPrice;
	}
}
