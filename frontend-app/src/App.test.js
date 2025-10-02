
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';

test('renders E-COMPTA IA heading on dashboard', () => {
  render(<App />);
  const headingElement = screen.getByText(/Bienvenue sur E-COMPTA IA/i);
  expect(headingElement).toBeInTheDocument();
});
