from functools import wraps

def response_json_wrapper(func):
    @wraps(func)  # 保留原函数元信息
    async def wrapper(*args, **kwargs):
        # 调用被装饰的函数并获取返回值
        result = await func(*args, **kwargs)
        # 假设函数返回的是一个字典，包含 code, data, message
        if isinstance(result, dict) and 'code' in result and 'data' in result and 'message' in result:
            return result
        else:
            # 如果不符合预期格式，包装为默认响应
            return {"code": 200, "data": result, "message": "success"}
    return wrapper

def make_response( data=None, code=200, message='success'):
    """
    创建统一的响应格式
    """
    return {
        "code": code,
        "data": data,
        "message": message
    }