import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProjectManager from './components/ProjectManager';
import ProjectDetails from './components/ProjectDetails';
import Login from './components/Login';
import Register from './components/Register';
import 'semantic-ui-css/semantic.min.css';


function App() {
    return (
        <Router>
            <Routes>
                {/* Маршрут для страницы входа */}
                <Route path="/auth/login" element={<Login />} />

                {/* Маршрут для страницы регистрации */}
                <Route path="/auth/register" element={<Register />} />

                {/* Маршрут для управления проектами */}
                <Route path="/project" element={<ProjectManager />} />

                {/* Маршрут для просмотра деталей проекта */}
                <Route path="/get/:id" element={<ProjectDetails />} />
            </Routes>
        </Router>
    );
}

export default App;
