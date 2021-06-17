package com.jsprest.controller;

import com.jsprest.entity.Admin;
import com.jsprest.entity.LoginModel;
import com.jsprest.repository.AdminRepository;
import com.jsprest.service.AdminService;
import com.jsprest.service.ProjectService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AdminController {

	private static final Logger logger = LogManager.getLogger("AdminController");
	
    @Autowired
    AdminService adminService;

    @Autowired
    ProjectService prjService;
    
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping("/home")
    public String home(Authentication auth, Model model) {
    	logger.info("home "+auth);
        if (auth == null) {
            //return "redirect:/login";
        }
        System.out.println("home admin controller step 2 "+auth);
        
        model.addAttribute("nbreProjets", prjService.countProjets());
        model.addAttribute("tasksDone", 27);
        model.addAttribute("users", 245);
        model.addAttribute("uVisitors", 39);
        
        return "index";
    }


    @RequestMapping("/")
    public String indexPage(Authentication auth) {
    	System.out.println("index admin controller");
        if (auth == null) {
            return "redirect:/loginPage";
        }
        System.out.println("index admin controller s2");
        return "index";
    }

    @RequestMapping("/adp")
    public String adp() {
    	System.out.println("adp admin controller");
        return "project/adp";
    }


    @RequestMapping("/login")
    public String login(Authentication auth) {

    	System.out.println("login admin controller");
        return "login";


    }


    @RequestMapping(value = "/loginPagePost", method = RequestMethod.POST)
    public String loginPagePost(@ModelAttribute LoginModel loginModel, Authentication auth, final RedirectAttributes redirect) {
    	System.out.println("login post admin controller "+loginModel.getEmail());

        String email = loginModel.getEmail();
        String password = loginModel.getPassword();

        Admin adminDetails = adminService.findByEmail(email);


        if (adminDetails == null) {
            redirect.addFlashAttribute("msg", "email id not found");

            return "redirect:/loginPage";
        } else if (encoder.matches(password, adminDetails.getPassword())) {
            return "redirect:/home";
        } else {

            redirect.addFlashAttribute("msg", "invalid credentials");

            return "redirect:/loginPage";
        }


        //auth.getCredentials().equals(loginModel.getEmail())

    }


    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout) {
    	System.out.println("loginPage admin controller");
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }

        if (logout != null) {
            model.addObject("message", "Logged out  successfully.");
        }

        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("logout admin controller ");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/admin/loginPage?logout=true";
    }


    //	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/homePage")
    public String successLoginString(Authentication auth, ModelMap model) {
    	System.out.println("homePage admin controller ..3");

        // String userName=auth.getName();
        model.addAttribute("mzName", "MZ Name");
    	model.addAttribute("nbreProjets", 142);

        return "index";

    }


    @RequestMapping("/denied")
    public String denied(Authentication auth) {
    	System.out.println("denied admin controller");
        return "404";

    }


}
