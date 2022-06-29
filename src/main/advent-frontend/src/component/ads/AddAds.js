import React, { useState } from 'react'
import { Input } from '../utils/Input';

export const AddAds = () => {
  const [adName, setAdName] = useState('')
  const [data, setData] = useState()

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch('http://localhost:8080/advent/api/ads', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ name: adName })
    })
      .then(res => {
        return res.json()
      })
      .then(data => {
        setData(data)
        console.log(data)
      })
  }
  return (<>
    {data && <div>{JSON.stringify(data.name)}</div>}
    <form className='loginForm' onSubmit={handleSubmit}>
      <Input idValue={"name"} impName={"Advertisment Name"} inpType={"text"} inpValue={adName} inpState={setAdName}/>
      <input type="submit" value="Submit" />
    </form>
  </>
  )
}
