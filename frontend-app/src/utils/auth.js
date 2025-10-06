// Utilitaires pour la gestion de l'authentification

const TOKEN_KEY = 'token';
const TOKEN_EXPIRY_KEY = 'token_expiry';

/**
 * Définit le token avec une expiration optionnelle
 * @param {string} token - Le token d'authentification
 * @param {number} expiresIn - Durée de validité en secondes (optionnel)
 */
export const setToken = (token, expiresIn = null) => {
  localStorage.setItem(TOKEN_KEY, token);
  
  if (expiresIn) {
    const expiryTime = Date.now() + expiresIn * 1000;
    localStorage.setItem(TOKEN_EXPIRY_KEY, expiryTime.toString());
  }
};

/**
 * Récupère le token s'il est valide
 * @returns {string|null} Le token ou null s'il est expiré
 */
export const getToken = () => {
  const token = localStorage.getItem(TOKEN_KEY);
  const expiry = localStorage.getItem(TOKEN_EXPIRY_KEY);
  
  if (!token) return null;
  
  // Vérifier l'expiration si définie
  if (expiry && Date.now() > parseInt(expiry)) {
    clearAuth();
    return null;
  }
  
  return token;
};

/**
 * Supprime toutes les données d'authentification
 */
export const clearAuth = () => {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(TOKEN_EXPIRY_KEY);
};

/**
 * Vérifie si l'utilisateur est authentifié
 * @returns {boolean}
 */
export const isAuthenticated = () => {
  return getToken() !== null;
};

/**
 * Déconnecte l'utilisateur et redirige vers la page de connexion
 */
export const logout = () => {
  clearAuth();
  window.location.href = '/login';
};

