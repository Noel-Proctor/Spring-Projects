import { AboutUsImage } from "../Utils/constants";



function About() {


    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 bg-orange-50 shadow-sm mt-4 rounded-md">
            <div className="text-slate-800 text-4xl font-bold text-center mb-12">About Us</div>

            <div>
                <div className="flex flex-col md:flex-row items-center justify-center md:space-x-10 bg-">
                    <div className="w-full md:w-1/2  bg-white p-6 rounded-lg shadow-md">
                        <img src={AboutUsImage} alt="About Us Image" className="w-full h-auto object-cover rounded-lg transform transition-transform duration-300 hover:scale-105"></img>
                    </div>
                    <div className="w-full md:w-1/2">
                        <h1 className="text-3xl font-bold text-slate-800">Who We Are</h1>
                        <p className="text-slate-800 mt-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                            Ut quis turpis eget sapien luctus tincidunt. Integer nec nunc et odio tincidunt ultricies. Donec sit amet nisl nec libero venenatis aliquam. Nulla facilisi /P</p>
                    </div>
                </div>
            </div>

            <div className="flex flex-col items-centre justify-center my-20">
                <div className="text-slate-800 text-2xl font-bold text-center mb-5 w-full">Customer Reviews</div>
                <div className="w-full bg-white p-6 rounded-lg shadow-sm">
                    test
                </div>
            </div>


        </div>


    )
}

export default About;   