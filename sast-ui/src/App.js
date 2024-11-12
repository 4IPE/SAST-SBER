// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProjectManager from './ProjectManager';
import ProjectDetails from './ProjectDetails';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/get" element={<ProjectManager />} />
                <Route path="/get/:id" element={<ProjectDetails />} />
            </Routes>
        </Router>
    );
}

export default App;
