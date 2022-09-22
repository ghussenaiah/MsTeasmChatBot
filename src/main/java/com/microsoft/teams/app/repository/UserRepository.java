package com.microsoft.teams.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microsoft.teams.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


	// @Query("SELECT u FROM User u WHERE u.departmentId = :departmentId")
	public List<User> findAllByDepartmentId(String departmentid);

	
	// fetching hod data
	//@Query("SELECT u FROM User u JOIN u.Role r WHERE u.departmentId = :departmentId and r.roleName='Indent Approver1'")
	//public User findAll(@Param("departmentId") String departmentid);

}
