import { useEffect } from "react";
import { FaExclamationTriangle } from 'react-icons/fa'
import ProductCard from "./ProductCard";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../../store/actions";
import Filter from "../Filter";
import useProductFilter from "./useProductFilter";



function Product() {

    const { isLoading, errorMessage } = useSelector(state => state.errors);
    const { products } = useSelector((state) => state.products);
    const dispatch = useDispatch();

    useProductFilter();

    useEffect(() => {
        dispatch(fetchProducts());

    }, [dispatch]);


    useEffect(() => {
        console.log(products);
    }, [])


    return (
        <div className="lg:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            <Filter></Filter>

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

                            <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
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