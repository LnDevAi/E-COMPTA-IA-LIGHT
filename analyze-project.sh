#!/bin/bash

echo "=========================================="
echo "RAPPORT D'ANALYSE E-COMPTA-IA-LIGHT"
echo "=========================================="
echo ""

echo "1. STRUCTURE DES DOSSIERS"
echo "=========================================="
tree -L 4 -I 'target|node_modules' backend/src/main/java/com/ecomptaia/
echo ""

echo "2. FICHIERS PAR MODULE"
echo "=========================================="
echo "Module Security:"
find backend/src/main/java/com/ecomptaia/security -name "*.java" 2>/dev/null | wc -l
echo ""
echo "Module Accounting:"
find backend/src/main/java/com/ecomptaia/accounting -name "*.java" 2>/dev/null | wc -l
echo ""
echo "Module Config:"
find backend/src/main/java/com/ecomptaia/config -name "*.java" 2>/dev/null | wc -l
echo ""

echo "3. LISTE DES ENTITÉS"
echo "=========================================="
find backend/src/main/java/com/ecomptaia -path "*/entity/*.java" -exec basename {} \; 2>/dev/null
echo ""

echo "4. LISTE DES REPOSITORIES"
echo "=========================================="
find backend/src/main/java/com/ecomptaia -path "*/repository/*.java" -exec basename {} \; 2>/dev/null
echo ""

echo "5. LISTE DES SERVICES"
echo "=========================================="
find backend/src/main/java/com/ecomptaia -path "*/service/*.java" -exec basename {} \; 2>/dev/null
echo ""

echo "6. LISTE DES CONTROLLERS"
echo "=========================================="
find backend/src/main/java/com/ecomptaia -path "*/controller/*.java" -exec basename {} \; 2>/dev/null
echo ""

echo "7. CONFIGURATION"
echo "=========================================="
echo "application.yml existe:" 
test -f backend/src/main/resources/application.yml && echo "OUI" || echo "NON"
echo "pom.xml existe:"
test -f backend/pom.xml && echo "OUI" || echo "NON"
echo ""

echo "8. DÉPENDANCES DANS POM.XML"
echo "=========================================="
grep -E "<artifactId>(spring-boot-starter|h2|lombok|jwt)" backend/pom.xml 2>/dev/null | sed 's/^[ \t]*//'
echo ""

echo "9. DERNIERS COMMITS"
echo "=========================================="
git log --oneline -5 2>/dev/null
echo ""

echo "10. FICHIERS MODIFIÉS NON COMMITÉS"
echo "=========================================="
git status --short 2>/dev/null
echo ""

echo "11. TESTS UNITAIRES"
echo "=========================================="
find backend/src/test -name "*Test.java" 2>/dev/null | wc -l
echo "fichiers de tests trouvés"
echo ""

echo "12. ENDPOINTS DANS LES CONTROLLERS"
echo "=========================================="
grep -r "@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping" backend/src/main/java/com/ecomptaia --include="*Controller.java" 2>/dev/null | sed 's/^.*\/\(.*Controller.java\):/\1: /' | head -20
echo ""

echo "=========================================="
echo "FIN DU RAPPORT"
echo "=========================================="
