import React from 'react'
import { useParams } from "react-router-dom";
import { useFetch } from '../CustomHook';

export const AdsDetail = () => {
    const { id } = useParams();
    const { data, isPending, error } = useFetch(`http://localhost:8080/advent/api/ads/${id}`);
    //const navigate = useNavigate();
    return (
        <div className="blog-details">
            {error && <div> {error}</div>}
            {isPending && <div>Loading...</div>}
            {data && (
                <>
                    <p>{data.id}</p>
                    <p>{data.name}</p>
                    <p>{data.companyOffer}</p>
                    <p>{data.createdAt}</p>
                    <p>{data.description}</p>
                    <p>{data.officePlace}</p>
                    <p>{data.requirements}</p>
                    <p>{data.salaryFrom}</p>
                    <p>{data.salaryTo}</p>
                    <p>{data.workType}</p>
                    {console.log(data)}
                </>
            )}
            <h1>detailss</h1>
        </div>
    );
}
/*<p>{data.benefits}</p>
                    <p>{data.publisherName}</p>
                    <p>{data.companyLogo}</p>
*/
