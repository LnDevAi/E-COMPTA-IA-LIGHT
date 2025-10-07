import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { I18nextProvider } from 'react-i18next';
import { ThemeProvider } from '@mui/material/styles';
import CompteResultat from './CompteResultat';
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

describe('CompteResultat', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders the component with form fields', () => {
    renderWithProviders(<CompteResultat />);
    
    expect(screen.getByRole('heading', { name: /Income Statement|Compte de Résultat/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/Start Date|Date de début/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/End Date|Date de fin/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Generate|Générer/i })).toBeInTheDocument();
  });

  it('displays validation error when submitting empty form', async () => {
    renderWithProviders(<CompteResultat />);
    
    const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
    fireEvent.click(submitButton);

    await waitFor(() => {
      // Should show validation errors - check for helper text or error messages
      const helperTexts = screen.queryAllByText(/Invalid date|Date invalide|required|requis/i);
      expect(helperTexts.length).toBeGreaterThan(0);
    }, { timeout: 3000 });
  });

  it('generates compte de resultat successfully', async () => {
    const mockData = {
      charges: [
        { compte: '6000', libelle: 'Achats', montant: 10000 }
      ],
      produits: [
        { compte: '7000', libelle: 'Ventes', montant: 15000 }
      ],
      totalCharges: 10000,
      totalProduits: 15000,
      resultatNet: 5000
    };

    apiClient.get.mockResolvedValueOnce({ data: mockData });

    renderWithProviders(<CompteResultat />);
    
    const startDateInput = screen.getByLabelText(/Start Date|Date de début/i);
    const endDateInput = screen.getByLabelText(/End Date|Date de fin/i);
    
    // Fill in dates
    fireEvent.change(startDateInput, { target: { value: '2024-01-01' } });
    fireEvent.change(endDateInput, { target: { value: '2024-12-31' } });

    // Wait for validation to complete
    await waitFor(() => {
      const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
      expect(submitButton).not.toBeDisabled();
    }, { timeout: 2000 });

    const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
    fireEvent.click(submitButton);

    // Wait for API call
    await waitFor(() => {
      expect(apiClient.get).toHaveBeenCalled();
    }, { timeout: 5000 });

    // Wait for results to be displayed
    await waitFor(() => {
      expect(screen.getByText(/Achats/i)).toBeInTheDocument();
    }, { timeout: 5000 });
    
    expect(screen.getByText(/Ventes/i)).toBeInTheDocument();
  });

  it('displays error message when API call fails', async () => {
    const errorMessage = 'Erreur serveur';
    apiClient.get.mockRejectedValueOnce({
      response: { data: { message: errorMessage } }
    });

    renderWithProviders(<CompteResultat />);
    
    const startDateInput = screen.getByLabelText(/Start Date|Date de début/i);
    const endDateInput = screen.getByLabelText(/End Date|Date de fin/i);
    
    fireEvent.change(startDateInput, { target: { value: '2024-01-01' } });
    fireEvent.change(endDateInput, { target: { value: '2024-12-31' } });

    const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByText(new RegExp(errorMessage, 'i'))).toBeInTheDocument();
    });
  });

  it('displays profit result correctly', async () => {
    const mockData = {
      charges: [],
      produits: [],
      totalCharges: 5000,
      totalProduits: 10000,
      resultatNet: 5000
    };

    apiClient.get.mockResolvedValueOnce({ data: mockData });

    renderWithProviders(<CompteResultat />);
    
    fireEvent.change(screen.getByLabelText(/Start Date|Date de début/i), { 
      target: { value: '2024-01-01' } 
    });
    fireEvent.change(screen.getByLabelText(/End Date|Date de fin/i), { 
      target: { value: '2024-12-31' } 
    });

    // Wait for validation
    await waitFor(() => {
      const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
      expect(submitButton).not.toBeDisabled();
    }, { timeout: 2000 });

    fireEvent.click(screen.getByRole('button', { name: /Generate|Générer/i }));

    await waitFor(() => {
      expect(apiClient.get).toHaveBeenCalled();
    }, { timeout: 5000 });

    await waitFor(() => {
      expect(screen.getByText(/Profit|Bénéfice/i)).toBeInTheDocument();
    }, { timeout: 5000 });
  });

  it('displays loss result correctly', async () => {
    const mockData = {
      charges: [],
      produits: [],
      totalCharges: 10000,
      totalProduits: 5000,
      resultatNet: -5000
    };

    apiClient.get.mockResolvedValueOnce({ data: mockData });

    renderWithProviders(<CompteResultat />);
    
    fireEvent.change(screen.getByLabelText(/Start Date|Date de début/i), { 
      target: { value: '2024-01-01' } 
    });
    fireEvent.change(screen.getByLabelText(/End Date|Date de fin/i), { 
      target: { value: '2024-12-31' } 
    });

    // Wait for validation
    await waitFor(() => {
      const submitButton = screen.getByRole('button', { name: /Generate|Générer/i });
      expect(submitButton).not.toBeDisabled();
    }, { timeout: 2000 });

    fireEvent.click(screen.getByRole('button', { name: /Generate|Générer/i }));

    await waitFor(() => {
      expect(apiClient.get).toHaveBeenCalled();
    }, { timeout: 5000 });

    await waitFor(() => {
      expect(screen.getByText(/Loss|Perte/i)).toBeInTheDocument();
    }, { timeout: 5000 });
  });
});
