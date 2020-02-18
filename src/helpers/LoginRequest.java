package helpers;

public class LoginRequest 
{
	private String email;
	private String password;
	
	public LoginRequest()
	{
		super();
	}
	
	public LoginRequest(String email,String password)
	{
		this.email = email;
		this.password = password;
	}
	
	public static Boolean Match(LoginRequest r1, LoginRequest r2)
	{
		return r1.email.equals(r2.email) && r1.password.equals(r2.password);
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
}