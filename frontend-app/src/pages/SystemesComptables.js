
import React, { useEffect, useState } from 'react';
import { getSystemes, createSysteme } from '../services/systemeComptableService';
import SystemeComptableForm from '../components/SystemeComptableForm';

const SystemesComptables = () => {
  const [systemes, setSystemes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);

  const fetchSystemes = () => {
    setLoading(true);
    getSystemes()
      .then(res => {
        setSystemes(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Erreur lors du chargement des systèmes');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchSystemes();
  }, []);

  const handleCreate = async (data) => {
    try {
      await createSysteme(data);
      setSuccess('Système comptable créé !');
      setShowForm(false);
      fetchSystemes();
    } catch {
      setError('Erreur lors de la création');
    }
  };

  return (
    <div style={{ maxWidth: 800, margin: '2rem auto' }}>
      <h2>Systèmes comptables</h2>
      <button onClick={() => setShowForm(true)} style={{ marginBottom: 20 }}>Ajouter un système</button>
      {showForm && (
        <SystemeComptableForm
          onSubmit={handleCreate}
          onCancel={() => setShowForm(false)}
        />
      )}
      {success && <div style={{ color: 'green', marginBottom: 10 }}>{success}</div>}
      {error && <div style={{ color: 'red', marginBottom: 10 }}>{error}</div>}
      {loading && <div>Chargement...</div>}
      <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', boxShadow: '0 2px 8px #ccc' }}>
        <thead style={{ background: '#2c3e50', color: '#fff' }}>
          <tr>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Code</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Libellé</th>
          </tr>
        </thead>
        <tbody>
          {systemes.map(sys => (
            <tr key={sys.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{sys.code}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{sys.libelle}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SystemesComptables;
