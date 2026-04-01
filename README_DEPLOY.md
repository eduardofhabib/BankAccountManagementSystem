# Guia de Deploy - Sistema Bancário UNPOO

Como o Vercel é focado em frontend e funções serverless (Node.js/Python), ele não suporta nativamente a execução de um servidor Java Spring Boot de longa duração. Para que este projeto funcione corretamente, utilizaremos uma estratégia de **Deploy Híbrido**.

## 1. Backend (Java Spring Boot)
Você deve hospedar o backend em uma plataforma que suporte Java. Como o Render pode tentar auto-detectar erroneamente o projeto como Node.js, utilizaremos o **Dockerfile** que acabei de criar para garantir o ambiente correto.

### Passos para Render (Via Docker):
1. Conecte seu repositório GitHub ao [Render](https://render.com/).
2. Escolha **"Web Service"**.
3. No campo **"Runtime"**, selecione **"Docker"** (Isso evita que o Render tente usar Node.js).
4. O Render detectará automaticamente o arquivo `Dockerfile` na raiz do projeto.
5. Em **"Environment Variables"**, adicione:
   - `APP_CORS_ALLOWED_ORIGINS`: `https://seu-projeto.vercel.app` (URL do seu frontend no Vercel).
6. Clique em **"Deploy Web Service"**.

## 2. Frontend (Vercel)
O Vercel servirá apenas os arquivos estáticos (HTML/CSS/JS) e fará o roteamento das chamadas de API.

### Passos para Vercel:
1. Conecte seu repositório ao [Vercel](https://vercel.com/).
2. No passo de configuração do projeto:
   - **Framework Preset**: Escolha "Other".
   - **Root Directory**: Mantenha a raiz.
   - **Output Directory**: `src/main/resources/static`.
3. O arquivo `vercel.json` na raiz cuidará do resto.
4. **IMPORTANTE**: Após o deploy do backend, atualize o arquivo `vercel.json` com a URL real do seu backend no Render/Railway.

```json
{
  "version": 2,
  "public": true,
  "cleanUrls": true,
  "rewrites": [
    {
      "source": "/api/(.*)",
      "destination": "https://seu-backend.onrender.com/api/$1"
    },
    {
      "source": "/(.*)",
      "destination": "/src/main/resources/static/$1"
    }
  ]
}
```

## 3. Por que esta estratégia?
- **Performance**: O frontend é servido pela rede global (Edge) do Vercel.
- **Transparência**: O frontend continua chamando `/api/...`, e o Vercel faz o proxy para o backend, evitando problemas de CORS e mantendo a URL limpa.
- **Custo**: Ambas as plataformas possuem planos gratuitos generosos.
