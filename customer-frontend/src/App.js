// import logo from './logo.svg';
import {Routes, Route} from 'react-router-dom';
import './App.css';
import Login from './pages/login/login';
import Home from './pages/home/home';
import AddCustomer from './pages/AddCustomer/add';
import UpdateCustomer from './pages/UpdateCustomer/updateCustomer';

function App() {
  return (
    <>
    <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/home" element={<Home/>}/>
        <Route path='/add' element={<AddCustomer/>}/>
        <Route path="/update/:customerId" element={<UpdateCustomer />} />

    </Routes>
    </>
  );
}

export default App;
