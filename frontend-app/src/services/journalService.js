// Service journaux
import apiClient from '../config/api';

const API_URL = '/api/journaux';

export const getJournaux = () => apiClient.get(API_URL);
export const getJournalByCode = (code) => apiClient.get(`${API_URL}/code/${code}`);
export const createJournal = (data) => apiClient.post(API_URL, data);
export const updateJournal = (id, data) => apiClient.put(`${API_URL}/${id}`, data);