import React, { useState } from 'react';
import axios from '../services/axios';
// ...existing code...
import dayjs from 'dayjs';

function exportToExcel(data) {
  const header = ['Numéro Compte', 'Libellé', 'Total Débit', 'Total Crédit', 'Solde Débiteur', 'Solde Créditeur'];
  const rows = data.map(row => [
    row.numeroCompte,
    row.libelle,
    row.totalDebit,
    row.totalCredit,
    row.soldeDebiteur,
    row.soldeCrediteur
  ]);
  let csvContent = 'data:text/csv;charset=utf-8,' + header.join(';') + '\n';
  rows.forEach(r => {
    csvContent += r.join(';') + '\n';
  });
  const encodedUri = encodeURI(csvContent);
  const link = document.createElement('a');
  link.setAttribute('href', encodedUri);
  link.setAttribute('download', 'balance.csv');
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

export default function Balance() {
  const [dateDebut, setDateDebut] = useState(dayjs().startOf('year').format('YYYY-MM-DD'));
  const [dateFin, setDateFin] = useState(dayjs().format('YYYY-MM-DD'));
  const [systeme, setSysteme] = useState('NORMAL');
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [totals, setTotals] = useState({
    totalDebit: 0,
    totalCredit: 0,
    soldeDebiteur: 0,
    soldeCrediteur: 0
  });

  const fetchBalance = async () => {
    setLoading(true);
    setError('');
    try {
      const res = await axios.get('/api/rapports/balance', {
        params: {
          dateDebut,
          dateFin,
          systeme
        }
      });
      setData(res.data);
      // Calcul des totaux
      let totalDebit = 0, totalCredit = 0, soldeDebiteur = 0, soldeCrediteur = 0;
      res.data.forEach(row => {
        totalDebit += row.totalDebit || 0;
        totalCredit += row.totalCredit || 0;
        soldeDebiteur += row.soldeDebiteur || 0;
        soldeCrediteur += row.soldeCrediteur || 0;
      });
      setTotals({ totalDebit, totalCredit, soldeDebiteur, soldeCrediteur });
    } catch (e) {
      setError(e.response?.data?.message || 'Erreur lors du chargement de la balance');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Balance Comptable</h2>
      <form style={{ display: 'flex', gap: '1rem', marginBottom: '1rem', alignItems: 'center' }} onSubmit={e => { e.preventDefault(); fetchBalance(); }}>
        <label>
          Système comptable :
          <select value={systeme} onChange={e => setSysteme(e.target.value)} style={{ marginLeft: 8 }}>
            <option value="NORMAL">AUDCIF Normal</option>
            <option value="MINIMAL">AUDCIF Minimal</option>
          </select>
        </label>
        <label>
          Date début :
          <input type="date" value={dateDebut} onChange={e => setDateDebut(e.target.value)} style={{ marginLeft: 8 }} />
        </label>
        <label>
          Date fin :
          <input type="date" value={dateFin} onChange={e => setDateFin(e.target.value)} style={{ marginLeft: 8 }} />
        </label>
        <button type="submit" disabled={loading}>Charger</button>
        <button type="button" onClick={() => exportToExcel(data)} disabled={!data.length}>Exporter Excel</button>
      </form>
      {loading && <span>Chargement...</span>}
      {error && <div style={{ color: 'red', marginBottom: '1rem' }}>{error}</div>}
      <div style={{ overflowX: 'auto', marginTop: '1rem' }}>
        <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff' }}>
          <thead>
            <tr style={{ background: '#eee' }}>
              <th style={{ padding: '8px', border: '1px solid #ccc' }}>Numéro Compte</th>
              <th style={{ padding: '8px', border: '1px solid #ccc' }}>Libellé</th>
              <th style={{ padding: '8px', border: '1px solid #ccc' }}>Total Débit</th>
              <th style={{ padding: '8px', border: '1px solid #ccc' }}>Total Crédit</th>
              <th style={{ padding: '8px', border: '1px solid #ccc' }}>Solde Débiteur</th>
              <th style={{ padding: '8px', border: '1px solid #ccc' }}>Solde Créditeur</th>
            </tr>
          </thead>
          <tbody>
            {data.map((row, idx) => (
              <tr key={idx}>
                <td style={{ padding: '8px', border: '1px solid #ccc' }}>{row.numeroCompte}</td>
                <td style={{ padding: '8px', border: '1px solid #ccc' }}>{row.libelle}</td>
                <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}>{row.totalDebit}</td>
                <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}>{row.totalCredit}</td>
                <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}>{row.soldeDebiteur}</td>
                <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}>{row.soldeCrediteur}</td>
              </tr>
            ))}
          </tbody>
          <tfoot>
            <tr style={{ background: '#f5f5f5' }}>
              <td colSpan={2} style={{ padding: '8px', border: '1px solid #ccc' }}><b>Totaux</b></td>
              <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}><b>{totals.totalDebit}</b></td>
              <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}><b>{totals.totalCredit}</b></td>
              <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}><b>{totals.soldeDebiteur}</b></td>
              <td style={{ padding: '8px', border: '1px solid #ccc', textAlign: 'right' }}><b>{totals.soldeCrediteur}</b></td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
  );
}
