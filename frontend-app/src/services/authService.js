// Service d'authentification
import apiClient from '../config/api';

const API_URL = '/api/auth';

export const register = (data) => apiClient.post(`${API_URL}/register`, data);
export const login = (data) => apiClient.post(`${API_URL}/login`, data);