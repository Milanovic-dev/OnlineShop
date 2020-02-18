package beans;

import java.util.ArrayList;

import beans.User.Role;

public class Admin extends User
{
	public Admin(String username, String email, String password)
	{
		super(username,email,password);
		
	}
	
	public Admin(String username, String email, String password, String name, String lastName, String city,
			String contact)
	{
		super(username,email,password,name,lastName,Role.Admin,city,contact,java.time.LocalDateTime.now().toString());
	}
	
	public Admin()
	{
		super();
	}
	
	public static Admin Parse(User u)
	{
		Admin a = new Admin(u.getUsername(),u.getEmail(),u.getPassword(),u.getName(),u.getLastName(),u.getCity(),u.getContact());
		
		return a;
	}
}
