import React from 'react';


const Dashboard = () => {
  return (
    <div style={{ textAlign: 'center', marginTop: '3rem' }}>
      <h2>Bienvenue sur E-COMPTA IA</h2>
      <p>Votre plateforme intelligente de gestion comptable.</p>
      <div style={{ marginTop: 30, fontSize: 18 }}>
        <ul style={{ listStyle: 'none', padding: 0 }}>
          <li>• Visualisez vos comptes et écritures</li>
          <li>• Gérez vos entreprises et journaux</li>
          <li>• Accédez à votre plan comptable</li>
          <li>• Sécurisez vos accès avec l’authentification</li>
        </ul>
      </div>
    </div>
  );
};

export default Dashboard;
