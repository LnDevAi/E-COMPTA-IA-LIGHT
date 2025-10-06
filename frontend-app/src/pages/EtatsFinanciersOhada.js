import React, { useState, useEffect, useCallback } from 'react';
import apiClient from '../config/api';

export default function EtatsFinanciersOhada() {
  const [entreprises, setEntreprises] = useState([]);
  const [selectedEntreprise, setSelectedEntreprise] = useState(null);
  const [exercice, setExercice] = useState('');
  const [etats, setEtats] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    loadEntreprises();
  }, []);

  const loadEntreprises = async () => {
    try {
      const response = await apiClient.get('/api/entreprises');
      setEntreprises(response.data);
    } catch (err) {
      console.error('Erreur lors du chargement des entreprises:', err);
      setError('Erreur lors du chargement des entreprises');
    }
  };

  const handleGenerer = useCallback(async () => {
    if (!selectedEntreprise || !exercice) {
      setError('Veuillez sélectionner une entreprise et saisir l\'exercice');
      return;
    }

    setLoading(true);
    setError('');
    try {
      const response = await apiClient.post('/api/etats-financiers-ohada/generer', 
        selectedEntreprise,
        { params: { exercice } }
      );
      setEtats(response.data);
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.response?.data || err.message;
      setError(`Erreur lors de la génération des états financiers : ${errorMessage}`);
    } finally {
      setLoading(false);
    }
  }, [selectedEntreprise, exercice]);

  return (
    <div>
      <h1>États Financiers OHADA</h1>
      <p style={{ marginBottom: 20, color: '#666' }}>
        Génération des états financiers conformes au référentiel OHADA
      </p>

      <div style={{ marginBottom: 20, padding: 15, background: '#f8f9fa', borderRadius: 4 }}>
        <div style={{ marginBottom: 10 }}>
          <label style={{ display: 'block', marginBottom: 5, fontWeight: 'bold' }}>
            Entreprise:
          </label>
          <select
            value={selectedEntreprise?.id || ''}
            onChange={(e) => {
              const entreprise = entreprises.find(ent => ent.id === e.target.value);
              setSelectedEntreprise(entreprise);
            }}
            style={{ width: '100%', padding: 8, fontSize: 14 }}
          >
            <option value="">-- Sélectionner une entreprise --</option>
            {entreprises.map(ent => (
              <option key={ent.id} value={ent.id}>
                {ent.nom} - {ent.typeOhada || 'N/A'}
              </option>
            ))}
          </select>
        </div>

        <div style={{ marginBottom: 10 }}>
          <label style={{ display: 'block', marginBottom: 5, fontWeight: 'bold' }}>
            Exercice (ex: 2024):
          </label>
          <input
            type="text"
            value={exercice}
            onChange={(e) => setExercice(e.target.value)}
            placeholder="2024"
            style={{ width: '100%', padding: 8, fontSize: 14 }}
          />
        </div>

        <button 
          onClick={handleGenerer} 
          disabled={loading || !selectedEntreprise || !exercice}
          style={{ 
            padding: '10px 20px', 
            background: '#007bff', 
            color: 'white', 
            border: 'none', 
            borderRadius: 4,
            cursor: loading ? 'not-allowed' : 'pointer',
            opacity: (loading || !selectedEntreprise || !exercice) ? 0.6 : 1
          }}
        >
          {loading ? 'Génération en cours...' : 'Générer les États Financiers OHADA'}
        </button>
      </div>

      {error && (
        <div style={{ 
          color: 'red', 
          marginBottom: 20, 
          padding: 15, 
          background: '#ffe6e6', 
          borderRadius: 4,
          border: '1px solid #ffcccc'
        }}>
          {error}
        </div>
      )}

      {etats && (
        <div style={{ marginTop: 30 }}>
          <h2>États Financiers Générés</h2>
          <p style={{ marginBottom: 20, color: '#666' }}>
            Exercice: {etats.exercice} | Type: {etats.typeEtat || 'N/A'}
          </p>

          {/* Bilan OHADA */}
          {etats.bilan && (
            <div style={{ marginBottom: 30 }}>
              <h3 style={{ 
                background: '#007bff', 
                color: 'white', 
                padding: 10, 
                borderRadius: '4px 4px 0 0' 
              }}>
                Bilan OHADA
              </h3>
              <div style={{ border: '1px solid #ddd', padding: 15 }}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 20 }}>
                  {/* Actif */}
                  <div>
                    <h4>ACTIF</h4>
                    <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: 13 }}>
                      <thead>
                        <tr style={{ background: '#f0f0f0' }}>
                          <th style={{ border: '1px solid #ddd', padding: 6, textAlign: 'left' }}>
                            Poste
                          </th>
                          <th style={{ border: '1px solid #ddd', padding: 6, textAlign: 'right' }}>
                            Montant
                          </th>
                        </tr>
                      </thead>
                      <tbody>
                        {etats.bilan.actif && etats.bilan.actif.map((item) => (
                          <tr key={`actif-${item.libelle}`}>
                            <td style={{ border: '1px solid #ddd', padding: 6 }}>
                              {item.libelle}
                            </td>
                            <td style={{ border: '1px solid #ddd', padding: 6, textAlign: 'right' }}>
                              {item.montant?.toFixed(2)}
                            </td>
                          </tr>
                        ))}
                        <tr style={{ fontWeight: 'bold', background: '#f8f9fa' }}>
                          <td style={{ border: '1px solid #ddd', padding: 6 }}>
                            Total Actif
                          </td>
                          <td style={{ border: '1px solid #ddd', padding: 6, textAlign: 'right' }}>
                            {etats.bilan.totalActif?.toFixed(2)}
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>

                  {/* Passif */}
                  <div>
                    <h4>PASSIF</h4>
                    <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: 13 }}>
                      <thead>
                        <tr style={{ background: '#f0f0f0' }}>
                          <th style={{ border: '1px solid #ddd', padding: 6, textAlign: 'left' }}>
                            Poste
                          </th>
                          <th style={{ border: '1px solid #ddd', padding: 6, textAlign: 'right' }}>
                            Montant
                          </th>
                        </tr>
                      </thead>
                      <tbody>
                        {etats.bilan.passif && etats.bilan.passif.map((item) => (
                          <tr key={`passif-${item.libelle}`}>
                            <td style={{ border: '1px solid #ddd', padding: 6 }}>
                              {item.libelle}
                            </td>
                            <td style={{ border: '1px solid #ddd', padding: 6, textAlign: 'right' }}>
                              {item.montant?.toFixed(2)}
                            </td>
                          </tr>
                        ))}
                        <tr style={{ fontWeight: 'bold', background: '#f8f9fa' }}>
                          <td style={{ border: '1px solid #ddd', padding: 6 }}>
                            Total Passif
                          </td>
                          <td style={{ border: '1px solid #ddd', padding: 6, textAlign: 'right' }}>
                            {etats.bilan.totalPassif?.toFixed(2)}
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* Compte de Résultat OHADA */}
          {etats.compteResultat && (
            <div style={{ marginBottom: 30 }}>
              <h3 style={{ 
                background: '#28a745', 
                color: 'white', 
                padding: 10, 
                borderRadius: '4px 4px 0 0' 
              }}>
                Compte de Résultat OHADA
              </h3>
              <div style={{ border: '1px solid #ddd', padding: 15 }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: 13 }}>
                  <thead>
                    <tr style={{ background: '#f0f0f0' }}>
                      <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'left' }}>
                        Rubrique
                      </th>
                      <th style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                        Montant
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {etats.compteResultat.lignes && etats.compteResultat.lignes.map((ligne) => (
                      <tr key={`cr-ligne-${ligne.libelle}`}>
                        <td style={{ border: '1px solid #ddd', padding: 8 }}>
                          {ligne.libelle}
                        </td>
                        <td style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                          {ligne.montant?.toFixed(2)}
                        </td>
                      </tr>
                    ))}
                    <tr style={{ fontWeight: 'bold', background: '#e8f4f8' }}>
                      <td style={{ border: '1px solid #ddd', padding: 8 }}>
                        Résultat Net
                      </td>
                      <td style={{ 
                        border: '1px solid #ddd', 
                        padding: 8, 
                        textAlign: 'right',
                        color: etats.compteResultat.resultatNet >= 0 ? 'green' : 'red',
                        fontWeight: 'bold'
                      }}>
                        {etats.compteResultat.resultatNet?.toFixed(2)}
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {/* Tableau des Flux de Trésorerie */}
          {etats.tableauFlux && (
            <div style={{ marginBottom: 30 }}>
              <h3 style={{ 
                background: '#17a2b8', 
                color: 'white', 
                padding: 10, 
                borderRadius: '4px 4px 0 0' 
              }}>
                Tableau des Flux de Trésorerie
              </h3>
              <div style={{ border: '1px solid #ddd', padding: 15 }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: 13 }}>
                  <tbody>
                    {etats.tableauFlux.lignes && etats.tableauFlux.lignes.map((ligne) => (
                      <tr key={`flux-${ligne.libelle}`}>
                        <td style={{ border: '1px solid #ddd', padding: 8 }}>
                          {ligne.libelle}
                        </td>
                        <td style={{ border: '1px solid #ddd', padding: 8, textAlign: 'right' }}>
                          {ligne.montant?.toFixed(2)}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {/* Informations complémentaires */}
          <div style={{ 
            marginTop: 30, 
            padding: 15, 
            background: '#f8f9fa', 
            borderRadius: 4,
            fontSize: 13
          }}>
            <h4>Informations</h4>
            <ul>
              <li>Type d'entreprise: {selectedEntreprise?.typeOhada || 'N/A'}</li>
              <li>Système comptable: OHADA</li>
              <li>Exercice: {etats.exercice}</li>
              <li>Date de génération: {new Date().toLocaleDateString()}</li>
            </ul>
          </div>
        </div>
      )}
    </div>
  );
}
