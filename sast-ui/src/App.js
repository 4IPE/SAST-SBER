import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './components/MainPage';
import Login from './components/Login';
import Register from './components/Register';
import 'semantic-ui-css/semantic.min.css';
import Profile from "./components/Profile";
import ProjectDetails from "./components/ProjectDetails";


function App() {
    return (
        <Router>
            <Routes>
                {/* Маршрут для страницы входа */}
                <Route path="/auth/login" element={<Login />} />

                {/* Маршрут для страницы регистрации */}
                <Route path="/auth/register" element={<Register />} />

                {/* Маршрут для главной страницы */}
                <Route path="/" element={<MainPage />} />

                {/* Маршрут для просмотра профиля */}
                <Route path="user/profile" element={<Profile />} />

                {/* Маршрут для просмотра детальной информации о проекте */}
                <Route path="report/get" element={<ProjectDetails />} />
            </Routes>
        </Router>
    );
}

export default App;
