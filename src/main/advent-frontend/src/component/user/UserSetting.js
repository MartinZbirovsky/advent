import { useState, useEffect } from "react"
import { getData } from "../CustomHook";

export const UserSetting = ({userId = 14}) => {
 // const isUserAdmin = true
  const url = `http://localhost:8080/advent/api/user/${userId}`
  const [data, setData] = useState('')
  const [isPending, setIsPending] = useState(true)

  useEffect(() => {
      getCategory(url)
    }, [url])

  async function getCategory(reqUrl) {
      const response = await getData(reqUrl)
      setIsPending(false)
      setData(response)
  }
  return (
    <div>
      {isPending && <p>Loading...</p>}
      {data && 
      <form>
        <label>{data.firstName}</label>
        <input/>
      </form>
      }
      {!data && !isPending && <p>Something happen - missing user</p>}
    </div> 
  )
}
