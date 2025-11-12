```
缓存凭证
git config --global credential.helper cache
15分钟
git config --global credential.helper 'cache --timeout=3600'
清除凭据
git config --global --unset credential.helper
```

```bash
分支合并
-切分支
-合并至main分支
-提交更改
git checkout main
git merge --squash feature-main
git commit -m "Squashed merge of feature-branch"
-强制
git fetch --all
git reset --hard origin/main
```