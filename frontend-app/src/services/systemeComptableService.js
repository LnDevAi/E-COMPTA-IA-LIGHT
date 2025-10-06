// Service systèmes comptables
import apiClient from '../config/api';

const API_URL = '/api/systemes-comptables';

export const createSysteme = (data) => apiClient.post(API_URL, data);
export const getSystemes = () => apiClient.get(API_URL);
export const getSysteme = (code) => apiClient.get(`${API_URL}/${code}`);