import { useState } from 'react'
import './App.css'
import Products from './components/product/Products'
import { Toaster } from 'react-hot-toast'
import { Route, Routes } from 'react-router-dom'
import Home from './Home/Home'
import NavBar from './components/common/NavBar'


function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div><Toaster /></div>
      <NavBar></NavBar>
      <Routes>
        <Route path="/" element={<Home></Home>}></Route>
        <Route path="/products" element={<Products></Products>}></Route>
        <Route path="/contact" element={<h1>Contact Us</h1>}></Route>
        <Route path="/account" element={<h1>My Account</h1>}></Route>
        <Route path="/cart" element={<h1>User Cart</h1>}></Route>
        <Route path="/login" element={<h1>Login Screen</h1>}></Route>

      </Routes>

    </>
  )
}

export default App
