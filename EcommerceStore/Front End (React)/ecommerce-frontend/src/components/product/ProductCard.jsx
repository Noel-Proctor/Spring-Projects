import { useState } from "react";
import { FaShoppingCart } from "react-icons/fa";
import ProductViewModal from "./ProductViewModal";
import { trimToSize } from "../../utils";

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
        <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
            <div onClick={handleProductOnclick} className="w-full overflow-hidden aspect-[3/2]">
                <img src={image}
                    alt={productName}
                    className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105" />
            </div>
            <div className="p-4" >
                <h2 className="text-lg font-semibold mb-2 cursor-pointer">
                    {trimToSize(productName, 30)}
                </h2>
                <div className="min-h-20 max-h-20">
                    <p className="text-gray-600 text-sm">{trimToSize(description, 130)}</p>
                </div>

                <div className="flex flex-col sm:flex-row items-centre justify-between">
                    {discount && specialPrice ? (
                        <div className="flex flex-col">
                            <span className="text-gray-400 line-through">£{Number(price).toFixed(2)}</span>
                            <span className="text-gray-700 font-bold text-xl">£{Number(specialPrice).toFixed(2)}</span>
                        </div>
                    ) : (
                        <div className="mt-6">
                            <span className="text-gray-700 text-xl font-bold">
                                £{Number(price).toFixed(2)}
                            </span>

                        </div>
                    )}
                    <button disabled={!isAvailable || btnLoader}
                        onClick={() => { }}
                        className={`bg-blue-500 ${isAvailable ? "opacity-100 hover:bg-blue-600" : "opacity-70"}
                                 text-white py-2 px-3 rounded-lg items-center transition-colors duration-300 w-full sm:w-36 flex justify-center mt-4 sm:mt-0`}>
                        <FaShoppingCart className="mr-2"></FaShoppingCart>
                        {isAvailable ? "Add To Cart" : "Sold Out"}
                    </button>
                </div>
            </div>

            {showProductViewModal && (
                <div>
                    <ProductViewModal
                        isOpen={showProductViewModal}
                        setIsOpen={setShowProductViewModal}
                        product={selectedProduct}
                        isAvailable={isAvailable}>
                    </ProductViewModal>
                </div>)
            }

        </div>

    )
}

export default ProductCard;