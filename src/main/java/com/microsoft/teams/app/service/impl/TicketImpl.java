package com.microsoft.teams.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.microsoft.teams.app.entity.Support_298;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.SupportRepo;
import com.microsoft.teams.app.repository.TicketRepo;





@Component
public class TicketImpl  implements TicketRepo{
	
	@Autowired
	TicketRepo ticketRepo;

	@Override
	public List<Ticket_296> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket_296> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket_296> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Ticket_296> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Ticket_296> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Ticket_296> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ticket_296 getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Ticket_296> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Ticket_296> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Ticket_296> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Ticket_296> S save(S entity) {
		ticketRepo.save(entity);
		return null;
	}

	@Override
	public Optional<Ticket_296> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Ticket_296 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Ticket_296> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Ticket_296> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Ticket_296> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Ticket_296> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Ticket_296> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Support_298> findAllByDepartmentId(String departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket_296 findAllByTicketNumber(String ticketNumber) {
		return ticketRepo.findAllByTicketNumber(ticketNumber);
	}

	
	@Override
	public Ticket_296 findAllByChatGroupId(String chatgroupid) {
		// TODO Auto-generated method stub
		return ticketRepo.findAllByChatGroupId(chatgroupid);

	}

	@Override
	public Ticket_296 findAllByClstktreplyId(String clstktreplyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket_296> findAllByStatuscycleId(String statuscycleid) {
		// TODO Auto-generated method stub
		return null;
	}
	 

	
}