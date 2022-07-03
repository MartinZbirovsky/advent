import React, { useState } from 'react';
import { Input } from '../utils/Input';
import { Link } from 'react-router-dom';
import { InputSpread } from '../utils/InputSpread';
import { postData } from '../CustomHook';

export const Register = () => {
  const url = 'http://localhost:8080/advent/api/users/registration'

  const [user, setUser] = useState({ firstName: "", lastName: "", email: "", password: "" });
  const [passwordRe, setPasswordRe] = useState("");
  const [message, setMessage] = useState("")
  const [isCreated, setIsCreated] = useState(false)

  async function handleAddnew(e) {
    e.preventDefault()
    
    if (user.password === passwordRe) {
      const response = await postData(url, user)

      if (response.hasOwnProperty("error")) {
        setMessage(response.error)
      } else {
        setIsCreated('true')
      }
    } else {
      setMessage("Passwords must be the same")
    }
  }

  return (
    <>
      {!isCreated ? <div>
        <form className='registerForm' onSubmit={e => handleAddnew(e)}>
          <lable>First name</lable>
          <InputSpread object={user} setObjState={setUser} propertyName={"firstName"} />
          <lable>Last Name</lable>
          <InputSpread object={user} setObjState={setUser} propertyName={"lastName"} />
          <lable>Email</lable>
          <InputSpread object={user} setObjState={setUser} propertyName={"email"} />
          <lable>Password</lable>
          <InputSpread object={user} setObjState={setUser} propertyName={"password"} inputType="password" />
          <Input required idValue={"passwordRe"} impName={"Password Rewrite"} inpType={"password"} inpValue={passwordRe} inpState={setPasswordRe} />
          <input type="submit" value="Submit" />
        </form>
        <p>{message}</p>
      </div>
        :
        <div>
          <h1>WELCOME {user.firstName + " " + user.lastName}</h1>
          <p>pls confirm your email: {user.email}</p>
          <h3><Link to="/login">LOGIN</Link></h3>
        </div>}
    </>
  )
}