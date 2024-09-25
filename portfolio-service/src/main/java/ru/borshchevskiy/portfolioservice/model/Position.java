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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "positions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "security_name")
    private String securityName;
    @Column(name = "ticker")
    private String ticker;
    @Column(name = "position_type")
    @Enumerated(EnumType.STRING)
    private PositionType positionType;
    /**
     * Average acquisition price of share
     */
    @Column(name = "average_acquisition_price")
    private BigDecimal averageAcquisitionPrice;
    @Column(name = "quantity")
    private Long quantity;
    /**
     * Sum of acquisition values of deals
     */
    @Column(name = "acquisition_value")
    private BigDecimal acquisitionValue;
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @OneToMany(mappedBy = "position")
    private List<Deal> deals;
    @Transient
    private BigDecimal currentPrice;
    @Transient
    private BigDecimal liquidationValue;
    @Transient
    private BigDecimal profitLoss;
    @Transient
    private Double profitLossPercentage;
}
