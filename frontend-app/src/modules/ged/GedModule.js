import React, { useState } from 'react';
import axios from 'axios';

export default function GedModule() {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');

  const handleFileChange = e => setFile(e.target.files[0]);
  const handleUpload = async () => {
    const formData = new FormData();
    formData.append('file', file);
    try {
      await axios.post('/api/ged/upload', formData);
      setMessage('Import réussi !');
      localStorage.setItem('gedStatus', 'Import GED réussi');
      window.dispatchEvent(new Event('ged-uploaded'));
    } catch (e) {
      setMessage('Erreur import : ' + (e.response?.data || e.message));
      localStorage.setItem('gedStatus', 'Erreur import GED');
      window.dispatchEvent(new Event('ged-uploaded'));
    }
  };

  return (
    <div>
      <h2>Importer une pièce comptable</h2>
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleUpload} disabled={!file}>Télécharger</button>
      <p>{message}</p>
    </div>
  );
}
