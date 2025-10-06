import React, { useState } from 'react';
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
} from '@mui/material';
import apiClient from '../config/api';
import { compteResultatSchema } from '../validation/schemas';

export default function CompteResultat() {
  const { t } = useTranslation();
  const [compteResultat, setCompteResultat] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const { control, handleSubmit, formState: { errors }, watch } = useForm({
    resolver: yupResolver(compteResultatSchema),
    defaultValues: {
      dateDebut: '',
      dateFin: ''
    }
  });

  const dateDebut = watch('dateDebut');
  const dateFin = watch('dateFin');

  const onSubmit = async (data) => {
    setLoading(true);
    setError('');
    try {
      const response = await apiClient.get('/api/financial/compte-resultat', {
        params: {
          dateDebut: data.dateDebut,
          dateFin: data.dateFin
        }
      });
      setCompteResultat(response.data);
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.response?.data || err.message;
      setError(t('incomeStatement.errors.generationError', { message: errorMessage }));
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        {t('incomeStatement.title')}
      </Typography>
      
      <Paper sx={{ p: 3, mb: 3 }}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Box sx={{ display: 'flex', gap: 2, mb: 2, flexWrap: 'wrap' }}>
            <Controller
              name="dateDebut"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label={t('incomeStatement.startDate')}
                  type="date"
                  InputLabelProps={{ shrink: true }}
                  error={!!errors.dateDebut}
                  helperText={errors.dateDebut ? t(errors.dateDebut.message) : ''}
                  sx={{ flexGrow: 1, minWidth: 200 }}
                />
              )}
            />
            <Controller
              name="dateFin"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label={t('incomeStatement.endDate')}
                  type="date"
                  InputLabelProps={{ shrink: true }}
                  error={!!errors.dateFin}
                  helperText={errors.dateFin ? t(errors.dateFin.message) : ''}
                  sx={{ flexGrow: 1, minWidth: 200 }}
                />
              )}
            />
          </Box>
          <Button
            type="submit"
            variant="contained"
            disabled={loading}
            startIcon={loading && <CircularProgress size={20} />}
          >
            {loading ? t('incomeStatement.generating') : t('incomeStatement.generate')}
          </Button>
        </form>
      </Paper>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {compteResultat && (
        <Box>
          <Typography variant="h5" component="h2" gutterBottom>
            {t('incomeStatement.title')} - {t('incomeStatement.period', { 
              startDate: dateDebut, 
              endDate: dateFin 
            })}
          </Typography>
          
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>
              {t('incomeStatement.expenses')}
            </Typography>
            <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>{t('incomeStatement.account')}</TableCell>
                    <TableCell>{t('incomeStatement.label')}</TableCell>
                    <TableCell align="right">{t('incomeStatement.amount')}</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {compteResultat.charges && compteResultat.charges.map((charge, index) => (
                    <TableRow key={`charge-${index}`}>
                      <TableCell>{charge.compte}</TableCell>
                      <TableCell>{charge.libelle}</TableCell>
                      <TableCell align="right">
                        {charge.montant?.toFixed(2)} €
                      </TableCell>
                    </TableRow>
                  ))}
                  <TableRow sx={{ fontWeight: 'bold', bgcolor: 'grey.100' }}>
                    <TableCell colSpan={2}>
                      {t('incomeStatement.totalExpenses')}
                    </TableCell>
                    <TableCell align="right">
                      {compteResultat.totalCharges?.toFixed(2)} €
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </TableContainer>
          </Box>

          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>
              {t('incomeStatement.revenues')}
            </Typography>
            <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>{t('incomeStatement.account')}</TableCell>
                    <TableCell>{t('incomeStatement.label')}</TableCell>
                    <TableCell align="right">{t('incomeStatement.amount')}</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {compteResultat.produits && compteResultat.produits.map((produit, index) => (
                    <TableRow key={`produit-${index}`}>
                      <TableCell>{produit.compte}</TableCell>
                      <TableCell>{produit.libelle}</TableCell>
                      <TableCell align="right">
                        {produit.montant?.toFixed(2)} €
                      </TableCell>
                    </TableRow>
                  ))}
                  <TableRow sx={{ fontWeight: 'bold', bgcolor: 'grey.100' }}>
                    <TableCell colSpan={2}>
                      {t('incomeStatement.totalRevenues')}
                    </TableCell>
                    <TableCell align="right">
                      {compteResultat.totalProduits?.toFixed(2)} €
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </TableContainer>
          </Box>

          <Paper sx={{ p: 3, bgcolor: 'info.lighter' }}>
            <Typography variant="h6" gutterBottom>
              {t('incomeStatement.result')}
            </Typography>
            <Typography variant="h5" component="div" sx={{ fontWeight: 'bold' }}>
              {t('incomeStatement.netResult')}:{' '}
              <Box
                component="span"
                sx={{ 
                  color: compteResultat.resultatNet >= 0 ? 'success.main' : 'error.main' 
                }}
              >
                {compteResultat.resultatNet?.toFixed(2)} €
              </Box>
            </Typography>
            <Typography 
              sx={{ 
                mt: 1, 
                color: compteResultat.resultatNet >= 0 ? 'success.main' : 'error.main',
                fontWeight: 'medium'
              }}
            >
              {compteResultat.resultatNet >= 0 
                ? `✓ ${t('incomeStatement.profit')}`
                : `✗ ${t('incomeStatement.loss')}`
              }
            </Typography>
          </Paper>
        </Box>
      )}
    </Box>
  );
}
