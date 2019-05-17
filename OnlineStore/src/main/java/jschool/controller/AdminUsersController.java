package jschool.controller;

import jschool.dto.UserDTO;
import jschool.service.UserService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * This controller is reponsible for users page in admin.
 * Allow some manipulations with users for admin
 * Eg. make someone manager role.
 * */
@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService ps){
        this.userService = ps;
    }

    /**
     * This method processes get user list page request
     * @param model model for rendering
     * @return users list page
     */
    @GetMapping
    public ModelAndView listUsers(ModelMap model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("listUsers", this.userService.listUsersDTO());
        return new ModelAndView("admin/adminUsers", model);
    }

    /**
     * This method processes remove user request
      * @param id id of user to remove
     * @return redirect to user page
     */
    @PostMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id){
        this.userService.removeUser(id);
        return "redirect:/users";
    }

    /**
     * This method processes user admin status change request
     * @param json json input with user data and admin status
     * @param m model for rendering
     * @param redir attributes for redirect
     * @return redirect to users page
     */
    @PostMapping(value = "/makeUserAdmin", produces = MediaType.TEXT_HTML_VALUE)
    public String updateUserPassword(@RequestBody String json, ModelMap m, RedirectAttributes redir){
        Message message = this.userService.changeAdminStatus(new Message(), json);
        redir.addFlashAttribute("message", message);
        return "redirect:/admin/users";

    }
}
