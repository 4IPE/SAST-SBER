import React, { useState, useEffect } from 'react';
import '../styles/Profile.css';
import '../styles/Header.css';
import {useNavigate} from "react-router-dom";
import useUserData from "./config/useUserData";

const Profile = () => {
    const navigate = useNavigate();
    const {userStatus, user, loading} = useUserData();
    const [projects, setProjects] = useState([]);

    useEffect(() => {
        const fetchUserInfo = async () => {
            setProjects(user.projects);

        };
        fetchUserInfo();
    }, []);

    if (!userStatus) {
        return <p>Ошибка: пользователь не авторизован</p>;
    }

    if (!projects.length) {
        return <p>Загрузка данных...</p>;
    }

    return (
        <>
            <header className="header">
                <div className="icon" onClick={() => navigate('/main')}>
                    <i className="ui icon arrow left"></i>
                </div>
                <div className="icon" onClick={() => navigate('/user/profile')}>
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
