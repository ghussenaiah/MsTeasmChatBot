package com.microsoft.teams.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microsoft.teams.app.entity.AutoGenarationCode;

@Repository
public interface AutoGenerationRepo extends JpaRepository<AutoGenarationCode, Long> {

	// @Transactional(readOnly=false)
	// @Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName")
	// public List<Support_298>
	// listDepertmentOverDepartmentName(@Param("departmentName") String name);

	// List<Support_298> findAllByDepartmentId(String departmentId);

	// @Transactional(readOnly=false)

	/*
	 * @Query("SELECT s FROM Support_298 s WHERE s.departmentid = :departmentid")
	 * public List<Support_298> findAllByDepartmentId(@Param("departmentid") String
	 * departmentId);
	 */

	@Query("SELECT a FROM AutoGenarationCode a WHERE a.entityId = :entityId")
	public AutoGenarationCode getLastTicketNumber(@Param("entityId") String entityid);
}
