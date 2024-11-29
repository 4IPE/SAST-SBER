import React, {useState} from 'react';
import axios from 'axios';
import '../styles/ProjectManager.css';
import '../styles/Header.css';
import {useNavigate} from "react-router-dom";

const ProjectManager = () => {
    const [message, setMessage] = useState('');
    const [projectUrl, setProjectUrl] = useState('');
    const userId = sessionStorage.getItem('userId');
    const token = sessionStorage.getItem('token');
    const navigate = useNavigate();
    const PROJECT_URL = 'http://localhost:8080/project';

    const extractRepoName = (url) => {
        const repoRegex = /https?:\/\/(?:www\.)?github\.com\/[^\/]+\/([^\/]+)/;
        const match = url.match(repoRegex);
        return match ? match[1] : null; // Возвращает название репозитория или null, если не найдено
    };

    // Проверяем, установлен ли userId
    if (!userId) {
        return <p>Ошибка: пользователь не авторизован</p>;
    }

    // Функция для добавления нового проекта
    const addProject = async () => {

        const repoName = extractRepoName(projectUrl);

        if (!repoName) {
            setMessage('Пожалуйста, введите корректный URL проекта: https://github.com/user/repository');
            return;
        }

        try {
            const newProject = {
                name: repoName,
                url: projectUrl,
                userId: userId,
            };


            await axios.post(`${PROJECT_URL}/save`, newProject, {
                headers: {'Content-Type': 'application/json', 'Authorization' : 'Bearer ' + token},
            });

            setMessage('Проект добавлен успешно!');
            navigate('/profile');
            setProjectUrl('');
        } catch (error) {
            setMessage('Ошибка добавления проекта: ' + error.message);
        }
    };

    return (
        <>
            <header className="header">
                <div className="icon">
                </div>
                <div className="icon" onClick={() => navigate('/profile')}>
                    <i className="ui icon user"></i>
                </div>
            </header>

            <div className="project-manager-container">
                <h2 className="title">Добавить проект</h2>
                <div className="input-container">
                    <div className="input-with-button">
                        <input
                            type="text"
                            placeholder="Введите URL вашего GitHub проекта"
                            value={projectUrl}
                            onChange={(e) => setProjectUrl(e.target.value)}
                        />
                        <button className="ui icon inverted green button"
                                onClick={addProject}>
                            <i className="right arrow icon"></i>
                        </button>
                    </div>
                </div>
                {message && <p className="message">{message}</p>}
            </div>
        </>
    );
};

export default ProjectManager;
