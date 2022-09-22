package com.microsoft.teams.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microsoft.teams.app.entity.EscalationMap_312;

@Repository
public interface EscalationMapDao extends JpaRepository<EscalationMap_312, String> {

	
	// @Transactional(readOnly=false)
	// @Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName")
	// public List<Support_298>
	// listDepertmentOverDepartmentName(@Param("departmentName") String name);

	//List<Support_298> findAllByDepartmentId(String departmentId);
	
	//@Transactional(readOnly=false)
    @Query("SELECT e FROM EscalationMap_312 e WHERE e.supportid = :supportid")
    public EscalationMap_312 findAllBySupportId(@Param("supportid") String supportid);
    
   // @Query("SELECT u FROM User u JOIN u.Role r WHERE u.departmentId = :departmentId and r.roleName='Indent Approver1'")
   //	public List<Support_298> findAll(@Param("departmentId") String departmentid);
  	
  // @Query("SELECT s FROM Support_298 s JOIN s.User u WHERE s.id = :id")
  //	public Support_298 findAll(@Param("id") String id);
    
   // @Query("SELECT s FROM Support_298 s WHERE s.supportdepartmentid = :supportdepartmentid")
   // public List<Support_298> findAllById(@Param("supportdepartmentid") String departmentId);
}
