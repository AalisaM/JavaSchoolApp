package jschool.controller;
import jschool.dto.ProductDTO;
import jschool.dto.ProductRawDTO;
import jschool.dto.ProductRestDTO;
import jschool.service.*;
import jschool.validator.Message;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

/**
 * Controller for  home page and manipulations on it
 * **/
@Controller
@RequestMapping("/")
public class CatalogController {
    private final ProductService productService;
    private final StatisticsService statisticsService;
    private final CartService cartService;
    private final String ipAddr;
    private static final Logger log = Logger.getLogger(CatalogController.class);

    @Autowired
    public CatalogController(ProductService productService, StatisticsService statisticsService, CartService cartService, String ipAddr) {
        this.productService = productService;
        this.statisticsService = statisticsService;
        this.cartService = cartService;
        this.ipAddr = ipAddr;
    }


    /**
     * This method processes cataogue request
     * @param m model for rendering
     * @return catalogue page
     */
    @GetMapping
    public String listProducts(Model m){
        log.info("list products");
        m.addAttribute("curCart", this.cartService.getCurUserCart());
        m.addAttribute("listCategories", this.productService.listCategoryDTO());
        m.addAttribute("listProducts", this.productService.listDTO());
        m.addAttribute("ip", this.ipAddr);
        return "catalogue/catalogue";
    }

    /**
     * This method processes list products request.
     * Used for autocomplete.
     * @return map product id - product name
     */
    @GetMapping(value = "/auto")
    public @ResponseBody HashMap<String,String> listProductsAuto(){
        HashMap <String,String> map = new LinkedHashMap<>();
        Set<ProductDTO> list =  this.productService.listDTO();
        for (ProductDTO p :list){
            map.put(String.valueOf(p.getId()), p.getName());
        }
        log.info(map);
        return map;
    }

    /**
     * This method processes filter products request
     * @param json filter json input
     * @param model model for rendering
     * @return filtered products page
     */
    @PostMapping(value= "/filter", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView filterProduct(@RequestBody String json, ModelMap model) {
	    log.info(json);
        model.addAttribute("ip", this.ipAddr);
        model.addAttribute("listCategories", this.productService.listCategoryDTO());
        try {
            model.addAttribute("listProducts", this.productService.filterDTO(json));
        }catch (Exception e){
            log.error(e.toString());
        }
        return new ModelAndView("catalogue/filteredProductList", model);
    }

    /**
     * This method processes search request
     * @param json input json with phrase to search
     * @param model model for rendering
     * @return page with search results
     */
    @PostMapping(value= "/search", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView searchProducts(@RequestBody String json, ModelMap model) {
        model.addAttribute("listCategories", this.productService.listCategoryDTO());
        model.addAttribute("ip", this.ipAddr);
        try {
            model.addAttribute("listProducts", this.productService.search(json));
        }catch (Exception e){
            Message m = new Message();
            m.getErrors().add("Failed to perform search");
            model.addAttribute("message", m);
            log.error(e.toString());
        }
        return new ModelAndView("catalogue/searchedProductsList", model);
    }

    /**
     * This method processes get specific product page request
     * @param id id of product
     * @param model model for rendering
     * @return product page profile
     */
    @GetMapping("/product/{id}")
    public String showProductProfile(@PathVariable("id") int id, Model model){
        model.addAttribute("listCategories", this.productService.listCategoryDTO());
        model.addAttribute("product", this.productService.findByIdFTO(id));
        model.addAttribute("ip", this.ipAddr);
        return "catalogue/productProfile";
    }


    /**
     * This method processes top popular products request for last month
     * @return set of products
     */
    @GetMapping(value = "/restProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<ProductRestDTO> getRestProds(){
        return statisticsService.getRestProd(LocalDate.now().plusDays(1),
                LocalDate.now().minusMonths(1),
                10).keySet();
    }

}
