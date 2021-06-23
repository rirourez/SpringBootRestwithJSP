package com.jsprest.controller;

import com.jsprest.entity.Project;

import com.jsprest.entity.Task;
import com.jsprest.repository.ProjectRepository;
import com.jsprest.service.CustomerService;
import com.jsprest.service.ProjectService;
import com.jsprest.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@Controller
public class ProjectController {

	private static final Logger logger = LogManager.getLogger("ProjectController");
	
    @Autowired
    private ProjectRepository projectRepository;
    
	@Autowired
    CustomerService customService;
	
    @Autowired
    ProjectService prjService;
    
    @Autowired
    TaskService taskService;

    @RequestMapping("/project/list")
    public String list(Authentication auth, Model model) {
    	logger.info("list "+auth);
    	
        return "project/list";
    }
    
    @RequestMapping(value = "/project/view")
    public String view(@RequestParam Long id, Authentication auth, Model model) {
    	logger.info("view, "+id);
    	
    	Project p = prjService.getProject(id);
    	if (p == null)
    		return list(auth, model);
    	model.addAttribute("project", p);

    	model.addAttribute("tasks", taskService.getProjectTasks(id));
        return "project/view";
    }
    
    @RequestMapping(value = "/project/edit")
    public String edit(@RequestParam Long id, @RequestParam String action, Authentication auth, Model model) {
    	logger.info("edit, "+id+", "+action);
    	
    	Project p = prjService.getProject(id);
    	if (p == null)
    		return list(auth, model);
    	
    	if ("del".equals(action)) {
    		prjService.delete(p);
    		return list(auth, model);
    	}
    	model.addAttribute("project", p);
    	model.addAttribute("tasks", taskService.getProjectTasks(id));
        return "project/edit";
    }
    
    @PostMapping("/project/update")
    public String update(@Valid Project project, BindingResult result, Authentication auth, Model model) {
    	logger.info("update, , "+project);
    	if (result.hasErrors()) {
            //project.setProjectId(id);
            //Project p = prjService.getProject(id);
        	if (project == null)
        		return list(auth, model);
        	
        	model.addAttribute("project", project);
        	
            return view(project.getProjectId(), auth, model);
        }
    	Project prj = prjService.getProject(project.getProjectId());
    	if (prj !=null) {
    		prj.setName(project.getName());
    		prj.setDescription(project.getDescription());
    		prj.setStatus(project.isStatus());
    		prjService.save(prj);
    	} else {
            prjService.save(project);
    	}
        return view(project.getProjectId(), auth, model);
    }
    
    @RequestMapping(value = "/project/add")
    public String add(@RequestParam(required = false) String action, Project project, Authentication auth, Model model) {
    	logger.info("add, "+action);
    	if (project == null)	project = new Project();
    	
    	if ("save".equals(action)) {
    		project = prjService.save(project);
    		return view(project.getProjectId(), auth, model);
    	}
    	model.addAttribute("project", project);
    	model.addAttribute("customers", customService.getCustomers());
        return "project/add";
    }
    
    @RequestMapping(value = "/saveProject", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getSaved(Project project) {
        Map<String, Object> map = new HashMap<String, Object>();

        projectRepository.save(project);
        map.put("status", "200");
        map.put("message", "Project has been saved successfully");

        System.out.println(map);
        return map;
    }


    @RequestMapping(value = "/viewProject", method = RequestMethod.GET)
    public String viewProject() {

        return "project/viewProject";
    }


    @RequestMapping(value = "/addProject", method = RequestMethod.GET)
    public String addProject() {

        return "project/addProject";
    }


    @RequestMapping(value = "/project/all", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAll(Task task) {
    	logger.info("get all");
        Map<String, Object> map = new HashMap<String, Object>();

        List<Project> allProject = prjService.getProjects();

        if (allProject != null) {
            map.put("status", "200");
            map.put("message", "Data found");
            map.put("data", allProject);
        } else {
            map.put("status", "404");
            map.put("message", "Project not found");

        }

        System.out.println(map);
        return map;
    }


    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> delete(Project project) {
        Map<String, Object> map = new HashMap<String, Object>();

        //projectRepository.delete(project);
        map.put("status", "200");
        map.put("message", "Your project has been deleted successfully");

        System.out.println(map);
        return map;
    }


}
