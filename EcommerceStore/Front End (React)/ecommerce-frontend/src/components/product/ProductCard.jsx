import { useState } from "react";
import { FaShoppingCart } from "react-icons/fa";
import ProductViewModal from "./ProductViewModal";
import { trimToSize } from "../../Utils/utils";

function ProductCard({
    id, productName, image, description, quantity, price, discount, specialPrice
}) {
    const [showProductViewModal, setShowProductViewModal] = useState(false);
    const btnLoader = false;
    const [selectedProduct, setSelectedProduct] = useState(null);
    const isAvailable = quantity && Number(quantity) > 0;

    const handleProductOnclick = () => {
        const product = { id, productName, image, description, quantity, price, discount, specialPrice };
        setSelectedProduct(product);
        setShowProductViewModal(true);
    };

    return (
        <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300 flex flex-col w-full h-full">
            <div onClick={handleProductOnclick} className="w-full overflow-hidden aspect-[3/2]">
                <img
                    src={image}
                    alt={productName}
                    className="w-full h-full object-cover cursor-pointer transition-transform duration-300 transform hover:scale-105"
                />
            </div>
            <div className="p-2 sm:p-3 md:p-4 flex flex-col flex-grow">
                <h2 className="text-sm sm:text-base md:text-lg font-semibold mb-1 sm:mb-2 cursor-pointer line-clamp-1">
                    {trimToSize(productName, 30)}
                </h2>
                <div className="min-h-[3.5rem] sm:min-h-[4rem] md:min-h-[5rem] max-h-[3.5rem] sm:max-h-[4rem] md:max-h-[5rem] flex-grow">
                    <p className="text-gray-600 text-xs sm:text-sm line-clamp-3">
                        {trimToSize(description, 130)}
                    </p>
                </div>
                <div className="flex flex-col sm:flex-row items-center justify-between gap-2 sm:gap-3 mt-2 sm:mt-3">
                    {discount && specialPrice ? (
                        <div className="flex flex-col items-center sm:items-start">
                            <span className="text-gray-400 line-through text-xs sm:text-sm">
                                £{Number(price).toFixed(2)}
                            </span>
                            <span className="text-gray-700 font-bold text-sm sm:text-base md:text-lg">
                                £{Number(specialPrice).toFixed(2)}
                            </span>
                        </div>
                    ) : (

                        <div className="flex flex-col items-center sm:items-start">
                            <span className="invisible text-gray-400 line-through text-xs sm:text-sm">£0.00</span>
                            <span className="text-gray-700 text-sm sm:text-base md:text-lg font-bold">
                                £{Number(price).toFixed(2)}
                            </span>
                        </div>

                    )}
                    <button
                        disabled={!isAvailable || btnLoader}
                        onClick={() => { }}
                        className={`flex items-center justify-center w-full sm:w-auto min-w-[100px] sm:min-w-[120px] px-2 py-1.5 sm:px-3 sm:py-2 
              bg-blue-500 text-white rounded-lg transition-colors duration-300
              ${isAvailable ? "opacity-100 hover:bg-blue-600" : "opacity-70"}
              text-xs sm:text-sm mt-2 sm:mt-0`}
                    >
                        <FaShoppingCart className="mr-1 sm:mr-2" />
                        {isAvailable ? "Add To Cart" : "Sold Out"}
                    </button>
                </div>
            </div>
            {showProductViewModal && (
                <ProductViewModal
                    isOpen={showProductViewModal}
                    setIsOpen={setShowProductViewModal}
                    product={selectedProduct}
                    isAvailable={isAvailable}
                />
            )}
        </div>
    );
}

export default ProductCard;