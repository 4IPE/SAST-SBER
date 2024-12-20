import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Auth.css';
import apiClient from "./config/apiClient";

function Login() {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const validateForm = () => {
        if (!formData.username.trim()) {
            setMessage('Введите имя пользователя');
            return false;
        }

        if (formData.username.length < 5) {
            setMessage('Имя пользователя должно содержать минимум 5 символов');
            return false;
        }

        if (!formData.password.trim()) {
            setMessage('Введите пароль');
            return false;
        }

        setMessage(''); // Очистка сообщения, если все поля валидны
        return true;
    };

    const handleSignIn = async () => {
        if (!validateForm()) {
            return;
        }

        try {
            const response = await apiClient.post('/auth/login', formData, {
                headers: { 'Content-Type': 'application/json' },
            });

            console.log(response.data); // Лог успешного ответа
            setMessage(`Вход успешен!`);

            navigate('/');
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
