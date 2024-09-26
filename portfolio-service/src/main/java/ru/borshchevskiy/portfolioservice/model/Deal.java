package ru.borshchevskiy.portfolioservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "deals")
@Getter
@Setter
@RequiredArgsConstructor
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "security_name")
    private String securityName;
    @Column(name = "ticker")
    private String ticker;
    @Column(name = "deal_type")
    @Enumerated(EnumType.STRING)
    private DealType dealType;
    /**
     * Price of single share
     */
    @Column(name = "acquisition_price")
    private BigDecimal acquisitionPrice;
    @Column(name = "quantity")
    private Long quantity;
    /**
     * Value of all shares in deal = acquisitionPrice * quantity
     */
    @Column(name = "acquisition_value")
    private BigDecimal acquisitionValue;
    @Column(name = "market_commission")
    private BigDecimal marketCommission;
    @Column(name = "broker_commission")
    private BigDecimal brokerCommission;
    @Column(name = "other_commission")
    private BigDecimal otherCommission;
    @Column(name = "total_commission")
    private BigDecimal totalCommission;
    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        return Objects.equals(id, deal.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", securityName='" + securityName + '\'' +
                ", ticker='" + ticker + '\'' +
                ", dealType=" + dealType +
                ", acquisitionPrice=" + acquisitionPrice +
                ", quantity=" + quantity +
                ", acquisitionValue=" + acquisitionValue +
                '}';
    }
}
