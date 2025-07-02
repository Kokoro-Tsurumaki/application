import os

from flask import Blueprint, render_template, jsonify, send_file, send_from_directory, request

from werkzeug.utils import secure_filename

from backend.net.template.resume import zhangxianjie_resume
from backend.utils.path_util import static_dir
from backend.net.routes_decorator import response_json_wrapper, make_response

main_api = Blueprint('main', __name__, url_prefix='/api')

#
#
#
# # 分发
# @main_app.route('/distribute')
# def distribute():
#     return render_template("distribute.html")
#
# @main_app.route('/test')
# def test():
#     return render_template("test.html")
#
# @main_app.route('/test_', methods=['GET'])
# @response_json_wrapper
# def test_():
#     return "test"
#
#
# # 近代史json
# @main_app.route('/json/modern-history')
# def new():
#     return send_from_directory(
#         static_dir,
#         'data/modern-history.json',
#         mimetype='application/json'
#     )
#
# # 近代史word
# @main_app.route('/word/modern-history')
# def document_docx():
#     return send_file(
#         "../../assets/static/data/modern-history.docx",
#         mimetype='application/vnd.openxmlformats-officedocument.wordprocessingml.document',
#         as_attachment=False  # True 会触发下载，False 直接显示
#     )
#
#
# @main_app.route('/word/resume')
# def document_docx_resume():
#     return send_file(
#         "../../assets/static/data/resume.docx",
#         mimetype='application/vnd.openxmlformats-officedocument.wordprocessingml.document',
#         as_attachment=False  # True 会触发下载，False 直接显示
#     )
#
# # word文档解析
# @main_app.route('/document/<document_type>')
# def document(document_type):
#     # 定义合法路径映射
#     script_mapping = {
#         'modern-history': 'js/modern-history-docx.js',
#         'resume': 'js/resume-docx.js',
#     }
#     script_mapping_css = {
#         'modern-history': 'css/modern-history-docx.css',
#         'resume': 'css/resume-docx.css',
#     }
#     # 获取对应的JS路径（默认回退）
#     js_path = script_mapping.get(document_type, 'js/default.js')
#     css_path = script_mapping_css.get(document_type, 'css/default.css')
#     return render_template("document.html", js_path=js_path,css_path=css_path)
#
#
# # 列表
# # @main.route('/table')
# # def table():
# #     return render_template("table.html")
#
#
# @main_app.route('/tables/<table_type>')
# def table_show(table_type):
#     # 定义合法路径映射
#     script_mapping = {
#         'modern-history': 'js/modern-history.js',
#         'resume': 'js/resume-docx.js',
#     }
#
#     # 获取对应的JS路径（默认回退）
#     js_path = script_mapping.get(table_type, 'js/default.js')
#     # 渲染模板并传递JS路径
#     return render_template('tables.html', js_path=js_path)
#
# @main_app.route('/live2d')
# def live2d():
#     return render_template("live2d.html")
#
#
# # 下载补丁
# @main_app.route('/hotfix/<file_name>')
# def hotfix(file_name):
#     return send_file(
#         "assets/static/data/hotfix/"+file_name,
#         mimetype='application/vnd.openxmlformats-officedocument.wordprocessingml.document',
#         as_attachment=False  # True 会触发下载，False 直接显示
#     )
#
#
# @main_app.route('/upload_hotfix', methods=['POST'])
# def upload_hotfix():
#     # 检查是否有文件部分
#     if 'file' not in request.files:
#         return jsonify({
#             "status": "error",
#             "error": "No file part"
#         }), 400
#
#     file = request.files['file']
#
#     # 检查是否选择了文件
#     if file.filename == '':
#         return jsonify({
#             "status": "error",
#             "error": "No selected file"
#         }), 400
#
#     # 确保上传目录存在
#     upload_folder = "assets/static/data/hotfix/"
#     os.makedirs(upload_folder, exist_ok=True)
#
#     # 处理文件名
#     filename = secure_filename(file.filename)
#     save_path = os.path.join(upload_folder, filename)
#
#     try:
#         file.save(save_path)
#         return jsonify({
#             "status": "success",
#             "path": f"/hotfix/{filename}",
#             "size": os.path.getsize(save_path)
#         }), 200
#     except Exception as e:
#         print(str(e))
#         return jsonify({
#             "status": "error",
#             "error": str(e)
#         }), 500

@response_json_wrapper
@main_api.route('/resume/<name>', methods=['GET'])
def resume(name):
    if name == "张先杰":
        return  make_response(200, zhangxianjie_resume, message='success')
    else: return  make_response(404, None, message='改名称对应的简历不存在')



