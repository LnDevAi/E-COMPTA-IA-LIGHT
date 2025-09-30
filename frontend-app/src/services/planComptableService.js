// Service plan comptable
import axios from 'axios';

const API_URL = '/api/plan-comptable';

export const getPlanComptable = (systeme) => axios.get(`${API_URL}/${systeme}`);
export const importPlanComptable = (systeme, data) => axios.post(`${API_URL}/${systeme}/import`, data);