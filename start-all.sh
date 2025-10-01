#!/bin/bash
# Lancer backend Spring Boot
(cd backend && ./mvnw spring-boot:run &)
# Lancer frontend React
(cd frontend-app && npm start &)
echo "Backend lancé sur http://localhost:8080"
echo "Frontend lancé sur http://localhost:3000"
