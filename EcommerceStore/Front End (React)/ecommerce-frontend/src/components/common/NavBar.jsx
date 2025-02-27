import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import Badge from '@mui/material/Badge';
import { Link } from "react-router-dom";
import { FaStore } from "react-icons/fa6";
import { useLocation } from "react-router-dom";
import { FaSignInAlt } from "react-icons/fa";
import { MdAccountCircle } from "react-icons/md";

function NavBar() {

    const location = useLocation();
    const path = location.pathname;

    return (
        <div className="h-[70px] bg-custom-gradient text-white z-50 flex items-center sticky top-0">
            <div className="lg: px-6 sm: px-4 w-full flex justify-between">

                <div className=" w-full flex flex-row justify-start items-center">
                    <Link to="/" className="flex items-center text-2xl pr-5">
                        <FaStore className="mr-2 text-3xl"></FaStore>
                        <span className="font-[Poppins]"> Store Name</span>
                    </Link>

                    <ul className="flex flex-row text-slate-800">
                        <li className="font-[500] transition-all duration-150 mr-3">
                            <Link to="/" className={`${path === "/" ? "text-white font-semibold" : "text-gray-400"}`}>
                                <span className="font-[Poppins]">Home</span>
                            </Link>
                        </li>
                        <li className="font-[500] transition-all duration-150 mr-3">
                            <Link to="/Products" className={`${path === "/Products" ? "text-white font-semibold" : "text-gray-400"}`}>
                                <span className="font-[Poppins]"> Products</span>
                            </Link>
                        </li>
                        <li className="font-[500] transition-all duration-150 mr-3">
                            <Link to="/Contact" className={`${path === "/Contact" ? "text-white font-semibold" : "text-gray-400"}`}>
                                <span className="font-[Poppins]"> Contact Us</span>
                            </Link>
                        </li>
                    </ul>

                </div>

                <div className='w-full flex flex-row justify-end items-center'>
                    <ul className="flex flex-row text-slate-800">
                        <li className="font-[500] transition-all duration-150 mr-4">
                            <Link to="/Account" className={`flex items-center ${path === "/Account" ? "text-white font-semibold" : "text-gray-400"}`}>
                                <MdAccountCircle className="text-3xl mr-2" />
                                <span className="font-[Poppins]"> My Account</span>
                            </Link>
                        </li>
                        <li className="font-[500] transition-all duration-150 mr-10">
                            <Link to="/cart" >
                                <Badge
                                    showZero
                                    badgeContent={4}
                                    color="primary"
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                >

                                    <ShoppingCartIcon className={`${path === '/cart' ? 'text-white scale-110' : 'text-gray-500'}`}></ShoppingCartIcon>
                                </Badge>
                            </Link>
                        </li>

                        <li className="font-[500] transition-all duration-150 mr-3">
                            <Link to="/login" className={`flex items-center ${path === "/login" ? "text-white font-semibold" : "text-gray-400"}`}>
                                <FaSignInAlt className='mr-2' />
                                <span>Login</span>

                            </Link>
                        </li>
                    </ul>
                </div>



            </div>

        </div >

    )


}


export default NavBar; 