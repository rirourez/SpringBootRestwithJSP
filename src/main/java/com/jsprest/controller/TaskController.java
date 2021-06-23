package com.jsprest.controller;

import com.jsprest.entity.Project;
import com.jsprest.entity.Task;
import com.jsprest.repository.TaskRepository;
import com.jsprest.service.CustomerService;
import com.jsprest.service.ProjectService;
import com.jsprest.service.TaskService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


@Controller
public class TaskController {

	
	private static final Logger logger = LogManager.getLogger("TaskController");
	
    @Autowired
    private TaskRepository taskRepository;
	
    @Autowired
    TaskService taskService;
    
    
    @Autowired
    CustomerService customService;
	
    
    @Autowired
    ProjectService prjService;
    @RequestMapping("/task/list")
    public String list(Authentication auth, Model model) {
    	logger.info("list "+auth);
    	return "task/list";
    }
    
   
    @RequestMapping(value = "/task/saveTask", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getSaved(Task task) {
        Map<String, Object> map = new HashMap<String, Object>();

        taskRepository.save(task);
        map.put("status", "200");
        map.put("message", "Task has been saved successfully");

        System.out.println(map);
        return map;
    }


    @RequestMapping(value = "/task/view", method = RequestMethod.GET)
    public String view(@RequestParam Long id, Authentication auth, Model model) {
    	logger.info("view, "+id);
    	
    	Task t = taskService.getTask(id);
    	if (t == null)
    		return list(auth, model);
    	model.addAttribute("task", t);
        return "task/view";
    }

    @RequestMapping(value = "/task/edit")
    public String edit(@RequestParam Long id, @RequestParam String action, Authentication auth, Model model) {
    	logger.info("edit, "+id+", "+action);
    	
    	Task t = taskService.getTask(id);
    	if (t == null)
    		return list(auth, model);
    	
    	if ("del".equals(action)) {
    		taskService.delete(t);
    		return list(auth, model);
    	}
    	model.addAttribute("task", t);
        return "task/edit";
    }
    
    @PostMapping("/task/update")
    public String update(@Valid Task task, BindingResult result, Authentication auth, Model model) {
    	logger.info("update, "+task);
    	if (result.hasErrors()) {
            //project.setProjectId(id);
            //Project p = prjService.getProject(id);
        	if (task == null)
        		return list(auth, model);
        	
        	model.addAttribute("task", task);
        	
            return view(task.getTaskId(), auth, model);
        }
    	Task t = taskService.getTask(task.getTaskId());
    	if (t !=null) {
    		t.setName(task.getName());
    		t.setDescription(task.getDescription());
    		t.setStatus(task.isStatus());
    		taskService.save(t);
    	} else {
            taskService.save(task);
    	}
        return view(task.getTaskId(), auth, model);
    }

    @RequestMapping(value = "/task/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false) String action, Task task, Authentication auth, Model model) {
    	logger.info("add, "+action);
    	if (task == null)	task = new Task();
    	
    	if ("save".equals(action)) {
    		task = taskService.save(task);
    		return view(task.getTaskId(), auth, model);
    	}
    	model.addAttribute("task", task);
        return "task/add";
    }


    @RequestMapping(value = "/task/all", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAll(Task task) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Task> allTask = taskService.getTasks();

        if (allTask != null) {
            map.put("status", "200");
            map.put("message", "Data found");
            map.put("data", allTask);
        } else {
            map.put("status", "404");
            map.put("message", "Task not found");
        }
        System.out.println(map);
        return map;
    }


    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> delete(Task task) {
        Map<String, Object> map = new HashMap<String, Object>();

        taskRepository.delete(task);
        map.put("status", "200");
        map.put("message", "Your task has been deleted successfully");

        System.out.println(map);
        return map;
    }


}
