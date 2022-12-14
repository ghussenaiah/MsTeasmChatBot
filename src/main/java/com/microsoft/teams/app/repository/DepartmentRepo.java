package com.microsoft.teams.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.microsoft.teams.app.entity.SupportDepartment_311;

@Repository
public interface DepartmentRepo extends JpaRepository<SupportDepartment_311, String> {

	// @Transactional(readOnly=false)
	// @Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName")
	// public List<Department_23>
	// listDepertmentOverDepartmentName(@Param("departmentName") String name);
}
