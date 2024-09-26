package ru.borshchevskiy.portfolioservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
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
    @ManyToOne(fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portfolio portfolio = (Portfolio) o;

        return Objects.equals(id, portfolio.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
