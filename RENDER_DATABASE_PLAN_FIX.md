# Render Plan Fix - Documentation

## Issue Description

The deployment to Render was failing with errors related to deprecated plans.

### Initial Issue (PR #16)
```
A render.yaml file was found, but there was an issue.

databases[0].plan
Legacy Postgres plans, including 'starter', are no longer supported for new databases. 
Update your database instance to a new plan in your render.yaml
```

### Subsequent Issue (Current)
After fixing the database plan, both frontend and backend web services were failing to deploy. Render has deprecated the "starter" plan for web services as well.

## Root Cause

Render deprecated the "starter" plan for both:
1. **PostgreSQL databases** - Fixed in PR #16
2. **Web services** - Fixed in this update

The `render.yaml` configuration file was using the legacy `starter` plan which is no longer accepted for new service instances.

## Solution

Updated all plans in `render.yaml` from `starter` to `free`, which is Render's current free tier plan.

## Changes Made

### 1. render.yaml - Database (line 49) - PR #16
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

### 2. render.yaml - Backend Service (line 7) - Current Fix
```yaml
# Before:
services:
  - type: web
    name: ecompta-backend
    env: docker
    dockerfilePath: ./Dockerfile.backend
    plan: starter  # ❌ Deprecated

# After:
services:
  - type: web
    name: ecompta-backend
    env: docker
    dockerfilePath: ./Dockerfile.backend
    plan: free  # ✅ Current free tier
```

### 3. render.yaml - Frontend Service (line 40) - Current Fix
```yaml
# Before:
  - type: web
    name: ecompta-frontend
    env: docker
    dockerfilePath: ./frontend-app/Dockerfile
    plan: starter  # ❌ Deprecated

# After:
  - type: web
    name: ecompta-frontend
    env: docker
    dockerfilePath: ./frontend-app/Dockerfile
    plan: free  # ✅ Current free tier
```

### 4. Documentation Updates

#### DEPLOYMENT_GUIDE.md
- Updated manual configuration section to show `Instance type: Free` instead of `Starter` for all services
- Added note: "Note: 'Starter' plan is deprecated for both web services and databases"
- Added important note in automatic deployment section explaining the change

#### VERIFICATION_WORKFLOWS.md
- Updated all plan references from "starter" to "free"

#### CORRECTIONS_PR2.md
- Updated historical reference from "Ajout du plan `starter`" to "Base de données avec plan `free` (mis à jour depuis `starter` qui est déprécié)"

## Validation

All validation checks pass:

✅ YAML syntax is valid  
✅ Database plan is set to 'free'  
✅ Backend service plan is set to 'free'  
✅ Frontend service plan is set to 'free'  
✅ No deprecated 'starter' plan in configuration  
✅ All required services configured (backend, frontend, postgres)  
✅ All database connection variables present  
✅ Database references use 'fromDatabase' correctly  

## Deployment Impact

- **Web Services**: Backend and frontend services now use the `free` plan (required change)
- **Database**: Uses the `free` plan (required change from PR #16)
- **No Breaking Changes**: All environment variables and service configurations remain the same
- **Cost**: No cost change - both `starter` (legacy) and `free` (current) are/were free tiers

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
