import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Auth.css';

const AUTH_URL = 'http://localhost:8080/auth';

function Registration() {
    const [formData, setFormData] = useState({ username: '', password: '', confirmPassword: '' });
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSignUp = async () => {
        if (formData.password !== formData.confirmPassword) {
            setMessage('Пароли не совпадают');
            return;
        }

        try {
            const response = await axios.post(`${AUTH_URL}/sign-up`, {
                username: formData.username,
                password: formData.password
            }, {
                headers: { 'Content-Type': 'application/json' },
            });
            setMessage(`Регистрация успешна! Токен: ${response.data.token}`);
        } catch (error) {
            setMessage('Ошибка регистрации: ' + (error.response?.data?.message || error.message));
        }
    };

    return (
        <div className="auth-container">
            <h2>Регистрация</h2>
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
            <div>
                <label>Подтверждение пароля:</label>
                <input
                    type="password"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    required
                />
            </div>
            <button onClick={handleSignUp}>Зарегистрироваться</button>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Registration;
