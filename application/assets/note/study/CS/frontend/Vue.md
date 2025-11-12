```
//debug运行
npm run dev
npm run build
```
npm install -g pnpm@latest-10
```bash
pnpm install --registry=https://registry.npmmirror.com
```

| 类别         | 命令                                 | 功能描述                   |
| ---------- | ---------------------------------- | ---------------------- |
| ​**项目管理**​ | `npm init`                         | 创建 package.json        |
|            | `npm init -y`                      | 快速创建（跳过提问）             |
| ​**依赖安装**​ | `npm install`                      | 安装所有依赖                 |
|            | `npm install <package>`            | 安装指定包                  |
|            | `npm install <package>@<version>`  | 安装指定版本                 |
|            | `npm install -g <package>`         | 全局安装                   |
|            | `npm install --save <package>`     | 安装并保存到 dependencies    |
|            | `npm install --save-dev <package>` | 安装并保存到 devDependencies |
| ​**依赖管理**​ | `npm list`                         | 列出已安装依赖                |
|            | `npm list -g --depth=0`            | 列出全局安装的包               |
|            | `npm outdated`                     | 检查过时的依赖                |
|            | `npm update`                       | 更新所有依赖                 |
|            | `npm update <package>`             | 更新指定依赖                 |
|            | `npm uninstall <package>`          | 移除依赖                   |
| ​**依赖分析**​ | `npm dedupe`                       | 减少重复依赖                 |
|            | `npm prune`                        | 移除未使用的依赖               |
|            | `npm audit`                        | 检查安全漏洞                 |
|            | `npm fund`                         | 查看依赖资金来源               |
| ​**脚本执行**​ | `npm run <script>`                 | 运行 package.json 中的脚本   |
|            | `npm start`                        | 运行 start 脚本            |
|            | `npm test`                         | 运行 test 脚本             |
| ​**发布管理**​ | `npm publish`                      | 发布包到 registry          |
|            | `npm unpublish`                    | 取消发布                   |
|            | `npm login`                        | 登录 npm 账号              |
|            | `npm whoami`                       | 显示当前登录用户               |