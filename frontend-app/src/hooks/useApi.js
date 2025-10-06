import { useState, useCallback } from 'react';
import apiClient from '../config/api';

/**
 * Hook personnalisé pour gérer les appels API de manière standardisée
 * @param {Function} apiFunction - Fonction qui retourne une promesse d'appel API
 * @returns {Object} - { data, loading, error, execute, reset }
 */
export const useApi = (apiFunction) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const execute = useCallback(
    async (...args) => {
      try {
        setLoading(true);
        setError(null);
        const response = await apiFunction(...args);
        setData(response.data);
        return response.data;
      } catch (err) {
        const errorMessage =
          err.response?.data?.message ||
          err.response?.data ||
          err.message ||
          'Une erreur est survenue';
        setError(errorMessage);
        throw err;
      } finally {
        setLoading(false);
      }
    },
    [apiFunction]
  );

  const reset = useCallback(() => {
    setData(null);
    setError(null);
    setLoading(false);
  }, []);

  return { data, loading, error, execute, reset };
};

/**
 * Hook pour effectuer un GET
 * @param {string} url - L'URL de l'endpoint
 * @param {Object} config - Configuration axios optionnelle
 * @returns {Object} - { data, loading, error, refetch }
 */
export const useGet = (url, config = {}) => {
  const apiFunction = useCallback(
    () => apiClient.get(url, config),
    [url, config]
  );
  
  const { data, loading, error, execute } = useApi(apiFunction);

  return { data, loading, error, refetch: execute };
};

/**
 * Hook pour effectuer un POST
 * @param {string} url - L'URL de l'endpoint
 * @returns {Object} - { data, loading, error, post }
 */
export const usePost = (url) => {
  const apiFunction = useCallback(
    (payload, config = {}) => apiClient.post(url, payload, config),
    [url]
  );

  const { data, loading, error, execute } = useApi(apiFunction);

  return { data, loading, error, post: execute };
};

/**
 * Hook pour effectuer un PUT
 * @param {string} url - L'URL de l'endpoint
 * @returns {Object} - { data, loading, error, put }
 */
export const usePut = (url) => {
  const apiFunction = useCallback(
    (payload, config = {}) => apiClient.put(url, payload, config),
    [url]
  );

  const { data, loading, error, execute } = useApi(apiFunction);

  return { data, loading, error, put: execute };
};

/**
 * Hook pour effectuer un DELETE
 * @param {string} url - L'URL de l'endpoint
 * @returns {Object} - { data, loading, error, del }
 */
export const useDelete = (url) => {
  const apiFunction = useCallback(
    (config = {}) => apiClient.delete(url, config),
    [url]
  );

  const { data, loading, error, execute } = useApi(apiFunction);

  return { data, loading, error, del: execute };
};

export default useApi;
