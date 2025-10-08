## Multi-Tenancy Strategy

- **Model**: Single database, tenant scoping at entity level (company/organization ID)
- **Auth**: JWT contains tenant claims; middleware sets tenant context
- **Data Isolation**: All queries filtered by tenant; repositories enforce tenant predicates
- **Migrations**: Global schema, no per-tenant DDL
- **Rate Limits**: Per-tenant quotas aligned to plan
- **Backups**: Daily full, 7-day retention (Starter), 30-day (Growth), 90-day (Scale)

Operational Controls:
- Admin tooling to suspend tenant, reset password, rotate keys
- Export per-tenant data (CSV/JSON), on-demand
