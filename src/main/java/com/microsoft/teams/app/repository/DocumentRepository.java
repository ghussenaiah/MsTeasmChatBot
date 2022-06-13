package com.microsoft.teams.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microsoft.teams.app.entity.DocumentModel;



@Repository
public interface DocumentRepository  extends JpaRepository<DocumentModel, Integer>  {
	

	DocumentModel findByDocId(String docId);
	
	void deleteByDocId(String docId);
}
