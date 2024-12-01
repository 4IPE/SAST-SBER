import { useState, useEffect } from 'react';
import apiClient from "./apiClient";

const useUserData = () => {
    const [userStatus, setUserStatus] = useState(null); // null: загрузка, true: авторизован, false: не авторизован
    const [user, setUser] = useState(null); // Данные пользователя
    const [loading, setLoading] = useState(true); // Флаг загрузки

    useEffect(() => {
        const fetchUserInfo = async () => {
            setLoading(true);
            try {
                // Проверка статуса пользователя
                const statusResponse = await apiClient.get('/user/status');
                if (statusResponse.status === 200) {
                    setUserStatus(true);
                    // Получение данных пользователя
                    const userResponse = await apiClient.get('/user/get');
                    setUser(userResponse.data);
                } else {
                    setUserStatus(false);
                }
            } catch (error) {
                setUserStatus(false);
                console.error('Ошибка проверки пользователя:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchUserInfo();
    }, []);

    return {
        userStatus, // true, false, или null (при загрузке)
        user, // данные пользователя или null
        loading, // true, пока идет загрузка
    };
};

export default useUserData;
