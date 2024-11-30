import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/Auth.css';
import apiClient from "./config/axiosConfig";

function Login() {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSignIn = async () => {
        try {
            const response = await apiClient.post('/login', formData, {
                headers: { 'Content-Type': 'application/json' },
            });

            console.log(response.data); // Лог успешного ответа
            setMessage(`Вход успешен!`);

            // Сохранение токена и id пользователя в localStorage
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('userId', response.data.userId);

        } catch (error) {
            console.error(error.response?.data); // Лог ответа с ошибкой
            setMessage('Ошибка входа: ' + (error.response?.data?.message || error.message));
        }
    };

    return (
        <div className="auth-container">
            <h2>Вход</h2>
            <div>
                <label>Имя пользователя:</label>
                <input
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label>Пароль:</label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />
            </div>
            <button onClick={handleSignIn}>Войти</button>
            <button onClick={() => navigate('/auth/register')} className="secondary-button">
                Регистрация
            </button>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Login;
