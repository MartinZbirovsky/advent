import React from 'react';
import { Outlet, NavLink } from 'react-router-dom';

export const Navbar = () => {
  return (
    <nav className="navbar">
      <ul className="links">
        <li><NavLink to="/">HOME</NavLink></li>
        <li><NavLink to="/ads">SHOW ADS</NavLink></li>
        <li><NavLink to="/addads">ADD NEW</NavLink></li>    
        <li><NavLink to="/users">USERS</NavLink></li>
        <li><NavLink to="/categories">CATEGORIES</NavLink></li>
        <li><NavLink to="/user">SETTING</NavLink></li>
        <li><NavLink to="/login">LOGIN</NavLink></li>
        <li><NavLink to="/register">REGISTER</NavLink></li>
      </ul>
      <Outlet />
    </nav>
  )
}
