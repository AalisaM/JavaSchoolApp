package jschool.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorizedOrdersDTO {
    private List<OrderHistoryDTO> unpaidOrders;
    private List<OrderHistoryDTO> paidOrders;
    private List<OrderHistoryDTO> processedOrders;
    private Integer userRoleId;
}
