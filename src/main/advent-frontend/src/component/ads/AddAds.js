import React, { useState } from 'react'
import { InputSpread } from '../utils/InputSpread';
import { postData } from '../CustomHook';

export const AddAds = () => {
  const url = 'http://localhost:8080/advent/api/ads'

  const [message, setMessage] = useState('')
  const [ads, setAds] = useState({
    name: '',
    description: '',
    requirements: '',
    companyOffer: '',
    salaryFrom: '',
    salaryTo: '',
    officePlace: ''
  })

  async function handleAddnew(e) {
    e.preventDefault()

    const response = await postData(url, ads)
    if (response.hasOwnProperty("error")) {
      setMessage(response.error)
    } else {
      console.log(response)
      setMessage(response.name + ' created')
    }
  }

  return (
    <>
      {!message && <form className='loginForm' onSubmit={e => handleAddnew(e)}>
        <lable>Ads. name</lable>
        <InputSpread object={ads} setObjState={setAds} propertyName={"name"} />
        <input type="submit" value="Submit" />
      </form>
      }
      {message && <button onClick={() => setMessage('')}>Create next</button>}
      <p>{message}</p>
    </>
  )
}
