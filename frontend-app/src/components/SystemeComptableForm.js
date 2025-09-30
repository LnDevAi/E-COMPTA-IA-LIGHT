import React, { useState } from 'react';

const SystemeComptableForm = ({ onSubmit, initialData = {}, onCancel }) => {
  const [form, setForm] = useState({
    code: initialData.code || '',
    libelle: initialData.libelle || ''
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
        <label>Code</label>
        <input name="code" value={form.code} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <div style={{ marginBottom: 10 }}>
        <label>Libellé</label>
        <input name="libelle" value={form.libelle} onChange={handleChange} required style={{ marginLeft: 10 }} />
      </div>
      <button type="submit" style={{ marginRight: 10 }}>Valider</button>
      {onCancel && <button type="button" onClick={onCancel}>Annuler</button>}
    </form>
  );
};

export default SystemeComptableForm;
