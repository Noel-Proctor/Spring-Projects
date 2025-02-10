
import { RotatingLines } from 'react-loader-spinner'

function Loader({ text }) {

    return (
        <div className='flex justify-center items-center w-full h-[450px]' >
            <div className="flex flex-col items-center gap-1">
                <RotatingLines
                    visible={true}
                    height="96"
                    width="96"
                    strokeColor="#2C3E50"
                    strokeWidth="4"
                    animationDuration="0.75"
                    ariaLabel="rotating-lines-loading"
                    wrapperStyle={{}}
                    wrapperClass="text-"
                />
                {text &&
                    <div>
                        <p className='text-slate-800'>{text}</p>
                    </div>
                }
            </div>
        </div>
    )



}

export default Loader;