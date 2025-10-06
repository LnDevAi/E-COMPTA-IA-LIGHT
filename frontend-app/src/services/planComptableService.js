// Service plan comptable
import apiClient from '../config/api';

const API_URL = '/api/plan-comptable';

export const getPlanComptable = (systeme) => apiClient.get(`${API_URL}/${systeme}`);
export const importPlanComptable = (systeme, data) => apiClient.post(`${API_URL}/${systeme}/import`, data);