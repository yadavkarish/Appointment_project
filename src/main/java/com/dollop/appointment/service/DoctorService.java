package com.dollop.appointment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dollop.appointment.dao.DoctorDAOImp;
import com.dollop.appointment.dao.UserDAOImp;
import com.dollop.appointment.model.DoctorSettingData;
import com.dollop.appointment.model.UserData;

public class DoctorService 
{
	
	UserDAOImp udi = null;
	DoctorDAOImp ddi = null;
	public DoctorService() 
	{
		udi = new UserDAOImp();
		ddi = new DoctorDAOImp();
	}
	
	public void doctorRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String mobileNumber = request.getParameter("mobileNumber");
		String password = request.getParameter("password");

		fname = fname.trim();
		lname = lname.trim();
		mobileNumber = mobileNumber.trim();
		password = password.trim();

		if (fname == "" || lname == "" || mobileNumber == "" || password == "") 
		{
			request.setAttribute("loginError", "Please Enter details !!!!!!");
			RequestDispatcher rd = request.getRequestDispatcher("doctor-register.jsp");
			rd.forward(request, response);
		} 
		else if (isValid(mobileNumber)) 
		{

			UserData ud = new UserData();
			ud.setFname(fname);
			ud.setLname(lname);
			ud.setMobileNumber(mobileNumber);
			ud.setPassword(password);
			ud.setType(1);

			udi.addUserData(ud);
			request.setAttribute("signup", "SignUp Successfully!!!!");
			request.setAttribute("mobile", mobileNumber);
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		} 
		else 
		{
			request.setAttribute("loginError", "Please Enter Valid mobile number !!!!!!");
			RequestDispatcher rd = request.getRequestDispatcher("doctor-register.jsp");
			rd.forward(request, response);
		}
	}

	public void doctorProfileSettingInsData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Get Basic Information of Doctor
		DoctorSettingData dpsd = getBasicInfo(request, response);
		
		//Get Education Details of Doctor
		dpsd = getEducationInfo(request,response,dpsd);
		
/*				
		String []award=request.getParameterValues("award");
		for(int i=0;i<award.length;i++)
		{
			award[i]=award[i].trim();
		}
		
		String []awardYear=request.getParameterValues("awardYear");
		for(int i=0;i<awardYear.length;i++)
		{
			awardYear[i]=awardYear[i].trim();
		}
		
		String []memberships=request.getParameterValues("memberships");
		for(int i=0;i<memberships.length;i++)
		{
			memberships[i]=memberships[i].trim();
		}
		
		String []registration=request.getParameterValues("registration");
		for(int i=0;i<registration.length;i++)
		{
			registration[i]=registration[i].trim();
		}
		
		String []registrationYear=request.getParameterValues("registrationYear");
		for(int i=0;i<registrationYear.length;i++)
		{
			registrationYear[i]=registrationYear[i].trim();
		}
*/
		// all the variables are set data into DoctorSettingData in getter data
		
		
		

/*		
		dpsd.setAward(award);
		dpsd.setAwardYear(awardYear);
		dpsd.setMemberships(memberships);
		dpsd.setRegistration(registration);
		dpsd.setRegistrationYear(registrationYear);

		dpsd.setDegreeId(degreeId);	
*/	
		System.out.println("Going Doctor DAO");
		ddi.doctorProfileInsData(dpsd);
	}



	//---------------------------------------------------------------------------------------------------------------------------------------
	
	public DoctorSettingData getBasicInfo(HttpServletRequest request, HttpServletResponse response)
	{
		DoctorSettingData dpsd = new DoctorSettingData();
		
		String firstName =request.getParameter("firstName");
		firstName=firstName.trim();
		String lastName=request.getParameter("lastName");
		lastName=lastName.trim();
		String mobileNumber=request.getParameter("mobileNumber");
		mobileNumber=mobileNumber.trim();
		String gender=request.getParameter("gender");
		gender=gender.trim();
		String dateOfBirth=request.getParameter("dateOfBirth");
		dateOfBirth=dateOfBirth.trim();
		String biography=request.getParameter("biography");
		biography=biography.trim();
		String clinicName=request.getParameter("clinicName");
		clinicName=clinicName.trim();
		String clinicAddress=request.getParameter("clinicAddress");
		clinicAddress=clinicAddress.trim();
		String addressLine1=request.getParameter("addressLine1");
		addressLine1=addressLine1.trim();
		String addressLine2=request.getParameter("addressLine2");
		addressLine2=addressLine2.trim();
		String city=request.getParameter("city");
		city=city.trim();
		String state=request.getParameter("state");
		state=state.trim();
		String country=request.getParameter("country");
		country=country.trim();
		String postalCode=request.getParameter("postalCode");
		postalCode=postalCode.trim();
		
		//changed
		String pricing = "";
		if("price_free".equals(request.getParameter("rating_option")))
			pricing = "00";
		else if("custom_price".equals(request.getParameter("rating_option")))
			pricing = (request.getParameter("pricing")).trim();
				
		String services=request.getParameter("services");
		services=services.trim();
		String specialist=request.getParameter("specialist");
		specialist=specialist.trim();
		
/*
  		if(firstName=="" || lastName=="" || mobileNumber=="" || gender=="" || dateOfBirth=="" || biography == "" || clinicName=="" || clinicAddress=="" || addressLine1=="" || addressLine2=="" || city=="" || state=="" || country=="" || postalCode=="" || services=="" || specialist=="")
		{
			
		}
*/				
		dpsd.setFirstName(firstName);
		dpsd.setLastName(lastName);
		dpsd.setMobileNumber(mobileNumber);
		dpsd.setGender(gender);
		dpsd.setDateOfBirth(dateOfBirth);
		dpsd.setBiography(biography);
		dpsd.setClinicName(clinicName);
		dpsd.setClinicAddress(clinicAddress);
		dpsd.setAddressLine1(addressLine1);
		dpsd.setAddressLine2(addressLine2);
		dpsd.setCity(city);
		dpsd.setState(state);
		dpsd.setCountry(country);
		dpsd.setPostalCode(postalCode);
		dpsd.setPricing(pricing);
		dpsd.setServices(services);
		dpsd.setSpecialist(specialist);
		
		return dpsd;
	}

//--------------------------------------------------------------------------------------------------------------------------------------	

	public DoctorSettingData getEducationInfo(HttpServletRequest request,HttpServletResponse response,DoctorSettingData dpsd)
	{
		ArrayList<String> dId = new ArrayList<String>(Arrays.asList(request.getParameterValues("degreeId")));
		ArrayList<Integer> degreeId = new ArrayList<Integer>();
		for (String did : dId) 									//Convert String ArrayList to Integer
			degreeId.add(Integer.parseInt(did));		
		
		ArrayList<String> degree = new ArrayList<String>(Arrays.asList(request.getParameterValues("degree")));
		ArrayList<String> college = new ArrayList<String>(Arrays.asList(request.getParameterValues("college")));
		ArrayList<String> yearCompletetion = new ArrayList<String>(Arrays.asList(request.getParameterValues("yearCompletetion")));
		
		if(degree.size() == college.size() && degree.size()==yearCompletetion.size() && degree.size()!=0)
		{				
			for(int i=0;i<degree.size();i++)
			{	
				degree.set(i, degree.get(i).trim());
				college.set(i, college.get(i).trim());
				yearCompletetion.set(i, yearCompletetion.get(i).trim());
			}		
			dpsd.setDegreeId(degreeId);
			dpsd.setDegree(degree);
			dpsd.setCollege(college);
			dpsd.setYearCompletetion(yearCompletetion);
		}
		else
		{
			System.out.println("Empty");
		}		
		return dpsd;
	}
	
	/*
	 String [] hospitalId = request.getParameterValues("hospitalId");
		String [] hospitalName = request.getParameterValues("hospitalName");
		String [] from = request.getParameterValues("from");
		String [] to = request.getParameterValues("to");
		String [] designation = request.getParameterValues("Designation");

		if(hospitalName.length == from.length && hospitalName.length == to.length && hospitalName.length == designation.length && hospitalName.length == 0)
		{
			for(int i=0;i<hospitalName.length;i++)
				hospitalName[i]=hospitalName[i].trim();
			
			for(int i=0;i<from.length;i++)
				from[i]=from[i].trim();
			
			for(int i=0;i<to.length;i++)
				to[i]=to[i].trim();
			
			for(int i=0;i<designation.length;i++)
				designation[i]=designation[i].trim();
			
			dpsd.setHospitalId(hospitalId);
			dpsd.setHospitalName(hospitalName);
			dpsd.setFrom(from);
			dpsd.setTo(to);
			dpsd.setDesignation(designation);
			return dpsd;
		}
		else
		{
			System.out.println("Empty");
		}

	  
	 */
	public static boolean isValid(String s) 
	{
		// The given argument to compile() method
		// is regular expression. With the help of
		// regular expression we can validate mobile
		// number.
		// The number should be of 10 digits.

		// Creating a Pattern class object
		Pattern p = Pattern.compile("^\\d{10}$");

		// Pattern class contains matcher() method
		// to find matching between given number
		// and regular expression for which
		// object of Matcher class is created
		java.util.regex.Matcher m = p.matcher(s);

		// Returning boolean value
		return (m.matches());
	}

}
