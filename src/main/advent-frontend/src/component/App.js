import { Routes ,Route } from 'react-router-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import { Navbar } from './Navbar';
import { HomePage } from './home/HomePage';
import { Footer } from './Footer';
import { Login } from './user/Login';
import { Register } from './user/Register';
import { AdsList } from './ads/AdsList';
import { AdsDetail } from './ads/AdsDetail';
import NotFountMessage from './utils/NotFoundMessage';
import { AddAds } from './ads/AddAds';
import { UserList } from './user/UserList';
import { useState } from 'react';
import { UserSetting } from './user/UserSetting';
import { CategoryPage } from './category/CategoryPage'
import { UserDetail } from './user/UserDetail';

function App() {
  const [token, setToken] = useState()

  return (
    <Router>
      <div className="app">
        <Navbar />
        <main className='mainContent'>
          <Routes >
            <Route index element={<HomePage/>} />
            <Route path="/ad/:id" element={<AdsDetail/>} /> 
            <Route path="/ads" element={<AdsList/>} /> 
            <Route path="/ads/:categoryId" element={<AdsList/>} />
            <Route path="/addads" element={<AddAds/>} />
            <Route path="/users" element={<UserList/>} /> 
            <Route path="/categories" element={<CategoryPage/>} /> 
            <Route path="/user" element={<UserSetting/>} /> 
            <Route path="/userdetail/:id" element={<UserDetail/>} /> 
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />
            <Route path="*" element={<NotFountMessage/>} />   
          </Routes >
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
//<Route path="/create" element={<Create/>} />  