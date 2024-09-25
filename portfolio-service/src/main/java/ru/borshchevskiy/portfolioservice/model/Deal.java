package ru.borshchevskiy.portfolioservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deals")
@Getter
@Setter
@ToString
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
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
