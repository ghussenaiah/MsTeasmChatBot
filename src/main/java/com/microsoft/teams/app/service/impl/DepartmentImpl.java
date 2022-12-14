package com.microsoft.teams.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.microsoft.teams.app.entity.SupportDepartment_311;
import com.microsoft.teams.app.repository.DepartmentRepo;

@Component
public class DepartmentImpl  implements DepartmentRepo{
	
	
	@Autowired
	DepartmentRepo departmentRepo;
	

	@Override
	public List<SupportDepartment_311> findAll() {
		
		return departmentRepo.findAll();
	}

	@Override
	public List<SupportDepartment_311> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupportDepartment_311> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SupportDepartment_311> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends SupportDepartment_311> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<SupportDepartment_311> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SupportDepartment_311 getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SupportDepartment_311> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SupportDepartment_311> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SupportDepartment_311> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SupportDepartment_311> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SupportDepartment_311> findById(String id) {
		return departmentRepo.findById(id);

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
	public void delete(SupportDepartment_311 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends SupportDepartment_311> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends SupportDepartment_311> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SupportDepartment_311> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SupportDepartment_311> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends SupportDepartment_311> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * @Override public List<Department_23> listDepertmentOverDepartmentName(String
	 * name) { // TODO Auto-generated method stub return null; }
	 */

}
