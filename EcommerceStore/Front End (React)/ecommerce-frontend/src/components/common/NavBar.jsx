import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import Badge from '@mui/material/Badge';
import { Link } from "react-router-dom";
import { FaStore } from "react-icons/fa6";
import { useLocation } from "react-router-dom";
import { FaSignInAlt } from "react-icons/fa";
import { MdAccountCircle } from "react-icons/md";
import { useState } from 'react';
import { RxCross2, RxHamburgerMenu } from "react-icons/rx";


function NavBar() {

    const location = useLocation();
    const path = location.pathname;
    const [showNavBar, setShowNavBar] = useState(false);

    return (
        <div className="h-[70px] bg-custom-gradient text-white z-50 flex items-center sticky top-0">

            <div className="lg:px-6 sm:px-4 w-full flex justify-between">
                <div className="flex flex-row items-center">
                    <Link to="/" className="flex items-center text-2xl pr-5">
                        <FaStore className="mr-2 text-3xl"></FaStore>
                        <span className="font-[Poppins]"> Store Name</span>
                    </Link>
                </div>

                <div className={`flex flex-row items-center ${showNavBar ? "" : ""} w-auto`} >
                    <ul className={`flex md:gap-10 gap-4 md:items-center text-slate-800 md:static fixed left-0 top-[70px]  md:shadow-none shadow-md 
                        ${showNavBar ? "h-auto shadow-md md:pb-0 pb-5" : "h-0 overflow-hidden"} transition-all duration-100 md:h-fit md:bg-none bg-custom-gradient text-white md:w-fit w-full md:flex-row flex-col px-4 md:px-0`}>

                        <li className="font-[500] transition-all duration-150 mr-4 w-full py-2 hover:bg-gray-700 md:py-0 md:w-auto rounded-sm ">
                            <Link to="/" className="">
                                <span className={`${path === "/" ? "text-white font-semibold" : "text-gray-400"}`}>Home</span>
                            </Link>
                        </li>

                        <li className="font-[500] transition-all duration-150 mr-4 py-2 w-full md:py-0   hover:bg-gray-700 md:w-auto lg:hover:bg-gray-700 rounded-sm">
                            <Link to="/Products" className="h-full w-full">
                                <span className={`${path === "/Products" ? "text-white font-semibold" : "text-gray-400"}`}> Products</span>
                            </Link>
                        </li>

                        <li className="font-[500] transition-all duration-150 mr-4 py-2 w-full hover:bg-gray-700 md:py-0 md:w-auto rounded-sm">
                            <Link to="/Contact" className="h-full w-full">
                                <span className={`${path === "/Contact" ? "text-white font-semibold" : "text-gray-400"}`}> Contact Us</span>
                            </Link>
                        </li>

                        <li className="font-[500] transition-all duration-150 mr-4 py-2 w-full hover:bg-gray-700 md:py-0 sm:w-auto rounded-sm" >
                            <Link to="/Account" className="flex flex-row h-full w-full">
                                <MdAccountCircle className="text-3xl mr-2" />
                                <span className={`${path === "/Account" ? "text-white font-semibold" : "text-gray-400"} block`}> My Account</span>
                            </Link>
                        </li>


                        <li className='flex items-center font-[500] transition-all duration-150 mr-10 py-2 w-full hover:gb-gray-700 md:w-auto rounded-sm'>
                            <Link to="/cart" className='h-full w-full flex flex-row'>
                                <Badge
                                    showZero
                                    badgeContent={0}
                                    color="primary"
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'left',
                                    }}
                                >
                                    <ShoppingCartIcon className={`${path === '/cart' ? 'text-white scale-110' : 'text-gray-500'} mr-2`}></ShoppingCartIcon>
                                </Badge>
                                <span className={`${path === "/cart" ? "text-white font-semibold" : "text-gray-400"} block`}> Cart</span>
                            </Link>
                        </li>


                        <li className="font-[500] transition-all duration-150 mr-3">
                            <Link to="/login" className={`flex items-center space-x-2 px-4 py-[6px] ${path === "/login" ? "text-white font-semibold" : "text-gray-200"} bg-gradient-to-r from-purple-600 to-red-500  rounded-md shadow-lg hover:from-purple500 hover:to-red-400 transition duration-300 ease-in-out transform`}>
                                <FaSignInAlt className='mr-2' />
                                <span>Login</span>
                            </Link>
                        </li>

                    </ul>

                </div>


                <button onClick={() => setShowNavBar(!showNavBar)}
                    className="md:hidden flex items-center md:mt-0 mt-2 mr-2">
                    {showNavBar ? (<RxCross2 className="text-white text-3xl"></RxCross2>) : (<RxHamburgerMenu />)}
                </button>
            </div>
        </div >

    )


}


export default NavBar; 