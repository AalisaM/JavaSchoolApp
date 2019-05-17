package jschool.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderFullDTO {

    private OrderHistoryDTO orderHistoryDTO;
    private List<OrderAuditDTO> orderAuditDTO;

}
