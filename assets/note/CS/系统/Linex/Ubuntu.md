基于[[Linex]]内核的操作系统，他们之间有大部分常用命令的重叠`ls`、`cd`、`pwd`、`mkdir`、`rm`、`cp`、`mv`等，但两者之间也存在一些差异，Ubuntu使用APT作为其包管理器，而其他Linux发行版可能使用不同的包管理器，如Yum或DNF‌。
挂载共享文件夹
sudo mount -t fuse.vmhgfs-fuse .host:/ /mnt/hgfs -o allow_other

```undefined
查看正在运行的gun端口号
sudo lsof -iTCP -sTCP:LISTEN -P | grep gunicorn
关闭程序
sudo kill -9 4004
sudo killall gunicorn

```


clash
```
启动
./clash -d .
```

```
sudo rm -r /var/www/application
# Clone to your home directory first
git clone https://github.com/kkr155/application.git ~/application
git clone git@github.com:kkr155/application.git ~/application
# Move the repository to /var/www
sudo mv ~/application /var/www/

# Change ownership to your user
sudo chown -R $USER:$USER /var/www/application

nohup gunicorn -w 4 -b 0.0.0.0:5000 -t 120 --access-logfile access.log --error-logfile error.log "backend.app:app" > app.log 2>&1 &
```
反向代理
```bash
配置文件目录
/etc/nginx/sites-available

sudo chown -R lighthouse:www-data /var/www/application
sudo chmod -R 750 /var/www/application

sudo chown -R www-data:www-data /var/www/application/
sudo chmod -R 755 /var/www/application/

sudo usermod -aG www-data $USER

sudo chown -R :www-data /var/www/application


sudo apt update
sudo apt install nginx
启用配置

# 创建符号链接到 sites-available
sudo ln -s /var/www/application/nginx/application.conf /etc/nginx/sites-available/application


# 创建符号链接到 sites-enabled
sudo ln -s /etc/nginx/sites-available/application /etc/nginx/sites-enabled/

测试并
sudo nginx -t

检查证书匹配
sudo certbot certificates | grep Domains
检查https部署
curl -I https://kokoro.xj.cn

sudo systemctl stop nginx

重启
sudo systemctl restart nginx
配置防火墙
sudo ufw allow 'Nginx Full'
配置https
安装
sudo apt install certbot python3-certbot-nginx
获取并安装证书


sudo certbot --nginx -d spring.xj.cn

sudo certbot --nginx -d your_domain.com -d www.your_domain.com

更新证书
sudo certbot renew --force-renewal --cert-name spring.xj.cn

验证完整性
sudo openssl verify -CAfile /etc/letsencrypt/live/spring.xj.cn/fullchain.pem \
                   /etc/letsencrypt/live/spring.xj.cn/cert.pem
                   
# 删除旧证书（谨慎操作前建议备份）
sudo rm -rf /etc/letsencrypt/live/spring.xj.cn
sudo rm -rf /etc/letsencrypt/archive/spring.xj.cn


# 重新申请证书（使用--standalone模式绕过Nginx问题）
sudo certbot certonly --standalone -d spring.xj.cn




强制删除所有
# 删除所有同名证书（包括带序号的）
sudo certbot delete --cert-name spring.xj.cn
sudo certbot delete --cert-name spring.xj.cn-0001

# 清理残留文件
sudo rm -rf /etc/letsencrypt/{archive,live,renewal}/spring.xj.cn*

sudo certbot certificates

```

```
pip install gunicorn
//使用 ​**systemd 托管**​（防止进程崩溃）
gunicorn -w 4 -b 127.0.0.1:5000 app:app
sudo nano /etc/systemd/system/flask.service
gunicorn -w 4 -b 127.0.0.1:5000 --log-level debug app:app > gunicorn.log 2>&1 &
tail -f gunicorn.log
检查运行
ps aux | grep gunicorn
检查监听
sudo netstat -tuln | grep 5000

本地测试
curl http://127.0.0.1:5000
```