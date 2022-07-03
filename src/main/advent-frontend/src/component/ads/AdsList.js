import React, { useEffect, useState } from 'react'
import { ObjectToTable } from '../utils/ObjectToTable.js';
import { useParams } from 'react-router-dom';
import { Pagination } from '../utils/Pagination.js';
import { SearchBar } from '../utils/SearchBar.js';
import { getData } from "../CustomHook";

export const AdsList = () => {
  const { categoryId = "" } = useParams();
  const baseUrl = `http://localhost:8080/advent/api/ads/?categoryId=${categoryId}`
  const [url, setUrl] = useState(baseUrl)

  const [data, setData] = useState('')
  const [searchName, setSearchName] = useState('')
  const [isPending, setIsPending] = useState(true)

  useEffect(() => {
    getCategory(url)
  }, [url])

  const handleSearch = () => {
    setUrl(baseUrl + `?email=${searchName}`)
}

async function getCategory(reqUrl) {
    const response = await getData(reqUrl)
    setIsPending(false)
    setData(response)
}

  return (
    //kategorii nejde resetovat po otevreni srze kategorii... pridat field na reset
    <>
    <SearchBar searchValue={searchName} valueState= {setSearchName} handleSubmitMethod={handleSearch} />


      {isPending && <div>Loading...</div>}
      {data &&
        <div className='adsList'>
          <ul>
            {data.content.map(obj =>
                  <ObjectToTable object={obj} routValue={"ad"}/>
              )}
          </ul>
          <Pagination pages={data.totalPages} elements={data.totalElements} />
        </div>}
    </>
  )
}
