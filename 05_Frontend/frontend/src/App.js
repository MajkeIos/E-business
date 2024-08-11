import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Products from './components/Products';
import Basket from './components/Basket';
import Payment from './components/Payment';
import './App.css';

function App() {
    return (
        <Router>
            <div>
                <h1>Shop</h1>
                <Routes>
                    <Route path="/" element={<Products />} />
                    <Route path="/basket" element={<Basket />} />
                    <Route path="/payment" element={<Payment />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;