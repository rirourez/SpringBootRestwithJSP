package com.jsprest.service;

import java.util.List;

import com.jsprest.entity.Task;

public interface TaskService {

	public long countTasks();
	
	public long countProjectTasks(Long id);
	
	public List<Task> getProjectTasks(Long id);
	
	public Task getTask(Long id);
}
