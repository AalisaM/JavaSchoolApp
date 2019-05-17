package jschool.dto;

import lombok.Data;


@Data
public class OrderAuditDTO {
    private int id;
    private String manager;
    private boolean isPaid;

    private String prevOrderStatus;
    private String curOrderStatus;
    private java.sql.Timestamp date;
}
