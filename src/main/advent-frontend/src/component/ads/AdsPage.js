import React, { useEffect } from 'react'
import { ListObjectToTable } from '../utils/ListObjectToTable';
import { useFetch } from '../CustomHook'
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { Input } from '../utils/Input';

export const AdsPage = () => {
  const { categoryId = "" } = useParams();
  const baseUrl = `http://localhost:8080/advent/api/ads/?categoryId=${categoryId}`

  const [url, setUrl] = useState(baseUrl)
  const [searchName, setSearchName] = useState('')

  const { data, isPending, error } = useFetch(url)

  const handleSubmit = (e) => {
    e.preventDefault();
    setUrl(baseUrl + `&adName=${searchName}`)
  }

  useEffect(() => {
  }, [url])

  return (
    //kategorii nejde resetovat po otevreni srze kategorii... pridat field na reset
    <>
      <form className='searchForm' onSubmit={handleSubmit}>
        <Input idValue={"searchField"} impName={"Search"} inpType={"text"} inpValue={searchName} inpState={setSearchName} />
        <input type="submit" value="Submit" />
      </form>
      {error && <div> {error}</div>}
      {isPending && <div>Loading...</div>}
      {data && <ListObjectToTable data={data} />}
    </>
  )
}
