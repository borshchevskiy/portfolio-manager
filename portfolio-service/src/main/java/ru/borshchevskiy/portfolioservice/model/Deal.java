package ru.borshchevskiy.portfolioservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("deals")
@Data
public class Deal {
    @Id
    @Column("id")
    private Long id;
    @Column("security_name")
    private String securityName;
    @Column("ticker")
    private String ticker;
    @Column("position_type")
    private DealType dealType;
    /**
     * Price of single share
     */
    @Column("acquisition_price")
    private BigDecimal acquisitionPrice;
    @Column("quantity")
    private Long quantity;
    /**
     * Value of all shares in deal = acquisitionPrice * quantity
     */
    @Column("acquisition_value")
    private BigDecimal acquisitionValue;
    @Column("market_commission")
    private BigDecimal marketCommission;
    @Column("broker_commission")
    private BigDecimal brokerCommission;
    @Column("other_commission")
    private BigDecimal otherCommission;
    @Column("total_commission")
    private BigDecimal totalCommission;
    @Column("deal_date")
    private LocalDateTime date;
    @Column("position_id")
    private Position position;
}
