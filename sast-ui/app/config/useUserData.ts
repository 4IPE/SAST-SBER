import { useState, useEffect } from 'react';
import apiClient from "@/app/config/apiClient";

interface ProjectInfoDto {
    id: number;
    name: string;
    url: string;
}

interface User {
    id: number;
    username: string;
    projects: Set<ProjectInfoDto>;
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
