from functools import wraps

from flask import jsonify

def response_json_wrapper(func):
    @wraps(func)  # 保留原函数元信息
    def wrapper(*args, **kwargs):
        # 调用被装饰的函数并获取返回值
        result = func(*args, **kwargs)
        # 假设函数返回的是一个字典，包含 code, data, message
        if isinstance(result, dict) and 'code' in result and 'data' in result and 'message' in result:
            return jsonify(result), result['code']
        else:
            # 如果不符合预期格式，包装为默认响应
            return jsonify({"code": 200, "data": result, "message": "success"}), 200
    return wrapper

def make_response(code=200, data=None, message='success'):
    """
    创建统一的响应格式
    """
    return {
        "code": code,
        "data": data,
        "message": message
    }