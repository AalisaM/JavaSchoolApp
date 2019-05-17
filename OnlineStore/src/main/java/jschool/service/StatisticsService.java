package jschool.service;

import jschool.dto.ProductDTO;
import jschool.dto.ProductRawDTO;
import jschool.dto.ProductRestDTO;
import jschool.dto.UserDTO;
import jschool.validator.Message;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Statistics service interface
 */
public interface StatisticsService {

  /**
   * Get revenue for period
   * @param from begin date
   * @param to end date
   * @return revenue
   */
  Integer getRevenueForSelectedPeriod(LocalDate from, LocalDate to);

  /**
   * Get revenue for period
   * @param m Message Object info
   * @param json json period {'from' : , to' :}
   * @return revenue
   */
  @Transactional
  Integer getRevenueForSelectedPeriod(Message m, String json);

  /**
   * Get top best buyers for period
   * @param to start date
   * @param from end date
   * @param limit top
   * @return client list
   */
  @Transactional
  Map<UserDTO, Integer> getMostValuableClientForSelectedPeriod(LocalDate to, LocalDate from, Integer limit);

  /**
   * Get top best buyers for period
   * @param m Message Object info
   * @param json json period {'from' : , to' :, 'limit' : }
   * @return client list
   */
  @Transactional
  Map<UserDTO, Integer> getMostValuableClientForSelectedPeriod(Message m, String json);

  /**
   * Get top popular products for period
   * @param to start date
   * @param from end date
   * @param limit top
   * @return client list
   */
  @Transactional
  Map<ProductDTO, Integer> getMostOrderedProductsForSelectedPeriod(LocalDate to, LocalDate from, Integer limit);

  /**
   * Get top popular products for period
   * @param m Message Object info
   * @param json json period {'from' : , to' :, 'limit' : }
   * @return client list
   */
  @Transactional
  Map<ProductDTO, Integer> getMostOrderedProductsForSelectedPeriod(Message m, String json);

  /**
   * Get top popular products for period without extra data (RAW)
   * @param to start date
   * @param from end date
   * @param limit top
   * @return product top
   */
  @Transactional
  Map<ProductRestDTO, Integer> getRestProd(LocalDate to, LocalDate from, Integer limit);

  /**
   * Get products for period
   * @param m Message object info
   * @param to end date
   * @param from start date
   * @return map of objects for pdf
   */
  @Transactional
  HashMap<String, Object> getBoughtProductStatsForPDF(Message m, LocalDate from, LocalDate to);
}
