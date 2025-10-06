import React from 'react';
import { Link } from 'react-router-dom';

const Layout = ({ children }) => (
  <div style={{ display: 'flex', minHeight: '100vh' }}>
    <aside style={{ width: 240, background: '#2c3e50', color: '#fff', padding: '2rem 1rem', overflowY: 'auto' }}>
      <h2 style={{ color: '#ecf0f1', fontSize: 22, marginBottom: 30 }}>E-COMPTA IA</h2>
      <nav style={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
        <Link to="/" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4, transition: 'background 0.2s' }}>Dashboard</Link>
        
        <div style={{ marginTop: 16, marginBottom: 8, fontSize: 12, color: '#95a5a6', textTransform: 'uppercase', fontWeight: 'bold', paddingLeft: 12 }}>Base</div>
        <Link to="/comptes" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Comptes</Link>
        <Link to="/ecritures" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Écritures</Link>
        <Link to="/entreprises" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Entreprises</Link>
        <Link to="/journaux" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Journaux</Link>
        <Link to="/systemes-comptables" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Systèmes comptables</Link>
        <Link to="/plan-comptable" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Plan comptable</Link>
        
        <div style={{ marginTop: 16, marginBottom: 8, fontSize: 12, color: '#95a5a6', textTransform: 'uppercase', fontWeight: 'bold', paddingLeft: 12 }}>États Financiers</div>
        <Link to="/balance" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Balance</Link>
        <Link to="/grand-livre" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Grand Livre</Link>
        <Link to="/bilan" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Bilan</Link>
        <Link to="/compte-resultat" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Compte de Résultat</Link>
        <Link to="/notes-annexes" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Notes Annexes</Link>
        <Link to="/etats-financiers-ohada" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>États Financiers OHADA</Link>
        
        <div style={{ marginTop: 16, marginBottom: 8, fontSize: 12, color: '#95a5a6', textTransform: 'uppercase', fontWeight: 'bold', paddingLeft: 12 }}>Modules IA</div>
        <Link to="/ged" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>GED - Documents</Link>
        <Link to="/iaec/TEST" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>IAEC - Assistant IA</Link>
        <Link to="/sycebnl" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>SYCEBNL</Link>
        
        <div style={{ marginTop: 16, marginBottom: 8, fontSize: 12, color: '#95a5a6', textTransform: 'uppercase', fontWeight: 'bold', paddingLeft: 12 }}>Utilisateur</div>
        <Link to="/inscription" style={{ color: '#fff', textDecoration: 'none', padding: '8px 12px', borderRadius: 4 }}>Inscription</Link>
      </nav>
    </aside>
    <main style={{ flex: 1, background: '#f8f9fa', padding: '2rem' }}>
      <header style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginBottom: 20 }}>
        <Link to="/login" style={{ marginRight: 10 }}>Connexion</Link>
        <Link to="/register">Inscription</Link>
        <button style={{ marginLeft: 20 }} onClick={() => { localStorage.removeItem('token'); window.location.href = '/login'; }}>Déconnexion</button>
      </header>
      {children}
    </main>
  </div>
);

export default Layout;
