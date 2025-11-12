from ..core.extensions import db
from ..net.models import PgyerConfig
from ..net.routes_decorator import make_response, response_json_wrapper
from flask import request,Blueprint

pgyer_api = Blueprint('pgyer', __name__, url_prefix='/api/pgyer')
# pgyer_api = APIRouter(prefix='/api/pgyer', tags=["蒲公英"])

@pgyer_api.route('/addConfig', methods=['POST'])
@response_json_wrapper
def add_config():
     data = request.get_json()
     if not data or not all(key in data for key in ['name', 'apikey', 'appkey']):
        return make_response(400,message='Error:Missing required fields (name, apikey, appkey)')
     new_config = PgyerConfig(
        name=data['name'],
        apikey=data['apikey'],
        appkey=data['appkey'],
         buildBuildVersion=0,
         downloadURL=""
     )
     db.session.add(new_config)
     db.session.commit()
     return make_response(200,new_config.config_id,message='Config created')

@pgyer_api.route('/configs', methods=['GET'])
@response_json_wrapper
def get_configs():
    configs = PgyerConfig.query.all()
    config_list = [{
        'config_id': config.config_id,
        'name': config.name,
        'apikey': config.apikey,
        'appkey': config.appkey,
    } for config in configs]
    return make_response(200, config_list, message='Config list retrieved')


@pgyer_api.route('/deleteConfig/<int:config_id>', methods=['DELETE'])
@response_json_wrapper
def delete_config(config_id):
    config = PgyerConfig.query.get(config_id)
    if not config:
        return make_response(404, None, message='User not found')

    db.session.delete(config)
    db.session.commit()
    return make_response(200, None, message='Config deleted')