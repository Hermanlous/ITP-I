import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';
import { resolve } from 'path';

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    coverage: {
      reporter: ['text', 'json', 'html'],
      include: [
        'src/components/Canvas.tsx',
        'src/hook/useCanvas.ts',
        'src/App.tsx',
      ],
      exclude: [
        'src/test/setup.ts',
        'src/test/**/*.ts',
        'src/main.tsx',
        'src/vite-env.d.ts',
      ],
      all: true,
    },
    include: ['test/**/*.{test,spec}.{ts,tsx}'],
    alias: {
      '@': resolve(__dirname, './src'),
    },
  },
});