import React from 'react'
import { useState } from 'react'
import { Input } from '../utils/Input';

export const Login = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [token, setToken] = useState(null);
  const [error, setError] = useState(null)

  const handleSubmit = e => {
    e.preventDefault();
    fetch('http://localhost:8080/advent/api/login', {
      method: 'post',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        'username': username,
        'password': password
      }),
    })
      .then(res => {
        if (!res.ok) {
          throw Error('Could not fleth data from server!')
        }
        setError(null)
        return res.json()
      })
      .then(data => {
        console.log(data.access_token)
        setToken(data.access_token);
      })
      .catch(err => {
        setToken(null)
        setError(err.message)
        console.log(err.message)
      })
  }

  return (
    <>
      <form className='loginForm' onSubmit={handleSubmit}>
        <Input idValue={"email"} impName={"Email"} inpType={"text"} inpValue={username} inpState={setUsername}/>
        <Input idValue={"password"} impName={"Password"} inpType={"password"} inpValue={password} inpState={setPassword}/>
        <input type="submit" value="Submit" />
      </form>
      {token && <p>{token}</p>}
      {error && <h2>{error}</h2>}
    </>
  )
}