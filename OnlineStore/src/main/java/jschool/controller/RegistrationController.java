package jschool.controller;

import freemarker.template.TemplateException;
import jschool.dto.UserDTO;
import jschool.service.MailSender;
import jschool.service.UserService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

/***
 * This controller is responsibe for registration
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private UserService userService;
    private MailSender mailService;


    @Autowired
    RegistrationController(UserService userService, MailSender mailService){
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * This method processes registration page requests
     * @param model model for rendering
     * @return registration page
     */
    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/registration";
    }

    /**
     * This method processes account creation request.
     * Sends email in case registration was ok.
     * @param dto input userDTO
     * @param result binding result for validation
     * @param m model for futher rendering
     * @param redir attributes for redirecting
     * @return next page (registration if fails, catalogue if ok)
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    @PostMapping(value= "/add")
    public String createAccount(@Valid @ModelAttribute("user") UserDTO dto, BindingResult result,
                                Model m, RedirectAttributes redir) throws MessagingException, IOException, TemplateException {
        if (result.hasErrors()) {
            return "user/registration";
        }
        Message message = this.userService.addUser(dto);
        if (!message.getErrors().isEmpty()){
            m.addAttribute("user", dto);
            m.addAttribute("message", message);
            return "user/registration";
        }else{
            redir.addFlashAttribute("message", message);
            mailService.sendRegisterEmail(dto.getEmail(), dto.getPassword(), dto.getFullName());
            return "redirect:/";
        }
    }
}
