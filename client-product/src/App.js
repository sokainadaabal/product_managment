import React from 'react';
import './App.css';
import { BrowserRouter, Routes,Route } from 'react-router-dom';
import SignIn from './views/SignIn';
import Profile from './views/Profile'

function App() {
    const token = localStorage.getItem('accessToken');

    if(!token) {
        return <SignIn />
    }

    return (
        <div className="wrapper">
            <BrowserRouter>
                <Routes>
                    <Route path="/profile" element={<Profile />} />
                    <Route path="/" element={<Profile />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;