import { useEffect } from "react";
import toast from 'react-hot-toast';
import { FaExclamationTriangle } from 'react-icons/fa'


/**
 * errorMessage = Optional. Message displayed in a notfication pop up to user if message is set 
 * If no message is set then the mesage is not displayed.
 * 
 * errorText = Optional. Text that is displayed below the Error Icon.
 */
function ErrorMessage({ errorMessage, errorText }) {

    useEffect(() => {
        if (errorMessage) {
            toast.error(errorMessage)
        }
    }, []);

    return (

        <div className="flex justify-center items-center h-[200px]">
            <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"></FaExclamationTriangle>
            {errorText && <span className="text-slate-800 text-lg font-medium">
                {errorText}
            </span>}
        </div>

    )

}


export default ErrorMessage;