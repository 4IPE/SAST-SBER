import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';
import apiClient from '@/app/config/apiClient'; // Импортируем ваш настроенный apiClient

export async function middleware(req: NextRequest) {
    const token = req.cookies.get('token'); // Извлекаем токен из cookies

    // Если токена нет, перенаправляем на страницу логина
    if (!token) {
        console.error('Токен отсутствует');
        const loginUrl = req.nextUrl.clone();
        loginUrl.pathname = '/login';
        return NextResponse.redirect(loginUrl);
    }

    // try {
    //     // Делаем GET-запрос для проверки статуса пользователя через apiClient, передаем токен в заголовке
    //     const response = await apiClient.get('/user/status', {
    //         headers: {
    //             Authorization: `Bearer ${token}`, // Токен добавляется в заголовок
    //         },
    //     });
    //
    //     if (response.status === 200) {
    //         console.error('Доступ разрешен');
    //         return NextResponse.next();
    //     } else {
    //         console.error('Токен не валиден');
    //         const loginUrl = req.nextUrl.clone();
    //         loginUrl.pathname = '/login';
    //         return NextResponse.redirect(loginUrl);
    //     }
    // } catch (error) {
    //     console.error('Ошибка проверки статуса пользователя:', error);
    //
    //     // Перенаправляем на логин в случае ошибки
    //     const loginUrl = req.nextUrl.clone();
    //     loginUrl.pathname = '/login';
    //     return NextResponse.redirect(loginUrl);
    // }

    return NextResponse.next();
}

// Для всех страниц, кроме страницы логина и регистрации:
export const config = {
    matcher: ['/', '/profile', '/teams', '/projects/:path*'], // Middleware сработает только для этих маршрутов
};
