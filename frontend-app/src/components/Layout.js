import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import {
  Box,
  Drawer,
  AppBar,
  Toolbar,
  Typography,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  ListSubheader,
  Button,
  Container,
} from '@mui/material';
import { logout } from '../utils/auth';
import LanguageSwitcher from './LanguageSwitcher';

const drawerWidth = 240;

const Layout = ({ children }) => {
  const { t } = useTranslation();

  const menuSections = [
    {
      title: t('navigation.dashboard'),
      items: [
        { to: '/', label: t('navigation.dashboard') }
      ]
    },
    {
      title: 'Base',
      items: [
        { to: '/comptes', label: t('navigation.accounts') },
        { to: '/ecritures', label: t('navigation.entries') },
        { to: '/entreprises', label: t('navigation.companies') },
        { to: '/journaux', label: t('navigation.journals') },
        { to: '/systemes-comptables', label: t('navigation.accountingSystems') },
        { to: '/plan-comptable', label: t('navigation.chartOfAccounts') },
      ]
    },
    {
      title: t('navigation.balanceSheet'),
      items: [
        { to: '/balance', label: t('navigation.balance') },
        { to: '/grand-livre', label: t('navigation.generalLedger') },
        { to: '/bilan', label: t('navigation.balanceSheet') },
        { to: '/compte-resultat', label: t('navigation.incomeStatement') },
        { to: '/notes-annexes', label: t('navigation.notes') },
        { to: '/etats-financiers-ohada', label: t('navigation.ohadaFinancialStatements') },
      ]
    },
    {
      title: 'Modules IA',
      items: [
        { to: '/ged', label: t('navigation.ged') },
        { to: '/iaec/TEST', label: 'IAEC - Assistant IA' },
        { to: '/sycebnl', label: 'SYCEBNL' },
      ]
    },
  ];

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar
        position="fixed"
        sx={{
          width: `calc(100% - ${drawerWidth}px)`,
          ml: `${drawerWidth}px`,
          bgcolor: 'white',
          color: 'text.primary',
          boxShadow: 1,
        }}
      >
        <Toolbar>
          <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
            {t('app.title')}
          </Typography>
          <Box sx={{ display: 'flex', gap: 1, alignItems: 'center' }}>
            <Button
              component={RouterLink}
              to="/login"
              size="small"
            >
              {t('auth.login')}
            </Button>
            <Button
              component={RouterLink}
              to="/register"
              size="small"
            >
              {t('auth.register')}
            </Button>
            <Button
              onClick={logout}
              size="small"
              variant="outlined"
            >
              {t('navigation.logout')}
            </Button>
            <LanguageSwitcher />
          </Box>
        </Toolbar>
      </AppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            bgcolor: '#2c3e50',
            color: '#fff',
          },
        }}
        variant="permanent"
        anchor="left"
      >
        <Toolbar sx={{ minHeight: 64, display: 'flex', alignItems: 'center', px: 3 }}>
          <Typography variant="h6" noWrap component="div" sx={{ color: '#ecf0f1' }}>
            E-COMPTA IA
          </Typography>
        </Toolbar>
        <List>
          {menuSections.map((section, sectionIndex) => (
            <React.Fragment key={sectionIndex}>
              {section.title !== t('navigation.dashboard') && (
                <ListSubheader
                  sx={{
                    bgcolor: 'transparent',
                    color: '#95a5a6',
                    fontSize: '0.75rem',
                    textTransform: 'uppercase',
                    fontWeight: 'bold',
                    lineHeight: '32px',
                  }}
                >
                  {section.title}
                </ListSubheader>
              )}
              {section.items.map((item, itemIndex) => (
                <ListItem key={itemIndex} disablePadding>
                  <ListItemButton
                    component={RouterLink}
                    to={item.to}
                    sx={{
                      color: '#fff',
                      '&:hover': {
                        bgcolor: 'rgba(255, 255, 255, 0.1)',
                      },
                    }}
                  >
                    <ListItemText primary={item.label} />
                  </ListItemButton>
                </ListItem>
              ))}
            </React.Fragment>
          ))}
        </List>
      </Drawer>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          bgcolor: 'background.default',
          p: 3,
          mt: 8,
        }}
      >
        <Container maxWidth="xl">
          {children}
        </Container>
      </Box>
    </Box>
  );
};

export default Layout;
