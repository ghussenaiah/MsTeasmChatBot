package com.microsoft.teams.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.microsoft.teams.app.entity.EscalationMap_312;
import com.microsoft.teams.app.entity.SupportDepartment_311;
import com.microsoft.teams.app.repository.DepartmentRepo;
import com.microsoft.teams.app.repository.EscalationMapDao;



@Component
public class EscalationMapImpl  implements EscalationMapDao{
	

	@Autowired
	EscalationMapDao escRepo;

	@Override
	public List<EscalationMap_312> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EscalationMap_312> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EscalationMap_312> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends EscalationMap_312> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends EscalationMap_312> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<EscalationMap_312> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EscalationMap_312 getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends EscalationMap_312> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends EscalationMap_312> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<EscalationMap_312> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends EscalationMap_312> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EscalationMap_312> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
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
	public void delete(EscalationMap_312 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends EscalationMap_312> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends EscalationMap_312> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends EscalationMap_312> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends EscalationMap_312> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends EscalationMap_312> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EscalationMap_312 findAllBySupportId(String supportid) {
		// TODO Auto-generated method stub
		return escRepo.findAllBySupportId(supportid);
	}
	
	
}