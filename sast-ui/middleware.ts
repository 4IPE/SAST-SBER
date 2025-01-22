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

    return NextResponse.next();
}

// Для всех страниц, кроме страницы логина и регистрации:
export const config = {
    matcher: ['/', '/profile', '/teams/:path*', '/projects/:path*'], // Middleware сработает только для этих маршрутов
};
