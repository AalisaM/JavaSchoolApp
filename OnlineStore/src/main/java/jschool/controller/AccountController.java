package jschool.controller;

import jschool.dto.UserDTO;
import jschool.service.UserService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * This controller is responsible for account manipulations
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private UserService userService;
    private String ipAddr;

    @Autowired(required=true)
    public void setUserService(UserService ps) {
        this.userService = ps;
    }

    @Autowired
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    /**
     * Method for prcocessing get account page request
     *
     * @param model  model for page view
     * @return page for account
     */
    @GetMapping
    public String account(Model model){
        model.addAttribute("user", this.userService.getCurUserDTO());
        model.addAttribute("ip", this.ipAddr);
        return "user/account";
    }

    /**
     * Method for prcocessing get account page request
     *
     * @param p  userDTO data
     * @param result binding result for validating userDTO
     * @param redir attributes for redirecting
     * @return page for account
     */
    @PostMapping(value= "/edit")
    public String editAccount(@Valid @ModelAttribute("user") UserDTO p, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return "user/account";
        }
        Message message  = this.userService.updateUser(p);
        redir.addFlashAttribute("message", message);
        return "redirect:/account/";
    }

    /**
     * Method for processing add address request
     *
     * @param json input data in json format
     * @param redir attributes for redirecting to helper method
     * @return redirect
     */
    @PostMapping(value = "/addAddress")
    public String addAddress(@RequestBody String json,  RedirectAttributes redir){
        Message message = this.userService.addAddress(json);
        redir.addFlashAttribute("message",message);
        return "redirect:/account/accountData";
    }

    /**
     * Method for processing setting active request for user
     *
     * @param json input data in json format {user_id: , addr_id :}
     * @param redir  attributes for redirecting to helper method
     * @return redirect
     */
    @PostMapping(value = "/setActiveAddress")
    public String setActiveAddress(@RequestBody String json, RedirectAttributes redir){
        Message message = this.userService.setActiveAddress(json);
        redir.addFlashAttribute("message",message);
        return "redirect:/account/accountData";
    }

    /**
     * Method for processing removing address for user request
     *
     * @param json input data in json format {user_id: , addr_id : , addr_str: }
     * @param redir  attributes for redirecting to helper method
     * @return redirect
     */
    @PostMapping(value = "/removeAddress")
    /*{user_id: , addr_id : , addr_str: }*/
    public String removeAddress(@RequestBody String json, RedirectAttributes redir){
        Message message = this.userService.removeAddress(json);
        redir.addFlashAttribute("message",message);
        return "redirect:/account/accountData";
    }

    /**
     * This method is kinda helper method to get editing form page
     * @param model model for rendering
     * @return shipping page template
     */
    @GetMapping(value = "/accountData" , produces = MediaType.TEXT_HTML_VALUE)
    public String getAccountEditedData(Model model){
        try {
            model.addAttribute("user", this.userService.getCurUserDTO());
            Message message = (Message) model.asMap().get("message");
            if (message.getErrors().isEmpty()) {
                model.addAttribute("addressList", true);
            }
            return "user/userAccount";
        }catch (NullPointerException e){
            return "404";
        }
    }

    /**
     * Method for processing editing address for user request
     *
     * @param json  input data in json format {address_id : "a", address_str : "as"}
     * @param m model for futher rendering
     * @return page for account
     */
    @PostMapping(value = "/editAddress")
    public ModelAndView editAddress(@RequestBody String json, ModelMap m){
        Message message = this.userService.editAddress(json);
        m.addAttribute("user", this.userService.getCurUserDTO());
        m.addAttribute("message", message);
        if (message.getErrors().isEmpty()) {
            m.addAttribute("addressList", true);
        }
        return  new ModelAndView("user/userAccount",m);

    }

    /**
     * Method for updating password request
     *
     * @param json input data in json format
     * @param m model for futher rendering
     * @param redir attributes for redirecting
     * @return account page
     */
    @PostMapping(value = "/updatePassword", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView updateUserPassword(@RequestBody String json, ModelMap m, RedirectAttributes redir) {
        Message message = this.userService.changeUserPassword(json);
        m.addAttribute("user", this.userService.getCurUserDTO());
        if (!message.getErrors().isEmpty()) {
            m.addAttribute("message", message);
            m.addAttribute("modulePSWD", true);
            return new ModelAndView("user/account", m);
        }
        m.addAttribute("message", message);
        return new ModelAndView("user/account", m);
    }

    /**
     * Method for getting password page request processing
     * @param m model for futher rendering
     * @return password page template
     */
    @GetMapping(value = "/changePassword", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView changeUserPassword(ModelMap m) {
        return new ModelAndView("user/changePassword", m);
    }

}
