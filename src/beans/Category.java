package beans;

import java.util.List;

public class Category 
{
	private String name;
	private String description;
	private List<Deal> deals;
	private Boolean archived;
	
	public Category()
	{
		super();
		archived = false;
	}
	
	public Category(String name, String description)
	{
		this.name = name;
		this.description = description;
		archived = false;
	}
	
	public void addDeal(Deal d)
	{
		deals.add(d);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Deal> getDeals() {
		return deals;
	}

	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}
	
	public Boolean isArchived()
	{
		return archived;
	}
	
	public void archive()
	{
		archived = true;
	}
	
	public void unarchive()
	{
		archived = false;
	}
	
}
