package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
	private static String dbUrl = "jdbc:mysql://localhost:3306/company?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
	private static String dbUser = "bcuser";
	private static String dbPw = "bc123!@#";
    
	public List<BusinessCard> searchBusinessCard(String keyword){
    	List<BusinessCard> bcList = new ArrayList<>();
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;

    	try {
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPw);
			String sql = "SELECT * FROM businesscard WHERE name LIKE ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
			rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString(1);
				String phone = rs.getString(2);
				String companyName = rs.getString(3);
				BusinessCard bc = new BusinessCard(name,phone,companyName);
				bcList.add(bc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bcList;
    	
    }

    public Integer addBusinessCard(BusinessCard businessCard){
    	Integer insertCnt = 0;
    	
    	Connection conn = null;
    	PreparedStatement ps = null;

    	try {
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPw);
			String sql = "INSERT INTO businesscard (name,phone,companyName) VALUES(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessCard.getName());
			ps.setString(2, businessCard.getPhone());
			ps.setString(3, businessCard.getCompanyName());
			insertCnt = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	
		return insertCnt;	
    }
}
