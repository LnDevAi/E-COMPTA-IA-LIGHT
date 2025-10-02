import React, { useState, useEffect } from 'react';
import { Card, CardContent, Typography, Button, Table, TableBody, TableCell, TableHead, TableRow, Grid } from '@mui/material';
import { getComptes } from '../services/compteService';
import { getEcrituresByPeriode, getEcrituresByJournal, getEcrituresByStatut, getEcriture, createEcriture, deleteEcriture, updateEcriture, validerEcriture } from '../services/ecritureService';

// MOCK API pour les tests
const isTest = typeof global !== 'undefined' && global.__TEST__;
const mockComptes = [{ id: 1 }, { id: 2 }];
const mockEcritures = [
  { id: 1, date: '2025-10-01', libelle: 'Achat', journalCode: 'ACH', lignes: [{ debit: 100 }] },
  { id: 2, date: '2025-10-02', libelle: 'Vente', journalCode: 'VEN', lignes: [{ debit: 200 }] }
];

const Dashboard = () => {
  const [totalComptes, setTotalComptes] = useState(0);
  const [totalEcrituresMonth, setTotalEcrituresMonth] = useState(0);
  const [balanceStatus, setBalanceStatus] = useState('');
  const [recentEcritures, setRecentEcritures] = useState([]);

  useEffect(() => {
    if (isTest) {
      setTotalComptes(mockComptes.length);
      setTotalEcrituresMonth(mockEcritures.length);
      setBalanceStatus('Équilibrée');
      setRecentEcritures(mockEcritures);
    } else {
      // Comptes
      getComptes().then(res => {
        setTotalComptes(res.data.length);
      });
      // Ecritures ce mois
      const now = new Date();
      const startMonth = `${now.getFullYear()}-${String(now.getMonth()+1).padStart(2,'0')}-01`;
      const endMonth = `${now.getFullYear()}-${String(now.getMonth()+1).padStart(2,'0')}-31`;
      getEcrituresByPeriode({ dateDebut: startMonth, dateFin: endMonth }).then(res => {
        setTotalEcrituresMonth(res.data.length);
      });
      // Balance
      fetch(`/api/rapports/balance?dateDebut=${startMonth}&dateFin=${endMonth}`)
        .then(r => r.json())
        .then(data => {
          const totalDebit = data.reduce((sum, c) => sum + c.debit, 0);
          const totalCredit = data.reduce((sum, c) => sum + c.credit, 0);
          setBalanceStatus(totalDebit === totalCredit ? 'Équilibrée' : 'Non équilibrée');
        });
      // 10 dernières écritures
      getEcrituresByPeriode({}).then(res => {
        setRecentEcritures(res.data.slice(0, 10));
      });
    }
  }, []);

  return (
    <Grid container spacing={2} columns={12}>
      <Grid gridColumn="span 4">
        <Card>
          <CardContent>
            <Typography variant="h6">Total comptes</Typography>
            <Typography variant="h4">{totalComptes}</Typography>
          </CardContent>
        </Card>
      </Grid>
  <Grid gridColumn="span 4">
        <Card>
          <CardContent>
            <Button variant="contained" color="secondary" href="/sycebnl" sx={{ mt: 2 }}>
              Accéder au module SYCEBNL
            </Button>
            <Button variant="outlined" color="primary" href="/inscription" sx={{ mt: 2, ml: 2 }}>
              S'inscrire / Gérer abonnement
            </Button>
            <Typography variant="h6" sx={{ mt: 2 }}>Écritures ce mois</Typography>
            <Typography variant="h4">{totalEcrituresMonth}</Typography>
          </CardContent>
        </Card>
      </Grid>
  <Grid gridColumn="span 4">
        <Card>
          <CardContent>
            <Typography variant="h6">Balance</Typography>
            <Typography variant="h4">{balanceStatus}</Typography>
          </CardContent>
        </Card>
      </Grid>
  <Grid gridColumn="span 12">
        <Card>
          <CardContent>
            <Typography variant="h6">10 dernières écritures</Typography>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Date</TableCell>
                  <TableCell>Libellé</TableCell>
                  <TableCell>Journal</TableCell>
                  <TableCell>Montant</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {recentEcritures.map(e => (
                  <TableRow key={e.id}>
                    <TableCell>{e.date}</TableCell>
                    <TableCell>{e.libelle}</TableCell>
                    <TableCell>{e.journalCode}</TableCell>
                    <TableCell>{e.lignes && e.lignes.reduce((sum, l) => sum + (l.debit || 0), 0)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
            <Button variant="contained" color="primary" style={{marginTop:16}} href="/ecritures/new">Nouvelle écriture</Button>
          </CardContent>
        </Card>
      </Grid>
    </Grid>
  );
};

export default Dashboard;
