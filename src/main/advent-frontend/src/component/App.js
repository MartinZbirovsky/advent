import { Routes ,Route } from 'react-router-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import { Navbar } from './Navbar';
import { HomePage } from './Home/HomePage.js';
import { Footer } from './Footer';
import { Login } from './user/Login';
import { Register } from './user/Register';
import { AdsPage } from './ads/AdsPage';
import { AdsDetail } from './ads/AdsDetail';
import NotFountMessage from './utils/NotFoundMessage';
import { AddAds } from './ads/AddAds';
import { UserPage } from './user/UserPage';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <main className='mainContent'>
          <Routes >
            <Route index element={<HomePage/>} />
            <Route path="/ad/:id" element={<AdsDetail/>} /> 
            <Route path="/ads" element={<AdsPage/>} /> 
            <Route path="/ads/:categoryId" element={<AdsPage/>} />
            <Route path="/addads" element={<AddAds/>} />
            <Route path="/users" element={<UserPage/>} /> 
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />
            <Route path="*" element={<NotFountMessage/>} />   
          </Routes >
        </main>
      </div>
      <Footer />
    </Router>
  );
}

export default App;
//<Route path="/create" element={<Create/>} />  