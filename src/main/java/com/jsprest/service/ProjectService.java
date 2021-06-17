package com.jsprest.service;

import java.util.List;

import com.jsprest.entity.Project;

public interface ProjectService {

	public long countProjets();
	
	public Project getProject(Long id);
	
	public Project save(Project p);
	
	public void delete(Project p);

	public List<Project> getProjects();
}
