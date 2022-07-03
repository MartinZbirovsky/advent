import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Pagination } from '../utils/Pagination'
import { SearchBar } from '../utils/SearchBar'
import { getData } from "../CustomHook";


export const UserList = () => {
    const baseUrl = `http://localhost:8080/advent/api/users`
    const [url, setUrl] = useState(baseUrl)
    const [data, setData] = useState('')
    const [searchName, setSearchName] = useState('')
    const [isPending, setIsPending] = useState(true)

    useEffect(() => {
        getUsers(url)
    }, [url])

    const handleSearch = () => {
        setUrl(baseUrl + `?email=${searchName}`)
    }

    async function getUsers(reqUrl) {
        const response = await getData(reqUrl)
        setIsPending(false)
        setData(response)
    }

    return (
        <>
            <SearchBar searchValue={searchName} valueState={setSearchName} handleSubmitMethod={handleSearch} />
            {isPending && <div>Loading...</div>}
            {data && <>
                <ul>
                    {data.content.map((obj) =>
                        <li className="listItem">
                            <Link id={obj.id} to={`/userdetail/${obj.id}`}>
                                <p>{obj.id}</p>
                                <p>User email: {obj.email}</p>
                                <p>Is user confirm? {String(obj.enabled)}</p>
                                <p>Is user banned? {String(obj.accountNonLocked)}</p>
                            </Link>
                        </li>
                    )}
                </ul>
                <Pagination pages={data.totalPages} elements={data.totalElements} />
            </>}
        </>
    )
}

// import { ObjectToTable } from '../utils/ObjectToTable.js';
// zmenit response u list user a pouzit  <ObjectToTable object={obj} routValue={"userdetail"}/>