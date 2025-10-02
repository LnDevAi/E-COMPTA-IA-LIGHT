import axios from 'axios';

export const getSycebnlOrganizations = async () => {
  const res = await axios.get('/api/sycebnl/organizations');
  return res.data;
};

export const createSycebnlOrganization = async (orgData) => {
  const res = await axios.post('/api/sycebnl/organizations', orgData);
  return res.data;
};

export const exportSycebnlPdf = async () => {
  const res = await axios.get('/api/sycebnl/export/pdf', { responseType: 'blob' });
  const url = window.URL.createObjectURL(new Blob([res.data]));
  const link = document.createElement('a');
  link.href = url;
  link.setAttribute('download', 'sycebnl_export.pdf');
  document.body.appendChild(link);
  link.click();
  link.remove();
};
