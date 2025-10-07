#!/bin/bash

# Render Deployment Verification Script
# This script verifies that the production configuration is correct

echo "🔍 E-COMPTA-IA-LIGHT - Render Deployment Verification"
echo "======================================================="
echo ""

# Check if we're in the right directory
if [ ! -f "render.yaml" ]; then
    echo "❌ Error: render.yaml not found. Please run this script from the project root."
    exit 1
fi

echo "✅ Found render.yaml"

# Verify backend configuration
echo ""
echo "📦 Verifying Backend Configuration..."
echo "-------------------------------------"

# Check Dockerfile.backend exists
if [ -f "Dockerfile.backend" ]; then
    echo "✅ Dockerfile.backend exists"
else
    echo "❌ Dockerfile.backend NOT found"
    exit 1
fi

# Check Dockerfile.backend for production profile
if grep -q "\-Pprod" Dockerfile.backend; then
    echo "✅ Production profile in Dockerfile.backend"
else
    echo "❌ Production profile NOT found in Dockerfile.backend"
    exit 1
fi

# Check pom.xml for actuator dependency
if grep -q "spring-boot-starter-actuator" backend/pom.xml; then
    echo "✅ Actuator dependency found in pom.xml"
else
    echo "❌ Actuator dependency NOT found in pom.xml"
    exit 1
fi

# Check pom.xml for production profile
if grep -q '<id>prod</id>' backend/pom.xml; then
    echo "✅ Production profile found in pom.xml"
else
    echo "❌ Production profile NOT found in pom.xml"
    exit 1
fi

# Check application-prod.yml exists
if [ -f "backend/src/main/resources/application-prod.yml" ]; then
    echo "✅ application-prod.yml exists"
else
    echo "❌ application-prod.yml NOT found"
    exit 1
fi

# Check for HikariCP configuration
if grep -q "hikari:" backend/src/main/resources/application-prod.yml; then
    echo "✅ HikariCP configuration found"
else
    echo "❌ HikariCP configuration NOT found"
    exit 1
fi

# Check for actuator configuration
if grep -q "management:" backend/src/main/resources/application-prod.yml; then
    echo "✅ Actuator configuration found"
else
    echo "❌ Actuator configuration NOT found"
    exit 1
fi

# Check SecurityConfig for actuator endpoints
if grep -q "/actuator/health" backend/src/main/java/com/ecomptaia/config/SecurityConfig.java; then
    echo "✅ Actuator endpoints allowed in SecurityConfig"
else
    echo "❌ Actuator endpoints NOT allowed in SecurityConfig"
    exit 1
fi

# Check WebConfig for CORS
if grep -q "addCorsMappings" backend/src/main/java/com/ecomptaia/config/WebConfig.java; then
    echo "✅ CORS configuration found in WebConfig"
else
    echo "❌ CORS configuration NOT found in WebConfig"
    exit 1
fi

# Verify frontend configuration
echo ""
echo "🎨 Verifying Frontend Configuration..."
echo "--------------------------------------"

# Check frontend Dockerfile exists
if [ -f "frontend-app/Dockerfile" ]; then
    echo "✅ frontend-app/Dockerfile exists"
else
    echo "❌ frontend-app/Dockerfile NOT found"
    exit 1
fi

# Check Dockerfile for SPA routing (try_files)
if grep -q "try_files" frontend-app/Dockerfile; then
    echo "✅ SPA routing configured in Dockerfile"
else
    echo "⚠️  Warning: SPA routing may not be configured in Dockerfile"
fi

# Check package.json for homepage field
if grep -q '"homepage": "."' frontend-app/package.json; then
    echo "✅ Homepage field set to '.' in package.json"
else
    echo "❌ Homepage field NOT set correctly in package.json"
    exit 1
fi

# Check _redirects file exists
if [ -f "frontend-app/public/_redirects" ]; then
    echo "✅ _redirects file exists"
else
    echo "⚠️  Warning: _redirects file NOT found (not needed for Docker/nginx)"
fi

# Check .env.production
if [ -f "frontend-app/.env.production" ]; then
    echo "✅ .env.production exists"
    if grep -q "REACT_APP_API_URL=https://ecompta-backend.onrender.com" frontend-app/.env.production; then
        echo "✅ REACT_APP_API_URL configured correctly"
    else
        echo "⚠️  Warning: REACT_APP_API_URL may not be configured correctly"
    fi
else
    echo "❌ .env.production NOT found"
    exit 1
fi

# Check index.html for proper title
if grep -q "E-COMPTA-IA-LIGHT" frontend-app/public/index.html; then
    echo "✅ Index.html has proper title"
else
    echo "❌ Index.html title NOT updated"
    exit 1
fi

# Verify render.yaml configuration
echo ""
echo "☁️  Verifying render.yaml Configuration..."
echo "------------------------------------------"

# Check backend service type
if grep -q "env: docker" render.yaml; then
    echo "✅ Backend configured as Docker service"
else
    echo "❌ Backend NOT configured as Docker service"
    exit 1
fi

# Check frontend service type
if grep -q "dockerfilePath:.*frontend-app/Dockerfile" render.yaml; then
    echo "✅ Frontend configured as Docker service"
else
    echo "❌ Frontend NOT configured as Docker service"
    exit 1
fi

# Check health check path
if grep -q "healthCheckPath: /actuator/health" render.yaml; then
    echo "✅ Health check path configured"
else
    echo "❌ Health check path NOT configured"
    exit 1
fi

# Check database name
if grep -q "name: postgres" render.yaml; then
    echo "✅ Database name set to postgres"
else
    echo "❌ Database name NOT set correctly"
    exit 1
fi

echo ""
echo "🎉 All Verifications Passed!"
echo "============================="
echo ""
echo "Your application is ready for deployment on Render."
echo ""
echo "Next Steps:"
echo "1. Push your changes to GitHub"
echo "2. Go to Render Dashboard → Blueprints → New Blueprint Instance"
echo "3. Connect your repository and select the branch"
echo "4. Render will automatically deploy all services"
echo ""
echo "For manual testing:"
echo "  Backend build: cd backend && mvn clean package -DskipTests -Pprod"
echo "  Frontend build: cd frontend-app && npm install && npm run build"
echo "  Backend Docker: docker build -f Dockerfile.backend -t backend ."
echo "  Frontend Docker: docker build -f frontend-app/Dockerfile -t frontend ./frontend-app"
echo ""
