## 1. 安装 MySQL 服务器

bash

复制

```
# 更新包管理器
sudo apt update

# 安装 MySQL 服务器
sudo apt install mysql-server -y

# 启动 MySQL 服务
sudo systemctl start mysql

# 设置开机自启
sudo systemctl enable mysql

# 检查服务状态
sudo systemctl status mysql
```

## 2. 安全配置 MySQL

bash

复制

```
# 运行安全配置脚本
sudo mysql_secure_installation
```

## 3. 创建数据库和用户

bash

复制

```
# 登录 MySQL
sudo mysql -u root -p
```

在 MySQL 命令行中执行：

sql

复制

```
-- 创建数据库（与您的代码中一致）
CREATE DATABASE kkrdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建专用用户（不要使用 root）
CREATE USER 'kkruser'@'localhost' IDENTIFIED BY 'Kkr@1234';

-- 只授予必要权限
GRANT SELECT, INSERT, UPDATE, DELETE ON kkrdb.* TO 'kkruser'@'localhost';

-- 如果需要创建表（仅在初始化时需要）
GRANT CREATE TEMPORARY TABLES ON kkrdb.* TO 'kkruser'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;

-- 退出
EXIT;
```
## 5. 导入现有数据（如果有）

### 从本地导出数据

bash

复制

```
# 在本地机器上导出数据库
mysqldump -u root -p kkrdb > kkrdb_backup.sql
```

### 上传并导入到服务器

bash

复制

```
# 上传备份文件到服务器
scp kkrdb_backup.sql user@your-server:/tmp/

# 在服务器上导入
sudo mysql -u root -p kkrdb < /tmp/kkrdb_backup.sql
```