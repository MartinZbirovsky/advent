import React from 'react';
import { useState } from 'react';
import { Input } from './utils/Input';

export const Register = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
  }

  return (
    <form className='registerForm' onSubmit={handleSubmit}>
      <Input idValue={"firstname"} impName={"First Name"} inpType={"text"} inpValue={firstName} inpState={setFirstName} />
      <Input idValue={"secondname"} impName={"Second Name"} inpType={"text"} inpValue={lastName} inpState={setLastName} />
      <Input idValue={"email"} impName={"Email"} inpType={"text"} inpValue={email} inpState={setEmail} />
      <Input idValue={"password"} impName={"Password"} inpType={"password"} inpValue={password} inpState={setPassword} />
      <input type="submit" value="Submit" />
    </form>
  )
}
