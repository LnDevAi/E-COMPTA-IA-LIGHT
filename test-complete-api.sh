#!/bin/bash

API_URL="http://localhost:8080/api"
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

function print_success {
  echo -e "${GREEN}$1${NC}"
}
function print_error {
  echo -e "${RED}$1${NC}"
}

# 1. GET /api/test/hello
echo "1. GET /api/test/hello"
resp=$(curl -s -w "%{http_code}" "$API_URL/test/hello")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 2. POST /api/auth/register (admin)
echo "2. POST /api/auth/register"
resp=$(curl -s -w "%{http_code}" -X POST "$API_URL/auth/register" -H "Content-Type: application/json" -d '{"username":"admin","password":"admin123","role":"ADMIN"}')
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "201" ] || [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 3. POST /api/auth/login (get JWT)
echo "3. POST /api/auth/login"
resp=$(curl -s -w "%{http_code}" -X POST "$API_URL/auth/login" -H "Content-Type: application/json" -d '{"username":"admin","password":"admin123"}')
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

token=$(echo "$body" | grep -o '"token":"[^"]*"' | cut -d':' -f2 | tr -d '"')
if [ -z "$token" ]; then print_error "Token JWT non récupéré"; exit 1; fi

AUTH="Authorization: Bearer $token"

# 4. GET /api/entreprises
echo "4. GET /api/entreprises"
resp=$(curl -s -w "%{http_code}" "$API_URL/entreprises" -H "$AUTH")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 5. GET /api/comptes
echo "5. GET /api/comptes"
resp=$(curl -s -w "%{http_code}" "$API_URL/comptes" -H "$AUTH")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 6. GET /api/journaux
echo "6. GET /api/journaux"
resp=$(curl -s -w "%{http_code}" "$API_URL/journaux" -H "$AUTH")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 7. POST /api/ecritures (balanced entry)
echo "7. POST /api/ecritures"
entry='{"libelle":"Achat marchandises","date":"2025-10-01","journalCode":"AC","lignes":[{"compte":"6000","debit":50000,"credit":0},{"compte":"401","debit":0,"credit":50000}]}'
resp=$(curl -s -w "%{http_code}" -X POST "$API_URL/ecritures" -H "$AUTH" -H "Content-Type: application/json" -d "$entry")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "201" ] || [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

ecriture_id=$(echo "$body" | grep -o '"id":[0-9]*' | cut -d':' -f2)
if [ -z "$ecriture_id" ]; then print_error "ID écriture non récupéré"; exit 1; fi

# 8. POST /api/ecritures/{id}/valider
echo "8. POST /api/ecritures/$ecriture_id/valider"
resp=$(curl -s -w "%{http_code}" -X POST "$API_URL/ecritures/$ecriture_id/valider" -H "$AUTH")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 9. GET /api/rapports/balance?dateDebut=2025-01-01&dateFin=2025-12-31
echo "9. GET /api/rapports/balance"
resp=$(curl -s -w "%{http_code}" "$API_URL/rapports/balance?dateDebut=2025-01-01&dateFin=2025-12-31" -H "$AUTH")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi

# 10. GET /api/rapports/bilan?date=2025-12-31
echo "10. GET /api/rapports/bilan"
resp=$(curl -s -w "%{http_code}" "$API_URL/rapports/bilan?date=2025-12-31" -H "$AUTH")
body=${resp::-3}
code=${resp: -3}
if [ "$code" == "200" ]; then print_success "OK: $body"; else print_error "Erreur ($code): $body"; fi
