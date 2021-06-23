package com.jsprest.controller;

import com.jsprest.entity.Project;
import com.jsprest.entity.Task;
import com.jsprest.entity.User;
import com.jsprest.service.UsersService;

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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger("UserController");

	@Autowired
	UsersService userService;

	@RequestMapping("/user/list")
	public String list(Authentication auth, Model model) {
		logger.info("list "+auth);
		return "user/list";
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ModelAndView getPage() {
		ModelAndView view = new ModelAndView("home");
		return view;
	}

	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public String view(@RequestParam Integer id, Authentication auth, Model model) {
		logger.info("view, " + id);

		User u = userService.getUser(id);
		if (u == null)
			return list(auth, model);
		model.addAttribute("user", u);
		return "user/view";
	}

	@RequestMapping(value = "/user/edit")
	public String edit(@RequestParam Integer id, @RequestParam String action, Authentication auth, Model model) {
		logger.info("edit, " + id + ", " + action);

		User u = userService.getUser(id);
		if (u == null)
			return list(auth, model);

		if ("del".equals(action)) {
			userService.delete(u);
			return list(auth, model);
		}
		model.addAttribute("user", u);
		return "user/edit";
	}

	@RequestMapping(value = "/user/add")
    public String add(@RequestParam(required = false) String action, User user, Authentication auth, Model model) {
    	logger.info("add, "+action);
    	if (user == null)	user = new User();
    	
    	if ("save".equals(action)) {
    		user = userService.save(user);
    		return view(user.getUserId(), auth, model);
    	}
    	model.addAttribute("user", user);
        return "user/add";
    }

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSaved(User user) {
		Map<String, Object> map = new HashMap<String, Object>();

		userService.saveOrUpdate(user);
		map.put("status", "200");
		map.put("message", "Your record have been saved successfully");

		System.out.println(map);
		return map;
	}

	@RequestMapping(value = "/user/all", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAll() {
		logger.info("get all");
		Map<String, Object> map = new HashMap<String, Object>();

		List<User> allUsers = userService.list();

		if (allUsers != null) {
			map.put("status", "200");
			map.put("message", "Data found");
			map.put("data", allUsers);
		} else {
			map.put("status", "404");
			map.put("message", "User not found");
		}
		System.out.println(map);
		return map;
	}

	@PostMapping("/user/update")
	public String update(@Valid User user, BindingResult result, Authentication auth, Model model) {
		logger.info("update, " + user);
		if (result.hasErrors()) {

			if (user == null)
				return list(auth, model);

			model.addAttribute("user", user);

			return view(user.getUserId(), auth, model);
		}
		User u = userService.getUser(user.getUserId());
		if (u != null) {
			u.setUser_name(user.getUser_name());
			u.setPhone(user.getPhone());
			u.setAddress(user.getAddress());
			u.setEtat(user.getEtat());
			userService.save(u);
		} else {
			userService.save(user);
		}
		return view(user.getUserId(), auth, model);
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(User users) {
		Map<String, Object> map = new HashMap<String, Object>();

		userService.delete(users);
		map.put("status", "200");
		map.put("message", "Your record have been deleted successfully");

		System.out.println(map);
		return map;
	}
}
