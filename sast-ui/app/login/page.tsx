'use client'

import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import apiClient from "@/app/config/apiClient";  // Импорт для навигации в Next.js
import { AxiosError } from 'axios';

export default function Page() {
  const [loginData, setLoginData] = useState({
    username: '',
    password: '',
  })
  const [message, setMessage] = useState('');
  const router = useRouter();  // Инициализация навигации

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value })
  }

  const validateForm = () => {
    if (!loginData.username.trim()) {
      setMessage('Введите имя пользователя');
      return false;
    }

    if (!loginData.password.trim()) {
      setMessage('Введите пароль');
      return false;
    }

    setMessage(''); // Очистка сообщения, если все поля валидны
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // Предотвращаем стандартное поведение формы

    if (!validateForm()) {
      return;
    }

    try {
      const response = await apiClient.post('/auth/login', loginData, {
        headers: { 'Content-Type': 'application/json' },
      });
      console.log(response.data); // Лог успешного ответа
      router.push('/');  // Перенаправление на главную страницу

    } catch (error: unknown) {
      // Обработка ошибки
      if (error instanceof AxiosError) {
        console.error(error.response?.data); // Лог ответа с ошибкой
        setMessage('Ошибка входа: ' + (error.response?.data?.message || error.message));
      } else {
        console.error(error);  // Лог для неизвестных ошибок
        setMessage('Неизвестная ошибка');
      }
    }
  };

  return (
      <div className="container mx-auto px-4 py-8 animate-fade-in">
        <div className="flex flex-col items-center justify-center min-h-[80vh]">
          <h1 className="text-4xl font-bold mb-8 gradient-text">SAST</h1>
          <Card className="bg-card max-w-md w-full">
            <CardHeader>
              <CardTitle className="text-2xl font-bold text-text-primary text-center">Вход</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label htmlFor="username" className="block text-sm font-medium mb-1 text-text-primary">Имя пользователя</label>
                  <Input
                      id="username"
                      name="username"
                      type="text"
                      value={loginData.username}
                      onChange={handleChange}
                      placeholder="Введите имя пользователя"
                      className="bg-background"
                  />
                </div>
                <div>
                  <label htmlFor="password" className="block text-sm font-medium mb-1 text-text-primary">
                    Пароль
                  </label>
                  <Input
                      id="password"
                      name="password"
                      type="password"
                      value={loginData.password}
                      onChange={handleChange}
                      placeholder="Введите пароль"
                      className="bg-background"
                  />
                </div>
                <Button type="submit" className="w-full">Войти</Button>
              </form>
              {message && <p className="mt-4 text-center text-red-500">{message}</p>}
              <p className="mt-4 text-center text-text-secondary">
                Если у вас нет аккаунта, то Вы можете {' '}
                <Link href="/register" className="text-primary hover:underline">
                  зарегистрироваться
                </Link>
              </p>
            </CardContent>
          </Card>
        </div>
      </div>
  )
}
