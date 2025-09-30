
import React, { useEffect, useState } from 'react';
import { getJournaux, createJournal, updateJournal } from '../services/journalService';
import JournalForm from '../components/JournalForm';

const Journaux = () => {
  const [journaux, setJournaux] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editJournal, setEditJournal] = useState(null);

  const fetchJournaux = () => {
    setLoading(true);
    getJournaux()
      .then(res => {
        setJournaux(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Erreur lors du chargement des journaux');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchJournaux();
  }, []);

  const handleCreate = async (data) => {
    try {
      await createJournal(data);
      setSuccess('Journal créé !');
      setShowForm(false);
      fetchJournaux();
    } catch {
      setError('Erreur lors de la création');
    }
  };

  const handleEdit = async (data) => {
    try {
      await updateJournal(editJournal.id, data);
      setSuccess('Journal modifié !');
      setEditJournal(null);
      setShowForm(false);
      fetchJournaux();
    } catch {
      setError('Erreur lors de la modification');
    }
  };

  // Suppression à ajouter si le backend le permet

  return (
    <div style={{ maxWidth: 800, margin: '2rem auto' }}>
      <h2>Journaux</h2>
      <button onClick={() => { setShowForm(true); setEditJournal(null); }} style={{ marginBottom: 20 }}>Ajouter un journal</button>
      {showForm && (
        <JournalForm
          onSubmit={editJournal ? handleEdit : handleCreate}
          initialData={editJournal || {}}
          onCancel={() => { setShowForm(false); setEditJournal(null); }}
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
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {journaux.map(journal => (
            <tr key={journal.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{journal.code}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{journal.libelle}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>
                <button onClick={() => { setEditJournal(journal); setShowForm(true); }} style={{ marginRight: 8 }}>Modifier</button>
                {/* <button onClick={() => handleDelete(journal.id)} style={{ color: 'red' }}>Supprimer</button> */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Journaux;
