import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');

  return {
    base: '/',
    plugins: [
      react(),
    ],
    server: {
      host: true,
      port: Number(env.PORT) || 5030,
    },
    define: {
      API_URL: JSON.stringify(env.API_URL),
    },
  };
});
