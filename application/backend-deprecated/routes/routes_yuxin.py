
from backend.core.extensions import db
from backend.net.models import YUXINTestUser
from backend.net.routes_decorator import make_response, response_json_wrapper
from flask import Blueprint,request

yuxin_api = Blueprint('yuxin', __name__, url_prefix='/api/yuxin')
# 添加用户
@yuxin_api.route('/addUser', methods=['POST'])
@response_json_wrapper
def add_user():
    data = request.get_json()
    if not data or not all(key in data for key in ['name', 'username', 'password']):
        return make_response(400,message='Error:Missing required fields (name, username, password)')

    new_user = YUXINTestUser(
        name=data['name'],
        username=data['username'],
        password=data['password']
    )
    db.session.add(new_user)
    db.session.commit()
    return make_response(200,new_user.user_id,message='User created')

@yuxin_api.route('/users', methods=['GET'])
@response_json_wrapper
def get_users():
    users = YUXINTestUser.query.all()
    user_list = [{
        'user_id': user.user_id,
        'name': user.name,
        'username': user.username,
        'password': user.password,
    } for user in users]
    return make_response(200, user_list, message='User list retrieved')


@yuxin_api.route('/deleteUser/<int:user_id>', methods=['DELETE'])
@response_json_wrapper
def delete_user(user_id):
    user = YUXINTestUser.query.get(user_id)
    if not user:
        return make_response(404, None, message='User not found')

    db.session.delete(user)
    db.session.commit()
    return make_response(200, None, message='User deleted')