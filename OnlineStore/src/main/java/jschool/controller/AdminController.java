package jschool.controller;

import jschool.dto.OrderDTO;
import jschool.dto.ProductDTO;
import jschool.service.OrderService;
import jschool.service.ProductService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Controller is responsible for admin page some specific actions
 * */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private OrderService orderService;
    private ProductService productService;
    private String ipAddr;

    @Autowired
    AdminController(OrderService orderService, ProductService productService, String ipAddr){
        this.orderService = orderService;
        this.productService = productService;
        this.ipAddr = ipAddr;
    }

    /**
     * This method processes admin page get request
     * @param model model for rendering
     * @return admin page
     */
    @GetMapping
    public ModelAndView account(ModelMap model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("productList", this.productService.listDTO());
        model.addAttribute("listCategories", this.productService.listCategoryDTO());
        model.addAttribute("ip", this.ipAddr);
        return new ModelAndView("admin/admin", model);
    }

    /**
     * This method changes status of Order from admin panel and redirects to admin page
     * @param p orderdto to edit
     * @param model model for rendering
     * @param redir attributes for redirecting
     * @return redirect to admin page
     */
    @PostMapping(value= "/editOrder")
    public String editOrder(@Valid @RequestBody OrderDTO p, Model model, RedirectAttributes redir){
        Message message = this.orderService.updateOrder(p);
        redir.addFlashAttribute("message", message);
        return "redirect:/admin/";
    }


}
