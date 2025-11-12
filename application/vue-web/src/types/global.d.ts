// src/global.d.ts
export {}; // 确保文件被当作模块处理

declare global {
  interface Window {
    AndroidApp?: {
      downloadFile: (url: string, fileName: string) => void;
    };
  }
}
