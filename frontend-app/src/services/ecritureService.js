// Service écritures comptables
import axios from 'axios';

const API_URL = '/api/ecritures';

export const getEcriture = (id) => axios.get(`${API_URL}/${id}`);
export const createEcriture = (data) => axios.post(API_URL, data);
export const deleteEcriture = (id) => axios.delete(`${API_URL}/${id}`);
export const validerEcriture = (id) => axios.post(`${API_URL}/${id}/valider`);
export const getEcrituresByPeriode = (params) => axios.get(`${API_URL}/periode`, { params });
export const getEcrituresByJournal = (journalId) => axios.get(`${API_URL}/journal/${journalId}`);
export const getEcrituresByStatut = (statut) => axios.get(`${API_URL}/statut/${statut}`);