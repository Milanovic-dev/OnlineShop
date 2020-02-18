package beans;

import java.util.ArrayList;
import java.util.List;

public class User 
{
	public enum Role{Admin, Seller, Buyer }
		
	protected String username;
	protected String email;
	protected String password;
	protected String name;
	protected String lastName;
	protected Role role;
	protected String city;
	protected String contact;
	protected String date;
	protected List<Message> messages;
	
	public User()
	{
		super();
	}
	
	public User(String username, String email, String password)
	{
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		messages = new ArrayList<Message>();
	}

	public User(String username, String email, String password, String name, String lastName, Role role, String city,
			String contact, String date) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.role = role;
		this.city = city;
		this.contact = contact;
		this.date = date;
		messages = new ArrayList<Message>();
	}
	
	public void addMessage(Message m)
	{
		messages.add(m);
	}
	
	public void deleteMessage(String header,String content)
	{
		for(Message m : messages)
		{
			if(m.getHeader().equals(header) && m.getContent().equals(content))
			{
				m.archive();
			}
		}
	}
		
	public List<Message> getMessages()
	{
		List<Message> ret = new ArrayList<Message>();
		
		for(Message m : messages)
		{
			if(!m.isArchived())
			{
				ret.add(m);
			}
		}
		
		return ret;
	}
	
	public List<Message> getUnreadMessages()
	{
		List<Message> ret = new ArrayList<Message>();
		
		for(Message m : messages)
		{
			if(!m.isRead() && !m.isArchived()) ret.add(m);
		}
		
		return ret;
	}
	
	public void readAllMessages()
	{
		for(Message m : messages)
		{
			m.setRead(true);
		}
	}
	
	//Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString()
	{
		return "Username: " + this.username + "\n Password: " + this.password + "\n Email: "+ this.email;
	}
		
}
