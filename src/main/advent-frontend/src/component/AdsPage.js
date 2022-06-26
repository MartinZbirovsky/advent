import React from 'react'
import { AdsPageList } from './AdsPageList'
import useFetch from './CustomHook'

export const AdsPage = () => {
  const { data, isPending, error } = useFetch('http://localhost:8080/advent/api/ads/')

  return (
    <>
      {error && <div> {error}</div>}
      {isPending && <div>Loading...</div>}
      {data && <AdsPageList data={data} />}
    </>
  )
}
