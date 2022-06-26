import { Routes ,Route } from 'react-router-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import { Navbar } from './Navbar';
import { HomePage } from './HomePage.js';
import { Footer } from './Footer';
import { Login } from './Login';
import { Register } from './Register';
import { AdsPage } from './AdsPage';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className='main'>
          <Routes >
            <Route index element={<HomePage/>} />
            <Route path="/ads" element={<AdsPage/>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />
          </Routes >
        </div>
      </div>
      <Footer />
    </Router>
  );
}

export default App;
//<Route path="/create" element={<Create/>} />  