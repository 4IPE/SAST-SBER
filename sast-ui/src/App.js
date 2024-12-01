import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './components/MainPage';
import Login from './components/Login';
import Register from './components/Register';
import 'semantic-ui-css/semantic.min.css';
import Profile from "./components/Profile";


function App() {
    return (
        <Router>
            <Routes>
                {/* Маршрут для страницы входа */}
                <Route path="/auth/login" element={<Login />} />

                {/* Маршрут для страницы регистрации */}
                <Route path="/auth/register" element={<Register />} />

                {/* Маршрут для управления проектами */}
                <Route path="/main" element={<MainPage />} />

                {/* Маршрут для просмотра деталей проекта */}
                <Route path="user/profile" element={<Profile />} />
            </Routes>
        </Router>
    );
}

export default App;
