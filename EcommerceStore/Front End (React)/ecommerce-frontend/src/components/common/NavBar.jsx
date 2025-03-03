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

            <div className="lg: px-6 sm: px-4 w-full flex justify-between">
                <div className="flex flex-row items-center">
                    <Link to="/" className="flex items-center text-2xl pr-5">
                        <FaStore className="mr-2 text-3xl"></FaStore>
                        <span className="font-[Poppins]"> Store Name</span>
                    </Link>
                </div>

                <div className={`flex flex-row items-center ${showNavBar ? "absolute" : ""} sm:flex`} >
                    <ul className={`flex sm:gap-10 gap-4 sm:items-center flex-row text-slate-800 sm:static  left-0 top-[200px] sm:shadow-none shadow-md 
                        ${showNavBar ? "h-full rounded-md shadow-mdsm:pb-0 pb-5" : "h-0 overflow-hidden"} transition-all duration-100 sm:h-fit sm:bg-none bg-custom-gradient text-white sm:w-fit w-full sm:flex-row flex-col px-4 sm:px-0`}>

                        <Link to="/" className="font-[500] transition-all duration-150 mr-4 py-2  sm:py-0 w-full sm:w-auto hover:bg-gray-700 sm:hover:bg-transparent rounded-sm">
                            <li className='h-full lg:hover:bg-gray-700'>
                                <span className={`${path === "/" ? "text-white font-semibold" : "text-gray-400"}`}>Home</span>
                            </li>
                        </Link>

                        <Link to="/Products" className="font-[500] transition-all duration-150 mr-4 py-2 w-full sm:w-auto hover:bg-gray-700 sm:hover:bg-transparent rounded-sm">
                            <li className='h-full  lg:hover:bg-gray-700'>
                                <span className={`${path === "/Products" ? "text-white font-semibold" : "text-gray-400"}`}> Products</span>
                            </li>
                        </Link>

                        <Link to="/Contact" className="font-[500] transition-all duration-150 mr-4 py-2 w-full sm:w-auto hover:bg-gray-700 sm:hover:bg-transparent rounded-sm">
                            <li className='h-full lg:hover:bg-gray-700'>
                                <span className={`${path === "/Contact" ? "text-white font-semibold" : "text-gray-400"}`}> Contact Us</span>
                            </li>
                        </Link>

                        <Link to="/Account" className="font-[500] transition-all duration-150 mr-4 py-2 sm:py-0 w-full sm:w-auto hover:bg-gray-700 sm:hover:bg-transparent rounded-sm">
                            <li className='flex items-center h-full  lg:hover:bg-gray-700'>
                                <MdAccountCircle className="text-3xl mr-2" />
                                <span className={`${path === "/Account" ? "text-white font-semibold" : "text-gray-400"} block`}> My Account</span>
                            </li>
                        </Link>


                        <Link to="/cart" className='flex items-center font-[500] transition-all duration-150 mr-10 py-2 w-full sm:w-auto hover:bg-gray-700 sm:hover:bg-transparent rounded-sm'>
                            <li className=' lg:hover:bg-gray-700'>
                                <Badge
                                    showZero
                                    badgeContent={4}
                                    color="primary"
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                >

                                    <ShoppingCartIcon className={`${path === '/cart' ? 'text-white scale-110' : 'text-gray-500'} `}></ShoppingCartIcon>
                                </Badge>
                            </li>
                        </Link>


                        <li className="font-[500] transition-all duration-150 mr-3">
                            <Link to="/login" className={`flex items-center space-x-2 px-4 py-[6px] ${path === "/login" ? "text-white font-semibold" : "text-gray-200"} bg-gradient-to-r from-purple-600 to-red-500  rounded-md shadow-lg hover:from-purple500 hover:to-red-400 transition duration-300 ease-in-out transform`}>
                                <FaSignInAlt className='mr-2' />
                                <span>Login</span>

                            </Link>
                        </li>

                    </ul>

                </div>


                <button onClick={() => setShowNavBar(!showNavBar)}
                    className="sm:hidden flex items-center sm:mt-0 mt-2">
                    {showNavBar ? (<RxCross2 className="text-white text-3xl"></RxCross2>) : (<RxHamburgerMenu />)}
                </button>
            </div>
        </div >

    )


}


export default NavBar; 