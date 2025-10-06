// Service entreprises
import apiClient from '../config/api';

const API_URL = '/api/entreprises';

export const createEntreprise = (data) => apiClient.post(API_URL, data);
export const createEntrepriseAuto = (data) => apiClient.post(`${API_URL}/automatique`, data);
export const getEntreprises = () => apiClient.get(API_URL);
export const getEntreprise = (id) => apiClient.get(`${API_URL}/${id}`);
export const updateEntreprise = (id, data) => apiClient.put(`${API_URL}/${id}`, data);