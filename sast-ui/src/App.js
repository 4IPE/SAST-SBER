import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import 'semantic-ui-css/semantic.min.css';
import Login from './components/Login';
import Register from './components/Register';
import MainPage from './components/MainPage';
import Profile from "./components/Profile";
import ProjectDetails from "./components/ProjectDetails";
import ReportDetails from "./components/ReportDetails";


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
                <Route path="project" element={<ProjectDetails />} />

                {/* Маршрут для просмотра детальной информации об отчете */}
                <Route path="report" element={<ReportDetails />} />
            </Routes>
        </Router>
    );
}

export default App;
