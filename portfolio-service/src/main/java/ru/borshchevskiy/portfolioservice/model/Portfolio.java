package ru.borshchevskiy.portfolioservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "cash")
    private BigDecimal cash;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
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
