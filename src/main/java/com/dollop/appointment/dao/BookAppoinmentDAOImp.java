package com.dollop.appointment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.dollop.appointment.model.BookAppoinment;
import com.dollop.appointment.utility.DBConnection;

public class BookAppoinmentDAOImp {
	
	static Connection con = null;
	public BookAppoinmentDAOImp() 
	{
		con = DBConnection.openConnection();
	}

	public void createAppoinment(BookAppoinment ba) 
	{
		//This method for create appoinment
		String dml="insert into bookappoinment (?,?)"; 
		try {
			PreparedStatement ps = con.prepareStatement(dml);
			ps.setInt(3, ba.getAppDate());
			ps.setInt(4, ba.getAppTime());
			
			ps.executeUpdate();
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
}
