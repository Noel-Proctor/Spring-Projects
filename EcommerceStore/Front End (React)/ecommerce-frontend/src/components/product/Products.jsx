import { useEffect } from "react";
import { FaExclamationTriangle } from 'react-icons/fa'
import ProductCard from "./ProductCard";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../../store/actions";



function Product() {

    const { products } = useSelector((state) => state.products);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchProducts());

    }, [dispatch]);


    // const products = [

    //     {
    //         productId: 652,
    //         productName: "Iphone Xs max",
    //         image: "https://placehold.co/600x400",
    //         description: "Experience the latest in mobile technology with advanced cameras, powerful processing, and an all-day battery.",
    //         quantity: 0,
    //         price: 1450.0,
    //         discount: 10.0,
    //         specialPrice: 1305.0,
    //     },
    //     {
    //         productId: 654,
    //         productName: "MacBook Air M2s",
    //         image: "https://placehold.co/600x400",
    //         description: "Ultra-thin laptop with Apple's M2 chip, providing fast performance in a lightweight, portable design.",
    //         quantity: 1,
    //         price: 2550.0,
    //         discount: 20.0,
    //         specialPrice: 2040.0,
    //     },

    //     {
    //         productId: 655,
    //         productName: "GusBus",
    //         image: "https://placehold.co/600x400",
    //         description: "a bus for the gus",
    //         quantity: 10,
    //         price: 2340.0,
    //         discount: 0.0,
    //         specialPrice: 0,
    //     }
    // ]

    useEffect(() => {
        console.log(products);
    }, [])

    const isLoading = false;
    const errorMessage = "";

    return (
        <div className="lg: sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            {isLoading ? (<p>Products Loading</p>)
                : errorMessage ? (<div className="flex justify-center items-center h-[200px]">
                    <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"></FaExclamationTriangle>
                    <span className="text-slate-800 text-lg font-medium">
                        {errorMessage}
                    </span>
                </div>
                )
                    : (
                        <div className="min-h-[700px]">
                            <div className="pb-6 pt-14 grid 2xl: grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
                                {products && products.map((product) => (
                                    <ProductCard key={product.productId} {...product}></ProductCard>
                                ))}
                            </div>
                        </div>
                    )}
        </div>
    )
}

export default Product;