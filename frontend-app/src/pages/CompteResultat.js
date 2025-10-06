import React, { useState, useCallback } from 'react';
import apiClient from '../config/api';

export default function CompteResultat() {
  const [dateDebut, setDateDebut] = useState('');
  const [dateFin, setDateFin] = useState('');
  const [compteResultat, setCompteResultat] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleGenerer = useCallback(async () => {
    if (!dateDebut || !dateFin) {
      setError('Veuillez saisir les dates de début et de fin');
      return;
    }

    setLoading(true);
    setError('');
    try {
      const response = await apiClient.get('/api/financial/compte-resultat', {
        params: {
          dateDebut,
          dateFin
        }
      });
      setCompteResultat(response.data);
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.response?.data || err.message;
      setError(`Erreur lors de la génération du compte de résultat : ${errorMessage}`);
    } finally {
      setLoading(false);
    }
  }, [dateDebut, dateFin]);

  return (
    <div>
      <h1>Compte de Résultat</h1>
      
      <div style={{ marginBottom: 20 }}>
        <div style={{ marginBottom: 10 }}>
          <label>Date de début: </label>
          <input
            type="date"
            value={dateDebut}
            onChange={(e) => setDateDebut(e.target.value)}
            style={{ marginLeft: 10, padding: 5 }}
          />
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Date de fin: </label>
          <input
            type="date"
            value={dateFin}
            onChange={(e) => setDateFin(e.target.value)}
            style={{ marginLeft: 10, padding: 5 }}
          />
        </div>
        <button onClick={handleGenerer} disabled={loading} style={{ padding: '8px 16px' }}>
          {loading ? 'Génération...' : 'Générer le Compte de Résultat'}
        </button>
      </div>

      {error && (
        <div style={{ color: 'red', marginBottom: 20, padding: 10, background: '#ffe6e6', borderRadius: 4 }}>
          {error}
        </div>
      )}

      {compteResultat && (
        <div style={{ marginTop: 20 }}>
          <h2>Compte de Résultat - Du {dateDebut} au {dateFin}</h2>
          
          <div style={{ marginBottom: 30 }}>
            <h3>Charges</h3>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr style={{ background: '#f0f0f0' }}>
                  <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'left' }}>Compte</th>
                  <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'left' }}>Libellé</th>
                  <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>Montant</th>
                </tr>
              </thead>
              <tbody>
                {compteResultat.charges && compteResultat.charges.map((charge) => (
                  <tr key={`charge-${charge.compte}-${charge.libelle}`}>
                    <td style={{ border: '1px solid #ddd', padding: 8 }}>{charge.compte}</td>
                    <td style={{ border: '1px solid #ddd', padding: 8 }}>{charge.libelle}</td>
                    <td style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                      {charge.montant?.toFixed(2)} €
                    </td>
                  </tr>
                ))}
                <tr style={{ fontWeight: 'bold', background: '#f8f9fa' }}>
                  <td colSpan="2" style={{ border: '1px solid #ddd', padding: 8 }}>
                    Total Charges
                  </td>
                  <td style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                    {compteResultat.totalCharges?.toFixed(2)} €
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div style={{ marginBottom: 30 }}>
            <h3>Produits</h3>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr style={{ background: '#f0f0f0' }}>
                  <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'left' }}>Compte</th>
                  <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'left' }}>Libellé</th>
                  <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>Montant</th>
                </tr>
              </thead>
              <tbody>
                {compteResultat.produits && compteResultat.produits.map((produit) => (
                  <tr key={`produit-${produit.compte}-${produit.libelle}`}>
                    <td style={{ border: '1px solid #ddd', padding: 8 }}>{produit.compte}</td>
                    <td style={{ border: '1px solid #ddd', padding: 8 }}>{produit.libelle}</td>
                    <td style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                      {produit.montant?.toFixed(2)} €
                    </td>
                  </tr>
                ))}
                <tr style={{ fontWeight: 'bold', background: '#f8f9fa' }}>
                  <td colSpan="2" style={{ border: '1px solid #ddd', padding: 8 }}>
                    Total Produits
                  </td>
                  <td style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                    {compteResultat.totalProduits?.toFixed(2)} €
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div style={{ marginTop: 30, padding: 15, background: '#e8f4f8', borderRadius: 4 }}>
            <h3>Résultat</h3>
            <div style={{ fontSize: 18, fontWeight: 'bold' }}>
              Résultat Net: {' '}
              <span style={{ color: compteResultat.resultatNet >= 0 ? 'green' : 'red' }}>
                {compteResultat.resultatNet?.toFixed(2)} €
              </span>
            </div>
            {compteResultat.resultatNet >= 0 ? (
              <p style={{ marginTop: 10, color: 'green' }}>✓ Bénéfice</p>
            ) : (
              <p style={{ marginTop: 10, color: 'red' }}>✗ Perte</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
