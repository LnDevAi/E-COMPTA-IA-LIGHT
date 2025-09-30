
import React, { useEffect, useState } from 'react';
import { getEcrituresByPeriode, createEcriture, updateEcriture, deleteEcriture } from '../services/ecritureService';
import EcritureForm from '../components/EcritureForm';

const Ecritures = () => {
  const [ecritures, setEcritures] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editEcriture, setEditEcriture] = useState(null);

  const fetchEcritures = () => {
    setLoading(true);
    const now = new Date();
    const params = {
      debut: `${now.getFullYear()}-01-01`,
      fin: `${now.getFullYear()}-12-31`
    };
    getEcrituresByPeriode(params)
      .then(res => {
        setEcritures(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Erreur lors du chargement des écritures');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchEcritures();
  }, []);

  const handleCreate = async (data) => {
    try {
      await createEcriture(data);
      setSuccess('Écriture créée !');
      setShowForm(false);
      fetchEcritures();
    } catch {
      setError('Erreur lors de la création');
    }
  };

  const handleEdit = async (data) => {
    try {
      await updateEcriture(editEcriture.id, data);
      setSuccess('Écriture modifiée !');
      setEditEcriture(null);
      setShowForm(false);
      fetchEcritures();
    } catch {
      setError('Erreur lors de la modification');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Supprimer cette écriture ?')) return;
    try {
      await deleteEcriture(id);
      setSuccess('Écriture supprimée !');
      fetchEcritures();
    } catch {
      setError('Erreur lors de la suppression');
    }
  };

  return (
    <div style={{ maxWidth: 800, margin: '2rem auto' }}>
      <h2>Écritures comptables</h2>
      <button onClick={() => { setShowForm(true); setEditEcriture(null); }} style={{ marginBottom: 20 }}>Ajouter une écriture</button>
      {showForm && (
        <EcritureForm
          onSubmit={editEcriture ? handleEdit : handleCreate}
          initialData={editEcriture || {}}
          onCancel={() => { setShowForm(false); setEditEcriture(null); }}
        />
      )}
      {success && <div style={{ color: 'green', marginBottom: 10 }}>{success}</div>}
      {error && <div style={{ color: 'red', marginBottom: 10 }}>{error}</div>}
      {loading && <div>Chargement...</div>}
      <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', boxShadow: '0 2px 8px #ccc' }}>
        <thead style={{ background: '#2c3e50', color: '#fff' }}>
          <tr>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Date</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Libellé</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Montant</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Compte</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {ecritures.map(ecriture => (
            <tr key={ecriture.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ecriture.date}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ecriture.libelle}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ecriture.montant}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{ecriture.compteNumero}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>
                <button onClick={() => { setEditEcriture(ecriture); setShowForm(true); }} style={{ marginRight: 8 }}>Modifier</button>
                <button onClick={() => handleDelete(ecriture.id)} style={{ color: 'red' }}>Supprimer</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Ecritures;
