import React from 'react';
import { Link } from 'react-router-dom';

const Layout = ({ children }) => (
  <div style={{ display: 'flex', minHeight: '100vh' }}>
    <aside style={{ width: 220, background: '#2c3e50', color: '#fff', padding: '2rem 1rem' }}>
      <h2 style={{ color: '#ecf0f1', fontSize: 22, marginBottom: 30 }}>E-COMPTA IA</h2>
      <nav style={{ display: 'flex', flexDirection: 'column', gap: 16 }}>
        <Link to="/" style={{ color: '#fff', textDecoration: 'none' }}>Dashboard</Link>
        <Link to="/comptes" style={{ color: '#fff', textDecoration: 'none' }}>Comptes</Link>
        <Link to="/ecritures" style={{ color: '#fff', textDecoration: 'none' }}>Écritures</Link>
        <Link to="/entreprises" style={{ color: '#fff', textDecoration: 'none' }}>Entreprises</Link>
        <Link to="/journaux" style={{ color: '#fff', textDecoration: 'none' }}>Journaux</Link>
        <Link to="/systemes-comptables" style={{ color: '#fff', textDecoration: 'none' }}>Systèmes comptables</Link>
        <Link to="/plan-comptable" style={{ color: '#fff', textDecoration: 'none' }}>Plan comptable</Link>
        <Link to="/balance" style={{ color: '#fff', textDecoration: 'none' }}>Balance</Link>
        <Link to="/grand-livre" style={{ color: '#fff', textDecoration: 'none' }}>Grand Livre</Link>
        <Link to="/bilan" style={{ color: '#fff', textDecoration: 'none' }}>Bilan</Link>
        <Link to="/notes-annexes" style={{ color: '#fff', textDecoration: 'none' }}>Notes Annexes</Link>
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
