package com.ecommerce.store.service.OrderService;

import com.ecommerce.store.exceptions.APIException;
import com.ecommerce.store.model.*;
import com.ecommerce.store.payload.AddressDTO;
import com.ecommerce.store.payload.OrderDTO;
import com.ecommerce.store.payload.OrderItemDTO;
import com.ecommerce.store.payload.OrderRequestDTO;
import com.ecommerce.store.repositories.*;
import com.ecommerce.store.service.CartService.CartService;
import com.ecommerce.store.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public OrderDTO placeOrder(String paymentMethod, OrderRequestDTO orderRequestDTO) {

        //        1. get user cart
        User user = authUtil.loggedInUser();
        Optional<Cart> cart = cartRepository.findCartByUser(user.getUserId());

        if (cart.isEmpty()) {
            throw new APIException("No cart found.");
        }
        Cart user_cart = cart.get();

        Address address = addressRepository.findById(orderRequestDTO.getAddressId()).orElseThrow(
                () -> new APIException("Address not found.")
        );

        Order order = new Order();

        order.setAddress(address);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setEmail(user.getEmail());
        order.setTotal_Amount(user_cart.getTotalPrice());

        //        2. create order and payment.

        Payment payment = new Payment(orderRequestDTO.getPgName(), orderRequestDTO.getPgResponseMessage(), orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgPaymentId(), order, paymentMethod);

        paymentRepository.save(payment);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        List<CartItem> cartItems = user_cart.getCartItems();
        if (cartItems.isEmpty()){
            throw new APIException("No items in cart.");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item: cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrderedProductPrice(item.getProductPrice());
            orderItem.setDiscount(item.getDiscount());
            orderItem.setProduct(item.getProduct());

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cartItems.forEach(item ->{
            int quantity = item.getQuantity();

            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            cartService.deleteProductFromCart(product.getProductId(), user_cart.getCartId());

        });

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        orderDTO.getOrderItems().forEach(item -> modelMapper.map(item, OrderItemDTO.class));

        orderDTO.setAddress(modelMapper.map(address, AddressDTO.class));
        return orderDTO;
    }
}
