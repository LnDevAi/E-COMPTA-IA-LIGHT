
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Comptes from './pages/Comptes';
import Ecritures from './pages/Ecritures';
import Entreprises from './pages/Entreprises';
import Journaux from './pages/Journaux';
import SystemesComptables from './pages/SystemesComptables';
import PlanComptable from './pages/PlanComptable';

function App() {
  return (
    <Router>
      <Routes>
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
              {require('./pages/Balance').default()}
            </Layout>
          }
        />
        <Route
          path="/grand-livre"
          element={
            <Layout>
              {require('./pages/GrandLivre').default()}
            </Layout>
          }
        />
        <Route
          path="/bilan"
          element={
            <Layout>
              {require('./pages/Bilan').default()}
            </Layout>
          }
        />
        <Route
          path="/notes-annexes"
          element={
            <Layout>
              {require('./pages/NotesAnnexes').default()}
            </Layout>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
