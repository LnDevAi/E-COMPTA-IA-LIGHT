// Service entreprises
import axios from 'axios';

const API_URL = '/api/entreprises';

export const createEntreprise = (data) => axios.post(API_URL, data);
export const createEntrepriseAuto = (data) => axios.post(`${API_URL}/automatique`, data);
export const getEntreprises = () => axios.get(API_URL);
export const getEntreprise = (id) => axios.get(`${API_URL}/${id}`);