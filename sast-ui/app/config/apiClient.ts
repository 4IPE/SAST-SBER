import axios, { AxiosInstance } from 'axios';

const apiClient: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true,
});

export default apiClient;
