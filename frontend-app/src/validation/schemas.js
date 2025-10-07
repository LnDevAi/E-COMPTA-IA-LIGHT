import * as yup from 'yup';

// Compte de Résultat validation schema
export const compteResultatSchema = yup.object({
  dateDebut: yup
    .date()
    .required('validation.required')
    .typeError('validation.date'),
  dateFin: yup
    .date()
    .required('validation.required')
    .typeError('validation.date')
    .min(yup.ref('dateDebut'), 'incomeStatement.errors.invalidDateRange')
});

// États Financiers OHADA validation schema
export const etatsFinanciersOhadaSchema = yup.object({
  entrepriseId: yup
    .string()
    .required('validation.required'),
  exercice: yup
    .string()
    .required('validation.required')
    .matches(/^\d{4}$/, 'ohadaFinancialStatements.errors.invalidYear')
});

// Generic form schemas
export const dateRangeSchema = yup.object({
  startDate: yup
    .date()
    .required('validation.required')
    .typeError('validation.date'),
  endDate: yup
    .date()
    .required('validation.required')
    .typeError('validation.date')
    .min(yup.ref('startDate'), 'validation.dateAfter')
});

export const emailSchema = yup.object({
  email: yup
    .string()
    .required('validation.required')
    .email('validation.email')
});

export const passwordSchema = yup.object({
  password: yup
    .string()
    .required('validation.required')
    .min(8, 'validation.minLength')
});
