import React, { useState, useEffect } from 'react'
import { useParams } from "react-router-dom";
import { getData } from "../CustomHook";

export const AdsDetail = () => {
    
    const { id } = useParams();
    const url = `http://localhost:8080/advent/api/ads/${id}`

    const [data, setData] = useState('')
    const [isPending, setIsPending] = useState(true)

    useEffect(() => {
        getAd(url)
      }, [url])

    async function getAd(reqUrl) {
        const response = await getData(reqUrl)
        setIsPending(false)
        setData(response)
    }

    return (
        <div className="adDetail">
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
                </>
            )}
        </div>
    );
}
