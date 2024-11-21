import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import '../styles/ProjectManager.css';

const ProjectManager = () => {
    const [projects, setProjects] = useState([]);
    const [projectUrl, setProjectUrl] = useState('');
    const [message, setMessage] = useState('');

    // Получаем список проектов при загрузке компонента
    useEffect(() => {
        fetchProjects();
    }, []);

    // Функция для загрузки проектов
    const fetchProjects = async () => {
        try {
            const response = await axios.get('http://localhost:8080/project/get?id=1&from=0&size=5'); // Подставьте корректный ID пользователя
            setProjects(response.data);
        } catch (error) {
            console.error('Ошибка при загрузке проектов:', error);
        }
    };

    // Функция для добавления нового проекта
    const addProject = async () => {
        try {
            const newProject = { name: 'Новый проект', url: projectUrl, userId: 1 }; // Примерные данные проекта
            await axios.post('http://localhost:8080/project/save', newProject, {
                headers: { 'Content-Type': 'application/json' },
            });
            setMessage('Проект добавлен успешно!');
            setProjectUrl('');
            fetchProjects(); // Обновляем список проектов
        } catch (error) {
            setMessage('Ошибка добавления проекта: ' + error.message);
        }
    };

    return (
        <div className="project-manager-container">
            <h2>Управление проектами</h2>
            <div className="input-container">
                <input
                    type="text"
                    placeholder="Введите URL проекта"
                    value={projectUrl}
                    onChange={(e) => setProjectUrl(e.target.value)}
                />
                <button onClick={addProject}>Добавить проект</button>
            </div>
            {message && <p className="message">{message}</p>}
            <h3>Недавние проекты</h3>
            <ul>
                {projects.map((project) => (
                    <li key={project.id}>
                        <Link to={`/get/${project.id}`}>{project.name}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProjectManager;
