# Render Database Plan Fix - Documentation

## Issue Description

The deployment to Render was failing with the following error:

```
A render.yaml file was found, but there was an issue.

databases[0].plan
Legacy Postgres plans, including 'starter', are no longer supported for new databases. 
Update your database instance to a new plan in your render.yaml
```

## Root Cause

Render deprecated the "starter" plan for PostgreSQL databases. The `render.yaml` configuration file was using the legacy `starter` plan which is no longer accepted for new database instances.

## Solution

Updated the database plan in `render.yaml` from `starter` to `free`, which is Render's current free tier plan for PostgreSQL databases.

## Changes Made

### 1. render.yaml (line 49)
```yaml
# Before:
databases:
  - name: postgres
    databaseName: ecomptaia
    user: ecomptaia
    plan: starter  # ❌ Deprecated

# After:
databases:
  - name: postgres
    databaseName: ecomptaia
    user: ecomptaia
    plan: free  # ✅ Current free tier
```

### 2. Documentation Updates

#### DEPLOYMENT_GUIDE.md
- Updated manual configuration section to show `Instance type: Free` instead of `Starter`
- Added note: "Note: 'Starter' plan is deprecated for databases"
- Added important note in automatic deployment section explaining the change

#### CORRECTIONS_PR2.md
- Updated historical reference from "Ajout du plan `starter`" to "Base de données avec plan `free` (mis à jour depuis `starter` qui est déprécié)"

## Validation

All validation checks pass:

✅ YAML syntax is valid  
✅ Database plan is set to 'free'  
✅ No deprecated 'starter' plan for database  
✅ All required services configured (backend, frontend, postgres)  
✅ All database connection variables present  
✅ Database references use 'fromDatabase' correctly  

## Deployment Impact

- **Web Services**: The backend and frontend services continue to use the `starter` plan for compute instances (this is still valid for web services)
- **Database**: Now uses the `free` plan (required change)
- **No Breaking Changes**: All environment variables and service configurations remain the same
- **Cost**: No cost change - both `starter` (legacy) and `free` (current) were/are free tiers

## Next Steps

The `render.yaml` file is now ready for deployment. Users can:

1. Go to https://dashboard.render.com
2. Click "New +" → "Blueprint"
3. Connect the GitHub repository
4. Render will automatically read the updated `render.yaml` and create services

The deployment should now proceed without the database plan error.

## References

- Render Documentation: https://render.com/docs/yaml-spec
- Render Database Plans: https://render.com/pricing#databases
- Issue: Legacy Postgres plans deprecation

## Date
2024 - Fix applied via Pull Request
