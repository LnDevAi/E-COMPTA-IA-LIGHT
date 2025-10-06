
import './App.css';
import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import ErrorBoundary from './components/ErrorBoundary';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Comptes from './pages/Comptes';
import Ecritures from './pages/Ecritures';
import Entreprises from './pages/Entreprises';
import Journaux from './pages/Journaux';
import SystemesComptables from './pages/SystemesComptables';
import PlanComptable from './pages/PlanComptable';
import SycebnlPage from './pages/SycebnlPage';
import InscriptionPage from './pages/InscriptionPage';
import CompteResultat from './pages/CompteResultat';
import EtatsFinanciersOhada from './pages/EtatsFinanciersOhada';

// Lazy loading pour les pages moins fréquemment utilisées
const Balance = lazy(() => import('./pages/Balance'));
const GrandLivre = lazy(() => import('./pages/GrandLivre'));
const Bilan = lazy(() => import('./pages/Bilan'));
const NotesAnnexes = lazy(() => import('./pages/NotesAnnexes'));
const GedModule = lazy(() => import('./modules/ged/GedModule'));
const IaecWrapper = lazy(() => import('./pages/IaecWrapper'));

// Composant de chargement
const LoadingFallback = () => (
  <div style={{ padding: '2rem', textAlign: 'center' }}>
    <p>Chargement...</p>
  </div>
);

function App() {
  return (
    <ErrorBoundary>
      <Router>
        <Suspense fallback={<LoadingFallback />}>
          <Routes>
          <Route
            path="/inscription"
            element={
              <Layout>
                <InscriptionPage />
              </Layout>
            }
          />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/"
          element={
            <Layout>
              <Dashboard />
            </Layout>
          }
        />
        <Route
          path="/comptes"
          element={
            <Layout>
              <Comptes />
            </Layout>
          }
        />
        <Route
          path="/ecritures"
          element={
            <Layout>
              <Ecritures />
            </Layout>
          }
        />
        <Route
          path="/entreprises"
          element={
            <Layout>
              <Entreprises />
            </Layout>
          }
        />
        <Route
          path="/journaux"
          element={
            <Layout>
              <Journaux />
            </Layout>
          }
        />
          <Route
            path="/sycebnl"
            element={
              <Layout>
                <SycebnlPage />
              </Layout>
            }
          />
        <Route
          path="/systemes-comptables"
          element={
            <Layout>
              <SystemesComptables />
            </Layout>
          }
        />
        <Route
          path="/plan-comptable"
          element={
            <Layout>
              <PlanComptable />
            </Layout>
          }
        />
        <Route
          path="/balance"
          element={
            <Layout>
              <Balance />
            </Layout>
          }
        />
        <Route
          path="/grand-livre"
          element={
            <Layout>
              <GrandLivre />
            </Layout>
          }
        />
        <Route
          path="/bilan"
          element={
            <Layout>
              <Bilan />
            </Layout>
          }
        />
        <Route
          path="/compte-resultat"
          element={
            <Layout>
              <CompteResultat />
            </Layout>
          }
        />
        <Route
          path="/etats-financiers-ohada"
          element={
            <Layout>
              <EtatsFinanciersOhada />
            </Layout>
          }
        />
        <Route
          path="/notes-annexes"
          element={
            <Layout>
              <NotesAnnexes />
            </Layout>
          }
        />
        <Route
          path="/ged"
          element={
            <Layout>
              <GedModule />
            </Layout>
          }
                />
        <Route
          path="/iaec/:pieceId"
          element={
            <Layout>
              <IaecWrapper />
            </Layout>
          }
        />
      </Routes>
      </Suspense>
    </Router>
    </ErrorBoundary>
  );
}

export default App;
