import React, { useState } from 'react';
import { Paper, Typography, TextField, Button, Checkbox, FormControlLabel, FormGroup, Select, MenuItem } from '@mui/material';
import { inscrireUtilisateur } from '../services/utilisateurService';

const MODULES = [
  { value: 'sycebnl', label: 'SYCEBNL' },
  { value: 'ged', label: 'GED Pièce Comptable' },
  { value: 'iaec', label: 'Assistant IA Expert Comptable' },
  { value: 'reporting', label: 'Reporting avancé' },
  { value: 'notes', label: 'Notes Annexes' },
];

const ABONNEMENTS = [
  { value: 'basic', label: 'Basique' },
  { value: 'pro', label: 'Pro' },
  { value: 'premium', label: 'Premium' },
];

const InscriptionPage = () => {
  const [form, setForm] = useState({
    nom: '',
    email: '',
    password: '',
    abonnement: 'basic',
    modules: [],
    role: 'USER',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(f => ({ ...f, [name]: value }));
  };

  const handleModuleChange = (module) => {
    setForm(f => ({
      ...f,
      modules: f.modules.includes(module)
        ? f.modules.filter(m => m !== module)
        : [...f.modules, module],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await inscrireUtilisateur(form);
      window.alert('Inscription réussie !\n' + JSON.stringify(res, null, 2));
    } catch (err) {
      window.alert('Erreur lors de l’inscription : ' + err.message);
    }
  };

  return (
    <Paper sx={{ p: 4, maxWidth: 500, margin: 'auto', mt: 4 }}>
      <Typography variant="h5" gutterBottom>Inscription à la plateforme</Typography>
      <form onSubmit={handleSubmit}>
        <TextField label="Nom" name="nom" value={form.nom} onChange={handleChange} fullWidth sx={{ mb: 2 }} required />
        <TextField label="Email" name="email" value={form.email} onChange={handleChange} fullWidth sx={{ mb: 2 }} required type="email" />
        <TextField label="Mot de passe" name="password" value={form.password} onChange={handleChange} fullWidth sx={{ mb: 2 }} required type="password" />
        <Typography variant="subtitle1" sx={{ mt: 2 }}>Choix de l'abonnement :</Typography>
        <Select name="abonnement" value={form.abonnement} onChange={handleChange} fullWidth sx={{ mb: 2 }}>
          {ABONNEMENTS.map(opt => (
            <MenuItem key={opt.value} value={opt.value}>{opt.label}</MenuItem>
          ))}
        </Select>
        <Typography variant="subtitle1" sx={{ mt: 2 }}>Rôle utilisateur :</Typography>
        <Select name="role" value={form.role} onChange={handleChange} fullWidth sx={{ mb: 2 }}>
          <MenuItem value="USER">Utilisateur</MenuItem>
          <MenuItem value="ADMIN">Administrateur</MenuItem>
        </Select>
        <Typography variant="subtitle1" sx={{ mt: 2 }}>Modules dédiés :</Typography>
        <FormGroup>
          {MODULES.map(mod => (
            <FormControlLabel
              key={mod.value}
              control={<Checkbox checked={form.modules.includes(mod.value)} onChange={() => handleModuleChange(mod.value)} />}
              label={mod.label}
            />
          ))}
        </FormGroup>
        <Button type="submit" variant="contained" color="primary" sx={{ mt: 3 }}>S'inscrire</Button>
      </form>
    </Paper>
  );
};

export default InscriptionPage;
