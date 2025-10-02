import React, { useState } from 'react';
import axios from 'axios';

export default function IaecModule({ pieceId = 'TEST' }) {
  const [result, setResult] = useState('');
  const [ocr, setOcr] = useState('');
  const [ecritures, setEcritures] = useState([]);
  const handleAnalyze = async () => {
    try {
      const res = await axios.post('/api/iaec/analyze', pieceId);
      setResult('Proposition générée.');
      setOcr('Lecture IA : ' + JSON.stringify(res.data));
      setEcritures(res.data.lignes || []);
      localStorage.setItem('iaecStatus', 'Analyse IAEC réussie');
      window.dispatchEvent(new Event('iaec-analyzed'));
    } catch (e) {
      setResult('Erreur analyse : ' + (e.response?.data || e.message));
      localStorage.setItem('iaecStatus', 'Erreur analyse IAEC');
      window.dispatchEvent(new Event('iaec-analyzed'));
    }
  };
  return (
    <div>
      <h2>Assistant IA Expert Comptable</h2>
      <button onClick={handleAnalyze}>Analyser la pièce</button>
      <p>{result}</p>
      <div style={{marginTop:16}}>
        <h3>Lecture OCR</h3>
        <p>{ocr}</p>
        <h3>Proposition d'écritures</h3>
        <table>
          <thead>
            <tr><th>Compte</th><th>Libellé</th><th>Débit</th><th>Crédit</th></tr>
          </thead>
          <tbody>
            {ecritures.map((e, i) => (
              <tr key={i}>
                <td>{e.compte}</td>
                <td>{e.libelle}</td>
                <td>{e.debit}</td>
                <td>{e.credit}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
