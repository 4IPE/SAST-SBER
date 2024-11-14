// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProjectManager from './components/ProjectManager';
import ProjectDetails from './components/ProjectDetails';
import Auth from './components/Auth';
import Registration from './components/Registration';

function App() {
    return (
        <Router>
            <Routes>
                {/* Маршрут для страницы входа */}
                <Route path="/auth" element={<Auth />} />

                {/* Маршрут для страницы регистрации */}
                <Route path="/registration" element={<Registration />} />

                {/* Маршрут для управления проектами */}
                <Route path="/get" element={<ProjectManager />} />

                {/* Маршрут для просмотра деталей проекта */}
                <Route path="/get/:id" element={<ProjectDetails />} />
            </Routes>
        </Router>
    );
}

export default App;
