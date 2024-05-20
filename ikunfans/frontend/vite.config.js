import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'
import path from 'path'
import EnvironmentPlugin from 'vite-plugin-environment';
import {vitePluginForArco} from "@arco-plugins/vite-vue";
// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  return {
    // 项目插件
    plugins: [
      vue(),
      EnvironmentPlugin('all'),
      vitePluginForArco({
        style: 'css'
      })
    ],
    // 基础配置
    base: './',
    publicDir: 'public',
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
    port: 13880,
    css: {
      preprocessorOptions: {
        less: {
          modifyVars: {
            '@border-color-base': '#dce3e8',
          },
          javascriptEnabled: true,
        },
      },
    },
    build: {
      outDir: 'dist',
      assetsDir: 'assets',
      assetsInlineLimit: 4096,
      cssCodeSplit: true,
      brotliSize: false,
      sourcemap: false,
      minify: 'terser',
      terserOptions: {
        compress: {
          // 生产环境去除console及debug
          drop_console: false,
          drop_debugger: true,
        },
      },
    },
  }
})


