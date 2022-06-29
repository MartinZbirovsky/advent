import React from 'react'
import { useFetch } from "../CustomHook";
import { HomePageCat } from "./HomePageCatList";

export const HomePage = () => {
  const { data, isPending, error} = useFetch('http://localhost:8080/advent/api/cat');

  return (
    <>
      {error && <div> {error}</div>}
      {isPending && <div>Loading...</div>}
      {data && <HomePageCat data = {data}/>}
    </>
  )
}
