// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import '@testing-library/jest-dom';

// Enable test mode for Dashboard component
global.__TEST__ = true;

// Mock fetch for API calls not using axios
global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    json: () => Promise.resolve([]),
    text: () => Promise.resolve(''),
    status: 200,
    statusText: 'OK'
  })
);

// Clean up after each test
afterEach(() => {
  jest.clearAllMocks();
});
