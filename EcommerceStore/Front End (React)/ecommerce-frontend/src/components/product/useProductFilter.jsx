import { useSearchParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useEffect } from "react";
import { fetchProducts } from "../../store/actions";

const useProductFilter = () => {

    const [searchParams] = useSearchParams();
    const dispatch = useDispatch();


    useEffect(() => {

        const params = new URLSearchParams();
        const currentPage = searchParams.get("page") ? Number(searchParams.get("page")) : 1;
        const orderBy = searchParams.get("orderBy") || "asc";
        const keyword = searchParams.get("keyword") || null;
        const category = searchParams.get("category") || null;

        params.set("orderBy", "price");
        params.set("direction", orderBy);
        params.set("pageNumber", currentPage - 1);


        if (category) {
            params.set("category", category);
        }

        if (keyword) {
            params.set("keyword", keyword);
        }


        const queryString = params.toString();

        dispatch(fetchProducts(queryString));


    }, [dispatch, searchParams])

}

export default useProductFilter;