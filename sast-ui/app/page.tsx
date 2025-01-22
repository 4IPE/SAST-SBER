'use client'

import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import apiClient from "@/app/config/apiClient";
import useUserData from "@/app/config/useUserData";
import {AxiosError} from "axios";
import {ArrowRight} from "lucide-react";

export default function Home() {
  const [projectUrl, setProjectUrl] = useState('')
  const [message, setMessage] = useState('');
  const user = useUserData();

  const extractRepoName = (url: string): string | null => {
    const repoRegex = /https?:\/\/(?:www\.)?github\.com\/[^\/]+\/([^\/]+)/;
    const match = url.match(repoRegex);
    return match ? match[1] : null; // Возвращает название репозитория или null, если не найдено
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!projectUrl.trim()) {
      setMessage('Пожалуйста, введите URL проекта');
      return;
    }

    const repoName = extractRepoName(projectUrl);

    if (!repoName) {
      setMessage('Пожалуйста, введите корректный URL проекта: https://github.com/user/repository');
      return;
    }

    try {
      const newProject = {
        name: repoName,
        url: projectUrl,
        userId: user?.id,
      };

      await apiClient.post('/project/save', newProject, {
        headers: {'Content-Type': 'application/json'},
      });

      setMessage('Проект добавлен успешно!');
      setProjectUrl('');
      setTimeout(() => setMessage(''), 3000); // Очистить сообщение через 3 секунды

    } catch (error: unknown) {
      // Обработка ошибки
      if (error instanceof AxiosError) {
        console.error(error.response?.data); // Лог ответа с ошибкой
        setMessage('Ошибка добавления проекта: ' + (error.response?.data?.message || error.message));
      } else {
        console.error(error);  // Лог для неизвестных ошибок
        setMessage('Неизвестная ошибка');
      }
    }

  }


  return (
    <div className="container mx-auto px-4 py-8 animate-fade-in">
      <div className="flex flex-col items-center justify-center min-h-[80vh] gap-8">
        <h1 className="text-6xl font-bold gradient-text mb-8 sast-text">
          SAST
        </h1>
        <p className="text-xl text-muted-foreground max-w-2xl text-center">
          Статическая платформа тестирования безопасности ваших приложений
        </p>

        <form onSubmit={handleSubmit} className="w-full max-w-3xl mx-auto flex">
          <Input
              placeholder="Введите URL проекта"
              className="bg-card hover:bg-card-hover transition-colors text-lg py-6 px-6 rounded-l-full flex-grow"
              value={projectUrl}
              onChange={(e) => setProjectUrl(e.target.value)}
          />
          <Button
              type="submit"
              className="bg-primary hover:bg-primary/90 rounded-r-full flex justify-center items-center w-[60px] h-auto"
          >
            <ArrowRight className="text-black w-20 h-auto" />
          </Button>
        </form>

        {message && <p className="mt-4 text-center text-red-500">{message}</p>}

      </div>
    </div>
  )
}
