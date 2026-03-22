# PowerShell script to download Maven Wrapper
$baseUrl = "https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw"
$baseUrlCmd = "https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw.cmd"
$wrapperJarUrl = "https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"

if (!(Test-Path ".mvn/wrapper")) {
    New-Item -ItemType Directory -Path ".mvn/wrapper" -Force
}

Write-Host "Downloading Maven Wrapper files..."
Invoke-WebRequest -Uri $baseUrl -OutFile "mvnw"
Invoke-WebRequest -Uri $baseUrlCmd -OutFile "mvnw.cmd"
Invoke-WebRequest -Uri $wrapperJarUrl -OutFile ".mvn/wrapper/maven-wrapper.jar"

Write-Host "Download complete. Creating maven-wrapper.properties..."

$properties = "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip"
$properties | Out-File -FilePath ".mvn/wrapper/maven-wrapper.properties" -Encoding ascii

Write-Host "Success! You can now run the project using: .\mvnw spring-boot:run"
