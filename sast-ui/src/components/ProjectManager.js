import React, {useState} from 'react';
import axios from 'axios';
import '../styles/ProjectManager.css';

const ProjectManager = () => {
    const [message, setMessage] = useState('');
    const [projectUrl, setProjectUrl] = useState('');
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

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


            await axios.post('http://localhost:8080/project/save', newProject, {
                headers: {'Content-Type': 'application/json', 'Authorization' : 'Bearer ' + token},
            });

            setMessage('Проект добавлен успешно!');
            setProjectUrl('');
        } catch (error) {
            setMessage('Ошибка добавления проекта: ' + error.message);
        }
    };

    return (
        <div className="project-manager-container">
            <h2>Управление проектами</h2>
            <div className="input-container">
                <div className="input-with-button">
                    <input
                        type="text"
                        placeholder="Введите URL проекта"
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
    );
};

export default ProjectManager;
