package ru.borshchevskiy.portfolioservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("positions")
@Data
public class Position {
    @Id
    @Column("id")
    private Long id;
    @Column("security_name")
    private String securityName;
    @Column("ticker")
    private String ticker;
    @Column("position_type")
    private PositionType positionType;
    /**
     * Average acquisition price of share
     */
    @Column("average_acquisition_price")
    private BigDecimal averageAcquisitionPrice;
    @Column("quantity")
    private Long quantity;
    /**
     * Sum of acquisition values of deals
     */
    @Column("acquisition_value")
    private BigDecimal acquisitionValue;
    @Column("portfolio_id")
    private Portfolio portfolio;
    @Transient
    private BigDecimal currentPrice;
    @Transient
    private BigDecimal liquidationValue;
    @Transient
    private BigDecimal profitLoss;
    @Transient
    private Double profitLossPercentage;
}
