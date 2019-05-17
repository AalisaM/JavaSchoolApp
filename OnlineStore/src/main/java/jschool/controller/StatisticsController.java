package jschool.controller;


import jschool.dto.ProductDTO;
import jschool.dto.UserDTO;
import jschool.helpers.PDFViewGenerator;
import jschool.service.StatisticsService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;

/***
 * This controller is responsibe for admin manipulations with statistics
 */
@RestController
@RequestMapping("admin/statistics")
public class StatisticsController {

  private final StatisticsService statisticsService;

  @Autowired
  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  /**
   * This method processes basic statistics request
   * @param model model for rendering
   * @return statistic page with data for previous month
   */
  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  @ResponseBody
  public ModelAndView basicStatistics(ModelMap model) {
    model.addAttribute("revenueWeek", this.getRevenueForLastWeek());
    model.addAttribute("revenueMonth", this.getRevenueForLastMonth());
    LocalDate to = LocalDate.now();
    to = to.plusDays(1);
    LocalDate from = LocalDate.now();
    from = from.minusMonths(1);
    model.addAttribute("userTop", this.statisticsService.getMostValuableClientForSelectedPeriod(to,from, 10));
    model.addAttribute("productTop",  this.statisticsService.getMostOrderedProductsForSelectedPeriod(to,from, 10));
    return new ModelAndView("admin/statistics",model);
  }

  /**
   * This method processes statistics request for specific dates
   * @param json {to: ,from : ,limit:} json input
   * @param model model for rendering
   * @return statistics page with proper data
   */
  @PostMapping(value = "/specificStats", produces = MediaType.TEXT_HTML_VALUE)
  @ResponseBody
  public ModelAndView specificStatistics(@RequestBody String json, ModelMap model) {
    Message m = new Message();
    model.addAttribute("revenuePeriod", this.statisticsService.getRevenueForSelectedPeriod(m,json));
    model.addAttribute("userTop", this.statisticsService.getMostValuableClientForSelectedPeriod(m,json));
    model.addAttribute("productTop",  this.statisticsService.getMostOrderedProductsForSelectedPeriod(m,json));
    return new ModelAndView("admin/adminStatistics",model);
  }

  /**
   * This method processes get revenue for specific period request
   * @param json {to: ,from : } json input
   * @return revenue
   */
  @PostMapping(value = "/revenueForSelectedPeriod")
  public int getRevenueForSelectedPeriod(@RequestBody String json){
    return statisticsService.getRevenueForSelectedPeriod(new Message(), json);
  }

  /**
   * This method processes form pdf request
   * @param from date from period
   * @param to date to period
   * @return generated pdf page
   */
  @GetMapping(path = "/formPdfReport")
  public ModelAndView getPdf(@RequestParam("from") String from, @RequestParam("to") String to){
    return new ModelAndView(new PDFViewGenerator(),
                            this.statisticsService.getBoughtProductStatsForPDF(
                                    new Message(),
                                    LocalDate.parse(from),
                                    LocalDate.parse(to)
                            ));

  }

  /**
   * This method processes statistics request for last month
   * @return statistics page with proper data
   */
  @GetMapping(value = "/revenueForLastMonth")
  public int getRevenueForLastMonth() {
    return statisticsService.getRevenueForSelectedPeriod(
            LocalDate.now().minusMonths(1),
            LocalDate.now().plusDays(1));
  }

  /**
   * This method processes statistics request for last week
   * @return statistics page with proper data
   */
  @GetMapping(value = "/revenueForLastWeek")
  public int getRevenueForLastWeek(){
    return statisticsService.getRevenueForSelectedPeriod(
            LocalDate.now().minusWeeks(1),
            LocalDate.now().plusDays(1));
  }

  /**
   * This method processes top buyers request for selected period
   * @param json {to:, from:, limit:} json input to form top
   * @return map user - money spent
   */
  @PostMapping(value = "/userTopForSelectedPeriod")
  public Map<UserDTO, Integer> getTopUsersForSelectedPeriod(@RequestBody String json){
    return statisticsService.getMostValuableClientForSelectedPeriod(new Message(), json);
  }

  /**
   * This method processes top popular products request for selected period
   * @param json {to:, from:, limit:} json input to form top
   * @return map product - amount bought
   */
  @PostMapping(value = "/productTopForSelectedPeriod")
  public Map<ProductDTO, Integer> getTopProductsForSelectedPeriod(@RequestBody String json){
    return statisticsService.getMostOrderedProductsForSelectedPeriod(new Message(), json);
  }

}
