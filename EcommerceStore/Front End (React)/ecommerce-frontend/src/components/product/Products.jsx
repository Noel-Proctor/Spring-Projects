

import ProductCard from "./ProductCard";
import { useSelector } from "react-redux";
import Filter from "./Filter";
import useProductFilter from "./useProductFilter";
import Loader from "../common/Loader";
import ErrorMessage from '../common/ErrorMessage';
import PaginationComponent from "../common/PaginagtionComponent";



function Product() {

    const { isLoading, errorMessage } = useSelector(state => state.errors);
    const { products, categories, pagination } = useSelector((state) => state.products);

    useProductFilter();

    return (
        <div className="lg:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            <Filter categories={categories ? categories : []}></Filter>


            {isLoading ? (<Loader />)
                : errorMessage ? (<ErrorMessage errorMessage={errorMessage} errorText="Opps. Something went wrong." />
                )
                    : (
                        <div className="min-h-[700px]">

                            <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
                                {products && products.map((product) => (
                                    <ProductCard key={product.productId} {...product}></ProductCard>
                                ))}
                            </div>

                            <PaginationComponent numberOfPages={pagination.totalPages}>

                            </PaginationComponent>

                        </div>
                    )}
        </div>
    )
}

export default Product;