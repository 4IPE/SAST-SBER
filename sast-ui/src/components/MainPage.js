import React, { useState } from 'react';
import '../styles/MainPage.css';
import { useNavigate } from "react-router-dom";
import apiClient from "./config/apiClient";
import useUserData from "./config/useUserData";

const MainPage = () => {
    const [message, setMessage] = useState('');
    const [projectUrl, setProjectUrl] = useState('');
    const navigate = useNavigate();
    const { userStatus, user, loading } = useUserData();

    // Отображение загрузки, если данные еще не готовы
    if (loading) {
        return <p>Загрузка данных...</p>;
    }

    // Отображение ошибки, если пользователь не авторизован
    if (!userStatus) {
        return <p>Ошибка: пользователь не авторизован</p>;
    }

    const extractRepoName = (url) => {
        const repoRegex = /https?:\/\/(?:www\.)?github\.com\/[^\/]+\/([^\/]+)/;
        const match = url.match(repoRegex);
        return match ? match[1] : null; // Возвращает название репозитория или null, если не найдено
    };

    // Функция для добавления нового проекта
    const addProject = async () => {
        if (!projectUrl.trim()) {
            setMessage('Пожалуйста, введите URL проекта');
            return;
        }

        const repoName = extractRepoName(projectUrl);

        if (!repoName) {
            setMessage('Пожалуйста, введите корректный URL проекта: https://github.com/user/repository');
            return;
        }

        if (!user?.id) {
            setMessage('Ошибка: данные пользователя отсутствуют.');
            return;
        }

        try {
            const newProject = {
                name: repoName,
                url: projectUrl,
                userId: user.id,
            };

            await apiClient.post('/project/save', newProject, {
                headers: { 'Content-Type': 'application/json' },
            });

            setMessage('Проект добавлен успешно!');
            setProjectUrl('');
            setTimeout(() => setMessage(''), 3000); // Очистить сообщение через 3 секунды

        } catch (error) {
            setMessage('Ошибка добавления проекта: ' + (error.response?.data?.message || error.message));
        }
    };

    return (
        <>
            <header className="header">
                <div>
                </div>
                <h2 className="title">Управление проектами</h2>
                <div className="icon" onClick={() => navigate('/user/profile')}>
                    <i className="ui icon user"></i>
                </div>
            </header>

            <div className="project-manager-container">
                <div className="input-container">
                    <div className="input-with-button">
                        <input
                            type="text"
                            placeholder="Введите URL проекта"
                            value={projectUrl}
                            onChange={(e) => setProjectUrl(e.target.value)}
                        />
                        <button className="ui icon inverted green button" onClick={addProject}>
                            <i className="right arrow icon"></i>
                        </button>
                    </div>
                </div>
                {message && <p className="message">{message}</p>}
            </div>
        </>
    );
};

export default MainPage;
