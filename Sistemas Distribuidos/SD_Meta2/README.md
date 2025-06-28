# Instalação e Utilização do Googol

Para este projeto, a biblioteca JSOUP.jar foi utilizada.

### Instalação do Projeto
- Instale a biblioteca JSOUP.jar
- Esta deve estar presente na pasta "Lib" antes de executar o programa.
- Compile os arquivos

### Instalação da Biblioteca JSOUP.jar
- Faça o download do website [https://jsoup.org/download](https://jsoup.org/download) (Versão utilizada: 1.17.2)
- No VSCode, Aceder aos "Prefences: Open User Settings (JSON)" (clicar -> Ctrl + Shift + p)
- Adicionar a seguinte linha "java.project.referencedLibraries": ["path do jsoup file"] ou apenas a biblioteca no caso de já possuir outras bibliotecas

### Execução do Googol
- Para executar este projeto, siga a seguinte ordem:
    1. Spring / Gradle
    2. Gateway
    3. IndexStorageBarrel
    4. Downloader


### Instalacao do Gradle
- Faça download do website [gradle.org] (Versão utilizada: 8.7)
- Instale binary-only e na pagina que lhe é redericionada clique em Verify SHA-256_checksum
- Apos a instalacao dos ficheiros, abra no CMD a pasta de Download e execute os seguintes comandos:
    - type gradle-8.7-bin.zip.sha256
    - certutil -hashfile gradle_8.7-bin.zip SHA256
- Extraia a pasta gradle isntalada para o disco C, por exemplo: C:\Tools\Gradle
- Adicione "C:\Tools\Gradle\gradle-8.7\bin" na PATH em variaveis de ambiente
- Para verificar a instalacao, no CMD execute: gradle -v


### Execução do Googol usando Gradle
-  Para executar este projeto, siga a seguinte
    1. Instalar Gradle
    2. Abrir a pasta onde se encontra o build.gradle no CMD
    3. Executar os seguintes comandos:
    - gradle tasks
    - gradle build
    - gradle bootJar
    - cd build
    - cd libs
    - java -jar .\meta2-0.0.1-SNAPSHOT.jar


### Conectar via web (HTTPS)
- ipconfig no CMD, <IP> = IPV4
- https://<IP>:8443



