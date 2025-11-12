import re
import traceback
from http.client import HTTPException

from flask import request,jsonify
from werkzeug.exceptions import BadRequest
from backend.net.routes_decorator import response_json_wrapper, make_response


def register_handlers(app):
    """集中注册所有全局处理器"""

    @app.before_request
    def before_request():
        """请求前统一处理（如认证、日志）"""
        if request.method == 'OPTIONS':
            return  # 跳过预检请求
        # 示例：记录请求日志
        print(f"Request: {request.method} {request.path}")

    @app.after_request
    def after_request(response):
        """响应后统一处理（如跨域头、日志）"""
        response.headers.add('Access-Control-Allow-Headers', 'Content-Type')
        return response

    @app.errorhandler(404)
    @response_json_wrapper
    def not_found(error):
        return make_response(code=404, data=None, message="资源未找到")

    @app.errorhandler(500)
    @response_json_wrapper
    def internal_error(error):
        return make_response(code=500, data=None, message="服务器内部错误")

    @app.errorhandler(Exception)
    @response_json_wrapper
    def handle_exception(e):
        print(f"Unhandled Exception: {str(e)}\n{traceback.format_exc()}")
        # 分类处理不同异常类型
        if isinstance(e, (ValueError, BadRequest)):
            # 客户端输入错误 (400)
            return make_response(400, None,"请求参数不合法")
        elif isinstance(e, HTTPException):
            return make_response( e.code,None,e.name)
        elif match := re.search(r"UNIQUE constraint failed: (\w+)\.(\w+)", str(e)):
            field = match.group(2)  # 直接获取字段名
            return make_response(400, None, f'{field}已存在')
        elif "FOREIGN KEY constraint failed" in str(e):
            return make_response(400, None, '关联数据不存在')
        else:
            # 未处理的服务器内部错误 (500)
            app.logger.error(f"Unhandled Exception: {str(e)}\n{traceback.format_exc()}")
            print(f"Unhandled Exception: {str(e)}\n{traceback.format_exc()}")
            return make_response(500, "服务器内部错误","Internal Server Error")

