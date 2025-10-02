
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
import SycebnlPage from './pages/SycebnlPage';
import InscriptionPage from './pages/InscriptionPage';

function App() {
  return (
    <Router>
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
        <Route
          path="/ged"
          element={
            <Layout>
              {require('./modules/ged/GedModule').default()}
            </Layout>
          }
        />
        <Route
          path="/iaec/:pieceId"
          element={
            <Layout>
              {(() => {
                const { useParams } = require('react-router-dom');
                const IaecModule = require('./modules/iaec/IaecModule').default;
                const Wrapper = () => {
                  const { pieceId } = useParams();
                  return <IaecModule pieceId={pieceId} />;
                };
                return <Wrapper />;
              })()}
            </Layout>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
