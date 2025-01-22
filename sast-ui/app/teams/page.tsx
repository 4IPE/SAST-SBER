'use client'

import {useEffect, useState} from 'react'
import Link from 'next/link'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Users2, ChevronRight, FolderGit2 } from 'lucide-react'
import apiClient from "@/app/config/apiClient";
import useUserData from "@/app/config/useUserData";

interface Team {
    id: number,
    name: string,
    project: {
        id: number,
        name: string
    }
}

export default function Teams() {
    const user = useUserData();
    const [teams, setTeams] = useState<Team[]>([]);
    const [message, setMessage] = useState('')

    useEffect(() => {
        if (!user?.id) return;

        const fetchTeams = async () => {
            try {
                const response = await apiClient.get(`/team/get-all/${user.id}?from=1&size=5`);
                setTeams(response.data);
            } catch (error) {
                console.error('Ошибка получения данных:', error);
                setMessage('Неизвестная ошибка');
            }
        };

        fetchTeams();
    }, [user?.id]);



    if (!teams.length) {
        return (
            <div>
                <h1 className="text-4xl font-bold mb-8 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                    Команды
                </h1>
                <p className="text-center text-lg">У вас пока нет добавленных команд</p>
                {message && <p className="mb-4 text-center text-red-500">{message}</p>}
            </div>
        )
    }

    return (
        <div className="container mx-auto px-4 py-8 animate-fade-in">
            <h1 className="text-4xl font-bold mb-8 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                Команды
            </h1>
            {message && <p className="mb-4 text-center text-red-500">{message}</p>}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {teams.map((team) => (
          <Card key={team.id} className="bg-card hover:bg-card-hover transition-all duration-300 transform hover:-translate-y-1">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-xl font-bold text-text-primary">{team.name}</CardTitle>
              <Users2 className="h-5 w-5 text-text-secondary" />
            </CardHeader>
            <CardContent>
              <p className="text-sm text-text-secondary mb-1">Количество участников: ДОБАВИТЬ</p>
              <p className="text-sm text-text-secondary mb-4">Проект: {team.project.name}</p>
              <div className="flex space-x-2">
                <Link href={`/teams/${team.id}`} className="flex-1">
                  <Button variant="outline" className="w-full group">
                    Посмотреть команду
                    <ChevronRight className="ml-2 h-4 w-4 transition-transform group-hover:translate-x-1" />
                  </Button>
                </Link>
                <Link href={`/projects/${team.project.id}`} className="flex-1">
                  <Button variant="outline" className="w-full group">
                    Посмотреть проект
                    <FolderGit2 className="ml-2 h-4 w-4" />
                  </Button>
                </Link>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  )
}

