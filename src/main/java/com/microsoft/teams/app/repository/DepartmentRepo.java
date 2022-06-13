package com.microsoft.teams.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.microsoft.teams.app.entity.Department_23;


@Repository
public interface DepartmentRepo extends JpaRepository<Department_23, String> {

	
	// @Transactional(readOnly=false)
	//@Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName")
	//public List<Department_23> listDepertmentOverDepartmentName(@Param("departmentName") String name);
}
