
import React, { useEffect, useState } from 'react';
import { getPlanComptable, importPlanComptable } from '../services/planComptableService';
import PlanComptableForm from '../components/PlanComptableForm';

const PlanComptable = () => {
  const [plan, setPlan] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);

  const fetchPlan = () => {
    setLoading(true);
    getPlanComptable('france')
      .then(res => {
        setPlan(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Erreur lors du chargement du plan comptable');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchPlan();
  }, []);

  const handleCreate = async (data) => {
    try {
      await importPlanComptable('france', data);
      setSuccess('Compte ajouté au plan !');
      setShowForm(false);
      fetchPlan();
    } catch {
      setError('Erreur lors de l\'ajout');
    }
  };

  return (
    <div style={{ maxWidth: 800, margin: '2rem auto' }}>
      <h2>Plan comptable (France)</h2>
      <button onClick={() => setShowForm(true)} style={{ marginBottom: 20 }}>Ajouter un compte au plan</button>
      {showForm && (
        <PlanComptableForm
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
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Numéro</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Libellé</th>
            <th style={{ padding: '8px', border: '1px solid #ddd' }}>Type</th>
          </tr>
        </thead>
        <tbody>
          {plan.map(compte => (
            <tr key={compte.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{compte.numero}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{compte.libelle}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{compte.type}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PlanComptable;
