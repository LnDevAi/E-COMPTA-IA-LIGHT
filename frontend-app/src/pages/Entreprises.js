
import React, { useEffect, useState } from 'react';
import { getEntreprises, createEntreprise, updateEntreprise } from '../services/entrepriseService';
import EntrepriseForm from '../components/EntrepriseForm';

const Entreprises = () => {
  const [entreprises, setEntreprises] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editEntreprise, setEditEntreprise] = useState(null);

  const fetchEntreprises = () => {
    setLoading(true);
    getEntreprises()
      .then(res => {
        setEntreprises(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Erreur lors du chargement des entreprises');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchEntreprises();
  }, []);

  const handleCreate = async (data) => {
    try {
      await createEntreprise(data);
      setSuccess('Entreprise créée !');
      setShowForm(false);
      fetchEntreprises();
    } catch {
      setError('Erreur lors de la création');
    }
  };

  const handleEdit = async (data) => {
    try {
      await updateEntreprise(editEntreprise.id, data);
      setSuccess('Entreprise modifiée !');
      setEditEntreprise(null);
      setShowForm(false);
      fetchEntreprises();
    } catch {
      setError('Erreur lors de la modification');
    }
  };

  // Suppression à ajouter si le backend le permet

  return (
    <div style={{ maxWidth: 800, margin: '2rem auto' }}>
      <h2>Entreprises</h2>
      <button onClick={() => { setShowForm(true); setEditEntreprise(null); }} style={{ marginBottom: 20 }}>Ajouter une entreprise</button>
      {showForm && (
        <EntrepriseForm
          onSubmit={editEntreprise ? handleEdit : handleCreate}
          initialData={editEntreprise || {}}
          onCancel={() => { setShowForm(false); setEditEntreprise(null); }}
        />
      )}
      {success && <div style={{ color: 'green', marginBottom: 10 }}>{success}</div>}
      {error && <div style={{ color: 'red', marginBottom: 10 }}>{error}</div>}
      {loading && <div>Chargement...</div>}
      <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', boxShadow: '0 2px 8px #ccc' }}>
        <thead style={{ background: '#2c3e50', color: '#fff' }}>
          <tr>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Nom</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Type</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Adresse</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {entreprises.map(ent => (
            <tr key={ent.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ent.nom}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ent.type}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ent.adresse}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>
                <button onClick={() => { setEditEntreprise(ent); setShowForm(true); }} style={{ marginRight: 8 }}>Modifier</button>
                {/* <button onClick={() => handleDelete(ent.id)} style={{ color: 'red' }}>Supprimer</button> */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Entreprises;
