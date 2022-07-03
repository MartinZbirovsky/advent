import React from 'react';
import { Outlet, Link } from 'react-router-dom';

export const Navbar = () => {
  return (
    <nav className="navbar">
      <ul className="links">
        <li><Link to="/">HOME</Link></li>
        <li><Link to="/ads">SHOW ADS</Link></li>
        <li><Link to="/addads">ADD NEW</Link></li>    
        <li><Link to="/users">USERS</Link></li>
        <li><Link to="/categories">CATEGORIES</Link></li>
        <li><Link to="/user">SETTING</Link></li>
        <li><Link to="/login">LOGIN</Link></li>
        <li><Link to="/register">REGISTER</Link></li>
      </ul>
      <Outlet />
    </nav>
  )
}
