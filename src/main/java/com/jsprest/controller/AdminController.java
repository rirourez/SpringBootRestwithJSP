package com.jsprest.controller;

import com.jsprest.entity.Admin;
import com.jsprest.entity.LoginModel;
import com.jsprest.repository.AdminRepository;
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

    @Autowired
    AdminRepository arepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping("/homePage")
    public String home(Authentication auth, Model model) {
    	System.out.println("home admin controller");
        if (auth == null) {
            return "reditect:/login";
        }
        System.out.println("home admin controller step 2");
        model.addAttribute("nbreProjets", 142);
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
    	System.out.println("login post admin controller");

        String email = loginModel.getEmail();
        String password = loginModel.getPassword();

        Admin adminDetails = arepo.findByEmail(email);


        if (adminDetails == null) {
            redirect.addFlashAttribute("msg", "email id not found");

            return "redirect:/loginPage";
        } else if (encoder.matches(loginModel.getPassword(), adminDetails.getPassword())) {
            return "redirect:/homePage";
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
