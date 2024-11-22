package edu.du.testproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table( name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 사용자 정보

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // 상품 정보

    private int quantity;  // 구매 수량

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;  // 총 가격

    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate;  // 주문 날짜

}
