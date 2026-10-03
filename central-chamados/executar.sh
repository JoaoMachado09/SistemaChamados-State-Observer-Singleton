#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mvn -DskipTests package
java -Dfile.encoding=UTF-8 -jar target/central-chamados-1.0.0.jar "$@"
