'use client';

import {useEffect, useState} from 'react';
import {useParams} from 'next/navigation';
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Badge} from "@/components/ui/badge";
import {CheckCircle, ChevronDown, ChevronUp, CircleDashed, CircleFadingArrowUp, XCircle} from 'lucide-react';
import {Button} from "@/components/ui/button";
import apiClient from "@/app/config/apiClient";
import {AxiosError} from "axios";
import Link from "next/link";

interface ReportOutDto {
    id: number,
    content: string,
    createdAt: string,
    projectId: number,
    status: string
}

interface ProjectOutDto {
    name: string,
    url: string,
    userId: number,
    id: number,
    reports: Set<ReportOutDto>
}

export default function ProjectReports() {
    const params = useParams();
    const [project, setProject] = useState<ProjectOutDto | undefined>(undefined);
    const [reports, setReports] = useState<ReportOutDto[]>([]);
    const [expandedReport, setExpandedReport] = useState<number | null>(null);
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetchProject();
    }, [params.id]);

    const fetchProject = async () => {
        try {
            const response = await apiClient.get(`/project/get/${params.id}`);
            const project = response.data;
            setProject(project)
            setReports(project.reports);

        } catch (error: unknown) {
            // Обработка ошибки
            if (error instanceof AxiosError) {
                console.error(error.response?.data); // Лог ответа с ошибкой
                setMessage('Ошибка при загрузке отчетов: ' + (error.response?.data?.message || error.message));
            } else {
                console.error(error);  // Лог для неизвестных ошибок
                setMessage('Неизвестная ошибка');
            }
        }
    };

    const getStatusIcon = (status: string) => {
        switch (status) {
            case 'NEW':
                return <CircleDashed className="h-5 w-5 text-blue-500"/>;
            case 'RUN':
                return <CircleFadingArrowUp className="h-5 w-5 text-yellow-500"/>;
            case 'DONE':
                return <CheckCircle className="h-5 w-5 text-green-500"/>;
            case 'ERROR':
                return <XCircle className="h-5 w-5 text-red-500"/>;
            default:
                return null;
        }
    };

    const toggleReport = (reportId: number) => {
        setExpandedReport(expandedReport === reportId ? null : reportId);
    };

    const handleCreateReport = async () => {
        try {
            const newProject = {
                id: project?.id,
                name: project?.name,
                url: project?.url,
                userId: 0
            };

            const response = await apiClient.post(`/report/create`, newProject, {
                headers: {'Content-Type': 'application/json'},
            });

            await fetchProject();
            setMessage('Отчет успешно создан!');
            setTimeout(() => setMessage(''), 3000);

        } catch (error: unknown) {
            if (error instanceof AxiosError) {
                console.error(error.response?.data);
                setMessage('Ошибка создания отчета: ' + (error.response?.data?.message || error.message));
            } else {
                console.error(error);
                setMessage('Неизвестная ошибка');
            }
        }
    };

    if (!project) {
        return (<p className="text-center text-lg">У вас нет доступа к данному проекту</p>)
    }

    if (reports.length === 0) {
        return (
            <div>
                <div className="flex items-center justify-between mb-2">
                    <h1 className="text-4xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                        {project.name}
                    </h1>
                    <Button
                        variant="default"
                        onClick={handleCreateReport}
                    >
                        Добавить отчет
                    </Button>
                </div>
                <Link href={project?.url} target="_blank" className="text-primary hover:underline mb-4 block">
                    {project.url}
                </Link>
                {message && <p className="mb-4 text-center text-red-500">{message}</p>}
                <p className="text-center text-lg">У данного проекта пока нет отчетов</p>
            </div>
        )
    }

    return (
        <div className="container mx-auto px-4 py-8 animate-fade-in">
            <div className="flex items-center justify-between mb-2">
                <h1 className="text-4xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                    {project.name}
                </h1>
                <Button
                    variant="default"
                    onClick={handleCreateReport}
                >
                    Добавить отчет
                </Button>
            </div>
            <Link href={project?.url} target="_blank" className="text-primary hover:underline mb-4 block">
                {project.url}
            </Link>
            {message && <p className="mb-4 text-center text-red-500">{message}</p>}
            <div className="space-y-6 mb">
                {reports.map((report, index) => (
                    <Card key={report.id} className="bg-card hover:bg-card-hover transition-all duration-300">
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-xl font-bold text-text-primary">
                                Отчет {reports.length - index}
                            </CardTitle>
                            {getStatusIcon(report.status)}
                        </CardHeader>
                        <CardContent>
                            <div className="flex justify-between items-center mb-4">
                                <div>
                                    <p className="text-sm text-text-secondary mb-2">
                                        Дата создания: {new Date(report.createdAt).toLocaleString()}</p>
                                </div>
                                <Badge variant={report.status as "default" | "secondary" | "destructive"}
                                       className="capitalize">
                                    {report.status}
                                </Badge>
                            </div>
                            <Button
                                variant="outline"
                                className="w-full flex justify-between items-center"
                                onClick={() => toggleReport(report.id)}
                            >
                                {expandedReport === report.id ? 'Скрыть детали' : 'Показать детали'}
                                {expandedReport === report.id ? <ChevronUp className="h-4 w-4"/> :
                                    <ChevronDown className="h-4 w-4"/>}
                            </Button>
                            {expandedReport === report.id && (
                                <div className="mt-4 p-4 bg-background rounded-md animate-slide-down">
                                    <p className="text-text-primary"
                                       dangerouslySetInnerHTML={{__html: report.content}}>
                                    </p>
                                </div>
                            )}
                        </CardContent>
                    </Card>
                ))}
            </div>
        </div>
    );
}
