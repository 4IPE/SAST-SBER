import React, { useState } from 'react';
import '../styles/Auth.css';
import {useNavigate} from "react-router-dom";
import apiClient from "./config/apiClient";

function Register() {
    const [formData, setFormData] = useState({ username: '', password: '', confirmPassword: '' });
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

        if (formData.password.length < 5) {
            setMessage('Пароль должен содержать минимум 5 символов');
            return false;
        }

        if ((formData.password.trim() !== formData.confirmPassword.trim())) {
            setMessage('Пароли не совпадают');
            return false;
        }

        setMessage(''); // Очистка сообщения, если все поля валидны
        return true;
    };

    const handleSignUp = async () => {
        if(!validateForm()) {
            return;
        }

        try {
            const response = await apiClient.post('/auth/register', {
                username: formData.username,
                password: formData.password
            }, {
                headers: { 'Content-Type': 'application/json' },
            });

            setMessage('Регистрация успешна!');
            setTimeout(() => {
                navigate('/');
            }, 1000);

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

export default Register;