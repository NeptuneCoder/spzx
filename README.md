# spzx

# 查看端口（MAC）

sudo lsof -i:端口号

//nginx的默认目录
Add configs in -> /usr/local/etc/nginx/servers/
Default config -> /usr/local/etc/nginx/nginx.conf
Logs will be in -> /usr/local/var/log/nginx/
Default webroot is -> /usr/local/var/www/
Default listen address -> http://localhost:8080
如何启动nginx？
sudo nginx
如何停止nginx？
sudo nginx -s stop
如何重启nginx？
sudo nginx -s reload
如何查看nginx的版本？
nginx -v