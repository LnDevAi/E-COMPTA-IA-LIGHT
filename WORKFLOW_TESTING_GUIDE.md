# Quick Guide: Testing and Running Workflows

This guide provides quick commands for testing and validating workflows locally before they run in GitHub Actions.

## Prerequisites

- Java 17
- Maven 3.9+
- Node.js 20+
- npm 10+
- Docker (optional, for Docker build tests)
- Python 3 (for YAML/JSON validation)

## Quick Validation Commands

### 1. Validate All YAML Syntax
```bash
# Check all workflow files
for file in .github/workflows/*.yml; do
    python3 -c "import yaml; yaml.safe_load(open('$file'))" && echo "✓ $file valid" || echo "✗ $file invalid"
done
```

### 2. Validate Deployment Configs
```bash
# Validate render.yaml
python3 -c "import yaml; yaml.safe_load(open('render.yaml'))" && echo "✓ render.yaml valid"

# Validate railway.json
python3 -m json.tool railway.json > /dev/null && echo "✓ railway.json valid"

# Check required files exist
test -f nixpacks.toml && echo "✓ nixpacks.toml exists"
test -f Procfile && echo "✓ Procfile exists"
test -f Dockerfile.backend && echo "✓ Dockerfile.backend exists"
test -f frontend-app/Dockerfile && echo "✓ frontend-app/Dockerfile exists"
```

### 3. Test Backend Build (CI Workflow Simulation)
```bash
cd backend
mvn clean install
# Should output: BUILD SUCCESS and create target/ecompta-ia-light-2.0.0.jar
```

### 4. Test Backend Tests
```bash
cd backend
mvn test
# Should output: Tests run: 32, Failures: 0, Errors: 0, Skipped: 0
```

### 5. Test Frontend Build
```bash
cd frontend-app
npm install
npm run build
# Should create build/ directory with index.html and static/
```

### 6. Test Frontend Tests
```bash
cd frontend-app
npm test -- --watchAll=false
# Should output: Test Suites: 1 passed, Tests: 1 passed
```

### 7. Test ESLint (Lint Workflow)
```bash
cd frontend-app
npx eslint "src/**/*.js"
# Should complete with no errors (exit code 0)
```

### 8. Test Railway Build Simulation
```bash
# Simulate Railway's build process
cd frontend-app
npm ci
npm run build

cd ../backend
mkdir -p src/main/resources/static
cp -r ../frontend-app/build/* src/main/resources/static/
mvn clean package -DskipTests

# Should create backend/target/ecompta-ia-light-2.0.0.jar with frontend integrated
```

### 9. Comprehensive Validation Script
Run the complete validation test suite:
```bash
# Download and run the validation script
chmod +x /tmp/validate_all_workflows.sh
/tmp/validate_all_workflows.sh

# Expected output: "✓ All critical validations passed!"
```

## Manual Workflow Triggers

### Trigger Deploy Validation Workflow
1. Go to GitHub repository → Actions tab
2. Select "Deploy Validation" workflow
3. Click "Run workflow"
4. Select branch and click "Run workflow"

### Trigger Validate Railway Workflow
1. Go to GitHub repository → Actions tab
2. Select "Validate Railway Deployment" workflow
3. Click "Run workflow"
4. Select branch and click "Run workflow"

### Trigger Validate Render Workflow
1. Go to GitHub repository → Actions tab
2. Select "Validate Render Deployment" workflow
3. Click "Run workflow"
4. Select branch and click "Run workflow"

### Trigger Multi-Platform Validation
1. Go to GitHub repository → Actions tab
2. Select "Multi-Platform Deployment Validation" workflow
3. Click "Run workflow"
4. Select branch and click "Run workflow"

## Automatic Workflow Triggers

### Push/PR Triggers
All workflows automatically run on:
- Push to `main` branch
- Pull requests to `main` branch

Some workflows have path-specific triggers:
- **deploy.yml**: Triggers only on changes to deployment configs
- **validate-railway.yml**: Triggers on Railway config or code changes
- **validate-render.yml**: Triggers on Render config or code changes

### Scheduled Triggers
- **validate-deployment-platforms.yml**: Runs weekly (Sunday at midnight) to catch configuration drift

## Expected Workflow Execution Times

| Workflow | Duration | Frequency |
|----------|----------|-----------|
| CI | 5-8 min | Every push/PR |
| Full Stack CI | 6-10 min | Every push/PR |
| Lint | 2-3 min | Every push/PR |
| Deploy Validation | 1-2 min | Manual / Config changes |
| Validate Railway | 8-12 min | Manual / Railway changes |
| Validate Render | 10-15 min | Manual / Render changes |
| Multi-Platform | 15-20 min | Manual / Weekly |

## Troubleshooting

### Build Fails Locally but Should Work in CI
Some dependencies require network access that may be restricted locally. The workflows will work in GitHub Actions runners.

### Docker Build Fails with SSL Errors
Local Docker environments may have SSL certificate issues. These don't affect GitHub Actions runners which have properly configured certificates.

### Tests Fail
1. Ensure all dependencies are installed: `mvn clean install` and `npm install`
2. Check Java version: `java -version` (should be 17+)
3. Check Node version: `node --version` (should be 20+)
4. Clear caches: `mvn clean` and `rm -rf node_modules package-lock.json`

### Workflow Still Fails After Local Success
1. Check GitHub Actions logs for specific error messages
2. Ensure all files are committed and pushed
3. Check workflow file syntax with: `python3 -c "import yaml; yaml.safe_load(open('.github/workflows/WORKFLOW_NAME.yml'))"`

## Verification Checklist

Before pushing code that triggers workflows:

- [ ] All YAML files have valid syntax
- [ ] Backend builds successfully: `cd backend && mvn clean install`
- [ ] Backend tests pass: `cd backend && mvn test`
- [ ] Frontend builds successfully: `cd frontend-app && npm install && npm run build`
- [ ] Frontend tests pass: `cd frontend-app && npm test -- --watchAll=false`
- [ ] ESLint passes: `cd frontend-app && npx eslint "src/**/*.js"`
- [ ] Deployment configs are valid (render.yaml, railway.json, etc.)
- [ ] Documentation is updated if needed

## Quick Status Check

Run this one-liner to get a quick status of all validations:
```bash
cd /path/to/E-COMPTA-IA-LIGHT && \
echo "=== Quick Status Check ===" && \
(cd backend && mvn -q test 2>&1 | grep -E "Tests run|BUILD" | tail -2) && \
(cd frontend-app && npm test -- --watchAll=false 2>&1 | grep -E "Test Suites|Tests:" | tail -2) && \
python3 -c "import yaml; yaml.safe_load(open('render.yaml'))" && echo "✓ render.yaml valid" && \
python3 -m json.tool railway.json > /dev/null && echo "✓ railway.json valid" && \
echo "=== All Checks Passed ==="
```

## Resources

- **Workflow Files**: `.github/workflows/`
- **Documentation**: 
  - `VERIFICATION_WORKFLOWS.md` - Detailed workflow documentation
  - `WORKFLOWS_README.md` - Workflow usage guide
  - `WORKFLOW_VALIDATION_RESULTS.md` - Latest test results
  - `DEPLOYMENT_GUIDE.md` - Deployment platform guides

## Notes

- All workflows use caching to speed up builds (Maven, npm)
- Workflows run in parallel when possible to reduce total execution time
- Failed workflows provide detailed logs in GitHub Actions interface
- All validation checks must pass before deployment
