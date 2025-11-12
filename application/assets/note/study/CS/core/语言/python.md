### 类型提示
**Python 3.6+ 版本**加入了对"类型提示"的支持。

这些**"类型提示"**是一种新的语法（在 Python 3.6 版本加入）用来声明一个变量的类型。

通过声明变量的类型，编辑器和一些工具能给你提供更好的支持。

比如以下类型：

- `int`
- `float`
- `bool`
- `bytes`
### 嵌套类型[¶](https://fastapi.tiangolo.com/zh/python-types/#_7 "Permanent link")

有些容器数据结构可以包含其他的值，比如 `dict`、`list`、`set` 和 `tuple`。它们内部的值也会拥有自己的类型。

你可以使用 Python 的 `typing` 标准库来声明这些类型以及子类型。

它专门用来支持这些类型提示。

### 依赖注入与包装器​

#### ​ 核心区别**​

| ​**维度**​   | ​**依赖注入**​ | ​**包装器**​       |
| ---------- | ---------- | --------------- |
| ​**介入时机**​ | 请求处理前      | 请求前/后均可介入       |
| ​**主要目的**​ | 提供数据/服务    | 修改请求/响应行为       |
| ​**代码位置**​ | 路由参数声明处    | 全局或路由装饰器        |
| ​**典型应用**​ | 认证、数据库连接   | 日志记录、响应格式化、异常处理 |


```
创建虚拟环境
py -m venv .venv
python3 -m venv webvenv
激活虚拟环境
windows
.\.venv\Scripts\activate
linex
source webvenv/bin/activate
退出虚拟环境
deactivate
安装需要的包
pip install requests
pip install -r requirements.txt
```



生成依赖项
pip install pipreqs
`pipreqs . --force --encoding utf-8`

```
gunicorn -w 4 -b 0.0.0.0:5000 app:app
gunicorn -w 4 -b 0.0.0.0:5000 --access-logfile access.log --error-logfile error.log app:app -D
持久化运行
nohup python -m gunicorn -w 5 -b 0.0.0.0:5000 -t 120 backend.app > app.log 2>&1 &

python -m backend.app

cd /var/www/application
source venv/bin/activate
gunicorn -w 4 -b 127.0.0.1:5000 app:app
nohup gunicorn -w 4 -b 0.0.0.0:5000 -t 120 --access-logfile access.log --error-logfile error.log "backend.app:app" > app.log 2>&1 &
```

```
查看所有安装的包
pip freeze
pip freeze > requirements.txt
pip list 
pip list -r requirements.txt

```

```python
# 打包
# 生成单个 exe 文件（包含控制台窗口）
pyinstaller -w -F your_script.py
# `-w`：隐藏控制台窗口（纯 GUI 程序必加）
# `-F`：生成单个 exe 文件
```

``` python
# spec文件解析
a = Analysis(
    # 主入口文件列表（支持多文件）
    ['main.py', 'helper.py'],
  
    # 搜索路径（解决模块导入问题）
    pathex=['/project/src', '/lib'],
  
    # 二进制依赖（动态库等）
    binaries=[('libssl.so', '/usr/lib')],
  
    # 非代码资源文件（自动解压到临时目录）
    datas=[('config.ini', '.'), ('assets/*.png', 'images')],
  
    # 处理动态导入的模块
    hiddenimports=['pandas._libs.tslibs'],
  
    # 排除不需要的模块
    excludes=['tkinter'],
  
    # 控制台窗口配置（Windows）
    console=False
)
exe = EXE(
    # 设置程序图标（Windows/macOS）
    icon='app.ico',
  
    # 关闭控制台窗口（GUI程序必备）
    console=False,
  
    # 输出文件名称
    name='MyApp',
  
    # 启用UPX压缩（需安装upx）
    upx=True,
  
    # 加密打包（需安装tinyaes）
    cipher=block_cipher
)

pyinstaller --noconfirm --onedir --windowed --collect-submodules "src/core" --add-data "D:/Develop/Python/PythonProject/app_distribute/.venv/Lib/site-packages/customtkinter;customtkinter/" --paths=./src "app.py"

```

```

```
## tkinter
### 控件
```python
#单选
state = StringVar()  
state.set(titles[0])
Radiobutton(self.title_frame, text=details, variable=state, value=details)
#样式变为普通按钮
indicatoron=False
```

### 参数
```python
#多余空间靠左
#“n”, “ne”, “e”, “se”, “s”, “sw”, “w”, “nw”, 或者 “center” 来定位（ewsn 代表东西南北，上北下南左西右东）
anchor="w"

```
pack
```python
#填充 x y none both
fill="none"
# 跟随窗口大小
extend="True"
```

grid
```python
sticky="ew"
# 调整列权重，使其在窗口调整大小时占满空间  
self.title_frame.grid_columnconfigure(index, weight=1)
```

## 文件处理


### 元数据 
元数据指的是描述数据的数据
文件的元数据：
<table>
	<tr>
		<td>元数据类型</td>
		<td>示例值</td>
		<td>操作系统支持</td>
		<td>查看命令(Linux)</td>
	</tr>
	<tr>
	    <td>文件名</td>
	    <td>report.pdf</td>
	    <td>全系统</td>
	    <td>ls -l</td>
	</tr>
	<tr>
	    <td>创建时间</td>
	    <td>2025-08-20 14:30:00</td>
	    <td>Windows/macOS</td>
	    <td>stat(Linux无此字段)</td>
	</tr>
	<tr>
	    <td>最后修改时间</td>
	    <td>2025-08-20 14:30:00</td>
	    <td>全系统</td>
	    <td>ls -l time=modify</td>
	</tr>
	<tr>
	    <td>最后访问时间</td>
	    <td>2025-08-20 14:30:00</td>
	    <td>全系统</td>
	    <td>ls -l time=access</td>
	</tr>
	<tr>
	    <td>文件权限</td>
	    <td>-rw-r--r--(644)</td>
	    <td>Linux/macOS</td>
	    <td>ls -l</td>
	</tr>
	<tr>
	    <td>文件所有者</td>
	    <td>user:admin</td>
	    <td>Linux/macOS</td>
	    <td>ls -l</td>
	</tr>
	<tr>
	    <td>Inode编号</td>
	    <td>1234567</td>
	    <td>Linux/macOS</td>
	    <td>ls -l</td>
	</tr>
	<tr>
	    <td>文档类型</td>
	    <td>PDF文档</td>
	    <td>全系统</td>
	    <td>file</td>
	</tr>
	</table>
### 清空文件的方式
``` python
# 方法1：覆盖写入（会改变部分元数据）
with open("data.log", "w") as f:  # 创建新文件句柄
    pass

# 方法2：截断文件（保留更多元数据）
with open("data.log", "r+") as f:  # 使用原文件句柄
    f.truncate(0)
```