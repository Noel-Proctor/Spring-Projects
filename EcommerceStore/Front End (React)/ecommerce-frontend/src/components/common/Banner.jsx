import React, { useState, useEffect } from 'react';
// Import Swiper React components
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, EffectFade, Autoplay } from 'swiper/modules';
import { v4 as uuidv4 } from 'uuid';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import 'swiper/css/effect-fade';
import 'swiper/css/autoplay';
import { Link } from 'react-router-dom';
import { bannerImageOne, bannerImageTwo, bannerImageFour } from '../../Utils/constants';

function Banner() {
    const [bannerList, setBannerList] = useState([]);
    const colours = ["bg-banner-color1", "bg-banner-color2", "bg-banner-color3", "bg-banner-color4"];

    useEffect(() => {
        // Simulate fetching data from an API
        const fetchData = async () => {
            const data = [
                {
                    id: 1,
                    image: bannerImageOne,
                    title: "Home Comfort",
                    subtitle: "Living Room",
                    description: "Upgrade your space with cozy and stylish sofas",
                },
                {
                    id: 2,
                    image: bannerImageTwo,
                    title: "Work Hard, Play Hard",
                    subtitle: "Gaming and Computing",
                    description: "Experience the latest in home entertainment",
                },
                {
                    id: 3,
                    image: bannerImageFour,
                    title: "Home Improvement?",
                    subtitle: "Kitchen Appliances",
                    description: "Up to 20% off selected products",
                }
            ];
            setBannerList(data);
        };

        fetchData();
    }, []);

    const swiperKey = uuidv4();

    return (
        <div className='py-2 rounded-md'>
            <Swiper key={swiperKey}
                spaceBetween={50}
                slidesPerView={1}
                grabCursor={true}
                autoplay={{
                    delay: 10000,
                    disableOnInteraction: true
                }}

                modules={[Pagination, EffectFade, Navigation, Autoplay]}
                pagination={{ clickable: true }}
                scrollbar={{ draggable: true }}
            >
                {bannerList.map((item, i) => {
                    const slidekey = uuidv4();
                    const linkKey = uuidv4();

                    return (<div className="sm:h-[450px]">
                        <SwiperSlide key={slidekey}>
                            <div className={`carousel-item rounded-sm  ${colours[i]}  min-h-[340px] sm:h-[450px] content-center`}>
                                <div className='flex items-center justify-center'>
                                    <div className="lg:flex justify-center sm:w-full lg:w-1/2 p-8">
                                        <div className='text-center'>
                                            <h3 className='text-3xl text-white'>{item.title}</h3>
                                            <h1 className='text-5xl text-white font-bold mt-3'>{item.subtitle}</h1>
                                            <p className=' text-white  mt-3'>{item.description}</p>
                                            <Link key={linkKey} className='mt-6 inline-block bg-black text-white py-2 px-4 rounded hover:bg-gray-800'
                                                to="products">Shop
                                            </Link>
                                        </div>
                                    </div>
                                    <div className='hidden lg:flex w-full justify-center lg:w-1/2 p-4 max-h-[450px]'>
                                        <img className="max-h-full max-w-full object-contain" src={item?.image} alt={item.description} />
                                    </div>
                                </div>
                            </div>
                        </SwiperSlide>
                    </div>);

                })}
            </Swiper>
        </div>
    )
}

export default Banner;