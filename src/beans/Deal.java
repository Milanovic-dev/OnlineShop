package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;

public class Deal 
{
	public enum Status{Active, InProcess, Delivered}
	
	private String seller;	
	private String name;
	private String price;
	private String description;
	private int likes;
	private int dislikes;
	private String image;
	private Boolean active;
	private String startDate;
	private String endDate;
	private List<Review> reviews;
	private String city;
	private Boolean archived;
	private Status status;
	private String category;
	private int popularity;
	
	public Deal()
	{
		super();
		init();
	}
	
	public Deal(Deal d)
	{
		super();
		this.seller = d.getSeller();
		this.name = d.getName();
		this.price = d.getPrice();
		this.description = d.getDescription();
		this.likes = 0;
		this.dislikes = 0;
		this.image = d.getImage();
		this.active = true;
		this.startDate = java.time.LocalDateTime.now().toString();
		this.endDate = "";
		this.city = d.getCity();
		this.reviews = new ArrayList<Review>();
		this.category = d.getCategory();
		init();
	}
	
	public Deal(String name, String price, String description,String image,String city) 
	{
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.likes = 0;
		this.dislikes = 0;
		this.image = image;
		this.active = true;
		this.startDate = java.time.LocalDateTime.now().toString();
		this.endDate = "";
		this.city = city;
		this.reviews = new ArrayList<Review>();
		this.category = "";
		init();
	}
	
	

	public Boolean getActive() {
		return active;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	private void init()
	{
		archived = false;
		status = Status.Active;
		popularity = 0;
	}
	
	public String getSeller()
	{
		return seller;
	}
	
	public void setSeller(String seller)
	{
		this.seller = seller;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean isActive() {
		return active;
	}
	
	public Boolean isArchived()
	{
		return archived;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void archive()
	{
		this.archived = true;
	}
	
	public void unarchive()
	{
		this.archived = false;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public void setCategory(String s)
	{
		this.category = s;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public void setStatus(Status status)
	{
		this.status = status;
	}
	
	public void addLike()
	{
		likes++;
	}
	
	public void addDislike()
	{
		dislikes++;
	}
	
	public int getPopularity()
	{
		return popularity;
	}
	
	public void setPopularity(int a)
	{
		popularity = a;
	}
	
	public void addReview(Review r)
	{
		this.reviews.add(r);
	}
	
	public Boolean containsReview(String header)
	{
		for(Review r : reviews)
		{
			if(r.getHeader().equals(header))
			{
				return true;
			}
		}
		
		return false;
	}
	
}
