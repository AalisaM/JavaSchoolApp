package jschool.controller;

import jschool.dto.OrderFullDTO;
import jschool.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/***
 * This controller is responsibe for some order requests.
 * Eg. processing order profile page request
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private String ipAddr;

    @Autowired
    public OrderController(OrderService orderService, String ipAddr) {
        this.orderService = orderService;
        this.ipAddr = ipAddr;
    }

    /**
     * This method processes order profile pages requests
     * @param id id of order to get
     * @param model model for rendering
     * @return order profile page
     */
    @GetMapping("/{id}")
    public String showProductProfile(@PathVariable("id") int id, Model model){
        Integer userId = this.orderService.getCurUserId();
        if (!Objects.isNull(userId) && (userId < 0)) {
                model.addAttribute("nextOrderStatus", this.orderService.getStatusGraph());
        }
        OrderFullDTO orderDTO = this.orderService.findHistoryById(id);
        if (Objects.isNull(orderDTO)){
            return "404";
        }
        model.addAttribute("order", orderDTO);
        model.addAttribute("ip", this.ipAddr);
        return "order/orderProfilePage";
    }
}
