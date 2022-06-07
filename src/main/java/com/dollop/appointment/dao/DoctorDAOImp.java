package com.dollop.appointment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.dollop.appointment.model.DoctorSettingData;
import com.dollop.appointment.utility.DBConnection;

public class DoctorDAOImp {

static Connection con=null;

	public DoctorDAOImp()
	{
		con=(Connection) DBConnection.openConnection();
	}

	public DoctorSettingData getDoctor(String mobileNumber)
	{
		String dql ="SELECT * FROM doctorprofilesetting Where mobileNumber = ?"; 
		DoctorSettingData doctor = new DoctorSettingData();
		int doctorId = 8;
		try
		{
			PreparedStatement ps = con.prepareStatement(dql);
			ps.setString(1, mobileNumber);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				doctor.setDoctorId(rs.getInt("doctorId"));
				doctor.setFirstName(rs.getString("firstName"));
				doctor.setLastName(rs.getString("lastName"));
				doctor.setMobileNumber(rs.getString("mobileNumber"));
				doctor.setGender(rs.getString("gender"));
				doctor.setDateOfBirth(rs.getString("dateOfBirth"));
				doctor.setBiography(rs.getString("biography"));
				doctor.setClinicName(rs.getString("clinicName"));
				doctor.setClinicAddress(rs.getString("clinicAddress"));
				doctor.setAddressLine1(rs.getString("addressLine1"));
				doctor.setAddressLine2(rs.getString("addressLine2"));
				doctor.setCity(rs.getString("city"));
				doctor.setState(rs.getString("state"));
				doctor.setCountry(rs.getString("country"));
				doctor.setPostalCode(rs.getString("postalCode"));
				doctor.setPricing(rs.getString("pricing"));
				doctor.setServices(rs.getString("services"));
				doctor.setSpecialist(rs.getString("specialist"));
			}
			doctor = getEducationDetails(doctorId,doctor);
			return doctor;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return doctor;
	}
//-----------------------------------------------------------------------------------------------
		
	public DoctorSettingData getEducationDetails(int doctorId,DoctorSettingData doctor)
	{
		String dql = "SELECT * FROM doctoreducation WHERE doctorId = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(dql);
			ps.setInt(1, doctorId);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Integer> degreeId = new ArrayList<Integer>();
			ArrayList<String> degree = new ArrayList<String>();
			ArrayList<String> college = new ArrayList<String>();
			ArrayList<String> yearCompletetion = new ArrayList<String>();
			
			while(rs.next())
			{
				degreeId.add(rs.getInt("degreeId"));
				degree.add(rs.getString("degree"));
				college.add(rs.getString("collage"));
				yearCompletetion.add(rs.getString("yearCompletetion"));
			}
			doctor.setDegreeId(degreeId);
			doctor.setDegree(degree);
			doctor.setCollege(college);
			doctor.setYearCompletetion(yearCompletetion);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return doctor;
	}

//---------------------------------------------------------------------------------------------------------------------
	
	public void doctorProfileInsData(DoctorSettingData dsd)
	{
		int doctorId = 8;
		boolean b1 = insertBasicInformation(dsd);		
		boolean b2 = insertEducationDetails(dsd,doctorId);
			
//			String dml4 = "UPDATE doctoraward SET award=?,awardYear=? WHERE doctorId=?";
//			String dml5 = "UPDATE doctormemberships SET membership=?,WHERE doctorId=?";
//			String dml6 = "UPDATE doctorregistrations SET registration=?,year=? WHERE doctorId=?";

/*		String dml = null;	
		int rowCount = 0;
		String [] hospitalId = dsd.getHospitalId();
		String [] hospitalName = dsd.getHospitalName();
		String [] fromYear = dsd.getFrom();
		String [] toYear = dsd.getTo();
		String [] designation = dsd.getDesignation();
			
		for(int i=0;i<hospitalName.length;i++)
		{	
			if(hospitalId.length == hospitalName.length)
			{
				int hid = Integer.parseInt(hospitalId[i]);
				if(isDoctorDetailsExist("doctorexperience",doctorId,"hospitalId",hid))
				{	
					dml = "UPDATE doctorexperience SET hospitalName= ?,fromYear=?,toYear=?,designation=? WHERE doctorId=? AND hospitalId =?";
					try
					{
						PreparedStatement ps = con.prepareStatement(dml);
						ps.setString(1,hospitalName[i]);
						ps.setString(2,fromYear[i]);
						ps.setString(3,toYear[i]);
						ps.setString(4,designation[i]);
						ps.setInt(5,doctorId);
						ps.setInt(6,hid);
						rowCount = ps.executeUpdate();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}	
			}	
			else
			{
				dml = "INSERT INTO doctorEducation(hospitalName,froamYear,toYear,designation,doctorId) VALUES(?,?,?,?,?)";
				try
				{
					PreparedStatement ps = con.prepareStatement(dml);
					ps.setString(1, hospitalName[i]);
					ps.setString(2, fromYear[i]);
					ps.setString(3, toYear[i]);
					ps.setString(4, designation[i]);
					ps.setInt(5, doctorId);
					rowCount = ps.executeUpdate();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}					
			}					
		}		
//		if(rowCount>0)
//			return true;
//		else
//			return false;	
 * */
 
	}
	
	public boolean insertBasicInformation(DoctorSettingData dsd)
	{
		int doctorId = 8;
		String dml = "UPDATE doctorprofilesetting SET firstName=?,lastName=?,mobileNumber=?,gender=?,dateOfBirth=?,biography=?,clinicName=?,clinicAddress=?,addressLine1=?,addressLine2=?,city=?,state=?,country=?,postalCode=?,pricing=?,services=?,specialist=? WHERE doctorId=?";
		try
		{
			PreparedStatement ps= (PreparedStatement) con.prepareStatement(dml);
			
			ps.setString(1, dsd.getFirstName());
			ps.setString(2, dsd.getLastName());
			ps.setString(3, dsd.getMobileNumber());
			ps.setString(4, dsd.getGender());
			ps.setString(5, dsd.getDateOfBirth());
			ps.setString(6, dsd.getBiography());
			ps.setString(7, dsd.getClinicName());
			ps.setString(8, dsd.getClinicAddress());
			ps.setString(9, dsd.getAddressLine1());
			ps.setString(10, dsd.getAddressLine2());
			ps.setString(11, dsd.getCity());
			ps.setString(12, dsd.getState());
			ps.setString(13, dsd.getCountry());
			ps.setString(14, dsd.getPostalCode());
			ps.setString(15, dsd.getPricing());
			ps.setString(16, dsd.getServices());
			ps.setString(17, dsd.getSpecialist());
			
			ps.setInt(18, doctorId);
			int rowCount = ps.executeUpdate();
//			System.out.println(rowCount);
			if(rowCount == 1)
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertEducationDetails(DoctorSettingData dsd, int doctorId)
	{
		String dml = null;	
		int rowCount = 0;
		ArrayList<Integer> degreeId = dsd.getDegreeId();
		ArrayList<String> degree = dsd.getDegree();
		ArrayList<String> college = dsd.getCollege();
		ArrayList<String> yearCompletetion = dsd.getYearCompletetion();
			
		for(int i=0;i<degree.size();i++)
		{	
			if(degreeId.size() == degree.size())
			{
			 	if(isDoctorDetailsExist("doctoreducation",doctorId,"degreeId",degreeId.get(i)))
				{	
			 		System.out.println("Inside Condition True");
					dml = "UPDATE doctorEducation SET degree= ?,collage=?,yearCompletetion=? WHERE doctorId=? AND degreeId =?";
					try
					{
						PreparedStatement ps = con.prepareStatement(dml);
						ps.setString(1,degree.get(i));
						ps.setString(2,college.get(i));
						ps.setString(3, yearCompletetion.get(i));
						ps.setInt(4, doctorId);
						ps.setInt(5, degreeId.get(i));
						rowCount = ps.executeUpdate();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}	
			}	
			else
			{
				dml = "INSERT INTO doctorEducation(degree,collage,yearCompletetion,doctorId) VALUES(?,?,?,?)";
				try
				{
					PreparedStatement ps = con.prepareStatement(dml);
					ps.setString(1, degree.get(i));
					ps.setString(2, college.get(i));
					ps.setString(3, yearCompletetion.get(i));
					ps.setInt(4, doctorId);
					rowCount = ps.executeUpdate();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}					
			}					
		}		
		if(rowCount>0)
			return true;
		else
			return false;
	}
	
	public void insertDoctorExperience(DoctorSettingData dsd, int doctorId)
	{
		String dml = null;	
		int rowCount = 0;
		ArrayList<Integer> degreeId = dsd.getDegreeId();
		ArrayList<String> degree = dsd.getDegree();
		ArrayList<String> college = dsd.getCollege();
		ArrayList<String> yearCompletetion = dsd.getYearCompletetion();
	}
	public boolean isDoctorDetailsExist(String tableName,int doctorId,String tableIdName,int tableId)
	{
		String dql = "SELECT * FROM "+tableName+" WHERE doctorId = ? AND "+tableIdName+" = ?";
		System.out.println("String : "+dql);
		try
		{
			PreparedStatement ps = con.prepareStatement(dql);
			ps.setInt(1, doctorId);
			ps.setInt(2, tableId);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
