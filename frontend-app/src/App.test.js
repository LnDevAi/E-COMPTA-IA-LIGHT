
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders E-COMPTA IA heading on dashboard', () => {
  render(<App />);
  const headingElement = screen.getByText(/E-COMPTA IA/i);
  expect(headingElement).toBeInTheDocument();
});
