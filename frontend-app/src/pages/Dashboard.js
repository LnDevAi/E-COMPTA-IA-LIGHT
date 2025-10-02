import { Card, CardContent, Typography, Button, Table, TableBody, TableCell, TableHead, TableRow, Grid, Alert, Snackbar } from '@mui/material';
import { getComptes } from '../services/compteService';
import { getEcrituresByPeriode, getEcrituresByJournal, getEcrituresByStatut, getEcriture, createEcriture, deleteEcriture, updateEcriture, validerEcriture } from '../services/ecritureService';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const [totalComptes, setTotalComptes] = useState(0);
  const [totalEcrituresMonth, setTotalEcrituresMonth] = useState(0);
  const [balanceStatus, setBalanceStatus] = useState('');
  const [recentEcritures, setRecentEcritures] = useState([]);
  // Amélioration : affichage du statut d'import GED et IAEC
  const [gedStatus, setGedStatus] = useState('');
  const [iaecStatus, setIaecStatus] = useState('');
  const [snackOpen, setSnackOpen] = useState(false);
  const [snackMsg, setSnackMsg] = useState('');

  // Sécurité : vérification de l'authentification
  const navigate = useNavigate();
  useEffect(() => {
    const token = localStorage.getItem('jwt');
    if (!token) {
      navigate('/login');
    }
  }, [navigate]);

  useEffect(() => {
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
  }, []);

  // Amélioration : écoute des events de succès GED/IAEC via localStorage
  useEffect(() => {
    const gedListener = () => {
      const msg = localStorage.getItem('gedStatus');
      if (msg) {
        setGedStatus(msg);
        setSnackMsg(msg);
        setSnackOpen(true);
      }
    };
    const iaecListener = () => {
      const msg = localStorage.getItem('iaecStatus');
      if (msg) {
        setIaecStatus(msg);
        setSnackMsg(msg);
        setSnackOpen(true);
      }
    };
    window.addEventListener('ged-uploaded', gedListener);
    window.addEventListener('iaec-analyzed', iaecListener);
    return () => {
      window.removeEventListener('ged-uploaded', gedListener);
      window.removeEventListener('iaec-analyzed', iaecListener);
    };
  }, []);

  // Rôles : affichage conditionnel des boutons selon le rôle
  const userRole = localStorage.getItem('role') || 'USER';

  return (
    <React.Fragment>
      <Typography variant="h4" align="center" gutterBottom>
        Bienvenue sur E-COMPTA IA
      </Typography>
      <Snackbar open={snackOpen} autoHideDuration={4000} onClose={() => setSnackOpen(false)}>
        <Alert severity={snackMsg.includes('Erreur') ? 'error' : 'success'} sx={{ width: '100%' }}>
          {snackMsg}
        </Alert>
      </Snackbar>
      <Grid container spacing={2}>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Total comptes</Typography>
              <Typography variant="h4">{totalComptes}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Écritures ce mois</Typography>
              <Typography variant="h4">{totalEcrituresMonth}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Balance</Typography>
              <Typography variant="h4">{balanceStatus}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12}>
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
                    <TableRow key={e.id} hover>
                      <TableCell>{e.date}</TableCell>
                      <TableCell>{e.libelle}</TableCell>
                      <TableCell>{e.journalCode}</TableCell>
                      <TableCell>{e.lignes && e.lignes.reduce((sum, l) => sum + (l.debit || 0), 0)}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
              <Button variant="contained" color="primary" style={{marginTop:16}} href="/ecritures/new" disabled={userRole!=='ADMIN' && userRole!=='COMPTABLE'}>Nouvelle écriture</Button>
              <Button variant="contained" color="secondary" style={{marginTop:16, marginRight:8}} href="/ged" disabled={userRole==='VISITEUR'}>Importer une pièce (GED)</Button>
              <Button variant="contained" color="secondary" style={{marginTop:16}} href="/iaec/123" disabled={userRole==='VISITEUR'}>Assistant IA Expert Comptable</Button>
              <Button variant="outlined" color="success" style={{marginTop:16, marginLeft:8}} onClick={() => {
                const csv = recentEcritures.map(e => `${e.date},${e.libelle},${e.journalCode},${e.lignes && e.lignes.reduce((sum, l) => sum + (l.debit || 0), 0)}`).join('\n');
                const blob = new Blob([`Date,Libellé,Journal,Montant\n${csv}`], {type:'text/csv'});
                const url = URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'ecritures.csv';
                a.click();
                URL.revokeObjectURL(url);
              }}>Exporter les écritures (CSV)</Button>
              <Button variant="outlined" color="info" style={{marginTop:16, marginLeft:8}} onClick={() => {
                fetch('/api/ecritures/export/pdf', {
                  method: 'GET',
                  headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                  }
                })
                  .then(response => response.blob())
                  .then(blob => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'ecritures.pdf';
                    a.click();
                    window.URL.revokeObjectURL(url);
                  });
              }}>Exporter les écritures (PDF)</Button>
              {gedStatus && <Typography color="success.main">{gedStatus}</Typography>}
              {iaecStatus && <Typography color="info.main">{iaecStatus}</Typography>}
            </CardContent>
          </Card>
        </Grid>
        {logs.length > 0 && (
          <Grid item xs={12}>
            <Card style={{marginTop:32}}>
              <CardContent>
                <Typography variant="h6">Logs d'activité</Typography>
                <Table size="small">
                  <TableHead>
                    <TableRow>
                      <TableCell>Action</TableCell>
                      <TableCell>Date</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {logs.map((log, i) => (
                      <TableRow key={i}>
                        <TableCell>{log.action}</TableCell>
                        <TableCell>{log.date}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </Grid>
        )}
      </Grid>
    </React.Fragment>
  );
};

export default Dashboard;
