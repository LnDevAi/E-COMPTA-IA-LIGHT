import React, { useState } from 'react';

const EcritureForm = ({ onSubmit, initialData = {}, onCancel }) => {
  const [form, setForm] = useState({
    date: initialData.date || '',
    libelle: initialData.libelle || '',
    montant: initialData.montant || '',
    compteNumero: initialData.compteNumero || ''
  });

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = e => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: 20, background: '#fff', padding: 20, borderRadius: 8, boxShadow: '0 2px 8px #ccc' }}>
      <div style={{ marginBottom: 10 }}>
        <label>Date</label>
        <input name="date" type="date" value={form.date} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <div style={{ marginBottom: 10 }}>
        <label>Libellé</label>
        <input name="libelle" value={form.libelle} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <div style={{ marginBottom: 10 }}>
        <label>Montant</label>
        <input name="montant" type="number" value={form.montant} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <div style={{ marginBottom: 10 }}>
        <label>Compte</label>
        <input name="compteNumero" value={form.compteNumero} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <button type="submit" style={{ marginRight: 10 }}>Valider</button>
      {onCancel && <button type="button" onClick={onCancel}>Annuler</button>}
    </form>
  );
};

export default EcritureForm;
