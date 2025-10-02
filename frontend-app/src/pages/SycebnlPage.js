import React, { useEffect, useState } from 'react';
import { Button, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, CircularProgress } from '@mui/material';
import { getSycebnlOrganizations, createSycebnlOrganization, exportSycebnlPdf } from '../services/sycebnlService';

const SycebnlPage = () => {
  const [organizations, setOrganizations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    getSycebnlOrganizations()
      .then(data => setOrganizations(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const handleExportPdf = () => {
    exportSycebnlPdf()
      .then(() => window.alert('Export PDF lancé !'))
      .catch(err => window.alert('Erreur export PDF : ' + err.message));
  };

  return (
    <Paper sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>Module SYCEBNL</Typography>
      <Button variant="contained" color="primary" onClick={handleExportPdf} sx={{ mb: 2 }}>Exporter PDF</Button>
      {loading ? <CircularProgress /> : error ? <Typography color="error">{error}</Typography> : (
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Nom</TableCell>
                <TableCell>Pays</TableCell>
                <TableCell>Numéro d'enregistrement</TableCell>
                <TableCell>Date de création</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {organizations.map(org => (
                <TableRow key={org.id}>
                  <TableCell>{org.id}</TableCell>
                  <TableCell>{org.name}</TableCell>
                  <TableCell>{org.country}</TableCell>
                  <TableCell>{org.registrationNumber}</TableCell>
                  <TableCell>{org.createdAt}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Paper>
  );
};

export default SycebnlPage;
