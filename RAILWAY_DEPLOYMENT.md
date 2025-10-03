# Deployment Guide for Railway

This guide explains how to deploy the E-COMPTA-IA-LIGHT application on Railway.

## Prerequisites

- A Railway account (https://railway.app)
- The Railway CLI installed (optional)

## Deployment Steps

### 1. Backend Deployment

1. Go to your Railway project: https://railway.com/project/cec25c67-f140-4ffe-8818-30a45aa43b53
2. Click on the backend service
3. Connect this GitHub repository to the service
4. Railway will automatically detect the `nixpacks.toml` configuration

### 2. Environment Variables

Configure the following environment variables in Railway:

#### Required Variables:
- `PORT` - Railway will set this automatically (usually 8080)
- `JWT_SECRET` - Set a strong secret key for JWT tokens (minimum 256 bits)

#### Optional Variables (for PostgreSQL):
If you want to use PostgreSQL instead of H2 (recommended for production):

1. Add a PostgreSQL database service in Railway
2. Railway will automatically inject these variables when you connect the database:
   - `PGHOST` - PostgreSQL host
   - `PGPORT` - PostgreSQL port (default: 5432)
   - `PGDATABASE` - Database name
   - `PGUSER` - Database username
   - `PGPASSWORD` - Database password
   - `DATABASE_URL` - Complete JDBC connection string

3. You can also manually set these optional variables:
   - `DATABASE_DRIVER` - Set to `org.postgresql.Driver` (optional, auto-detected)
   - `HIBERNATE_DIALECT` - Set to `org.hibernate.dialect.PostgreSQLDialect` (optional, auto-detected)
   - `JPA_DDL_AUTO` - Set to `update` for production (default: `update`)
   - `H2_CONSOLE_ENABLED` - Set to `false` for production (default: `false`)

**Note**: Railway automatically provides PostgreSQL connection via `PGUSER`, `PGPASSWORD`, and `DATABASE_URL`. The application is configured to use these variables automatically.

### 3. Build Configuration

The `nixpacks.toml` file is configured to:
- Use Maven and JDK 17
- Build the Spring Boot application
- Run the JAR file on startup

### 4. Database Setup

#### Option A: H2 (Default - for development)
- No additional setup needed
- Data is in-memory and will be lost on restart

#### Option B: PostgreSQL (Recommended for production)
1. In Railway, add a new PostgreSQL database service
2. Connect it to your backend service
3. Railway will automatically provide the DATABASE_URL
4. Update the environment variables as mentioned above

### 5. Frontend Deployment (Optional)

For the frontend, you can either:

#### Option A: Separate Frontend Service
1. Create a new service in Railway
2. Point it to the `frontend-app` directory
3. Use the provided `frontend-app/Dockerfile`
4. Set environment variable:
   - `REACT_APP_API_URL` - URL of your backend service

#### Option B: Serve from Backend
The backend can be configured to serve the built React app as static files.

## Health Check

Once deployed, verify the deployment:
- Backend health: Access your Railway service URL (e.g., `https://your-app.railway.app`)
- You should see a Spring Boot error page (404 or login) - this means the app is running
- Try accessing `/api/auth/login` endpoint to verify API is working
- H2 Console (if enabled): `https://your-app.railway.app/h2-console`

**Note**: The application doesn't have a root endpoint, so accessing the root URL will show a 404 or redirect to login. This is expected behavior.

To verify the application is fully operational:
```bash
curl -X POST https://your-app.railway.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123","role":"USER"}'
```

## Troubleshooting

### Build Fails
- Check that JDK 17 is being used (not JDK 21)
- Verify Maven dependencies are accessible
- Check Railway build logs

### Database Connection Issues
- Verify DATABASE_URL format for PostgreSQL
- Check database credentials
- Ensure database service is running

### Schema Generation Errors (OID, UUID, bigserial)
**Symptoms**: Application crashes with errors like "Unknown data type: OID" or "Unknown data type: UUID"

**Cause**: This happens when the application is configured with PostgreSQL dialect but connecting to H2 database.

**Solution**: The `application-railway.yml` has been fixed to:
- Default to H2 dialect when no PostgreSQL database is configured
- Automatically switch to PostgreSQL dialect when DATABASE_URL points to PostgreSQL
- Use the following environment variables in Railway when using PostgreSQL:
  ```
  DATABASE_URL=jdbc:postgresql://hostname:5432/dbname
  DATABASE_DRIVER=org.postgresql.Driver
  HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
  ```

### Runtime Errors
- Check application logs in Railway dashboard
- Verify all required environment variables are set
- Ensure JWT_SECRET is properly configured
- Check that the correct Spring profile is active (should be `railway`)

### Post-Deployment Failures
**Symptoms**: Build succeeds but application crashes immediately after deployment

**Common causes**:
1. **Missing JWT_SECRET**: Set JWT_SECRET environment variable in Railway
2. **Wrong dialect**: Fixed in latest version - Railway profile now defaults to H2
3. **Database connection**: If using PostgreSQL, ensure the database service is running and connected

## Configuration Files

- `nixpacks.toml` - Railway build configuration
- `Dockerfile.backend` - Alternative Docker-based deployment
- `backend/src/main/resources/application.yml` - Application configuration with environment variable support

## Support

For issues specific to Railway deployment:
- **Detailed troubleshooting**: See the Troubleshooting section above
- **Railway Documentation**: https://docs.railway.app
- **Railway Discord**: https://discord.gg/railway
- **Railway Status**: https://status.railway.app

**Common issues solved**:
- ✅ Schema errors (OID, UUID, bigserial) - Fixed by using H2 dialect as default
- ✅ Post-deployment crashes - Fixed by proper environment variable handling
- ✅ Database connection issues - Documented in troubleshooting section
