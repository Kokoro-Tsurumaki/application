# 不使用域名的服务器部署方式db.init_app(app)
from pathlib import Path
import importlib.util
from flask import Flask
from backend.core.extensions import db
from backend.net.handlers import register_handlers
from backend.utils.path_util import template_dir, static_dir
from flask_cors import CORS  # 添加这行

app = Flask(__name__,
            template_folder=str(template_dir),
            static_folder=str(static_dir))


app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///notes.db'
app.config['SQLALCHEMY_BINDS'] = {
    'yuxin': 'sqlite:///yuxin.db',       # 雨昕相关
    'config': 'sqlite:///config.db',       # 三方配置相关
    'topic': 'sqlite:///topic.db'       # 题目
}

db.init_app(app)

# 主路由
from backend.routes.routes_main import main_api as main_routes
app.register_blueprint(main_routes)
# 雨昕的路由
from backend.routes.routes_yuxin import yuxin_api as yuxin_routes
app.register_blueprint(yuxin_routes)
#蒲公英的路由
from backend.routes.routes_pgyer import pgyer_api as pgyer_routes
app.register_blueprint(pgyer_routes)
#题目的路由
from backend.routes.routes_topic import topic_api as topic_routes
app.register_blueprint(topic_routes)


with app.app_context():
    db.create_all()

# 注册全局处理器
register_handlers(app)
CORS(app)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
