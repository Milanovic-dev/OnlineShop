package beans;

import java.util.ArrayList;
import java.util.List;

import beans.User.Role;

public class Buyer extends User{
	
	private List<Deal> ordered;
	private List<Deal> delivered;
	private List<Deal> favorites;
	
	public Buyer()
	{
		super();
	}
	
	public Buyer(String username, String email, String password)
	{
		super(username,email,password);
		
		ordered = new ArrayList<Deal>();
		delivered = new ArrayList<Deal>();
		favorites = new ArrayList<Deal>();
	}
	
	public Buyer(String username, String email, String password, String name, String lastName, String city,
			String contact, String date)
	{
		super(username,email,password,name,lastName,Role.Buyer,city,contact,date);
		ordered = new ArrayList<Deal>();
		delivered = new ArrayList<Deal>();
		favorites = new ArrayList<Deal>();
	}
	
	public Buyer(User u)
	{
		super(u.getUsername(),u.getEmail(),u.getPassword(),u.getName(),u.getLastName(),Role.Buyer,u.getCity(),u.getContact(),u.getDate());
		ordered = new ArrayList<Deal>();
		delivered = new ArrayList<Deal>();
		favorites = new ArrayList<Deal>();
	}
	
	public void addToOrdered(Deal d)
	{
		ordered.add(d);
	}
	
	public Deal getFromOrdered(String name)
	{
		for(Deal d: ordered)
		{
			if(d.getName().equals(name))
			{
				return d;
			}
		}
		
		return null;
	}
	
	public Boolean contains(Deal deal)
	{
		return ordered.contains(deal);
	}
	
	
	
	public Deal getFromDelivered(String name)
	{
		for(Deal d: delivered)
		{
			if(d.getName().equals(name))
			{
				return d;
			}
		}
		
		return null;
	}
	
	public void deleteFromOrdered(Deal d)
	{
		ordered.remove(d);
	}
	
	public void addToDelivered(Deal d)
	{
		delivered.add(d);
	}
	
	public void addToFavorites(Deal d)
	{
		favorites.add(d);
	}
	
	public void deleteFromFavorites(Deal d)
	{
		favorites.remove(d);
	}

	public List<Deal> getOrdered() {
		return ordered;
	}

	public List<Deal> getDelivered() {
		return delivered;
	}

	public List<Deal> getFavorites() {
		return favorites;
	}
	
	public static Buyer Parse(User u)
	{
		Buyer b = new Buyer(u.getUsername(),u.getEmail(),u.getPassword(),u.getName(),u.getLastName(),u.getCity(),u.getContact(),u.getDate());
		
		return b;
	}
	
}
