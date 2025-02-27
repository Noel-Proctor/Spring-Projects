import React, { useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { useDispatch, useSelector } from "react-redux";
import ProductCard from '../product/ProductCard';
import { fetchProducts } from "../../store/actions";
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import ErrorMessage from './ErrorMessage';
import Loader from "./Loader";


function ProductCarousel() {
    const dispatch = useDispatch();
    const { products } = useSelector((state) => state.products);
    const { isLoading, errorMessage } = useSelector(state => state.errors);

    useEffect(() => {
        dispatch(fetchProducts());
    }, [dispatch]);

    useEffect(() => {
        console.log(products);
    }, [products]);

    return (
        <div>
            {isLoading ? (<Loader />) : errorMessage ? (<ErrorMessage errorMessage={errorMessage} errorText="Opps. Something went wrong." />)
                : (<div className="w-full px-2 sm:px-6 lg:py-6 flex flex-col items-center">
                    <div className="product-carousel-container max-w-full">
                        <Swiper
                            navigation
                            modules={[Navigation]}
                            className="mySwiper"
                            spaceBetween={16}
                            breakpoints={{
                                320: {
                                    slidesPerView: 1,
                                    spaceBetween: 8,
                                },
                                480: {
                                    slidesPerView: 2,
                                    spaceBetween: 12,
                                },
                                768: {
                                    slidesPerView: 3,
                                    spaceBetween: 16,
                                },
                                1024: {
                                    slidesPerView: 4,
                                    spaceBetween: 16,
                                },
                                1280: {
                                    slidesPerView: 5.2,
                                    spaceBetween: 18,
                                },
                            }}
                        >
                            {products && products.slice(0, 8).map((item, i) => (
                                <SwiperSlide key={'slide' + i}>
                                    <ProductCard key={item.productName + i} {...item} />
                                </SwiperSlide>
                            ))}
                        </Swiper>
                    </div>
                </div>)
            }
        </div>


    );
}

export default ProductCarousel;