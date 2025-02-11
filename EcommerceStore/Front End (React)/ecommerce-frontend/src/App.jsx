import { useState } from 'react'
import './App.css'
import Products from './components/product/Products'
import { Toaster } from 'react-hot-toast'
import { Route, Routes } from 'react-router-dom'
import Home from './Home/Home'


function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div><Toaster /></div>
      <Routes>
        <Route path="/" element={<Home></Home>}></Route>
        <Route path="/products" element={<Products></Products>}></Route>
      </Routes>

    </>
  )
}

export default App
