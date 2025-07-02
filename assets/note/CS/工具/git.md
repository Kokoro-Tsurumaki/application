```
缓存凭证
git config --global credential.helper cache
15分钟
git config --global credential.helper 'cache --timeout=3600'
清除凭据
git config --global --unset credential.helper
```