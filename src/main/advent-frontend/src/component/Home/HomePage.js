import React, { useState } from 'react'
import { getData } from "../CustomHook";
import { HomeCatList } from '../category/HomeCatList';
import { useEffect } from 'react';

export const HomePage = () => {
  const url = 'http://localhost:8080/advent/api/cat'
  const [dat, setDat] = useState('')
  const [isPending, setIsPending] = useState(true)
 
  useEffect(() => {
    getCategory()
  }, [])

  async function getCategory() {
      const response = await getData(url)
      setIsPending(false)
      setDat(response)
  }

  return (
    <>
      {isPending && <div>Loading...</div>}
      {dat &&
        <>
          <div>Chose Category</div>
          <HomeCatList data={dat} />
        </>
      }
    </>
  )
}
