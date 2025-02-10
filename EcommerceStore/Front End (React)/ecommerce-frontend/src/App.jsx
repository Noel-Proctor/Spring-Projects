import { useState } from 'react'
import './App.css'
import Products from './components/product/Products'
import { Toaster } from 'react-hot-toast'


function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div><Toaster /></div>
      <Products></Products>
    </>
  )
}

export default App
