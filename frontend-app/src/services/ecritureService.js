// Service écritures comptables
import apiClient from '../config/api';

const API_URL = '/api/ecritures';

export const getEcriture = (id) => apiClient.get(`${API_URL}/${id}`);
export const createEcriture = (data) => apiClient.post(API_URL, data);
export const deleteEcriture = (id) => apiClient.delete(`${API_URL}/${id}`);
export const validerEcriture = (id) => apiClient.post(`${API_URL}/${id}/valider`);
export const getEcrituresByPeriode = (params) => apiClient.get(`${API_URL}/periode`, { params });
export const getEcrituresByJournal = (journalId) => apiClient.get(`${API_URL}/journal/${journalId}`);
export const getEcrituresByStatut = (statut) => apiClient.get(`${API_URL}/statut/${statut}`);
export const updateEcriture = (id, data) => apiClient.put(`${API_URL}/${id}`, data);