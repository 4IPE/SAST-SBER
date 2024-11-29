import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/Profile.css';
import '../styles/Header.css';
import {useNavigate} from "react-router-dom";

const Profile = () => {
    const [projects, setProjects] = useState([]);
    const userId = sessionStorage.getItem('userId');
    const token = sessionStorage.getItem('token');
    const navigate = useNavigate();
    const PROJECT_URL = 'http://localhost:8080/project';

    useEffect(() => {
        if (userId) {
            fetchProjects();
        }
    }, []);

    const fetchProjects = async () => {
        try {
            const response = await axios.get(
                `${PROJECT_URL}/get?id=${userId}&from=1&size=10`,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + token,
                    },
                }
            );
            setProjects(response.data); // Устанавливаем список проектов
        } catch (error) {
            console.error('Ошибка при загрузке данных проектов:', error);
        }
    };

    if (!userId) {
        return <p>Ошибка: пользователь не авторизован</p>;
    }

    if (!projects.length) {
        return <p>Загрузка данных...</p>; // Если список пуст или еще не загружен
    }

    return (
        <>
            <header className="header">
                <div className="icon" onClick={() => navigate('/main')}>
                    <i className="ui icon arrow left"></i>
                </div>
                <div className="icon" onClick={() => navigate('/profile')}>
                    <i className="ui icon user"></i>
                </div>
            </header>

            <div className="projects-container">
                <h2 className="title">Список проектов пользователя</h2>
                <div className="projects-list">
                    {projects.map((project, index) => (
                        <div className="project-card" key={index}>
                            <h3 className="project-name">
                                {project.name}
                            </h3>
                            <p className="project-url">
                                <a href={project.url} target="_blank" rel="noopener noreferrer">
                                    {project.url}
                                </a>
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        </>
    );

};

export default Profile;
