import React from 'react'
import { useState, useEffect } from 'react';

export const CategoryPage = () => {
  const url = ('http://localhost:8080/advent/api/cat/');

  const [name, setName] = useState("");
  const [message, setMessage] = useState("")
  const [data, setData] = useState("")
  const [load, setLoad] = useState(false)

  const deleteItemByIdFromUrl = (category, removeUrl) => {
    console.log(removeUrl)
    fetch(removeUrl + category.id, { method: 'DELETE' })
      .then(() => setLoad(true))
      .catch(err => setMessage(err));
  }

  const addNewObject = (postUrl, body) => {
    fetch(postUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    })
      .then((res) => res.json())
      .then((res) => {
        if (res.hasOwnProperty("error")) {
          setMessage(res.error)
        } else {
          setLoad(true)
          setName('')
          setMessage(res.name + "created!")
        }
      })
      .catch((error) => {
        setMessage('Error: ' + error);
      });
  }

  useEffect(() => {
    setLoad(false)
    fetch(url)
      .then(res => res.json())
      .then((data) => {
        console.log(data)
        setData(data)
      })
  }, [load])


  return (
    <>
      <div>
        <div>All categories</div>
        {data &&
          <ul className='itemList'>{
            data.content.map(category =>
              <li key={category.id} onClick={() => deleteItemByIdFromUrl(category, url)}>{category.id + " - " + category.name}
                <button>Remove</button>
              </li>)
          }
          </ul>
        }
      </div>
      <div>
        <input type="text" value={name} placeholder="Name" onChange={(e) => setName(e.target.value)}/>
        <button onClick={() => addNewObject(url, name)}>Create</button>
      </div>
      <p>{message}</p>
    </>
  )
}
