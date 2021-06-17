package com.jsprest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprest.entity.Project;
import com.jsprest.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
    ProjectRepository prjRepo;
	
	@Override
	public long countProjets() {		
		return prjRepo.count();
	}
	
	@Override
	public Project getProject(Long id) {
		if (id == null)					return null;
		Optional<Project> p = prjRepo.findById(id);
		if (p == null || p.isEmpty())		return null;
		return p.get();
	}
	
	@Override
	public List<Project> getProjects() {
		
		List<Project> p = prjRepo.findAll();
		return p;
	}
	
	@Override
	public Project save(Project p) {
		if (p == null)					return null;
		return prjRepo.save(p);
	}
	
	@Override
	public void delete(Project p) {
		if (p == null)					return;
		prjRepo.deleteById(p.getProjectId());
	}

}
