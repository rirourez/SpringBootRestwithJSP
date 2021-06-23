package com.jsprest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprest.entity.Project;
import com.jsprest.entity.Task;
import com.jsprest.repository.ProjectRepository;
import com.jsprest.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
    TaskRepository taskRepo;
	
	@Autowired
    ProjectRepository prjRepo;
	
	@Override
	public long countTasks() {		
		return taskRepo.count();
	}
	
	@Override
	public long countProjectTasks(Long id) {		
		return taskRepo.count();
	}
	
	@Override
	public List<Task> getProjectTasks(Long id) {
		if (id == null)					return null;
		Optional<Project> p = prjRepo.findById(id);
		List<Task> o = taskRepo.findByProject(p.get());
		return o;
	}
	
	@Override
	public Task getTask(Long id) {
		if (id == null)					return null;
		Optional<Task> o = taskRepo.findById(id);
		if (o == null || o.isEmpty())		return null;
		return o.get();
	}
public List<Task> getTasks() {
		
		List<Task> t = taskRepo.findAll();
		return t;
}
	@Override
	public Task save(Task t) {
		if (t == null)					return null;
		return taskRepo.save(t);
	}

	@Override
	public void delete(Task t) {
		if (t == null)					return;
		taskRepo.deleteById(t.getTaskId());
	}
}
