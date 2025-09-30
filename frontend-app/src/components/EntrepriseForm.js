import React, { useState } from 'react';

const EntrepriseForm = ({ onSubmit, initialData = {}, onCancel }) => {
  const [form, setForm] = useState({
    nom: initialData.nom || '',
    type: initialData.type || '',
    adresse: initialData.adresse || ''
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
        <label>Nom</label>
        <input name="nom" value={form.nom} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <div style={{ marginBottom: 10 }}>
        <label>Type</label>
        <input name="type" value={form.type} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <div style={{ marginBottom: 10 }}>
        <label>Adresse</label>
        <input name="adresse" value={form.adresse} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <button type="submit" style={{ marginRight: 10 }}>Valider</button>
      {onCancel && <button type="button" onClick={onCancel}>Annuler</button>}
    </form>
  );
};

export default EntrepriseForm;
