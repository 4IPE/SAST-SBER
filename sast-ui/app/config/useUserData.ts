import { useState, useEffect } from 'react';
import apiClient from "@/app/config/apiClient";

interface User {
    id: number;
    username: string;
    email: string;
}

const useUserData = () => {
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const userResponse = await apiClient.get('/user/get');
                setUser(userResponse.data as User);
            } catch (error) {
                console.error('Ошибка проверки пользователя:', error);
            }
        };

        fetchUserInfo();
    }, []);

    return user;
};

export default useUserData;
