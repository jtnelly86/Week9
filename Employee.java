import java.sql.*;
import java.lang.StringBuilder;

public class Employee
{

	int employeeID;
	String firstName;
	String lastName;
	int age;
	String title;
	private double salary;
	String status;
	int supervisor_id;
	String department;
	int insurance;
	String hireDate;
	String phone;
	String superFirstName;
	String superLastName;
	int bonus;
	
	String connectionString = "jdbc:mysql://127.0.0.1:3306/company";
	String dbLogin = "javauser";
	String dbPassword = "j4v4us3r?";
	

	public Employee(int employeeID)
	{
		this.employeeID = employeeID;
		getEmployee();
		getSuperName();
	}
	
	public void getEmployee()
	{
		try
		{
			Connection conn = null;
			String sql = "SELECT employee_id, first_name, last_name, age, title, salary, status, supervisor_id,"
					+ " bonus, department, insurance, hiredate, phone FROM employees WHERE employee_id = " + this.employeeID + ";";
			
			conn = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
			if (conn!=null)
			{
				try (Statement stmt = conn.createStatement();
	        		    ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
					{
						this.setEmployeeID(Integer.parseInt(rs.getString("employee_id")));
						this.setFirstName(rs.getString("first_name"));
						this.setLastName(rs.getString("last_name"));
						this.setAge(Integer.parseInt(rs.getString("age")));
						this.setTitle(rs.getString("title"));
						this.setSalary(Double.parseDouble(rs.getString("salary")));
						this.setStatus(rs.getString("status"));
						updateStatus();
						this.setSupervisor_id(Integer.parseInt(rs.getString("supervisor_id")));
						this.setBonus(Integer.parseInt(rs.getString("bonus")));
						this.setDepartment(rs.getString("department"));
						this.setInsurance(Integer.parseInt(rs.getString("insurance")));
						
						this.setHireDate(rs.getString("hiredate"));
						this.setPhone(rs.getString("phone"));
						formatPhone();
					}
				}
				catch (Exception e)
				{
					e.getMessage();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Database connection failed.");
            e.printStackTrace();
		}
	}
	
	public void getSuperName()
	{
		try
		{
			Connection conn = null;
			String sqlS = "SELECT first_name, last_name FROM employees WHERE employee_id = " + Integer.toString(this.supervisor_id) + " ;";
			
			conn = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
			if (conn!=null)
			{
				try (Statement stmt = conn.createStatement();
	        		    ResultSet rs = stmt.executeQuery(sqlS))
				{
					if (rs.next())
					{
						this.setSuperFirstName(rs.getString("first_name"));
						this.setSuperLastName(rs.getString("last_name"));
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Database connection failed.");
            e.printStackTrace();
		}
	}
	
	public void updateStatus()
	{
		char i = this.status.charAt(0);
		switch(i)
		{
		case 'F':
			this.status = "Full-Time";
			break;
		case 'P':
			this.status = "Part-Time";
			break;
		case 'C':
			this.status = "Commission";
			break;
		default:
			this.status = "Unknown";
			break;
		}
	}
		
	
	
	public void formatPhone()
	{
		StringBuilder sb1 = new StringBuilder(this.phone);
		sb1.insert(0, "(").insert(4, ") ").insert(9, "-");
		this.phone = sb1.toString();
	}

	public void printEmployee() {
		
		System.out.println();
		System.out.println();
		printDashes(32);
		printDashes(32);
		System.out.println("\tEmployee Report");
		printDashes(32);
		System.out.println("     Personal Information");
		System.out.println("Name: " + this.getFirstName() + " " + this.getLastName());
		System.out.println("Age: " + this.getAge());
		System.out.println("Phone Number: " + this.getPhone());
		printDashes(32);
		System.out.println("\t   Position");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Department: " + this.getDepartment());
		System.out.println("Employee ID: " + this.getEmployeeID());
		formatHireDate();
		System.out.println("Hire date: " + this.getHireDate());
		System.out.println("Supervisor: " + this.getSuperFirstName() + " " + this.getSuperLastName());
		printDashes(32);
		printPay();
		printDashes(32);
		printDashes(32);
		System.out.println();
		System.out.println();
	}
	
	public void printPay()
	{
		int i = this.getInsurance();
		
		System.out.println("\tCompensation");
		System.out.println("Status: " + this.getStatus());
		
		switch (i)
		{
		case 0:
			System.out.println("Insurance Option: None");
			break;
		default:
			System.out.println("Insurance Option: " + this.getInsurance());
			break;
		}
		
		String j = this.getStatus();
		
		if (j == "Full-Time")
		{
			System.out.printf("Salary: $%,.2f/year\n", this.getSalary());
		}
		else if (j == "Part-Time")
		{
			System.out.printf("Hourly rate: $%,.2f/hour\n", ((this.salary / 52) / 30));
		}
		else if (j == "Commission")
		{
			System.out.printf("Hourly rate: $%,.2f/hour\n", (((this.salary / 4) / 52) / 30));
			System.out.printf("Commissions: $%,.2f\n", ((this.salary / 4) * 3));
		}
	}
	
	public void formatHireDate()
	{
		StringBuilder sb2 = new StringBuilder(this.hireDate);
		String s1 = sb2.substring(0, 4);
		sb2.delete(0, 5);
		sb2.append("-" + s1);
		this.hireDate = sb2.toString();
	}
	
	private void printDashes(int x) {
		for (int i = 0; i < x; i++) {
			
			System.out.print("-");
			
		}
		System.out.println("");
	}


	
	/**
	 * @param employeeID the employeeID to set
	 */
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * @return the employeeID
	 */
	public int getEmployeeID() {
		return employeeID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the supervisor_id
	 */
	public int getSupervisor_id() {
		return supervisor_id;
	}

	/**
	 * @param supervisor_id the supervisor_id to set
	 */
	public void setSupervisor_id(int supervisor_id) {
		this.supervisor_id = supervisor_id;
	}

	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the insurance
	 */
	public int getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(int insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the hireDate
	 */
	public String getHireDate() {
		return hireDate;
	}

	/**
	 * @param hireDate the hireDate to set
	 */
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return the superName
	 */
	public String getSuperFirstName() {
		return superFirstName;
	}

	/**
	 * @param superName the superName to set
	 */
	public void setSuperFirstName(String superFirstName) {
		this.superFirstName = superFirstName;
	}


	/**
	 * @return the superLastName
	 */
	public String getSuperLastName() {
		return superLastName;
	}

	/**
	 * @param superLastName the superLastName to set
	 */
	public void setSuperLastName(String superLastName) {
		this.superLastName = superLastName;
	}
}	
	