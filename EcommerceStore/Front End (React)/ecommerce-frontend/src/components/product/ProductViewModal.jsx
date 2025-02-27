import { Button, Dialog, DialogPanel, DialogTitle, DialogBackdrop } from '@headlessui/react';
import { useState } from 'react';
import Status from '../common/Status';
import { MdClose, MdDone } from "react-icons/md";
import { Divider } from '@mui/material';

function ProductViewModal({ isOpen, setIsOpen, product, isAvailable }) {
    const { id, productName, image, description, quantity, price, discount, specialPrice } = product;

    return (
        <Dialog open={isOpen} as="div" className="relative z-10" onClose={() => setIsOpen(false)} __demoMode>
            <DialogBackdrop className="fixed inset-0 bg-slate-800 bg-opacity-70 transition-opacity" />
            <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                <div className="flex min-h-full items-center justify-center p-4 sm:p-6">
                    <DialogPanel
                        transition
                        className="relative transform overflow-hidden rounded-xl bg-white/95 p-4 sm:p-6 shadow-xl transition-all w-full max-w-md sm:max-w-lg md:max-w-2xl"
                    >
                        {image && (
                            <img
                                src={image}
                                alt={productName}
                                className="w-full h-auto max-h-64 object-cover rounded-md mb-4"
                            />
                        )}

                        <DialogTitle as="h1" className="text-lg sm:text-xl md:text-2xl lg:text-3xl font-semibold text-gray-800 mb-3 sm:mb-4">
                            {productName}
                        </DialogTitle>

                        <div className="space-y-3 sm:space-y-4 text-gray-700 grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4">
                            <div className="flex flex-col items-center sm:items-start gap-1 sm:gap-2">
                                {discount && specialPrice ? (
                                    <div className="flex items-center gap-2">
                                        <span className="text-gray-400 line-through text-sm sm:text-base">
                                            £{Number(price).toFixed(2)}
                                        </span>
                                        <span className="text-base sm:text-lg md:text-xl font-semibold text-slate-800">
                                            £{Number(specialPrice).toFixed(2)}
                                        </span>
                                    </div>
                                ) : (
                                    <span className="text-base sm:text-lg md:text-xl font-semibold text-slate-800">
                                        £{Number(price).toFixed(2)}
                                    </span>
                                )}
                            </div>

                            <div className="flex justify-center sm:justify-end">
                                {isAvailable ? (
                                    <Status
                                        text={`${quantity} Available`}
                                        icon={MdDone}
                                        style="px-2 py-1 sm:px-3 sm:py-2 font-medium rounded flex bg-teal-200 text-teal-900 items-center gap-1 text-xs sm:text-sm"
                                        size="14 sm:16"
                                    />
                                ) : (
                                    <Status
                                        text="Out Of Stock"
                                        icon={MdClose}
                                        style="px-2 py-1 sm:px-3 sm:py-2 rounded flex bg-rose-200 text-teal-900 items-center font-medium text-xs sm:text-sm"
                                        size="16 sm:20"
                                    />
                                )}
                            </div>
                        </div>

                        <Divider className="my-3 sm:my-4" />

                        <p className="mt-2 text-xs sm:text-sm md:text-base text-gray-800 line-clamp-4 sm:line-clamp-6">
                            {description}
                        </p>

                        <div className="mt-4 flex justify-center sm:justify-end">
                            <Button
                                className="inline-flex items-center gap-2 rounded-md bg-gray-700 py-1.5 px-3 sm:py-2 sm:px-4 text-xs sm:text-sm font-semibold text-white shadow-inner shadow-white/10 focus:outline-none data-[hover]:bg-gray-600 data-[focus]:outline-1 data-[focus]:outline-white"
                                onClick={() => setIsOpen(false)}
                            >
                                Got it, thanks!
                            </Button>
                        </div>
                    </DialogPanel>
                </div>
            </div>
        </Dialog>
    );
}

export default ProductViewModal;