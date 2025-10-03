# Troubleshooting Render Deployment

This guide helps resolve common issues when deploying E-COMPTA-IA-LIGHT to Render.

## Table of Contents
- [Pre-Deployment Validation](#pre-deployment-validation)
- [Common Deployment Issues](#common-deployment-issues)
- [Database Issues](#database-issues)
- [Frontend Issues](#frontend-issues)
- [Logs and Debugging](#logs-and-debugging)

## Pre-Deployment Validation

Before deploying to Render, verify:

1. **render.yaml is valid**:
   ```bash
   # Check syntax
   cat render.yaml
   ```

2. **Docker builds work locally**:
   ```bash
   # Test backend build
   docker build -f Dockerfile.backend -t test-backend .
   
   # Test frontend build
   cd frontend-app && docker build -t test-frontend .
   ```

## Common Deployment Issues

### Issue 1: Blueprint Fails to Create Services

**Symptom**: Render shows errors when creating services from render.yaml

**Solutions**:
1. Check that `render.yaml` is at the root of the repository
2. Verify all database references use the correct name (e.g., `postgres`)
3. Ensure plan names are valid (`starter`, `standard`, etc.)

### Issue 2: Backend Build Fails

**Symptom**: Maven build fails during Docker image creation

**Common causes**:
1. **Missing dependencies**: Maven cannot download dependencies
   - Check Maven Central accessibility
   - Review build logs for specific errors

2. **JDK version mismatch**: 
   - Dockerfile uses JDK 17 (correct)
   - POM requires Java 17

**Solution**: The Dockerfile is already configured correctly with JDK 17

### Issue 3: Backend Starts but Crashes Immediately

**Symptom**: Build succeeds but service shows as "failed" or "crashed"

**Common causes**:

1. **Missing JWT_SECRET**:
   - Render should generate this automatically via `generateValue: true`
   - Check environment variables in Render dashboard
   - Manually set if needed (minimum 256 bits)

2. **Database connection fails**:
   - Database service not ready yet
   - Check database service is running
   - Verify database is in the same Render region

3. **Wrong Spring profile**:
   - Should be set to `prod` via `SPRING_PROFILES_ACTIVE=prod`
   - Check environment variables in Render dashboard

### Issue 4: Backend Runs but Shows Errors

**Symptom**: Service is running but logs show warnings or errors

**Check these**:
1. Database connection string format
2. All required environment variables are set
3. Database schema is being created (check `SPRING_JPA_HIBERNATE_DDL_AUTO=update`)

## Database Issues

### PostgreSQL Connection Problems

**Symptom**: "Connection refused" or "Authentication failed"

**Solutions**:

1. **Verify database is created**:
   - Check Render dashboard
   - Database service should be "Available"

2. **Check connection string**:
   - Render provides this via `fromDatabase` in render.yaml
   - Format should be: `jdbc:postgresql://host:port/database`
   - Username and password should match database credentials

3. **Network connectivity**:
   - Database and backend should be in the same Render environment
   - Check firewall rules (ipAllowList in render.yaml)

### Schema Generation Issues

**Symptom**: Errors about missing tables or columns

**Solutions**:
1. Check `SPRING_JPA_HIBERNATE_DDL_AUTO` is set to `update`
2. Check `SPRING_JPA_DATABASE_PLATFORM` is set to `org.hibernate.dialect.PostgreSQLDialect`
3. Review logs for schema creation errors

## Frontend Issues

### Build Fails

**Symptom**: npm build fails during Docker image creation

**Common causes**:
1. Missing dependencies in package.json
2. Node version mismatch

**Solutions**:
1. Verify all dependencies are in package.json
2. Dockerfile uses Node 22 (should work for most React apps)

### API Connection Issues

**Symptom**: Frontend loads but cannot connect to backend

**Solutions**:

1. **Check API URL**:
   - In render.yaml, `REACT_APP_API_URL` should point to backend service
   - Format: `https://ecompta-backend.onrender.com`
   - Update if you changed the backend service name

2. **CORS issues**:
   - Backend should allow requests from frontend domain
   - Check backend CORS configuration

3. **Build-time vs Runtime**:
   - React environment variables must be set at build time
   - Verify `REACT_APP_API_URL` is in envVars in render.yaml
   - ARG is passed in Dockerfile for build-time access

### Frontend Shows 404 or Cannot Access Backend

**Solutions**:
1. Verify backend service is running
2. Check backend URL is correct in frontend environment variables
3. Test backend endpoint directly: `curl https://your-backend.onrender.com/api/auth/login`

## Logs and Debugging

### Accessing Logs

1. **Via Render Dashboard**:
   - Go to your service
   - Click "Logs" tab
   - Select time range

2. **Via Render CLI**:
   ```bash
   render logs -s your-service-name --tail
   ```

### Key Log Messages to Look For

**Successful startup**:
```
Started EcomptaiaApplication in X.XXX seconds
Tomcat started on port 8080
```

**Database connected**:
```
HikariPool-1 - Start completed
Initialized JPA EntityManagerFactory
```

**Common error messages**:
- "Connection refused" → Database not accessible
- "Authentication failed" → Wrong database credentials
- "Unknown data type" → Dialect mismatch (should not happen with prod profile)
- "JWT_SECRET" missing → Environment variable not set

### Testing Backend Endpoints

```bash
# Health check (if actuator enabled)
curl https://your-backend.onrender.com/actuator/health

# Test authentication
curl -X POST https://your-backend.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"Test123!","role":"USER"}'

# Test login
curl -X POST https://your-backend.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"Test123!"}'
```

## Getting Help

If issues persist:

1. **Check Render Status**: https://status.render.com
2. **Review Render Docs**: https://render.com/docs
3. **Render Community**: https://community.render.com
4. **Application logs**: Enable DEBUG logging temporarily:
   - Add environment variable: `LOGGING_LEVEL_COM_ECOMPTAIA=DEBUG`

## Deployment Checklist

Before creating a support ticket, verify:

- [ ] render.yaml is valid and at repository root
- [ ] All services are defined (backend, frontend, database)
- [ ] Environment variables are set correctly
- [ ] Database service is running
- [ ] JWT_SECRET is generated or set
- [ ] Docker builds work locally
- [ ] Backend starts locally with prod profile
- [ ] Frontend builds successfully locally
- [ ] Repository is connected correctly to Render
- [ ] Latest commit is pushed to the connected branch
