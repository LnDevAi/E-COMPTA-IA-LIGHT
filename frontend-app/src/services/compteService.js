// Service comptes comptables
import apiClient from '../config/api';

const API_URL = '/api/comptes';

export const getComptes = () => apiClient.get(API_URL);
export const getCompteByNumero = (numero) => apiClient.get(`${API_URL}/numero/${numero}`);
export const getCompteByType = (type) => apiClient.get(`${API_URL}/type/${type}`);
export const createCompte = (data) => apiClient.post(API_URL, data);
export const updateCompte = (id, data) => apiClient.put(`${API_URL}/${id}`, data);
export const deleteCompte = (id) => apiClient.delete(`${API_URL}/${id}`);
export const getBalance = () => apiClient.get(`${API_URL}/balance`);
export const getGrandLivre = (numero) => apiClient.get(`${API_URL}/${numero}/grand-livre`);