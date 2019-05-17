package jschool.controller;

import jschool.dto.ShippingDTO;
import jschool.service.ShippingTypeService;
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
/***
 * This controller is responsibe for admin manipulations with shipping types
 */
@Controller
@RequestMapping("/admin")
public class AdminShippingController {

    private ShippingTypeService shippingTypeService;

    @Autowired
    public void setShippingTypeService(ShippingTypeService ps){
        this.shippingTypeService = ps;
    }

    /**
     * This method processes get shipping page requests
     * @param model model for rendering
     * @return shipping page
     */
    @GetMapping(value= "/shipping", produces = MediaType.TEXT_HTML_VALUE)
    public String listShippingTypes(Model model) {
        model.addAttribute("shipping", new ShippingDTO());
        model.addAttribute("listShippingTypes", this.shippingTypeService.listDTO());
        return "admin/adminShipping";
    }

    /**
     * This method processes add shipping request
     * @param p shipping to add
     * @param result binding result of validation
     * @param redir attributes for redirection
     * @return redirect to proper page
     */
    @PostMapping(value= "/shipping/add")
    public String addShippingType(@Valid @RequestBody ShippingDTO p, BindingResult result,  RedirectAttributes redir){
        Message m = new Message();
        redir.addFlashAttribute("add",true);
        if (result.hasErrors()) {
            m.getErrors().add("Invalid shipping type name");
        }else{
            m  = this.shippingTypeService.add(p);
        }
        redir.addFlashAttribute("message", m);
        redir.addFlashAttribute("shipping",p);
        if (m.getErrors().isEmpty()){
            return "redirect:/admin/shipping";
        }
        return "redirect:/admin/shipping/editForm";
    }

    /**
     * This method is kinda helper method to get editing form page
     * @param model model for rendering
     * @return shipping page template
     */
    @GetMapping(value = "/shipping/editForm" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getProductForm(ModelMap model){
        model.addAttribute("listShippingTypes", this.shippingTypeService.listDTO());
        return new ModelAndView("admin/adminShipping", model);
    }

    /**
     * This method processes edit shipping request
     * @param p input dto to edit
     * @param result binding result of validation
     * @param redir attributes for redirection
     * @return redirect to proper page
     */
    @PostMapping(value= "/shipping/edit")
    public String editShippingType(@Valid @RequestBody ShippingDTO p,BindingResult result,  RedirectAttributes redir){
        Message m = new Message();
        if (result.hasErrors()) {
            m.getErrors().add("Invalid shipping type name");
        }else{
            m  = this.shippingTypeService.update(p);
        }
        redir.addFlashAttribute("message", m);
        if (m.getErrors().isEmpty()){
            return "redirect:/admin/shipping";
        }
        redir.addFlashAttribute("shipping",p);
        return "redirect:/admin/shipping/editForm";
    }

    /**
     * This method processes shipping removal request
     * @param id id of shipping to remove
     * @param redir attributes for redirection
     * @return redirect to proper page
     */
    @GetMapping("shipping/remove/{id}")
    public String removeShippingType(@PathVariable("id") int id, RedirectAttributes redir){
        Message m = this.shippingTypeService.remove(id);
        redir.addFlashAttribute("message", m);
        return "redirect:/admin/shipping";
    }

    /**
     * This method processes get editing page for proper shipping request
     * @param id id of shipping to edit
     * @param model model for rendering
     * @return shipping page
     */
    @GetMapping(value= "/shipping/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String editShippingType(@PathVariable("id") int id, Model model){
        model.addAttribute("shipping", this.shippingTypeService.findByIdDTo(id));
        model.addAttribute("listShippingTypes", this.shippingTypeService.listDTO());
        return "admin/adminShipping";
    }
}
