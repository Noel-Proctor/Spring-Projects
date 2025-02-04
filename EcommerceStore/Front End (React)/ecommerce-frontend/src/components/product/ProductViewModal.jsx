import { Button, Dialog, DialogPanel, DialogTitle, DialogBackdrop } from '@headlessui/react'
import { useState } from 'react'

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
                                <DialogTitle as="h1" className="lg:text-3xl cm:text-2xl text-xl font-semibold leading-6 text-gray-800 mb-4 mt-5">
                                    {productName}
                                </DialogTitle>


                            </div>

                            <p className="mt-2 text-sm/6 text-slate-600/50">
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