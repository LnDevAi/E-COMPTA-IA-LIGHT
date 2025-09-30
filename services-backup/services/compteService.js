// Service comptes comptables
import axios from 'axios';

const API_URL = '/api/comptes';

export const getComptes = () => axios.get(API_URL);
export const getCompteByNumero = (numero) => axios.get(`${API_URL}/numero/${numero}`);
export const getCompteByType = (type) => axios.get(`${API_URL}/type/${type}`);
export const createCompte = (data) => axios.post(API_URL, data);
export const updateCompte = (id, data) => axios.put(`${API_URL}/${id}`, data);
export const deleteCompte = (id) => axios.delete(`${API_URL}/${id}`);
export const getBalance = () => axios.get(`${API_URL}/balance`);
export const getGrandLivre = (numero) => axios.get(`${API_URL}/${numero}/grand-livre`);