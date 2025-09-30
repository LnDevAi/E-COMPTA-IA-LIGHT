// Service systèmes comptables
import axios from 'axios';

const API_URL = '/api/systemes-comptables';

export const createSysteme = (data) => axios.post(API_URL, data);
export const getSystemes = () => axios.get(API_URL);
export const getSysteme = (code) => axios.get(`${API_URL}/${code}`);