'use client'

import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import apiClient from "@/app/config/apiClient";
import { AxiosError } from 'axios';

export default function Page() {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // Предотвращаем стандартное поведение формы

    try {
      const response = await apiClient.post(`/user/request?email=${email}`, {
        headers: { 'Content-Type': 'application/json' },
      });
      console.log(response.data);
      setMessage(response.data);
      setTimeout(() => setMessage(''), 3000);

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
          <h1 className="text-4xl font-bold mb-8">SAST</h1>
          <Card className="bg-card max-w-md w-full">
            <CardHeader>
              <CardTitle className="text-2xl font-bold text-text-primary text-center">Восстановление пароля</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label htmlFor="email" className="block text-sm font-medium mb-1 text-text-primary">
                    Почта
                  </label>
                  <Input
                      id="email"
                      name="email"
                      type="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      placeholder="Введите почту, к которой привязан Ваш аккаунт"
                      className="bg-background"
                  />
                </div>
                <Button type="submit" className="w-full">Восстановить</Button>
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
