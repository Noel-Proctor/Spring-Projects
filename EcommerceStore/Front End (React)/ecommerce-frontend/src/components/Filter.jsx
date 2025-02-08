import { FormControl, InputLabel, MenuItem, Select, Tooltip, IconButton, Button } from "@mui/material";
import { useState } from "react"
import { FiSearch } from "react-icons/fi";


function Filter() {

    const categories = [
        { categoryId: 1, categoryName: "Technology" },
        { categoryId: 2, categoryName: "Sports" },
        { categoryId: 3, categoryName: "Cooking" }
    ];

    const [category, setCategory] = useState("all");

    const handleCategoryChange = (event) => {
        setCategory(event.target.value)

    };


    return (
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4">
            <div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
                <input
                    type="text"
                    placeholder="Search Products"
                    className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-3 w-full focus:ring-gray-100" />
                <FiSearch className="absolute left-3 text-slate-800 size={20}" />
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
                        label="Category">

                        <MenuItem value="All">All</MenuItem>
                        {categories.map((item) => (
                            <MenuItem key={item.categoryId} value={item.categoryName}> {item.categoryName}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <Tooltip title="Sorted by price: asc">
                    <Button variant="contained" color="primary" className="flex items-center gap-2 h-10">Sort</Button>

                </Tooltip>



            </div>



        </div>


    )
}

export default Filter;