import React, { useState } from 'react';
import axios from '../services/axios';
import {
  Box,
  Typography,
  CircularProgress,
  Alert,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Card,
  CardContent,
  Button
} from '@mui/material';

const SYSTEMES = [
  { value: 'NORMAL', label: 'OHADA Normal' },
  { value: 'MINIMAL', label: 'OHADA Minimal' }
];

export default function NotesAnnexes() {
  const [systeme, setSysteme] = useState('NORMAL');
  const [notes, setNotes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const fetchNotes = async () => {
    setLoading(true);
    setError('');
    try {
      const res = await axios.get('/api/financial/notes-annexes', {
        params: { systeme }
      });
      setNotes(res.data);
    } catch (e) {
      setError(e.response?.data?.message || 'Erreur lors du chargement des notes annexes');
      setNotes([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom>Notes Annexes</Typography>
      <FormControl sx={{ minWidth: 220, mb: 2 }}>
        <InputLabel id="systeme-label">Système comptable</InputLabel>
        <Select
          labelId="systeme-label"
          value={systeme}
          label="Système comptable"
          onChange={e => setSysteme(e.target.value)}
        >
          {SYSTEMES.map(s => (
            <MenuItem key={s.value} value={s.value}>{s.label}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <Button variant="contained" onClick={fetchNotes} disabled={loading} sx={{ ml: 2 }}>
        Générer
      </Button>
      {loading && <CircularProgress sx={{ mt: 2 }} />}
      {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
      <Box mt={3}>
        {notes.map(note => (
          <Card key={note.id || note.titre} sx={{ mb: 2 }}>
            <CardContent>
              <Typography variant="h6">{note.titre}</Typography>
              <Typography>{note.contenu}</Typography>
            </CardContent>
          </Card>
        ))}
      </Box>
    </Box>
  );
}
