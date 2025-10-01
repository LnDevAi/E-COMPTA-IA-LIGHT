import { render, screen } from '@testing-library/react';
import App from './App';

test('renders E-COMPTA IA heading', () => {
  render(<App />);
  const headingElement = screen.getByText(/Bienvenue sur E-COMPTA IA/i);
  expect(headingElement).toBeInTheDocument();
});
