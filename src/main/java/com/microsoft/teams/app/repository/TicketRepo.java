package com.microsoft.teams.app.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microsoft.teams.app.entity.Support_298;
import com.microsoft.teams.app.entity.Ticket_296;



@Repository
@Transactional
public interface TicketRepo extends JpaRepository<Ticket_296, String> {
	
	
	// @Transactional(readOnly=false)
	// @Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName")
	// public List<Support_298>
	// listDepertmentOverDepartmentName(@Param("departmentName") String name);

	//List<Support_298> findAllByDepartmentId(String departmentId);
	
	//@Transactional(readOnly=false)
  //  @Query("SELECT s FROM Support_298 s WHERE s.supportdepartmentid = :supportdepartmentid")
   // public List<Support_298> findAllBySupportdepartmentId(@Param("supportdepartmentid") String supportdepartmentid);
    

	//@Query("SELECT t FROM Ticket_296 t WHERE t.statuscycleId = :statuscycleid and t.ticketQualityRate is null")
	@Query("SELECT t FROM Ticket_296 t WHERE t.statuscycleId = :statuscycleid")
	public List<Ticket_296> findAllByStatuscycleId(@Param("statuscycleid") String statuscycleid);
    
    
    public Ticket_296 findAllByTicketNumber(String ticketNumber);
    
    public Ticket_296 findAllByChatGroupId(String chatgroupid);
    
    public Ticket_296 findAllByClstktreplyId(String clstktreplyId);
   
}
