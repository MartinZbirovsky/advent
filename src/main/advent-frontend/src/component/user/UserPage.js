import React from 'react'
import { useFetch } from '../CustomHook'
import { ListObjectToTable } from '../utils/ListObjectToTable'
export const UserPage = () => {
    const { data, isPending, error } = useFetch('http://localhost:8080/advent/api/users')

    return (
        <>
            {error && <div> {error}</div>}
            {isPending && <div>Loading...</div>}
            {data && <ListObjectToTable data={data} />}
        </>
    )
}
