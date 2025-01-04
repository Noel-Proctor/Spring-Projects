package com.ecommerce.store.service.CartService;

import com.ecommerce.store.exceptions.APIException;
import com.ecommerce.store.exceptions.ResourceNotFoundException;
import com.ecommerce.store.model.Cart;
import com.ecommerce.store.model.CartItem;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.payload.CartDTO;
import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.repositories.CartItemRepository;
import com.ecommerce.store.repositories.CartRepository;
import com.ecommerce.store.repositories.ProductRepository;
import com.ecommerce.store.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartItemRepository cartItemRepository;


    public CartDTO addProductToCart(Long productId, Integer quantity) {

        //Create cart or fetch existing cart if it exists.
        Cart cart = createCart();

        //Retrieve product details from db.
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ResourceNotFoundException("Product", "id", productId)
        );

        //Validate
        if (product.getQuantity() < quantity) {
            if (product.getQuantity() <= 0) {
                throw new APIException("Product out of stock");
            }
            throw new APIException(String.format("There are only %d products in stock", product.getQuantity()));
        }

        //        Create cart item
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cart.getCartId());

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
        }

        cartItem.setQuantity(quantity);
        cartItem.setDiscount(product.getDiscount());

        if (product.getDiscount() >0){
            cartItem.setProductPrice(product.getSpecialPrice());
        }else{
            cartItem.setProductPrice(product.getPrice());
        }

        cartItem = cartItemRepository.save(cartItem);
        // added this line in as spring was not automatically updating cart.cartitems. Not sure why as mappings
        // appear correct.
        cart.getCartItems().add(cartItem);

        List<CartItem> cartItems = cart.getCartItems();

        double newTotalPrice =0;
        for (CartItem item : cartItems) {
            newTotalPrice += item.getProductPrice() * item.getQuantity();
        }

        cart.setTotalPrice(newTotalPrice);


        cartRepository.save(cart);

        //Save Cart Item.
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        //Get list of cart items
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item ->{
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());

            return map;
        });

        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    private Cart createCart() {

        Optional<Cart> userCart = cartRepository.findCartByUser(authUtil.loggedInUserId());

        if (userCart.isPresent()) {
            return userCart.get();
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtil.loggedInUser());

        Cart newCart = cartRepository.save(cart);

        return newCart;
    }

    @Override
    public List<CartDTO> getAllCarts() {

        List<Cart> carts = cartRepository.findAll();

        if (carts.size() == 0) {
            throw new APIException("No carts found");
        }

        List<CartDTO> cartDTOList = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> productDTOS = cart.getCartItems().stream()
                    .map(item -> modelMapper.map(item.getProduct(), ProductDTO.class))
                    .toList();

            cartDTO.setProducts(productDTOS);
            return cartDTO;
        }).collect(Collectors.toList());

        return cartDTOList;
    }

    @Override
    public CartDTO getUserCart() {

        String userEmail = authUtil.loggedInEmail();
        Optional<Cart> cart = cartRepository.findCartByEmail(userEmail);

        if (cart.isEmpty()) {
            throw  new APIException("No cart found.");

        }
        Cart userCart = cart.get();
        CartDTO cartDTO = modelMapper.map(userCart, CartDTO.class);

        List<ProductDTO> cartItems = userCart.getCartItems().stream()
                .map( item -> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                }
            ).toList();

        cartDTO.setProducts(cartItems);
        return cartDTO;

    }

    @Transactional
    @Override
    public CartDTO updateProductQuantity(Long productId, int operation) {

        String userEmail = authUtil.loggedInEmail();
        Optional<Cart> cart = cartRepository.findCartByEmail(userEmail);

        if (cart.isEmpty() || cart.get().getCartId() == null) {
            throw  new APIException("No cart found.");
        }

        Cart userCart = cart.get();

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId)
        );

        if (product.getQuantity() <=0) {
            throw new APIException("Product out of stock");
        }

        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, userCart.getCartId());

        if (cartItem == null) {
            throw new APIException("Product not in cart");
        }

        cartItem.setQuantity(cartItem.getQuantity() + operation);
        cartItem.setDiscount(product.getDiscount());
        cartItem.setProductPrice(product.getSpecialPrice());
        cartItemRepository.save(cartItem);

        if (cartItem.getQuantity() ==0){
            Long cartItemId = cartItem.getCartItemId();
            cartItemRepository.deleteByCartItemId(cartItemId);
        }

        double newTotalPrice =0;
        for (CartItem item : userCart.getCartItems()) {
            newTotalPrice += item.getProductPrice() * item.getQuantity();
        }

        userCart.setTotalPrice(newTotalPrice);
        userCart = cartRepository.save(userCart);

        CartDTO cartDTO = modelMapper.map(userCart, CartDTO.class);
        List<CartItem> cartItems = userCart.getCartItems();

        List<ProductDTO> productDTOList = cartItems.stream().map(item ->{
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        }).toList();

        cartDTO.setProducts(productDTOList);

        return cartDTO;
    }

    @Transactional
    @Override
    public String deleteProductFromCart(Long productId, Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if (cart.isEmpty()) {
            throw new APIException("No cart found");
        }
        Cart userCart = cart.get();

        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, userCart.getCartId());

        if (cartItem == null) {
            throw new APIException("Product not in cart");
        }

        userCart.setTotalPrice(userCart.getTotalPrice() - (cartItem.getQuantity()*cartItem.getProductPrice()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product '"+cartItem.getProduct().getProductName()+ "' removed from cart.";

    }

}
