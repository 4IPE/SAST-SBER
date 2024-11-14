import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/Auth.css';

const AUTH_URL = 'http://localhost:8080/auth';

function Auth() {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSignIn = async () => {
        try {
            const response = await axios.post(`${AUTH_URL}/sign-in`, formData, {
                headers: { 'Content-Type': 'application/json' },
            });
            setMessage(`Вход успешен! Токен: ${response.data.token}`);
        } catch (error) {
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
            <button onClick={() => navigate('/registration')} className="secondary-button">
                Регистрация
            </button>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Auth;
