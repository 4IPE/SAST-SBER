import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './ProjectManager.css';

function ProjectManager() {
    const [url, setUrl] = useState('');
    const [projects, setProjects] = useState([]);

    useEffect(() => {
        fetchProjects();
    }, []);

    const fetchProjects = async () => {
        try {
            const userId = 1; // Замените на реальный ID пользователя
            const response = await axios.get(`/project/get?id=${userId}&from=0&size=5`);
            setProjects(response.data);
        } catch (error) {
            console.error("Ошибка при загрузке проектов:", error);
        }
    };

    const addProject = async () => {
        if (!url.trim()) return;
        try {
            const project = { name: "Новый проект", url, userId: 1 };
            await axios.post('/project/save', project);
            fetchProjects();
            setUrl('');
        } catch (error) {
            console.error("Ошибка при добавлении проекта:", error);
        }
    };

    return (
        <div className="container">
            <div className="input-container">
                <input
                    type="text"
                    placeholder="Введите URL проекта"
                    value={url}
                    onChange={(e) => setUrl(e.target.value)}
                    className="input"
                />
                <button onClick={addProject} className="button">
                    Добавить
                </button>
            </div>

            <h2>Недавние проекты</h2>
            <ul className="project-list">
                {projects.map((project) => (
                    <li key={project.userId} className="project-item">
                        <Link to={`/get/${project.userId}`} className="project-link">
                            <strong>{project.name}</strong> — {project.url}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ProjectManager;
