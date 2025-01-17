'use client'

import {useEffect, useState} from 'react'
import Link from 'next/link'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { FolderGit2, ChevronRight } from 'lucide-react'
import useUserData from "@/app/config/useUserData";

interface ProjectInfoDto {
    id: number;
    name: string;
    url: string;
}

export default function Projects() {
    const user = useUserData()
    const [projects, setProjects] = useState<ProjectInfoDto[]>([]);

    useEffect(() => {
        if (user && user.projects) {
            setProjects(Array.from(user.projects)); // Преобразуем Set в массив
        }
    }, [user]);

    if (!projects.length) {
        return (
            <div>
                <h1 className="text-4xl font-bold mb-8 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                    Проекты
                </h1>
                <p className="text-center text-lg">У вас пока нет добавленных проектов</p>
            </div>
        )
    }

    return (
        <div className="container mx-auto px-4 py-8 animate-fade-in">
            <h1 className="text-4xl font-bold mb-8 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                Проекты
            </h1>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {projects.map((project) => (
                    <Card key={project.id}
                          className="bg-card hover:bg-card-hover transition-all duration-300 transform hover:-translate-y-1">
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-xl font-bold text-text-primary">{project.name}</CardTitle>
                            <FolderGit2 className="h-5 w-5 text-text-secondary"/>
                        </CardHeader>
                        <CardContent>
                            <p className="text-sm text-text-secondary mb-4">
                                <Link href={project.url} target="_blank" className="text-primary hover:underline">
                                    {project.url}
                                </Link>
                            </p>
                            <Link href={`/projects/${project.id}`}>
                                <Button variant="outline" className="w-full group">
                                    Посмотреть отчеты
                                    <ChevronRight
                                        className="ml-2 h-4 w-4 transition-transform group-hover:translate-x-1"/>
                                </Button>
                            </Link>
                        </CardContent>
                    </Card>
                ))}
            </div>
        </div>
    )
}

