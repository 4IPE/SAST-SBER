'use client'

import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import {useParams, useRouter, useSearchParams} from 'next/navigation'
import apiClient from "@/app/config/apiClient";
import { AxiosError } from 'axios';

export default function Page() {
  const searchParams = useSearchParams();
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [message, setMessage] = useState('');
  const router = useRouter();  // Инициализация навигации

  const validateForm = () => {

    if (!password.trim()) {
      setMessage('Введите пароль');
      return false;
    }

    if (password.length < 5) {
      setMessage('Пароль должен содержать минимум 5 символов');
      return false;
    }

    if ((password.trim() !== confirmPassword.trim())) {
      setMessage('Пароли не совпадают');
      return false;
    }

    setMessage('');
    return true;

  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // Предотвращаем стандартное поведение формы

    if (!validateForm()) {
      return;
    }

    try {
      const response = await apiClient.post(`/user/edit?token=${searchParams.get('token')}&password=${password}`, {
        headers: { 'Content-Type': 'application/json' },
      });

      console.log(response.data);
      router.push("/login");

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
              <CardTitle className="text-2xl font-bold text-text-primary text-center">Восстановление пароля</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label htmlFor="password" className="block text-sm font-medium mb-1 text-text-primary">
                    Новый пароль
                  </label>
                  <Input
                      id="password"
                      name="password"
                      type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      placeholder="Введите новый пароль"
                      className="bg-background"
                  />
                </div>

                <div>
                  <label htmlFor="confirmPassword" className="block text-sm font-medium mb-1 text-text-primary">
                    Подтверждение пароля
                  </label>
                  <Input
                      id="confirmPassword"
                      name="confirmPassword"
                      type="password"
                      value={confirmPassword}
                      onChange={(e) => setConfirmPassword(e.target.value)}
                      placeholder="Подтвердите пароль"
                      className="bg-background"
                  />
                </div>

                <Button type="submit" className="w-full">Подтвердить</Button>
              </form>
              {message && <p className="mt-4 text-center text-red-500">{message}</p>}
            </CardContent>
          </Card>
        </div>
      </div>
  )
}
