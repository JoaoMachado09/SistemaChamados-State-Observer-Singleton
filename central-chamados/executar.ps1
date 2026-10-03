param([switch]$Demo)
$ErrorActionPreference = "Stop"
Push-Location $PSScriptRoot
try {
    & mvn -DskipTests package
    if ($LASTEXITCODE -ne 0) { throw "Falha ao compilar. Verifique o JDK 17+ e o Maven." }
    $argumentos = @("-Dfile.encoding=UTF-8", "-jar", "target/central-chamados-1.0.0.jar")
    if ($Demo) { $argumentos += "--demo" }
    & java @argumentos
    if ($LASTEXITCODE -ne 0) { throw "Falha na execução." }
} finally { Pop-Location }
