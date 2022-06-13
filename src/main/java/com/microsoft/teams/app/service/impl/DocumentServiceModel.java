package com.microsoft.teams.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.teams.app.entity.DocumentModel;
import com.microsoft.teams.app.repository.DocumentRepository;


@Service 
public class DocumentServiceModel {

	@Autowired
	DocumentRepository docrepository;

	public DocumentModel save(DocumentModel docmodel) {
		return docrepository.save(docmodel);
	}

	public DocumentModel getFile(String docId) {
		return docrepository.findByDocId(docId);

	}

	public void delete(String docId) {
		docrepository.deleteByDocId(docId);
	}
}
