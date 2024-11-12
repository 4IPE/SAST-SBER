// ProjectDetails.js
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

function ProjectDetails() {
    const { id } = useParams();
    const [project, setProject] = useState(null);

    useEffect(() => {
        fetchProjectDetails();
    }, []);

    const fetchProjectDetails = async () => {
        try {
            const response = await axios.get(`/project/get/${id}`);
            setProject(response.data);
        } catch (error) {
            console.error('Ошибка при загрузке информации о проекте:', error);
        }
    };

    if (!project) return <p>Загрузка...</p>;

    return (
        <div style={{ padding: '20px' }}>
            <h2>{project.name}</h2>
            <h3>Список отчетов:</h3>
            <ul>
                {project.reports.map((report, index) => (
                    <li key={index}>{report}</li>
                ))}
            </ul>
        </div>
    );
}

export default ProjectDetails;
