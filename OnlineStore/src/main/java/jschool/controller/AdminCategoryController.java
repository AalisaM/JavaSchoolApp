package jschool.controller;

import jschool.dto.CategoryDTO;
import jschool.dto.CategoryRawDTO;
import jschool.service.CategoryService;
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
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * This controller is responsible for admin  manipulations with categories
 */
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setShippingTypeService(CategoryService ps){
        this.categoryService = ps;
    }


    /**
     * This method processes admin category page get request
     * @param redir attributes for redirect
     * @return category page template
     */
    @GetMapping(value= "/categories", produces = MediaType.TEXT_HTML_VALUE)
    public String listCategories(RedirectAttributes redir) {
        redir.addFlashAttribute("category", new CategoryDTO());
        return "redirect:/admin/categories/editForm";
    }

    /**
     * This method processes adding category request
     * @param p dto input
     * @param result binding result of vaidation
     * @param redir attributes for redirecting
     * @return redirection to proper page
     */
    @PostMapping(value= "/categories/add")
    public String addCategory(@Valid @RequestBody CategoryDTO p, BindingResult result, RedirectAttributes redir){
        Message m = new Message();
        redir.addFlashAttribute("add",true);
        if (result.hasErrors()) {
            m.getErrors().add("Invalid category name");
        }else{
            m  = this.categoryService.add(p);
        }
        redir.addFlashAttribute("message", m);
        if (m.getErrors().isEmpty()){
            return "redirect:/admin/categories";
        }
        redir.addFlashAttribute("category",p);
        return "redirect:/admin/categories/editForm";
    }

    /**
     * This method is kinda helper method to get editing form page
     * @param model model for rendering
     * @return categories page template
     */
    @GetMapping(value = "/categories/editForm" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCategoryForm(ModelMap model){
        model.addAttribute("categoryMap",this.categoryService.list().stream().collect(Collectors.toMap(
                CategoryRawDTO::getId,
                CategoryRawDTO::getTitle,
                (x, y) -> x,
                LinkedHashMap::new
        )));
        model.addAttribute("listCategories", this.categoryService.list());
        return new ModelAndView("admin/adminCategories", model);
    }

    /**
     * This method processes edit category request
     * @param p category dto input
     * @param result binding result of validation
     * @param redir atributes for redirecting
     * @return redirection to proper page
     */
    @PostMapping(value= "/categories/edit")
    public String editCategory(@Valid @RequestBody CategoryDTO p, BindingResult result,  RedirectAttributes redir){
        Message m = new Message();
        if (result.hasErrors()) {
            m.getErrors().add("Invalid category name");
        }else{
            m  = this.categoryService.update(p);
        }
        redir.addFlashAttribute("message", m);
        redir.addFlashAttribute("category",p);
        if (m.getErrors().isEmpty()){
            return "redirect:/admin/categories";
        }
        return "redirect:/admin/categories/editForm";
    }

    /**
     * This method processes remove category request
     * @param id id of category to remove
     * @param redir attributes for redirecting
     * @return redirection to category page
     */
    @GetMapping("categories/remove/{id}")
    public String removeCategory(@PathVariable("id") int id, RedirectAttributes redir){
        Message m = this.categoryService.remove(id);
        redir.addFlashAttribute("message", m);
        return "redirect:/admin/categories";
    }

    /**
     * This method processes category editing request
     * @param id id of category for editing
     * @param redir redirect attributes
     * @return category page with dto for editing
     */
    @GetMapping("categories/edit/{id}")
    public String editCategory(@PathVariable("id") int id, RedirectAttributes redir){
        redir.addFlashAttribute("category", this.categoryService.findById(id));
        return "redirect:/admin/categories/editForm";
    }

}
