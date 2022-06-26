import React from 'react';
import { Outlet, Link } from 'react-router-dom';

export const Navbar = () => {
  return (
    <nav className="navbar">
      <ul className="links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/ads">Show Ads.</Link></li>
        <li><Link to="/login">Login</Link></li>
        <li><Link to="/register">Register</Link></li>
      </ul>
      <Outlet />
    </nav>
  )
}
