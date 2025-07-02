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