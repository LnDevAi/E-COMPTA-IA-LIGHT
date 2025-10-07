import React, { useState, useEffect } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useTranslation } from 'react-i18next';
import {
  Box,
  Typography,
  TextField,
  Button,
  Alert,
  CircularProgress,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  MenuItem,
  Grid,
} from '@mui/material';
import apiClient from '../config/api';
import { etatsFinanciersOhadaSchema } from '../validation/schemas';

export default function EtatsFinanciersOhada() {
  const { t } = useTranslation();
  const [entreprises, setEntreprises] = useState([]);
  const [etats, setEtats] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const { control, handleSubmit, formState: { errors }, watch } = useForm({
    resolver: yupResolver(etatsFinanciersOhadaSchema),
    defaultValues: {
      entrepriseId: '',
      exercice: ''
    }
  });

  const entrepriseId = watch('entrepriseId');
  const exercice = watch('exercice');

  useEffect(() => {
    const loadEntreprises = async () => {
      try {
        const response = await apiClient.get('/api/entreprises');
        setEntreprises(response.data);
      } catch (err) {
        console.error('Error loading companies:', err);
        setError(t('ohadaFinancialStatements.errors.loadCompaniesError'));
      }
    };
    
    loadEntreprises();
  }, [t]);

  const onSubmit = async (data) => {
    const selectedEntreprise = entreprises.find(ent => ent.id === data.entrepriseId);
    
    setLoading(true);
    setError('');
    try {
      const response = await apiClient.post('/api/etats-financiers-ohada/generer', 
        selectedEntreprise,
        { params: { exercice: data.exercice } }
      );
      setEtats(response.data);
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.response?.data || err.message;
      setError(t('ohadaFinancialStatements.errors.generationError', { message: errorMessage }));
    } finally {
      setLoading(false);
    }
  };

  const selectedEntreprise = entreprises.find(ent => ent.id === entrepriseId);

  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        {t('ohadaFinancialStatements.title')}
      </Typography>
      <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
        {t('ohadaFinancialStatements.subtitle')}
      </Typography>

      <Paper sx={{ p: 3, mb: 3 }}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <Controller
              name="entrepriseId"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  select
                  label={t('ohadaFinancialStatements.company')}
                  error={!!errors.entrepriseId}
                  helperText={errors.entrepriseId ? t(errors.entrepriseId.message) : ''}
                  fullWidth
                >
                  <MenuItem value="">
                    {t('ohadaFinancialStatements.selectCompany')}
                  </MenuItem>
                  {entreprises.map(ent => (
                    <MenuItem key={ent.id} value={ent.id}>
                      {ent.nom} - {ent.typeOhada || 'N/A'}
                    </MenuItem>
                  ))}
                </TextField>
              )}
            />
            <Controller
              name="exercice"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label={t('ohadaFinancialStatements.fiscalYear')}
                  placeholder={t('ohadaFinancialStatements.fiscalYearPlaceholder')}
                  error={!!errors.exercice}
                  helperText={errors.exercice ? t(errors.exercice.message) : ''}
                  fullWidth
                />
              )}
            />
          </Box>
          <Button
            type="submit"
            variant="contained"
            disabled={loading}
            startIcon={loading && <CircularProgress size={20} />}
            sx={{ mt: 2 }}
          >
            {loading ? t('ohadaFinancialStatements.generating') : t('ohadaFinancialStatements.generate')}
          </Button>
        </form>
      </Paper>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {etats && (
        <Box>
          <Typography variant="h5" component="h2" gutterBottom>
            {t('ohadaFinancialStatements.title')}
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
            {t('ohadaFinancialStatements.fiscalYear')}: {etats.exercice} | Type: {etats.typeEtat || 'N/A'}
          </Typography>

          {/* Bilan OHADA */}
          {etats.bilan && (
            <Paper sx={{ mb: 3 }}>
              <Box sx={{ bgcolor: 'primary.main', color: 'white', p: 1.5 }}>
                <Typography variant="h6">
                  {t('ohadaFinancialStatements.balanceSheet')}
                </Typography>
              </Box>
              <Box sx={{ p: 2 }}>
                <Grid container spacing={3}>
                  {/* Actif */}
                  <Grid item xs={12} md={6}>
                    <Typography variant="subtitle1" gutterBottom fontWeight="bold">
                      {t('ohadaFinancialStatements.assets')}
                    </Typography>
                    <TableContainer>
                      <Table size="small">
                        <TableHead>
                          <TableRow>
                            <TableCell>Poste</TableCell>
                            <TableCell align="right">Montant</TableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {etats.bilan.actif && etats.bilan.actif.map((item, index) => (
                            <TableRow key={`actif-${index}`}>
                              <TableCell>{item.libelle}</TableCell>
                              <TableCell align="right">
                                {item.montant?.toFixed(2)}
                              </TableCell>
                            </TableRow>
                          ))}
                          <TableRow sx={{ fontWeight: 'bold', bgcolor: 'grey.100' }}>
                            <TableCell>Total Actif</TableCell>
                            <TableCell align="right">
                              {etats.bilan.totalActif?.toFixed(2)}
                            </TableCell>
                          </TableRow>
                        </TableBody>
                      </Table>
                    </TableContainer>
                  </Grid>

                  {/* Passif */}
                  <Grid item xs={12} md={6}>
                    <Typography variant="subtitle1" gutterBottom fontWeight="bold">
                      {t('ohadaFinancialStatements.liabilities')}
                    </Typography>
                    <TableContainer>
                      <Table size="small">
                        <TableHead>
                          <TableRow>
                            <TableCell>Poste</TableCell>
                            <TableCell align="right">Montant</TableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {etats.bilan.passif && etats.bilan.passif.map((item, index) => (
                            <TableRow key={`passif-${index}`}>
                              <TableCell>{item.libelle}</TableCell>
                              <TableCell align="right">
                                {item.montant?.toFixed(2)}
                              </TableCell>
                            </TableRow>
                          ))}
                          <TableRow sx={{ fontWeight: 'bold', bgcolor: 'grey.100' }}>
                            <TableCell>Total Passif</TableCell>
                            <TableCell align="right">
                              {etats.bilan.totalPassif?.toFixed(2)}
                            </TableCell>
                          </TableRow>
                        </TableBody>
                      </Table>
                    </TableContainer>
                  </Grid>
                </Grid>
              </Box>
            </Paper>
          )}

          {/* Compte de Résultat OHADA */}
          {etats.compteResultat && (
            <Paper sx={{ mb: 3 }}>
              <Box sx={{ bgcolor: 'success.main', color: 'white', p: 1.5 }}>
                <Typography variant="h6">
                  {t('ohadaFinancialStatements.incomeStatement')}
                </Typography>
              </Box>
              <Box sx={{ p: 2 }}>
                <TableContainer>
                  <Table size="small">
                    <TableHead>
                      <TableRow>
                        <TableCell>Rubrique</TableCell>
                        <TableCell align="right">Montant</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {etats.compteResultat.lignes && etats.compteResultat.lignes.map((ligne, index) => (
                        <TableRow key={`cr-ligne-${index}`}>
                          <TableCell>{ligne.libelle}</TableCell>
                          <TableCell align="right">
                            {ligne.montant?.toFixed(2)}
                          </TableCell>
                        </TableRow>
                      ))}
                      <TableRow sx={{ fontWeight: 'bold', bgcolor: 'info.lighter' }}>
                        <TableCell>Résultat Net</TableCell>
                        <TableCell 
                          align="right"
                          sx={{ 
                            color: etats.compteResultat.resultatNet >= 0 ? 'success.main' : 'error.main',
                            fontWeight: 'bold'
                          }}
                        >
                          {etats.compteResultat.resultatNet?.toFixed(2)}
                        </TableCell>
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
              </Box>
            </Paper>
          )}

          {/* Tableau des Flux de Trésorerie */}
          {etats.tableauFlux && (
            <Paper sx={{ mb: 3 }}>
              <Box sx={{ bgcolor: 'info.main', color: 'white', p: 1.5 }}>
                <Typography variant="h6">
                  {t('ohadaFinancialStatements.cashFlow')}
                </Typography>
              </Box>
              <Box sx={{ p: 2 }}>
                <TableContainer>
                  <Table size="small">
                    <TableBody>
                      {etats.tableauFlux.lignes && etats.tableauFlux.lignes.map((ligne, index) => (
                        <TableRow key={`flux-${index}`}>
                          <TableCell>{ligne.libelle}</TableCell>
                          <TableCell align="right">
                            {ligne.montant?.toFixed(2)}
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </Box>
            </Paper>
          )}

          {/* Informations complémentaires */}
          <Paper sx={{ p: 2, bgcolor: 'grey.50' }}>
            <Typography variant="h6" gutterBottom>
              Informations
            </Typography>
            <Box component="ul" sx={{ fontSize: '0.875rem' }}>
              <li>Type d'entreprise: {selectedEntreprise?.typeOhada || 'N/A'}</li>
              <li>Système comptable: OHADA</li>
              <li>{t('ohadaFinancialStatements.fiscalYear')}: {etats.exercice}</li>
              <li>Date de génération: {new Date().toLocaleDateString()}</li>
            </Box>
          </Paper>
        </Box>
      )}
    </Box>
  );
}
