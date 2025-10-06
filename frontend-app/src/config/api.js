import axios from 'axios';
import { getToken, logout } from '../utils/auth';

const API_BASE_URL = process.env.REACT_APP_API_URL || '';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 30000, // 30 seconds timeout
});

// Intercepteur pour ajouter le token à chaque requête
apiClient.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Intercepteur pour gérer les erreurs globalement
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // Gérer l'expiration du token (401 Unauthorized)
    if (error.response?.status === 401) {
      logout();
    }
    
    // Gérer les erreurs 403 Forbidden
    if (error.response?.status === 403) {
      console.error('Accès refusé');
    }
    
    return Promise.reject(error);
  }
);

export default apiClient;
