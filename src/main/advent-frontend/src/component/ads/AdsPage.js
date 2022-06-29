import React, { useEffect } from 'react'
import { AdsPageList } from './AdsPageList'
import { useFetch } from '../CustomHook'
import { useParams } from 'react-router-dom';
import { useState } from 'react';

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
    <>
      <form className='searchForm' onSubmit={handleSubmit}>
        <input name='choreDesc' type='text'  value={searchName} onChange={e => setSearchName(e.target.value)} />
        <input type="submit" value="Submit" />
      </form>
      {error && <div> {error}</div>}
      {isPending && <div>Loading...</div>}
      {data && <AdsPageList data={data} />}
    </>
  )
}
