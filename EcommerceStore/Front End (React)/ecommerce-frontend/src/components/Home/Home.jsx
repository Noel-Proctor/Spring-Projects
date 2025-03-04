
import Banner from "../common/Banner";
import ProductCarousel from "../common/ProductCarousel";



function Home() {

    return (
        <div>
            <div className="lg:px-6 sm:px-4">
                <Banner></Banner>
                <div className="py-5">
                    <div className="flex flex-col justify-center items-center space-y-2">
                        <h1 className="text-slate-700 text-4xl font-bold">Products</h1>
                        <span className="text-slate-700"> Discover Our Top Rated Items</span>
                    </div>
                    <ProductCarousel></ProductCarousel>
                </div>

            </div>
        </div >
    )
}

export default Home; 