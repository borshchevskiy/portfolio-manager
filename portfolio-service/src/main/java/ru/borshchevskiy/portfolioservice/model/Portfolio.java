package ru.borshchevskiy.portfolioservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table("portfolios")
@Data
public class Portfolio {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("cash")
    private BigDecimal cash;
    @Column("user_id")
    private User user;
    @MappedCollection(idColumn = "portfolio_id")
    private List<Position> positions;
    /**
     * Value of all positions + cash
     */
    @Transient
    private BigDecimal totalValue;
    /**
     * Value of all positions (excl. cash)
     */
    @Transient
    private BigDecimal positionsValue;
    /**
     * Profit or loss value for positions
     */
    @Transient
    private BigDecimal profitLoss;
    /**
     * Profit loss value in percentage of total portfolio's value
     */
    @Transient
    private Double profitLossPercentage;

    public Portfolio(String name, User user) {
        this.name = name;
        this.user = user;
        this.cash = BigDecimal.ZERO;
        this.positions = new ArrayList<>();
        this.totalValue = BigDecimal.ZERO;
        this.positionsValue = BigDecimal.ZERO;
        this.profitLoss = BigDecimal.ZERO;
        this.profitLossPercentage = 0.0;
    }
}
