# Quick Railway Deployment Guide

## Problem Fixed
**Before**: Railway deployment showed only text message: "Bienvenue sur l'API E-COMPTA-IA-LIGHT..."
**After**: Railway deployment shows full application UI with login/registration interface ✅

## What Changed

### 1. Build Process (nixpacks.toml)
```toml
# Now builds BOTH frontend and backend
[phases.setup]
nixPkgs = ["maven", "jdk17", "nodejs", "npm"]  # Added Node.js

[phases.build]
cmds = [
  "cd frontend-app && npm ci && npm run build",    # Build frontend
  "cd backend",
  "mkdir -p src/main/resources/static",
  "cp -r ../frontend-app/build/* src/main/resources/static/",  # Copy to backend
  "mvn clean package -DskipTests"                  # Build backend with frontend
]
```

### 2. Backend Configuration
- **WebConfig.java** (NEW): Serves static files and handles SPA routing
- **SecurityConfig.java**: Updated to allow access to static resources
- **HomeController.java**: Removed (was blocking static content)

### 3. Frontend Configuration
- **axios.js**: Uses relative URLs (`baseURL: ''`) for same-origin requests
- **.env.production**: Empty `REACT_APP_API_URL` for production

## Deployment Steps

### For New Deployments
1. Connect your GitHub repository to Railway
2. Set environment variable: `JWT_SECRET=your-secret-key`
3. Railway automatically detects `nixpacks.toml`
4. Deploy! The frontend will be included automatically

### For Existing Deployments
1. Pull the latest changes from this branch
2. Redeploy in Railway (it will use the new nixpacks.toml)
3. No configuration changes needed if `JWT_SECRET` is already set

## Verification

After deployment, test these URLs:

1. **Frontend UI**: `https://your-app.railway.app/`
   - Should show E-COMPTA-IA-LIGHT login page
   
2. **Static Files**: `https://your-app.railway.app/favicon.ico`
   - Should return favicon image

3. **API Endpoints**: `https://your-app.railway.app/api/auth/register`
   - Should accept POST requests with JSON body

## Troubleshooting

### Issue: Still seeing "Bienvenue" text message
**Solution**: 
- Clear your browser cache
- Force redeploy in Railway
- Check that the latest commit is deployed

### Issue: Build fails
**Possible causes**:
- Check Railway logs for Node.js installation
- Verify frontend dependencies install correctly
- Ensure Maven build succeeds

### Issue: Frontend shows but API calls fail
**Check**:
- Browser console for errors
- Network tab for failed requests
- Backend logs for exceptions

## Local Testing

Test the deployment locally before pushing:
```bash
# Build frontend
cd frontend-app && npm ci && npm run build

# Copy to backend
cd ../backend
mkdir -p src/main/resources/static
cp -r ../frontend-app/build/* src/main/resources/static/

# Build backend
mvn clean package -DskipTests

# Run
export JWT_SECRET="your-test-secret-key"
java -Dspring.profiles.active=railway -jar target/ecompta-ia-light-2.0.0.jar

# Test
curl http://localhost:8080/
# Should return HTML with React app
```

## Documentation

- **Full guide**: [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md)
- **Testing guide**: [TESTING_RAILWAY_DEPLOYMENT.md](TESTING_RAILWAY_DEPLOYMENT.md)
- **Architecture**: [RAILWAY_ARCHITECTURE.md](RAILWAY_ARCHITECTURE.md)

## Need Help?

1. Check the documentation files listed above
2. Review Railway build logs
3. Test locally using the commands above
4. Check browser console for frontend errors
5. Check Railway logs for backend errors
