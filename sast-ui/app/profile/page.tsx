'use client';

import { useEffect, useState } from 'react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Edit2, Save } from 'lucide-react';
import useUserData from '@/app/config/useUserData';
import apiClient from '@/app/config/apiClient';
import { AxiosError } from 'axios';

interface ProfileData {
  username: string;
  email: string;
  password: string;
}

export default function Profile() {
  const user = useUserData();
  const [profile, setProfile] = useState<ProfileData>({
    username: '',
    email: '',
    password: '',
  });
  const [isEditing, setIsEditing] = useState(false);
  const [message, setMessage] = useState<string>('');

  useEffect(() => {
    if (user) {
      setProfile({
        username: user.username || '',
        email: user.email || '',
        password: '',
      });
    }
  }, [user]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProfile((prevProfile) => ({
      ...prevProfile,
      [e.target.name]: e.target.value,
    }));
  };

  const validateForm = () => {
    if (isEditing && !profile.password.trim()) {
      setMessage('Введите новый пароль');
      return false;
    }

    if (isEditing && profile.password.length < 5) {
      setMessage('Пароль должен содержать минимум 5 символов');
      return false;
    }

    setMessage('');
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) return;

    try {
      const response = await apiClient.post('/user/profile',
          {
            username: profile.username,
            email: profile.email,
            password: profile.password,
          }, {
            headers: { 'Content-Type': 'application/json' },
          }
      );

      console.log(response.data);
      setMessage('Профиль успешно обновлен!');
      setIsEditing(false);
    } catch (error: unknown) {
      if (error instanceof AxiosError) {
        console.error(error.response?.data);
        setMessage('Ошибка обновления профиля: ' + (error.response?.data?.message || error.message));
      } else {
        console.error(error);
        setMessage('Произошла неизвестная ошибка');
      }
    }
  };

  return (
      <div className="container mx-auto px-4 py-8 animate-fade-in">
        <h1 className="text-4xl font-bold mb-8 gradient-text">Профиль</h1>
        <Card className="bg-card max-w-2xl mx-auto">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <div className="flex items-center">
              <Avatar className="h-20 w-20 mr-4">
                <AvatarFallback>{profile.username[0]}</AvatarFallback>
              </Avatar>
              <CardTitle className="text-2xl font-bold text-text-primary">
                {profile.username || 'Имя пользователя'}
              </CardTitle>
            </div>
            <Button variant="outline" size="icon" onClick={() => setIsEditing(!isEditing)}>
              {isEditing ? <Save className="h-4 w-4" /> : <Edit2 className="h-4 w-4" />}
            </Button>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">

              <div>
                <label htmlFor="email" className="block text-sm font-medium mb-1 text-text-primary">
                  Электронная почта
                </label>
                {isEditing ? (
                    <Input
                        id="email"
                        name="email"
                        type="email"
                        value={profile.email}
                        onChange={handleChange}
                        placeholder="Введите электронную почту"
                        className="bg-background"
                    />
                ) : (
                    <p className="text-text-primary py-2 px-3 bg-background rounded">{profile.email}</p>
                )}
              </div>

              {isEditing && (
                  <div>
                    <label htmlFor="password" className="block text-sm font-medium mb-1 text-text-primary">
                      Новый пароль
                    </label>
                    <Input
                        id="password"
                        name="password"
                        type="password"
                        value={profile.password}
                        onChange={handleChange}
                        placeholder="Введите новый пароль"
                        className="bg-background"
                    />
                  </div>
              )}

              {message && <p className="mt-4 text-center text-red-500">{message}</p>}
              {isEditing && <Button type="submit" className="w-full">Обновить профиль</Button>}
            </form>
          </CardContent>
        </Card>
      </div>
  );
}
