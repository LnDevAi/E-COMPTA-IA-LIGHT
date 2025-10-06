import apiClient from '../config/api';

export const inscrireUtilisateur = async (form) => {
  const res = await apiClient.post('/api/utilisateur/inscription', {
    nom: form.nom,
    email: form.email,
    password: form.password,
    abonnement: form.abonnement,
    modules: form.modules,
    role: form.role || 'USER',
  });
  return res.data;
};

export const gererAbonnement = async (form) => {
  const res = await apiClient.post('/api/utilisateur/abonnement', {
    email: form.email,
    abonnement: form.abonnement,
    modules: form.modules,
  });
  return res.data;
};
