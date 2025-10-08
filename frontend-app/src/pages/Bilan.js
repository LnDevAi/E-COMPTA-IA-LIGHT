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
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper
} from '@mui/material';

const SYSTEMES = [
  { value: 'NORMAL', label: 'AUDCIF Normal' },
  { value: 'MINIMAL', label: 'AUDCIF Minimal' }
];

export default function Bilan() {
  const [systeme, setSysteme] = useState('NORMAL');
  const [bilan, setBilan] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const fetchBilan = async () => {
    setLoading(true);
    setError('');
    try {
      const res = await axios.get('/api/financial/bilan', {
        params: { systeme }
      });
      setBilan(res.data);
    } catch (e) {
      setError(e.response?.data?.message || 'Erreur lors du chargement du bilan');
      setBilan(null);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom>Bilan</Typography>
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
      <Button variant="contained" onClick={fetchBilan} disabled={loading} sx={{ ml: 2 }}>
        Générer
      </Button>
      {loading && <CircularProgress sx={{ mt: 2 }} />}
      {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
      {bilan && (
        <Box mt={3}>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Rubrique</TableCell>
                  <TableCell align="right">Montant</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {bilan.rubriques?.map((r, idx) => (
                  <TableRow key={idx}>
                    <TableCell>{r.nom}</TableCell>
                    <TableCell align="right">{r.montant}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Box>
      )}
    </Box>
  );
}
