FROM python:3.11-slim

WORKDIR /app

# Instalar dependências do sistema (versão correta para PostgreSQL)
RUN apt-get update && apt-get install -y \
    gcc \
    postgresql-client \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

# Copiar requirements e instalar dependências Python
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copiar o código da aplicação
COPY . .

# Expor a porta
EXPOSE 8000

# Comando para executar a aplicação
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]