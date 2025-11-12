# 查看直接依赖
npm ls --depth=0
# 查看直接+一级依赖
npm ls --depth=1
# 检查过期依赖
npm outdated
# 删除未使用的依赖
npm prune
# 减少重复依赖
npm dedupe

# 查看实际未使用的依赖
npx depcheck
# 可视化依赖树
npx npm-remote-ls
# 查找大文件依赖
npx cost-of-modules

# 基础安全扫描
npm audit
# 自动修复漏洞
npm audit fix
# 强制修复所有漏洞
npm audit fix --force
# 忽略开发依赖审计
npm audit --production

# 清理缓存
npm cache clean --force
# 验证缓存完整性
npm cache verify
# 查看缓存位置
npm config get cache