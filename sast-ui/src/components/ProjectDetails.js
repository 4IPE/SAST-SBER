import React, {useEffect, useState} from 'react';
import '../styles/ProjectDetails.css'; // Подключим стили
import apiClient from './config/apiClient';
import {useNavigate} from "react-router-dom";

const ProjectDetails = () => {
    const [message, setMessage] = useState('');
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
            console.error('Ошибка при загрузке данных проекта: ', error);
        }
    };

    const addReport = async () => {
        try {
            await apiClient.post('/report/create', project, {
                headers: { 'Content-Type': 'application/json' },
            });

        } catch (error) {
            setMessage('Ошибка добавления проекта: ' + (error.response?.data?.message || error.message));
        }
    }


    if (!project) return <p className="error-message">Ошибка: данные проекта отсутствуют</p>;

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
                        <strong>Владелец:</strong> {project.userId}
                    </p>
                </div>

                <div>
                    <button className="ui icon green button" onClick={addReport}>
                        Создать отчет
                    </button>
                    {message && <p className="message">{message}</p>}
                </div>

                <div className="reports-container">
                    <h3 className="reports-title">Список отчетов</h3>
                    {reports.length > 0 ? (
                        <div className="reports-list">
                            {reports.map((report, index) => (
                                <div
                                    className="report-card"
                                    key={index}
                                    onClick={() => {
                                        sessionStorage.setItem('report', JSON.stringify(report));
                                        navigate(`/report`);
                                    }}>
                                    <h4 className="report-title">Отчет {index + 1}</h4>
                                    <p>Статус: {report.status}</p>
                                    <p className="report-date">
                                        <strong>Дата создания:</strong>{' '}
                                        {new Date(report.createdAt).toLocaleString()}
                                    </p>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <p className="no-reports-message">Отчеты отсутствуют</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default ProjectDetails;
