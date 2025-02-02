'use client'

import {useState} from 'react'
import {Input} from "@/components/ui/input"
import {Button} from "@/components/ui/button"
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card"
import Link from 'next/link'
import apiClient from "@/app/config/apiClient";
import {AxiosError} from "axios";

export default function Register() {
    const [registerData, setRegisterData] = useState({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    })
    const [message, setMessage] = useState('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setRegisterData({...registerData, [e.target.name]: e.target.value})
    }

    const validateForm = () => {
        if (!registerData.username.trim()) {
            setMessage('Введите имя пользователя');
            return false;
        }

        if (registerData.username.length < 5) {
            setMessage('Имя пользователя должно содержать минимум 5 символов');
            return false;
        }

        if (!registerData.email.trim()) {
            setMessage('Введите электронную почту');
            return false;
        }

        if (!registerData.password.trim()) {
            setMessage('Введите пароль');
            return false;
        }

        if (registerData.password.length < 5) {
            setMessage('Пароль должен содержать минимум 5 символов');
            return false;
        }

        if ((registerData.password.trim() !== registerData.confirmPassword.trim())) {
            setMessage('Пароли не совпадают');
            return false;
        }

        setMessage(''); // Очистка сообщения, если все поля валидны
        return true;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        if (!validateForm()) {
            return;
        }

        try {
            await apiClient.post('/auth/register', {
                username: registerData.username,
                email: registerData.email,
                password: registerData.password
            }, {
                headers: {'Content-Type': 'application/json'},
            });

            window.location.href = "/";

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

    }

    return (
        <div className="container mx-auto px-4 py-8 animate-fade-in">
            <div className="flex flex-col items-center justify-center min-h-[80vh]">
                <h1 className="text-4xl font-bold mb-8">SAST</h1>
                <Card className="bg-card max-w-md w-full">
                    <CardHeader>
                        <CardTitle className="text-2xl font-bold text-text-primary text-center">Создать
                            аккаунт</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <form onSubmit={handleSubmit} className="space-y-4">
                            <div>
                                <label htmlFor="username" className="block text-sm font-medium mb-1 text-text-primary">
                                    Имя пользователя
                                </label>
                                <Input
                                    id="username"
                                    name="username"
                                    type="text"
                                    value={registerData.username}
                                    onChange={handleChange}
                                    placeholder="Введите имя пользователя"
                                    className="bg-background"
                                />
                            </div>

                            <div>
                                <label htmlFor="username" className="block text-sm font-medium mb-1 text-text-primary">
                                    Электронная почта
                                </label>
                                <Input
                                    id="email"
                                    name="email"
                                    type="email"
                                    value={registerData.email}
                                    onChange={handleChange}
                                    placeholder="Введите электронную почту"
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
                                    value={registerData.password}
                                    onChange={handleChange}
                                    placeholder="Введите пароль"
                                    className="bg-background"
                                />
                            </div>

                            <div>
                                <label htmlFor="confirmPassword"
                                       className="block text-sm font-medium mb-1 text-text-primary">
                                    Подтверждение пароля
                                </label>
                                <Input
                                    id="confirmPassword"
                                    name="confirmPassword"
                                    type="password"
                                    value={registerData.confirmPassword}
                                    onChange={handleChange}
                                    placeholder="Подтвердите пароль"
                                    className="bg-background"
                                />
                            </div>

                            <Button type="submit" className="w-full">Создать</Button>
                        </form>
                        {message && <p className="mt-4 text-center text-red-500">{message}</p>}
                        <p className="mt-4 text-center text-text-secondary">
                            Уже есть аккаунт?{' '}
                            <Link href="/login" className="text-primary hover:underline">
                                Войти
                            </Link>
                        </p>
                    </CardContent>
                </Card>
            </div>
        </div>
    )
}

