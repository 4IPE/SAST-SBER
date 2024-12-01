import React, { useState, useEffect } from 'react';
import '../styles/ProjectDetails.css'; // Подключим стили
import apiClient from './config/apiClient';
import {useNavigate} from "react-router-dom";

const ProjectDetails = () => {
    const [reports, setReports] = useState([]);
    const project = JSON.parse(sessionStorage.getItem('project'));
    const navigate = useNavigate()

    useEffect(() => {
        if (project) {
            fetchProjectDetails();
        }
    }, []);

    const fetchProjectDetails = async () => {
        try {
            const response = await apiClient.get(`/report/get/${project.id}`);
            setReports(response.data);
        } catch (error) {
            console.error('Ошибка при загрузке данных проекта:', error);
        }
    };

    if (!project) return <p className="error-message">Ошибка: данные проекта отсутствуют.</p>;

    return (
        <>
            <header className="header">
                <div className="icon" onClick={() => navigate(-1)}>
                    <i className="ui icon arrow left"></i>
                </div>
                <h2 className="title">Управление проектами</h2>
                <div className="icon" onClick={() => navigate('/user/profile')}>
                    <i className="ui icon user"></i>
                </div>
            </header>

            <div className="project-details-container">
                <div className="project-header">
                    <h2 className="project-title">{project.name}</h2>
                    <p>
                        <strong>URL:</strong>{' '}
                        <a href={project.url} target="_blank" rel="noopener noreferrer" className="project-link">
                            {project.url}
                        </a>
                    </p>
                    <p>
                        <strong>Пользователь:</strong> {project.userId}
                    </p>
                </div>

                <div className="reports-container">
                    <h3 className="reports-title">Список репортов</h3>
                    {reports.length > 0 ? (
                        <div className="reports-list">
                            {reports.map((report, index) => (
                                <div className="report-card" key={index}>
                                    <h4 className="report-title">Отчет {index + 1}</h4>
                                    <div
                                        dangerouslySetInnerHTML={{__html: report.content}}
                                        className="report-content"
                                    />
                                    <p className="report-date">
                                        <strong>Дата создания:</strong>{' '}
                                        {new Date(report.createdAt).toLocaleString()}
                                    </p>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <p className="no-reports-message">Репорты отсутствуют</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default ProjectDetails;
