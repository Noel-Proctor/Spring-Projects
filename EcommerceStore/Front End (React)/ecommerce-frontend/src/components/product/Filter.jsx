import { FormControl, InputLabel, MenuItem, Select, Tooltip, IconButton, Button } from "@mui/material";
import { useEffect, useState } from "react"
import { FiArrowDown, FiArrowUp, FiRefreshCcw, FiSearch } from "react-icons/fi";
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom'
import { MdSearch } from "react-icons/md";


function Filter({ categories }) {


    const [category, setCategory] = useState("all");
    const [sortOrder, setSortOrder] = useState("asc");
    const [keyWord, setKeyword] = useState("");

    const [searchParams] = useSearchParams();
    const params = new URLSearchParams(searchParams);
    const pathname = useLocation().pathname;
    const navigate = useNavigate();


    useEffect(() => {
        const currentCategory = searchParams.get("category") || "all";
        const currentSortOrder = searchParams.get("orderBy") || "asc";
        const currentSearchTerm = searchParams.get("keyword") || "";
        setCategory(currentCategory);
        setSortOrder(currentSortOrder);
        setKeyword(currentSearchTerm);

    }, [searchParams])


    const handleCategoryChange = (event) => {
        const selectedCategory = event.target.value;

        if (selectedCategory === "all") {
            params.delete("category");
        } else {
            params.set("category", selectedCategory);
        }

        params.set("page", 1)
        navigate(`${pathname}?${params}`);
        setCategory(event.target.value);

    };

    const handleClearFilters = () => {
        navigate({ pathname: window.location.pathname });
    };


    const handleSearchOnClick = () => {
        // Error: event.target.value is not the correct way to get the input value when clicking the button
        const keyword = keyWord;

        if (keyword.trim() === "") {
            params.delete("keyword")
        } else {
            params.set("keyword", keyword)
        }

        params.set("page", 1)
        navigate(`${pathname}?${params}`);
    };

    const keywordOnChange = (event) => {
        setKeyword(event.target.value);
    }

    const toggleSort = () => {
        setSortOrder((prevOrder) => {
            const newOrder = (prevOrder === "asc") ? "desc" : "asc";
            params.set("orderBy", newOrder);
            navigate(`${pathname}?${params}`);
            return newOrder;
        })
    };



    return (
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4">
            <div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
                <input
                    type="text"
                    placeholder="Search Products"
                    value={keyWord}
                    onChange={keywordOnChange}
                    className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-3 w-full focus:ring-gray-100" />
                <FiSearch className="absolute left-3 text-slate-800 size={20}" />

                <div className="ml-5">
                    <Button onClick={handleSearchOnClick} variant="contained" color="primary" className="flex items-center gap-2 h-10 ml-2">
                        <MdSearch size={20} /> Search
                    </Button>
                </div>



            </div>


            {/* Category selection filter */}
            <div className="flex lg:flex-row flex-col gap-4 items-center">
                <FormControl
                    className="text-slate-800 border-slate-700"
                    variant="outlined"
                    size="small">
                    <InputLabel id="category-select-label">Category</InputLabel>
                    <Select
                        className="min-w-[120px] text-slate-800 border-slate-700"
                        labelId="category-select-label"
                        value={category}
                        onChange={handleCategoryChange}
                        label="Category"
                        style={{ width: '200px' }}>

                        <MenuItem value="all">All</MenuItem>
                        {categories.map((item) => (
                            <MenuItem key={item.categoryId} value={item.categoryName}> {item.categoryName}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <Tooltip title="Sorted by price: asc">
                    <Button onClick={toggleSort} variant="contained" color="primary" className="flex items-center gap-2 h-10">Sort

                        {sortOrder === "asc" ?
                            (<FiArrowUp size={20} />) :
                            (<FiArrowDown size={20} />)
                        }


                    </Button>
                </Tooltip>

                <button onClick={handleClearFilters}
                    className="flex items-center gap-2 bg-rose-900 text-white px-3 py-2 rounded-md transition duration-300 ease-in shadow-md focus:outline-none">
                    <FiRefreshCcw className="font-semibold" size={16} />
                    <span className="font-semibold ">Clear</span>
                </button>


            </div>



        </div>


    )
}

export default Filter;