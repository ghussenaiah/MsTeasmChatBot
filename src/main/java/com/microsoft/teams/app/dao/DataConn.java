package com.microsoft.teams.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.teams.app.utility.Utility;

import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class DataConn {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	
	@Autowired
	private Utility utility;
	
	
	
	
	public static Connection connection=null;
	
	public Connection getInstance() {
		if (connection == null) {
			synchronized (Connection.class) {
				if (connection == null) {
					connection = dbconnection();
				}
			}
		}
		return connection;
	} 
	
	// @PostConstruct
	public Connection dbconnection() {

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://135.181.202.86:5432/Srinivasa_Live_kagamierp",
					"postgres", "postgresql@kagamierp");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return connection;

	}
	
	
	public int createTicket(String name, Integer age) {

		// String SQL = "insert into Student (name, age) values (?, ?)";

		Long trasactionEntityId = utility.nextId();

		Date date = new Date();
		String createdatetime = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date);

		String SQLUser = "insert into ticket_296 (transactionentityid, createdatetime, createdby,updatedatetime, updatedby, chatgroupid, departmentid, "
				+ "description, employeeteamsid, id, priorityid, status,"
				+ " statuscycleid, supportid, ticketnumber, ticketqualitycomments, ticketqualityrate, tickettitle) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		jdbcTemplateObject.update(SQLUser, trasactionEntityId, createdatetime, "admin", createdatetime, "admin",
				"chat1", 0, "hello world", "test", age, age, age, age, age, age, age, age, age, age);

		return 0;
	}
	
	//String query = "select id,autoCodeNo,entityId,coloumnId,type from AutoGenarationCode entity where entityId = :entityId";
	//Map<String, Object> parameterMap = new HashMap<>();
	// parameterMap.put(RuntimeGenerationConstants.ENTITY_ID, entityName);

	
	public int update_chat() {
		return 0;
	}
	
}
