import { Button, Dialog, DialogPanel, DialogTitle, DialogBackdrop } from '@headlessui/react'
import { useState } from 'react'
import Status from '../common/Status';
import { MdClose, MdDone } from "react-icons/md";
import { Divider } from '@mui/material';


function ProductViewModal({ isOpen, setIsOpen, product, isAvailable }) {

    const { id, productName, image, description, quantity, price, discount, specialPrice } = product;
    const handleClickOpen = () => {
        setIsOpen(true);
    }

    return (
        <>
            <Dialog open={isOpen} as="div" className="relative z-10" onClose={close} __demoMode>

                <DialogBackdrop className="fixed inset-0 bg-slate-800 bg-opacity-70 transition-opacity" />
                <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                    <div className="flex min-h-full items-center justify-center p-4">
                        <DialogPanel
                            transition
                            className="relative transform overflow-hidden rounded-xl bg-white/95 p-6 shadow-xl  transition-all md:max-w-[620px] md:min-w-[620px] w-full   "
                        >

                            {image && (
                                <img src={image}
                                    alt={productName}
                                />
                            )}

                            <div className=" pt-10- pb-2">
                                <DialogTitle as="h1" className="lg:text-3xl sm:text-2xl text-xl font-semibold leading-6 text-gray-800 mb-4 mt-5">
                                    {productName}
                                </DialogTitle>
                            </div>


                            <div className="space-y-2 text-gray-700 pb-4 grid grid-cols-1 sm:grid-cols-2 gap-2">
                                <div className="flex flex-col sm:flex-row items-center justify-between gap-2">
                                    {discount && specialPrice ? (
                                        <div className="flex items-center gap-2">
                                            <span className="text-gray-400 line-through">
                                                ${Number(price).toFixed(2)}
                                            </span>
                                            <span className="sm:text-xl font-semibold text-slate-800">
                                                ${Number(specialPrice).toFixed(2)}
                                            </span>
                                        </div>
                                    ) : (
                                        <div className="flex items-center gap-2">
                                            <span className="sm:text-xl font-semibold text-slate-800">
                                                £{Number(price).toFixed(2)}
                                            </span>
                                        </div>
                                    )}
                                </div>

                                <div className="flex flex-row-reverse sm:basis-1/4">
                                    {isAvailable ? (
                                        <Status
                                            text={`${quantity} Available`}
                                            icon={MdDone}
                                            style="px-2 py-2 font-medium rounded flex bg-teal-200 text-teal-900 items-center gap-1"
                                            size="15"
                                        />
                                    ) : (
                                        <Status
                                            text="Out Of Stock"
                                            icon={MdClose}
                                            style="px-2 py-2 rounded flex bg-rose-200 text-teal-900 items-center font-medium"
                                            size="20"
                                        />
                                    )}
                                </div>
                            </div>

                            <Divider></Divider>


                            <p className="mt-2 text-sm/6 text-gray-800">
                                {description}
                            </p>
                            <div className="mt-4">
                                <Button
                                    className="inline-flex items-center gap-2 rounded-md bg-gray-700 py-1.5 px-3 text-sm/6 font-semibold text-white shadow-inner shadow-white/10 focus:outline-none data-[hover]:bg-gray-600 data-[focus]:outline-1 data-[focus]:outline-white data-[open]:bg-gray-700"
                                    onClick={() => setIsOpen(false)}
                                >
                                    Got it, thanks!
                                </Button>
                            </div>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
        </>
    )
}


export default ProductViewModal;