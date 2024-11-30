import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import apiClient from "./config/axiosConfig";

const ProjectDetails = () => {
    const { id } = useParams(); // Получаем ID проекта из URL
    const [project, setProject] = useState(null);

    useEffect(() => {
        fetchProjectDetails();
    }, [id]);

    const fetchProjectDetails = async () => {
        try {
            const response = await apiClient.get('/project/${id}');
            setProject(response.data);
        } catch (error) {
            console.error('Ошибка при загрузке данных проекта:', error);
        }
    };

    if (!project) return <p>Загрузка данных...</p>;

    return (
        <div>
            <h2>Детали проекта</h2>
            <p>Название: {project.name}</p>
            <p>URL: {project.url}</p>
            <p>Пользователь ID: {project.userId}</p>
        </div>
    );
};

export default ProjectDetails;
