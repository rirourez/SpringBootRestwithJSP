package com.jsprest.repository;

import com.jsprest.entity.Project;
import com.jsprest.entity.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByProject(Project project);
}
