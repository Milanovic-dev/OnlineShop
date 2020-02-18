package beans;

import java.util.ArrayList;
import java.util.List;

import beans.User.Role;

public class Seller extends User
{
	private List<Deal> published;
	private List<Deal> delivered;
	private List<Review> reviews;
	private int likes;
	private int dislikes;
	
	public Seller()
	{
		super();
	}
	
	public Seller(String username, String email, String password)
	{
		super(username,email,password);
		published = new ArrayList<Deal>();
		delivered = new ArrayList<Deal>();
		reviews = new ArrayList<Review>();
		likes = 0;
		dislikes = 0;
	}
	
	public Seller(String username, String email, String password, String name, String lastName, String city,
			String contact, String date)
	{
		super(username,email,password,name,lastName,Role.Seller,city,contact,date);
		published = new ArrayList<Deal>();
		delivered = new ArrayList<Deal>();
		reviews = new ArrayList<Review>();
		likes = 0;
		dislikes = 0;
	}
	
	public void addReview(Review r)
	{
		reviews.add(r);
	}
	
	public void addToPublished(Deal d)
	{
		d.setSeller(getEmail());
		published.add(d);
	}
	
	public void addToPublishedRange(List<Deal> deals)
	{
		for(Deal d: deals)
		{
			d.setSeller(getEmail());
		}
		
		published.addAll(deals);
	}
	
	public void deleteFromPublished(Deal d)
	{
		published.remove(d);
	}
	
	public void deleteReview(Review review)
	{
		for(Review r : reviews)
		{
			if(r.getHeader().equals(review.getHeader()) && r.getContent().equals(review.getContent()))
			{
				r.archive();
			}
		}
	}
	
	public void replaceReview(Review review,String header,String content)
	{
		for(Review r : reviews)
		{
			if(r.getHeader().equals(header) && r.getContent().equals(content))
			{
				r.setHeader(review.getHeader());
				r.setContent(review.getContent());
				r.setDeal(review.getDeal());
				r.setImageSrc(review.getImageSrc());
				r.setIsDealHonored(review.getIsDealHonored());
				r.setIsDescTrue(review.getIsDescTrue());
			}
		}
	}
	
	public void addToDelivered(Deal d)
	{
		delivered.add(d);
	}
	
	public Boolean contains(Deal d)
	{
		return published.contains(d) || delivered.contains(d);
	}
		
	public void addLike()
	{
		likes++;
	}
	
	public void addDislike()
	{
		dislikes++;
	}
	
	public int getLikes()
	{
		return likes;
	}
	
	public int getDislikes()
	{
		return dislikes;
	}
	
	public Deal getFromPublished(String name)
	{
		for(Deal d: published)
		{
			if(d.getName().equals(name))
			{
				return d;
			}
		}
		
		return null;
	}
	
	public List<Deal> getPublished() {
		return published;
	}

	public List<Deal> getDelivered() {
		return delivered;
	}
	
	public List<Review> getReviews()
	{
		return reviews;
	}

	public static Seller Parse(User u)
	{
		Seller s = new Seller(u.getUsername(),u.getEmail(),u.getPassword(),u.getName(),u.getLastName(),u.getCity(),u.getContact(),u.getDate());
			
		return s;
	}
}
