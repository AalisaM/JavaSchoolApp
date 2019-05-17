package jschool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import jschool.dao.OrderHistoryDAO;
import jschool.dao.OrderProductDAO;
import jschool.dao.UserDAO;
import jschool.dto.ProductDTO;
import jschool.dto.ProductRawDTO;
import jschool.dto.ProductRestDTO;
import jschool.dto.UserDTO;
import jschool.model.OrderHistory;
import jschool.model.OrderProduct;
import jschool.model.Product;
import jschool.model.User;
import jschool.service.DTOConverterService;
import jschool.service.StatisticsService;
import jschool.validator.Message;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class);

    private final OrderHistoryDAO orderHistoryDAO;
    private final UserDAO userDAO;
    private final OrderProductDAO orderProductDAO;
    private final DTOConverterService dtoConverterService;

    @Autowired
    public StatisticsServiceImpl(OrderHistoryDAO orderHistoryDAO, UserDAO userDAO,
                                 OrderProductDAO orderProductDAO, DTOConverterService dtoConverterService){
        this.orderHistoryDAO = orderHistoryDAO;
        this.userDAO = userDAO;
        this.orderProductDAO = orderProductDAO;
        this.dtoConverterService = dtoConverterService;
    }

    /**
     * Get revenue for period
     * @param from begin date
     * @param to end date
     * @return revenue
     */
    @Override
    @Transactional
    public Integer getRevenueForSelectedPeriod(LocalDate from, LocalDate to) {
        logger.info(orderHistoryDAO.getOrdersByDatePeriod(from, to));
        Integer res =  orderHistoryDAO.getOrdersByDatePeriod(from, to)
                .stream()
                .filter(a -> a.getPaymentStatus().getStatus().equals("paid"))
                .mapToInt(OrderHistory::getTotalPrice).sum();
        logger.info("res: "+ res);
        return  res;
    }


    /**
     * Get revenue for period
     * @param m Message Object info
     * @param json json period {'from' : , to' :}
     * @return revenue
     */
    @Override
    @Transactional
    public Integer getRevenueForSelectedPeriod(Message m, String json) {
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
          m.getErrors().add("wrong json for adding");
          return 0;
        }
        try {
            LocalDate from = LocalDate.parse(jsonNode.get("from").asText());
            LocalDate to = LocalDate.parse(jsonNode.get("to").asText());

            return getRevenueForSelectedPeriod(from, to);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }


    /**
     * Get top best buyers for period
     * @param to start date
     * @param from end date
     * @param limit top
     * @return client list
     */
    @Override
    @Transactional
    public Map<UserDTO, Integer> getMostValuableClientForSelectedPeriod(LocalDate to, LocalDate from, Integer limit){
        try {
            Timestamp dbFrom = Timestamp.valueOf(from.atStartOfDay());
            Timestamp dbTo = Timestamp.valueOf(to.atStartOfDay());

            Map<String, Integer> emailsToPrice = orderHistoryDAO.list().stream()
                    .filter(a -> a.getPaymentStatus().getStatus().equals("paid"))
                    .filter(a -> (a.getDate().after(dbFrom) && a.getDate().before(dbTo)))
                    .collect(Collectors.groupingBy(OrderHistory::getEmail,
                            Collectors.summingInt(OrderHistory::getTotalPrice)));

            Map<User, Integer> top =
                    emailsToPrice.entrySet().stream()
                            .sorted((f1, f2) -> Long.compare(f2.getValue(), f1.getValue()))
                            .limit(limit)
                            .collect(Collectors.toMap(
                                    a -> userDAO.findByEmail(a.getKey()),
                                    Entry::getValue,
                                    (x, y) -> x,
                                    LinkedHashMap::new
                            ));


            top.forEach((key, value) -> Hibernate.initialize(key.getAddresses()));
            top.forEach((key, value) -> Hibernate.initialize(key.getRoles()));
            return top.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            a -> dtoConverterService.getUserDTO(a.getKey()),
                            Map.Entry::getValue,
                            (x, y) -> x,
                            LinkedHashMap::new));
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }

    }

    /**
     * Get top best buyers for period
     * @param m Message Object info
     * @param json json period {'from' : , to' :, 'limit' : }
     * @return client list
     */
    @Override
    @Transactional
    public Map<UserDTO, Integer> getMostValuableClientForSelectedPeriod(Message m, String json){
        try {
            JsonNode jsonNode = UtilService.parseJsonInput(json);
            if (Objects.isNull(jsonNode)) {
              m.getErrors().add("wrong json for client period");
              return null;
            }
            ;
            String fromJ = jsonNode.get("from").asText();
            String toJ = jsonNode.get("to").asText();
            Integer limit = jsonNode.get("limit").asInt();

            LocalDate from, to;
            from = LocalDate.parse(fromJ);
            to = LocalDate.parse(toJ);

            return getMostValuableClientForSelectedPeriod(to, from, limit);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }


    /**
     * Get top popular products for period
     * @param to start date
     * @param from end date
     * @param limit top
     * @return client list
     */
    @Override
    @Transactional
    public Map<ProductDTO, Integer> getMostOrderedProductsForSelectedPeriod(LocalDate to, LocalDate from, Integer limit){
       try {
          Timestamp dbFrom = Timestamp.valueOf(from.atStartOfDay());
          Timestamp dbTo = Timestamp.valueOf(to.atStartOfDay());

          logger.info("get most ordered products");

          Map<Product, Integer> productToAmount = getBoughtProductToAmountMap(dbFrom,dbTo);

          logger.info("get result::");

          Map<Product, Integer> result = getSortedProductToAmountLimitedMap(productToAmount,limit);

          logger.info(result.size());

          result.forEach((key, value) -> Hibernate.initialize(key.getCartItem()));
          result.forEach((key, value) -> Hibernate.initialize(key.getOrderProducts()));

          return result.entrySet()
                  .stream()
                  .collect(Collectors.toMap(
                          a -> dtoConverterService.getProductDTO(a.getKey()),
                          Map.Entry::getValue,
                          (x, y) -> x,
                          LinkedHashMap::new));
      }catch (Exception e){
          logger.error(e.toString());
          return null;
      }
    }


    /**
     * Get top popular products for period
     * @param m Message Object info
     * @param json json period {'from' : , to' :, 'limit' : }
     * @return client list
     */
    @Override
    @Transactional
    public Map<ProductDTO, Integer> getMostOrderedProductsForSelectedPeriod(Message m, String json) {
        try {
            JsonNode jsonNode = UtilService.parseJsonInput(json);
            if (Objects.isNull(jsonNode)) {
              m.getErrors().add("wrong json for product period");
              return null;
            }
            ;

            String fromJ = jsonNode.get("from").asText();
            String toJ = jsonNode.get("to").asText();
            Integer limit = jsonNode.get("limit").asInt();

            LocalDate from = LocalDate.parse(fromJ);
            LocalDate to = LocalDate.parse(toJ);

            return getMostOrderedProductsForSelectedPeriod(to, from, limit);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }


    /**
     * Get top popular products for period without extra data (RAW)
     * @param to start date
     * @param from end date
     * @param limit top
     * @return product top
     */
    @Override
    @Transactional
    public Map<ProductRestDTO, Integer> getRestProd(LocalDate to, LocalDate from, Integer limit){
        try{
            Timestamp dbFrom = Timestamp.valueOf(from.atStartOfDay());
            Timestamp dbTo = Timestamp.valueOf(to.atStartOfDay());

            Map<Product, Integer> productToAmount = getBoughtProductToAmountMap(dbFrom,dbTo);

            logger.info("productToAmount got::" + productToAmount.size());

            Map<Product, Integer> result = getSortedProductToAmountLimitedMap(productToAmount,limit);

            logger.info("prodcu rest got::" + result.size());


            result.forEach((key, value) -> Hibernate.initialize(key.getCartItem()));
            result.forEach((key, value) -> Hibernate.initialize(key.getOrderProducts()));

            LinkedHashMap ad = result.entrySet()
                      .stream()
                      .collect(Collectors.toMap(
                              a -> dtoConverterService.getProductRestDTO(a.getKey()),
                              Map.Entry::getValue,
                              (x, y) -> x,
                              LinkedHashMap::new));
              logger.info(ad);
        return ad;
        }catch (Exception e){
            logger.error(e.toString());
            return null;
      }
    }

    //gets product list from dao by time period
    private  Map<Product, Integer> getBoughtProductToAmountMap(Timestamp from, Timestamp to){
        return orderProductDAO.list().stream()
                .filter(a -> a.getOrder().getDate().before(to))
                .filter(a -> a.getOrder().getDate().after(from))
                .filter(a -> a.getOrder().isActive())
                .collect(Collectors.groupingBy(OrderProduct::getProduct,
                        Collectors.summingInt(OrderProduct::getAmount)));
    }

    //sorts product - amount map by amount
    private  Map<Product, Integer> getSortedProductToAmountLimitedMap(Map<Product, Integer> productToAmount,int limit){
        return productToAmount.entrySet().stream()
                .sorted((f1, f2) -> Long.compare(f2.getValue(), f1.getValue()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Entry::getKey, Entry::getValue,
                        (x, y) -> x,
                        LinkedHashMap::new
                ));
    }


    /**
     * Get products for period
     * @param m Message object info
     * @param to end date
     * @param from start date
     * @return map of objects for pdf
     */
    @Override
    @Transactional
    public HashMap<String, Object> getBoughtProductStatsForPDF(Message m, LocalDate from, LocalDate to){
        try {
            Timestamp dbFrom = Timestamp.valueOf(from.atStartOfDay());
            Timestamp dbTo = Timestamp.valueOf(to.atStartOfDay());

            Map<Product, Integer> result =
                    orderProductDAO.list().stream()
                            .filter(a -> a.getOrder().getDate().before(dbTo))
                            .filter(a -> a.getOrder().getDate().after(dbFrom))
                            .filter(a -> a.getOrder().isPaid())
                            .collect(Collectors.groupingBy(OrderProduct::getProduct,
                                    Collectors.summingInt(OrderProduct::getAmount)))
                            .entrySet().stream()
                            .sorted(Comparator.comparingLong(f -> f.getKey().getId()))
                            .collect(Collectors.toMap(
                                Entry::getKey,
                                Entry::getValue,
                                (x, y) -> x,
                                LinkedHashMap::new
                            ));

            result.forEach((key, value) -> Hibernate.initialize(key.getCartItem()));
            result.forEach((key, value) -> Hibernate.initialize(key.getOrderProducts()));

            HashMap<String, Object> returnMap = new HashMap();
            returnMap.put("from", from);
            returnMap.put("to", to);
            returnMap.put("revenueData",
                    result.entrySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    a -> dtoConverterService.getProductDTO(a.getKey()),
                                    Map.Entry::getValue,
                                    (x, y) -> x,
                                    LinkedHashMap::new)));
            return returnMap;
        } catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }
}
