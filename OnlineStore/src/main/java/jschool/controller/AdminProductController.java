package jschool.controller;

import jschool.dto.ProductDTO;
import jschool.service.ProductService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService ps) {
        this.productService = ps;
    }

    /**
     * This method processes product tab in admin page get request
     * @param model model for rendering
     * @return admin product page
     */
    @GetMapping(value= "/products", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView createProductPage(ModelMap model){
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("productList", this.productService.listDTO());
        model.addAttribute("listCategories", this.productService.listCategoryDTO());
        return new ModelAndView("admin/adminProducts", model);
    }

    /**
     * This method processes editing product request
     * @param p productDTO
     * @param result binding result of validation
     * @param file product image
     * @param redir attributes for redirecting
     * @return redirect to proper page
     */
    @PostMapping(value= "/products/editWithImage", consumes = {"multipart/form-data"})
    public String editProductWithImage(@Valid @RequestPart("product") ProductDTO p , BindingResult result,
                                       @RequestPart(value ="file", required=false) MultipartFile file, RedirectAttributes redir){
        Message m = new Message();
        if (result.hasErrors()) {
            m.getErrors().add("Invalid product data");
        }else{
            m  = this.productService.editWithImage(p, file);
        }
        redir.addFlashAttribute("message", m);
        redir.addFlashAttribute("product",p);
        return "redirect:/admin/products/editForm";

    }

    /**
     * This method is kinda helper. Processes product form get request
     * @param model model for rendering
     * @return product form page
     */
    @GetMapping(value = "/products/editForm" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getProductForm(ModelMap model){
        model.addAttribute("listCategories", this.productService.listCategoryDTO());
        return new ModelAndView("admin/productEditForm", model);
    }

    /**
     * This method processes add product request
     * @param p product dto to add
     * @param result binding result of validation
     * @param file image file for product
     * @param redir attributes for redirecting
     * @return redirect to proper page
     */
    @PostMapping(value= "/products/addProduct", consumes = {"multipart/form-data"})
    public String addProduct(@Valid @RequestPart("product") ProductDTO p, BindingResult result,
                             @RequestPart(value ="file", required=false) MultipartFile file,  RedirectAttributes redir){
        Message m = new Message();
        if (result.hasErrors()) {
            m.getErrors().add("Invalid product data");

        }else{
            m = this.productService.addWithImage(p, file);
        }
        redir.addFlashAttribute("message", m);
        redir.addFlashAttribute("product",p);
        return "redirect:/admin/products/editForm";
    }

    /**
     * This method processes product removal request
     * @param id id of product to remove
     * @return redirect to admin product page
     */
    @GetMapping("products/remove/{id}")
    public String removeProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message",this.productService.remove(id));
        return "redirect:/admin/products";
    }

    /**
     * This method processes product removal request
     * @param id id of product to remove
     * @return redirect to admin product page
     */
    @GetMapping("products/revive/{id}")
    public String reviveProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message",this.productService.revive(id));
        return "redirect:/admin/products";
    }

}
