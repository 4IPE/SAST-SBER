import React, { useState, useEffect } from 'react';
import '../styles/Profile.css';
import '../styles/Header.css';
import { useNavigate } from 'react-router-dom';
import useUserData from './config/useUserData';

const Profile = () => {
    const navigate = useNavigate();
    const { userStatus, user, loading } = useUserData();
    const [projects, setProjects] = useState([]);

    useEffect(() => {
        if (user && user.projects) {
            setProjects(user.projects);
        }
    }, [user]);  // обновляем список проектов при изменении user

    // Отображение загрузки, если данные еще не готовы
    if (loading) {
        return <p>Загрузка данных...</p>;
    }

    // Отображение ошибки, если пользователь не авторизован
    if (!userStatus) {
        return <p>Ошибка: пользователь не авторизован</p>;
    }

    // Если проекты не найдены
    if (projects.length === 0) {
        return <p>У пользователя нет проектов.</p>;
    }

    return (
        <>
            <header className="header">
                <div className="icon" onClick={() => navigate(-1)}>
                    <i className="ui icon arrow left"></i>
                </div>
                <h2 className="title">Список проектов пользователя</h2>
                <div className="icon" onClick={() => navigate('/user/profile')}>
                    <i className="ui icon user"></i>
                </div>
            </header>

            <div className="projects-container">
                <div className="projects-list">
                    {projects.map((project, index) => (
                        <div
                            className="project-card"
                            key={index}
                            onClick={() => {
                                sessionStorage.setItem('project', JSON.stringify(project));
                                navigate(`/report/get`);
                            }}>
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
