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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            cartItem.setProductPrice(product.getSpecial_Price());
        }else{
            cartItem.setProductPrice(product.getPrice());
        }
        cartItem = cartItemRepository.save(cartItem);

        cart.setTotalPrice( cart.getTotalPrice() + (cartItem.getProductPrice()*quantity));

        //Save Cart Item.
        CartDTO cartDTO = modelMapper.map(cartRepository.save(cart), CartDTO.class);

        //Get list of cart items

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item ->{
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());

            return map;
        });

        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }



    private Cart createCart() {

        Cart userCart = cartRepository.findCartByUserId(authUtil.loggedInUserId());
        if (userCart == null) {
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtil.loggedInUser());

        Cart newCart = cartRepository.save(cart);

        return newCart;
    }

}
