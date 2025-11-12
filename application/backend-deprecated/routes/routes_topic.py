from backend.core.extensions import db
from flask import Blueprint,request

from backend.net.models import TopicType, Topic, TopicTag
from backend.net.routes_decorator import make_response, response_json_wrapper

topic_api = Blueprint('topic', __name__, url_prefix='/api/topic')

#添加题目类型
@topic_api.route('/addType', methods=['POST'])
@response_json_wrapper
def add_topic_type():
    data = request.get_json()
    if not data or not all(key in data for key in ['name']):
        return make_response(400,message='Error:Missing required fields (name)')
    new_topic_type = TopicType(type_name = data['name'])
    db.session.add(new_topic_type)
    db.session.commit()
    return make_response(200,new_topic_type.type_id,message='Topic Type created')

#添加标签
@topic_api.route('/addTag', methods=['POST'])
@response_json_wrapper
def add_tag():
    data = request.get_json()
    if not data or not all(key in data for key in ['name','typeId']):
        return make_response(400, message='Error:Missing required fields (name,typeId)')
    new_tag = TopicTag(tag_name=data['name'], type_id=data['typeId'])
    db.session.add(new_tag)
    db.session.commit()
    return make_response(200,new_tag.tag_id,message='Topic created')


#添加题目
@topic_api.route('/addTopic', methods=['POST'])
@response_json_wrapper
def add_topic():
    data = request.get_json()
    if not data or not all(key in data for key in ['name','answer','typeId']):
        return make_response(400, message='Error:Missing required fields (name,answer,typeId)')
    new_topic = Topic(
        topic_name=data['name'],
        topic_answer=data['answer'],
        type_id=data['typeId'],
        tag_id=data['tagId'] if 'tagId' in data else None)
    db.session.add(new_topic)
    db.session.commit()
    return make_response(200,new_topic.type_id,message='Topic created')

@topic_api.route('/types', methods=['GET'])
@response_json_wrapper
def get_types():
    types = TopicType.query.all()
    type_list = [{
        'typeId': type.type_id,
        'name': type.type_name,
    } for type in types]
    return make_response(200, type_list, message='Topic Type list retrieved')

@topic_api.route('/topics/<int:type_id>', methods=['GET'])
@response_json_wrapper
def get_topics(type_id):
    topics = TopicType.query.get(type_id)
    # 序列化直接关联的主题
    direct_topics = [
        {
            'topic_id': topic.topic_id,
            'topic_name': topic.topic_name,
            'topic_answer': topic.topic_answer
        }
        for topic in topics.direct_topics.all()  # 注意调用 .all()
    ]

    # 序列化标签及其关联的主题
    tags = []
    for tag in topics.tags.all():  # 注意调用 .all()
        tag_data = {
            'tag_id': tag.tag_id,
            'tag_name': tag.tag_name,
            'topics': [
                {
                    'topic_id': topic.topic_id,
                    'topic_name': topic.topic_name,
                    'topic_answer': topic.topic_answer
                }
                for topic in tag.topics.all()  # 注意调用 .all()
            ]
        }
        tags.append(tag_data)

    return make_response(200, {"topic" :direct_topics,"tag":tags}, message='Topic Type list retrieved')



