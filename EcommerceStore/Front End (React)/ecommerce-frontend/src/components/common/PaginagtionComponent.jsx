import Pagination from '@mui/material/Pagination';
import toast from 'react-hot-toast';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';

function PaginationComponent({ numberOfPages }) {

    const [searchParams] = useSearchParams();
    const pathName = useLocation().pathname;
    const params = new URLSearchParams(searchParams);
    const navigate = useNavigate();

    const currentPage = searchParams.get("page") ? Number(searchParams.get("page")) : 1;



    const paginationOnClick = (event, value) => {
        params.set("page", value.toString());
        navigate(`${pathName}?${params}`);
    };

    return (

        <div className='flex justify-center items-center'>
            <div>

                <Pagination onChange={paginationOnClick} count={numberOfPages} page={currentPage} size='large' variant='outlined' shape="rounded" />

            </div>
        </div>
    );
}

export default PaginationComponent; 