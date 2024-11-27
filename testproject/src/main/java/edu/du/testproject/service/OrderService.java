package edu.du.testproject.service;

import edu.du.testproject.entity.Order;
import edu.du.testproject.entity.User;
import edu.du.testproject.entity.Product;
import edu.du.testproject.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(User user, Product product, String size, String paymentMethod) {
        System.out.println("유저 받아오기");
        System.out.println("----"+user.getId());
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setSize(size);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDate(LocalDateTime.now());


        // 결제 상태와 트랜잭션 정보는 예시로 설정
        order.setPaymentStatus("Pending");
        order.setTransactionId("TXN12345");

        // DB에 주문 저장
        return orderRepository.save(order);
    }
    public Order saveOrder(Order order) {
        return orderRepository.save(order);  // OrderRepository를 통해 주문 저장
    }
}
