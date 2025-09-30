// Service journaux
import axios from 'axios';

const API_URL = '/api/journaux';

export const getJournaux = () => axios.get(API_URL);
export const getJournalByCode = (code) => axios.get(`${API_URL}/code/${code}`);
export const createJournal = (data) => axios.post(API_URL, data);
export const updateJournal = (id, data) => axios.put(`${API_URL}/${id}`, data);