import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#007bff',
      light: '#4dabff',
      dark: '#0056b3',
      contrastText: '#fff',
    },
    secondary: {
      main: '#6c757d',
      light: '#a6adb4',
      dark: '#495057',
      contrastText: '#fff',
    },
    success: {
      main: '#28a745',
      light: '#5cb85c',
      dark: '#1e7e34',
    },
    error: {
      main: '#dc3545',
      light: '#e57373',
      dark: '#c82333',
    },
    warning: {
      main: '#ffc107',
      light: '#ffd54f',
      dark: '#f57c00',
    },
    info: {
      main: '#17a2b8',
      light: '#5bc0de',
      dark: '#117a8b',
    },
    background: {
      default: '#f8f9fa',
      paper: '#ffffff',
    },
    text: {
      primary: '#212529',
      secondary: '#6c757d',
    },
  },
  typography: {
    fontFamily: [
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
    ].join(','),
    h1: {
      fontSize: '2.5rem',
      fontWeight: 600,
      lineHeight: 1.2,
    },
    h2: {
      fontSize: '2rem',
      fontWeight: 600,
      lineHeight: 1.2,
    },
    h3: {
      fontSize: '1.75rem',
      fontWeight: 600,
      lineHeight: 1.2,
    },
    h4: {
      fontSize: '1.5rem',
      fontWeight: 600,
      lineHeight: 1.2,
    },
    h5: {
      fontSize: '1.25rem',
      fontWeight: 600,
      lineHeight: 1.2,
    },
    h6: {
      fontSize: '1rem',
      fontWeight: 600,
      lineHeight: 1.2,
    },
    body1: {
      fontSize: '1rem',
      lineHeight: 1.5,
    },
    body2: {
      fontSize: '0.875rem',
      lineHeight: 1.5,
    },
    button: {
      textTransform: 'none',
      fontWeight: 500,
    },
  },
  spacing: 8,
  shape: {
    borderRadius: 4,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 4,
          padding: '8px 16px',
          boxShadow: 'none',
          '&:hover': {
            boxShadow: 'none',
          },
        },
        contained: {
          '&:hover': {
            boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
          },
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiOutlinedInput-root': {
            borderRadius: 4,
          },
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: 4,
        },
        elevation1: {
          boxShadow: '0 1px 4px rgba(0,0,0,0.1)',
        },
        elevation2: {
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        },
        elevation3: {
          boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
        },
      },
    },
    MuiTableHead: {
      styleOverrides: {
        root: {
          '& .MuiTableCell-head': {
            fontWeight: 600,
            backgroundColor: '#f0f0f0',
          },
        },
      },
    },
    MuiAlert: {
      styleOverrides: {
        root: {
          borderRadius: 4,
        },
      },
    },
  },
});

export default theme;
