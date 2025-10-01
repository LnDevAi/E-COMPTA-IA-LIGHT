import React, { useState, useEffect } from 'react';
import axios from '../services/axios';
import {
  Box,
  Button,
  CircularProgress,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  TextField,
  Alert,
  MenuItem,
  Card,
  CardContent,
  Grid,
  Select,
  InputLabel,
  FormControl
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import dayjs from 'dayjs';

export default function GrandLivre() {
  const [comptes, setComptes] = useState([]);
  const [selectedCompte, setSelectedCompte] = useState('');
  const [systeme, setSysteme] = useState('NORMAL');
  const [dateDebut, setDateDebut] = useState(dayjs().startOf('year'));
  const [dateFin, setDateFin] = useState(dayjs());
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [summary, setSummary] = useState({ totalDebit: 0, totalCredit: 0, finalBalance: 0 });

  useEffect(() => {
    // Récupérer la liste des comptes
    axios.get('/api/comptes').then(res => {
      setComptes(res.data);
    }).catch(() => {
      setComptes([]);
    });
  }, []);

  const fetchGrandLivre = async () => {
    if (!selectedCompte) return;
    setLoading(true);
    setError('');
    try {
      const res = await axios.get('/api/rapports/grand-livre', {
        params: {
          numeroCompte: selectedCompte,
          dateDebut: dateDebut.format('YYYY-MM-DD'),
          dateFin: dateFin.format('YYYY-MM-DD'),
          systeme
        }
      });
      let runningBalance = 0;
      let totalDebit = 0, totalCredit = 0;
      const rows = res.data.map(row => {
        runningBalance += (row.debit || 0) - (row.credit || 0);
        totalDebit += row.debit || 0;
        totalCredit += row.credit || 0;
        return { ...row, solde: runningBalance };
      });
      setData(rows);
      setSummary({ totalDebit, totalCredit, finalBalance: runningBalance });
    } catch (e) {
      setError(e.response?.data?.message || 'Erreur lors du chargement du Grand Livre');
      setData([]);
      setSummary({ totalDebit: 0, totalCredit: 0, finalBalance: 0 });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom>Grand Livre</Typography>
      <Grid container spacing={2} mb={2}>
        <Grid item xs={12} md={3}>
          <FormControl fullWidth>
            <InputLabel id="systeme-label">Système comptable</InputLabel>
            <Select
              labelId="systeme-label"
              value={systeme}
              label="Système comptable"
              onChange={e => setSysteme(e.target.value)}
            >
              <MenuItem value="NORMAL">OHADA Normal</MenuItem>
              <MenuItem value="MINIMAL">OHADA Minimal</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} md={3}>
          <FormControl fullWidth>
            <InputLabel id="compte-label">Compte</InputLabel>
            <Select
              labelId="compte-label"
              value={selectedCompte}
              label="Compte"
              onChange={e => setSelectedCompte(e.target.value)}
            >
              {comptes.map(c => (
                <MenuItem key={c.numero} value={c.numero}>
                  {c.numero} - {c.libelle}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} md={2}>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              label="Date début"
              value={dateDebut}
              onChange={setDateDebut}
              renderInput={(params) => <TextField {...params} fullWidth />}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={12} md={2}>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              label="Date fin"
              value={dateFin}
              onChange={setDateFin}
              renderInput={(params) => <TextField {...params} fullWidth />}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={12} md={2}>
          <Button variant="contained" onClick={fetchGrandLivre} disabled={loading || !selectedCompte} fullWidth>
            Charger
          </Button>
        </Grid>
      </Grid>
      {loading && <CircularProgress />}
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
      {selectedCompte && (
        <Card sx={{ mb: 2 }}>
          <CardContent>
            <Typography variant="h6">Résumé du compte</Typography>
            <Typography>Débit total : <b>{summary.totalDebit}</b></Typography>
            <Typography>Crédit total : <b>{summary.totalCredit}</b></Typography>
            <Typography>Solde final : <b>{summary.finalBalance}</b></Typography>
          </CardContent>
        </Card>
      )}
      <TableContainer component={Paper} sx={{ mt: 2 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Date</TableCell>
              <TableCell>Numéro Écriture</TableCell>
              <TableCell>Libellé</TableCell>
              <TableCell align="right">Débit</TableCell>
              <TableCell align="right">Crédit</TableCell>
              <TableCell align="right">Solde</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row, idx) => (
              <TableRow key={idx}>
                <TableCell>{row.date}</TableCell>
                <TableCell>{row.numeroEcriture}</TableCell>
                <TableCell>{row.libelle}</TableCell>
                <TableCell align="right">{row.debit}</TableCell>
                <TableCell align="right">{row.credit}</TableCell>
                <TableCell align="right">{row.solde}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}
