
import React, { useEffect, useState } from 'react';
import { getComptes, createCompte, updateCompte, deleteCompte } from '../services/compteService';
import CompteForm from '../components/CompteForm';

const Comptes = () => {
  const [comptes, setComptes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editCompte, setEditCompte] = useState(null);
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(1);
  const [pageSize] = useState(10);

  const fetchComptes = () => {
    setLoading(true);
    getComptes()
      .then(res => {
        setComptes(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Erreur lors du chargement des comptes');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchComptes();
  }, []);

  const handleCreate = async (data) => {
    try {
      await createCompte(data);
      setSuccess('Compte créé !');
      setShowForm(false);
      fetchComptes();
    } catch {
      setError('Erreur lors de la création');
    }
  };

  const handleEdit = async (data) => {
    try {
      await updateCompte(editCompte.id, data);
      setSuccess('Compte modifié !');
      setEditCompte(null);
      setShowForm(false);
      fetchComptes();
    } catch {
      setError('Erreur lors de la modification');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Supprimer ce compte ?')) return;
    try {
      await deleteCompte(id);
      setSuccess('Compte supprimé !');
      fetchComptes();
    } catch {
      setError('Erreur lors de la suppression');
    }
  };

  // Filtrage et pagination
  const filtered = comptes.filter(c =>
    c.numero.toLowerCase().includes(search.toLowerCase()) ||
    c.libelle.toLowerCase().includes(search.toLowerCase()) ||
    c.type.toLowerCase().includes(search.toLowerCase())
  );
  const paginated = filtered.slice((page - 1) * pageSize, page * pageSize);

  return (
    <div style={{ maxWidth: 800, margin: '2rem auto' }}>
      <h2>Comptes comptables</h2>
      <input
        type="text"
        placeholder="Recherche..."
        value={search}
        onChange={e => { setSearch(e.target.value); setPage(1); }}
        style={{ marginBottom: 20, padding: 8, width: '100%' }}
      />
      <button onClick={() => { setShowForm(true); setEditCompte(null); }} style={{ marginBottom: 20 }}>Ajouter un compte</button>
      {showForm && (
        <CompteForm
          onSubmit={editCompte ? handleEdit : handleCreate}
          initialData={editCompte || {}}
          onCancel={() => { setShowForm(false); setEditCompte(null); }}
        />
      )}
      {success && <div style={{ color: 'green', marginBottom: 10 }}>{success}</div>}
      {error && <div style={{ color: 'red', marginBottom: 10 }}>{error}</div>}
      {loading && <div>Chargement...</div>}
      <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', boxShadow: '0 2px 8px #ccc' }}>
        <thead style={{ background: '#2c3e50', color: '#fff' }}>
          <tr>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Numéro</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Libellé</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Type</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {paginated.map(compte => (
            <tr key={compte.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{compte.numero}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{compte.libelle}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{compte.type}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>
                <button onClick={() => { setEditCompte(compte); setShowForm(true); }} style={{ marginRight: 8 }}>Modifier</button>
                <button onClick={() => handleDelete(compte.id)} style={{ color: 'red' }}>Supprimer</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* Pagination */}
      <div style={{ marginTop: 20, display: 'flex', justifyContent: 'center', gap: 10 }}>
        {Array.from({ length: Math.ceil(filtered.length / pageSize) }, (_, i) => (
          <button
            key={i}
            onClick={() => setPage(i + 1)}
            style={{ padding: '6px 12px', background: page === i + 1 ? '#2c3e50' : '#eee', color: page === i + 1 ? '#fff' : '#333', border: 'none', borderRadius: 4 }}
          >
            {i + 1}
          </button>
        ))}
      </div>
    </div>
  );
};

export default Comptes;
