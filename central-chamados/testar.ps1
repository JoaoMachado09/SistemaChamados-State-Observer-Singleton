$ErrorActionPreference = "Stop"
Push-Location $PSScriptRoot
try {
    & mvn test
    if ($LASTEXITCODE -ne 0) { throw "Falha nos testes. Veja a saída acima." }
} finally { Pop-Location }
