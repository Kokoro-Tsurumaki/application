from backend.core.extensions import db

class Note(db.Model):
    __tablename__ = 'notes'
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(80), nullable=False)
    content = db.Column(db.Text)
    parent_id = db.Column(db.Integer, db.ForeignKey('notes.id'))
    is_folder = db.Column(db.Boolean, default=False)
    created_at = db.Column(db.DateTime, server_default=db.func.now())
    updated_at = db.Column(db.DateTime, onupdate=db.func.now())
    # __table_args__ = (
    #     db.Index('idx_parent', 'parent_id'),  # 加速文件夹查询
    #     db.Index('idx_updated', 'updated_at')  # 加速最近修改查询
    # )

class YUXINTestUser(db.Model):
    __bind_key__ = 'yuxin'
    __tablename__ = 'yuxin_user'
    user_id = db.Column(db.Integer, primary_key=True)                       #主键id
    name = db.Column(db.String(80), nullable=False)                         #谁的账户
    username = db.Column(db.String(80), nullable=False, unique=True)        #账户
    password = db.Column(db.String(80), nullable=False)                     #密码

class PgyerConfig(db.Model):
    __bind_key__ = 'config'
    __tablename__ = 'pgyer_config'
    __table_args__ = (
        db.UniqueConstraint('apikey', 'appkey', name='uix_apiKey_appKey'),  # 名称+API Key组合唯一
    )
    config_id = db.Column(db.Integer, primary_key=True)                     #主键id
    name = db.Column(db.String(80), nullable=False, unique=True)            #描述名称
    apikey = db.Column(db.String(80), nullable=False)                       #蒲公英apikey
    appkey = db.Column(db.String(80), nullable=False)                       #蒲公英appkey
    buildBuildVersion = db.Column(db.Integer, nullable=False)               #对应的蒲公英提交版本
    downloadURL = db.Column(db.String(80), nullable=False)                  #下载链接

class TopicType(db.Model):
    __bind_key__ = 'topic'
    __tablename__ = 'topic_type'
    type_id = db.Column(db.Integer, primary_key=True)
    type_name = db.Column(db.String(80), nullable=False, unique=True)
    # 一对多关系：一个分类下可以有多个标签（可选）
    tags = db.relationship('TopicTag', backref='type', lazy='dynamic')
    # 直接关联的Topic（tag_id为NULL）
    direct_topics = db.relationship(
        'Topic',
        backref='direct_type',
        primaryjoin='and_(TopicType.type_id == Topic.type_id, Topic.tag_id.is_(None))',  # 关键过滤条件
        lazy='dynamic'
    )

    # 通过标签关联的Topic（tag_id非NULL）
    tagged_topics = db.relationship(
        'Topic',
        primaryjoin='and_(TopicType.type_id == Topic.type_id, Topic.tag_id.isnot(None))',
        lazy='dynamic'
    )

class TopicTag(db.Model):
    __bind_key__ = 'topic'
    __tablename__ = 'topic_tag'
    # 联合唯一约束：同一分类下标签名不可重复
    __table_args__ = (
        db.UniqueConstraint('type_id', 'tag_name', name='uq_tag_type_name'),
    )
    tag_id = db.Column(db.Integer, primary_key=True)
    tag_name = db.Column(db.String(80), nullable=False, unique=True)
    type_id = db.Column(db.Integer, db.ForeignKey('topic_type.type_id'), nullable=False)
    # 一对多关系：一个标签下有多个主题
    topics = db.relationship('Topic', backref='tag', lazy='dynamic')


class Topic(db.Model):
    __bind_key__ = 'topic'
    __tablename__ = 'topic'
    __table_args__ = (
        # 联合唯一约束：同一标签下主题名不可重复（如果通过标签关联）
        db.UniqueConstraint('tag_id', 'topic_name', name='uq_topic_tag_name'),
        # 联合唯一约束：同一分类下主题名不可重复（如果直接通过分类关联）
        db.UniqueConstraint('type_id', 'topic_name', name='uq_topic_type_name'),
    )
    topic_id = db.Column(db.Integer, primary_key=True)
    topic_name = db.Column(db.String(80), nullable=False, unique=True)
    topic_answer = db.Column(db.String(80), nullable=False, unique=True)
    # 外键：关联分类（必须）
    type_id = db.Column(db.Integer, db.ForeignKey('topic_type.type_id'), nullable=False)
    # 外键：关联标签（可选，允许为 NULL）
    tag_id = db.Column(db.Integer, db.ForeignKey('topic_tag.tag_id'))
