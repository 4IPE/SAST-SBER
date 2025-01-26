'use client'

import {useEffect, useState} from 'react'
import {useParams, useRouter} from 'next/navigation'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import apiClient from "@/app/config/apiClient";
import {AxiosError} from "axios";
import {router} from "next/client";
import {useNavigate} from "react-router";
import useUserData from "@/app/config/useUserData";
import {CheckCircle, Crown, UserRoundPlus, UserRoundX} from "lucide-react";
import {Input} from "@/components/ui/input";

interface Team {
    id: number,
    name: string,
    project: Project,
    teammates: Set<User>,
    token: string
}

interface Project {
    name: string,
    ownerId: number
}

interface User {
    id: number,
    username: string,
    email: string
}

export default function TeamDetails() {
    const params = useParams();
    const user = useUserData();
    const [team, setTeam] = useState<Team>();
    const [teammates, setTeammates] = useState<User[]>([]);
    const [message, setMessage] = useState('');
    const [showForm, setShowForm] = useState(false);
    const [usernameToAdd, setUsernameToAdd] = useState('');
    const router = useRouter();

    useEffect(() => {
        fetchTeam();
    }, [params.id]);

    const fetchTeam = async () => {
        try {
            const response = await apiClient.get(`/team/get/${params.id}`);
            const team = response.data;
            setTeam(team)
            setTeammates(team.teammates)
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

    const handleAddUser = async () => {
        if (!usernameToAdd) {
            return;
        }

        try {
            const response = await apiClient.post(`/team/add?username=${usernameToAdd}&teamId=${params.id}`);
            setShowForm(false);
            setUsernameToAdd('');
            setMessage(`Пользователь ${usernameToAdd} добавлен!`);
            setTimeout(() => setMessage(''), 3000);
            await fetchTeam();
        } catch (error: unknown) {
            setShowForm(false);
            setUsernameToAdd('');
            if (error instanceof AxiosError) {
                console.error(error.response?.data);
                setMessage('Ошибка при добавлении пользователя: ' + (error.response?.data?.message || error.message));
                setTimeout(() => setMessage(''), 3000);

            } else {
                console.error(error);  // Лог для неизвестных ошибок
                setMessage('Неизвестная ошибка');
                setTimeout(() => setMessage(''), 3000);
            }
        }

    };

    const handleKickUser = async (username: string) => {
        try {
            const response = await apiClient.delete(`/team/kick?username=${username}&teamId=${params.id}`);
            await fetchTeam();
            setMessage(`Пользователь ${username} был исключен из команды`);
            setTimeout(() => setMessage(''), 3000);
        } catch (error: unknown) {
            // Обработка ошибки
            if (error instanceof AxiosError) {
                console.error(error.response?.data); // Лог ответа с ошибкой
                setMessage('Ошибка при удалении пользователя: ' + (error.response?.data?.message || error.message));
            } else {
                console.error(error);  // Лог для неизвестных ошибок
                setMessage('Неизвестная ошибка');
            }
        }
    };

    const handleDeleteTeam = async () => {
        try {
            const response = await apiClient.delete(`/team/delete?username=${user?.username}&teamId=${params.id}`);
            setMessage('Команда удалена успешно!');
            setTimeout(() => {
                router.push("/teams");
            }, 1000);

        } catch (error) {
            // Обработка ошибки
            if (error instanceof AxiosError) {
                console.error(error.response?.data);
                setMessage('Ошибка при удалении команды: ' + (error.response?.data?.message || error.message));
            } else {
                console.error(error);
                setMessage('Неизвестная ошибка');
            }
        }
    };

    const confirmDeleteTeam = () => {
        if (window.confirm("Вы уверены, что хотите удалить команду? Это действие необратимо.")) {
            handleDeleteTeam();
        } };

    const handleCreateToken = async () => {
        try {
            const response = await apiClient.post(`/team/create/token?teamId=${params.id}`);
            await fetchTeam();
        } catch (error: unknown) {
            if (error instanceof AxiosError) {
                console.error(error.response?.data);
                setMessage('Ошибка при создании токена: ' + (error.response?.data?.message || error.message));
                setTimeout(() => setMessage(''), 3000);
            } else {
                console.error(error);  // Лог для неизвестных ошибок
                setMessage('Неизвестная ошибка');
                setTimeout(() => setMessage(''), 3000);
            }
        }

    };

    if (!team) {
        return (<p className="text-center text-lg">У вас нет доступа к данной команде</p>)
    }

  return (
      <div className="container mx-auto px-4 py-8 animate-fade-in">
          <h1 className="text-4xl font-bold mb-2 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
              {team.name}
          </h1>

          {!team.token ? (
              <Button
                  className="bg-primary mb-8"
                  onClick={handleCreateToken}
              >
                  Сгенерировать токен
              </Button>
          ) : (
              <div className="text-primary mb-8">
                  Токен команды:

                  <p className="text-secondary hover:text-primary mb-2">
                      {team.token}
                  </p>

                  <Button
                      className="bg-primary"
                      onClick={handleCreateToken}
                  >
                      Сгенерировать токен
                  </Button>

              </div>
          )
          }

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              {teammates.map((teammate) => (
                  <Card key={teammate.id}
                        className="bg-card hover:bg-card-hover transition-all duration-300 transform hover:-translate-y-1">
                      <CardHeader className="flex flex-row items-center space-y-0 pb-2">
                      <Avatar className="h-10 w-10 mr-4">
                                  <AvatarImage src={teammate.username} alt={teammate.username}/>
                                  <AvatarFallback>{teammate.username.split(' ').map(n => n[0]).join('')}</AvatarFallback>
                              </Avatar>
                              <CardTitle className="text-xl font-bold mr-2">{teammate.username}</CardTitle>
                              {team.project.ownerId === teammate.id && (
                                  <Crown className="h-6 w-auto text-yellow-500"/>
                              )}
                          </CardHeader>
                          <CardContent>
                              <p className="text-sm text-muted-foreground">{teammate.email}</p>
                          </CardContent>

                          {team.project.ownerId === user?.id && teammate.id !== user.id && (
                              <Button
                                  className="fixed bottom-4 right-4 bg-primary text-white rounded-full p-4 shadow-lg hover:bg-red-500 focus:outline-none"
                                  onClick={() => handleKickUser(teammate.username)}
                              >
                                  <UserRoundX className="h-6 w-auto text-secondary"/>
                              </Button>
                          )}

                      </Card>
                  ))}
              </div>


              <div className="fixed bottom-4 right-4 flex space-x-2">
                  {team.project.ownerId === user?.id && (
                      <Button
                          className="bg-red-600 hover:bg-red-700 focus:outline-none"
                          onClick={confirmDeleteTeam}
                      >
                          Удалить команду
                      </Button>
                  )}

                  <Button
                      className="bg-primary text-white rounded-full p-4 shadow-lg hover:bg-green-500 focus:outline-none"
                      onClick={() => setShowForm(!showForm)}
                  >
                      <UserRoundPlus className="h-6 w-auto text-secondary"/>
                  </Button>
              </div>

              {showForm && (
                  <div
                      className="fixed bottom-16 right-4 bg-card text-card-foreground shadow-lg p-4 rounded-lg border border-gray-200 w-64">
                      <h2 className="text-lg font-semibold mb-2">Добавить пользователя</h2>
                      <Input
                          type="text"
                          className="mb-4"
                          placeholder="Введите имя пользователя"
                          value={usernameToAdd}
                          onChange={(e) => setUsernameToAdd(e.target.value)}
                      />
                      <div className="flex justify-end">
                          <Button
                              className="px-4 py-2 hover:bg-green-500"
                              onClick={handleAddUser}
                          >
                              Добавить
                          </Button>
                      </div>
                  </div>
              )}

              {message && <p className="mt-4 mb-4 text-center text-red-500">{message}</p>}

      </div>
)
}

