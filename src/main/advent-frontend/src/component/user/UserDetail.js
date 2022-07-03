import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getData } from "../CustomHook";

export const UserDetail = () => {
  const baseUrl = 'http://localhost:8080/advent/api/user/'
  
  const { id } = useParams();
  const url = baseUrl + `${id}`
  const deleteUrl = baseUrl + `${id}`
  const banUrl = baseUrl + `ban/${id}`
  const unbanUrl = baseUrl + `unban/${id}`

  const [data, setData] = useState('')
  const [message, setMessage] = useState('')
  const [isPending, setIsPending] = useState(true)

  useEffect(() => {
    getUser(url)
  }, [url])

  async function getUser(reqUrl, reqType) {
    const response = await getData(reqUrl, reqType)
    setIsPending(false)
    setData(response)
  }

  const deleteUser = () => {
    getUser(deleteUrl, 'DELETE')
    setMessage("USER REMOVED")
  }

  const banUser = () => {
    getUser(banUrl)
    setMessage("Email is banned!")
  }

  const enableUser = () => {
    getUser(unbanUrl)
    setMessage("Email is unbanned!")
  }

  return (
    <div>
      {isPending && <div>Loading...</div>}
      {data &&
        <>
          <form>
            <label>First Name: </label>
            <p value={data.firstName} />
            <label>Email: </label>
            <p value={data.email} />
            <p>Is email confirm? {String(data.enabled)} </p>
            <p>Is banned? {String(data.locked)} </p>
          </form>
          <button onClick={deleteUser}>Remove User</button>  
          {data.locked ? <button onClick={enableUser}>Unban User</button> : <button onClick={banUser}>Ban User</button>}
          <p>{message}</p>
        </>
      }
    </div>
  )
}
