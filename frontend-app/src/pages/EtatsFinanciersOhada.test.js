import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { I18nextProvider } from 'react-i18next';
import { ThemeProvider } from '@mui/material/styles';
import EtatsFinanciersOhada from './EtatsFinanciersOhada';
import apiClient from '../config/api';
import i18n from '../i18n/config';
import theme from '../theme/theme';

// Mock apiClient
jest.mock('../config/api');

const renderWithProviders = (component) => {
  return render(
    <I18nextProvider i18n={i18n}>
      <ThemeProvider theme={theme}>
        {component}
      </ThemeProvider>
    </I18nextProvider>
  );
};

describe('EtatsFinanciersOhada', () => {
  const mockEntreprises = [
    { id: '1', nom: 'Entreprise Test 1', typeOhada: 'SYSCOHADA' },
    { id: '2', nom: 'Entreprise Test 2', typeOhada: 'SYSCOHADA' }
  ];

  beforeEach(() => {
    jest.clearAllMocks();
    apiClient.get.mockResolvedValue({ data: mockEntreprises });
  });

  it('renders the component with form fields', async () => {
    renderWithProviders(<EtatsFinanciersOhada />);
    
    await waitFor(() => {
      expect(screen.getByText(/OHADA Financial Statements|États Financiers OHADA/i)).toBeInTheDocument();
    });
    
    expect(screen.getByLabelText(/Company|Entreprise/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Fiscal Year|Exercice/i)).toBeInTheDocument();
  });

  it('loads and displays companies on mount', async () => {
    renderWithProviders(<EtatsFinanciersOhada />);

    await waitFor(() => {
      expect(apiClient.get).toHaveBeenCalledWith('/api/entreprises');
    });

    const companySelect = screen.getByLabelText(/Company|Entreprise/i);
    fireEvent.mouseDown(companySelect);

    await waitFor(() => {
      expect(screen.getByText('Entreprise Test 1 - SYSCOHADA')).toBeInTheDocument();
      expect(screen.getByText('Entreprise Test 2 - SYSCOHADA')).toBeInTheDocument();
    });
  });

  it('displays validation error when submitting empty form', async () => {
    renderWithProviders(<EtatsFinanciersOhada />);
    
    await waitFor(() => {
      expect(apiClient.get).toHaveBeenCalled();
    });

    const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
    fireEvent.click(submitButton);

    await waitFor(() => {
      const errors = screen.queryAllByText(/required|requis/i);
      expect(errors.length).toBeGreaterThan(0);
    });
  });

  it('generates financial statements successfully', async () => {
    const mockEtats = {
      exercice: '2024',
      typeEtat: 'SYSCOHADA',
      bilan: {
        actif: [{ libelle: 'Actif immobilisé', montant: 100000 }],
        passif: [{ libelle: 'Capital', montant: 100000 }],
        totalActif: 100000,
        totalPassif: 100000
      },
      compteResultat: {
        lignes: [{ libelle: 'Chiffre d\'affaires', montant: 50000 }],
        resultatNet: 10000
      }
    };

    apiClient.get.mockResolvedValueOnce({ data: mockEntreprises });
    apiClient.post.mockResolvedValueOnce({ data: mockEtats });

    renderWithProviders(<EtatsFinanciersOhada />);

    await waitFor(() => {
      expect(screen.getByLabelText(/Company|Entreprise/i)).toBeInTheDocument();
    }, { timeout: 5000 });

    const companySelect = screen.getByLabelText(/Company|Entreprise/i);
    fireEvent.mouseDown(companySelect);
    
    await waitFor(() => {
      const option = screen.getByText('Entreprise Test 1 - SYSCOHADA');
      fireEvent.click(option);
    }, { timeout: 5000 });

    const yearInput = screen.getByLabelText(/Fiscal Year|Exercice/i);
    fireEvent.change(yearInput, { target: { value: '2024' } });

    // Wait for validation
    await waitFor(() => {
      const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
      expect(submitButton).not.toBeDisabled();
    }, { timeout: 2000 });

    const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(apiClient.post).toHaveBeenCalled();
    }, { timeout: 5000 });

    await waitFor(() => {
      expect(screen.getByText(/Actif immobilisé/i)).toBeInTheDocument();
    }, { timeout: 5000 });
    
    expect(screen.getByText(/Capital/i)).toBeInTheDocument();
  });

  it('displays error message when company loading fails', async () => {
    apiClient.get.mockRejectedValueOnce(new Error('Network error'));

    renderWithProviders(<EtatsFinanciersOhada />);

    await waitFor(() => {
      expect(screen.getByText(/Error loading companies|Erreur lors du chargement des entreprises/i)).toBeInTheDocument();
    });
  });

  it('displays error message when generation fails', async () => {
    const errorMessage = 'Erreur serveur';
    apiClient.get.mockResolvedValueOnce({ data: mockEntreprises });
    apiClient.post.mockRejectedValueOnce({
      response: { data: { message: errorMessage } }
    });

    renderWithProviders(<EtatsFinanciersOhada />);

    await waitFor(() => {
      expect(screen.getByLabelText(/Company|Entreprise/i)).toBeInTheDocument();
    });

    const companySelect = screen.getByLabelText(/Company|Entreprise/i);
    fireEvent.mouseDown(companySelect);
    fireEvent.click(screen.getByText('Entreprise Test 1 - SYSCOHADA'));

    const yearInput = screen.getByLabelText(/Fiscal Year|Exercice/i);
    fireEvent.change(yearInput, { target: { value: '2024' } });

    fireEvent.click(screen.getByRole('button', { name: /Generate|Générer/i }));

    await waitFor(() => {
      expect(screen.getByText(new RegExp(errorMessage, 'i'))).toBeInTheDocument();
    });
  });
});
