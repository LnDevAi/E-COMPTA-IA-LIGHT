# Testing the Railway Deployment Locally

This guide helps you test the Railway deployment configuration locally before deploying to Railway.

## Prerequisites

- Java 17
- Maven 3.9+
- Node.js 20+
- npm 10+

## Test Steps

### 1. Build the Frontend

```bash
cd frontend-app
npm ci
npm run build
```

This creates a `build/` directory with the production-optimized React application.

### 2. Copy Frontend Build to Backend

```bash
cd ..
mkdir -p backend/src/main/resources/static
cp -r frontend-app/build/* backend/src/main/resources/static/
```

This copies the built frontend files to the backend's static resources directory.

### 3. Build the Backend

```bash
cd backend
mvn clean package -DskipTests
```

This creates `target/ecompta-ia-light-2.0.0.jar` with the frontend files included.

### 4. Verify Frontend Files in JAR

```bash
jar tf target/ecompta-ia-light-2.0.0.jar | grep "BOOT-INF/classes/static" | head -15
```

You should see:
```
BOOT-INF/classes/static/
BOOT-INF/classes/static/static/
BOOT-INF/classes/static/index.html
BOOT-INF/classes/static/manifest.json
...
```

### 5. Run the Application

```bash
# Set JWT_SECRET environment variable (required)
export JWT_SECRET="your-secret-key-at-least-256-bits-long-for-testing-purposes"

# Run the application
java -Dspring.profiles.active=railway -jar target/ecompta-ia-light-2.0.0.jar
```

### 6. Test the Application

Open your browser and navigate to:
- **Frontend**: http://localhost:8080/ 
  - You should see the E-COMPTA-IA-LIGHT login/registration page
- **API**: http://localhost:8080/api/auth/register
  - You can test API endpoints with curl or Postman

### 7. Test API Registration

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123","role":"USER"}'
```

Expected response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "test",
  "role": "USER"
}
```

### 8. Test API Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123"}'
```

## Cleanup

After testing, clean up the generated files:

```bash
cd ..
rm -rf backend/src/main/resources/static backend/target frontend-app/build frontend-app/node_modules
```

## Simulating Railway Build

To simulate exactly what Railway will do:

```bash
# This mirrors the nixpacks.toml build process
cd frontend-app && npm ci && npm run build
cd ../backend
mkdir -p src/main/resources/static
cp -r ../frontend-app/build/* src/main/resources/static/
mvn clean package -DskipTests
```

## Troubleshooting

### Issue: Port 8080 already in use
Solution: Stop any running Spring Boot applications or use a different port:
```bash
java -Dspring.profiles.active=railway -Dserver.port=8081 -jar target/ecompta-ia-light-2.0.0.jar
```

### Issue: JWT_SECRET not set
Error message: `Property jwt.secret is required`
Solution: Set the JWT_SECRET environment variable before running the application.

### Issue: Frontend shows blank page
- Check browser console for errors
- Verify that `backend/src/main/resources/static/index.html` exists before building
- Ensure the JAR contains the static files (use `jar tf` command above)

### Issue: API calls fail with 404
- Verify the backend is running on the correct port
- Check that API endpoints start with `/api/`
- Test API endpoints directly with curl
