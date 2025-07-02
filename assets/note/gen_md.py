import os

# 设定要扫描的目录
ROOT_DIR = "CS"
OUTPUT_FILE = "README.md"


def generate_md_structure(root_dir):
    md_content = "# 目录结构\n\n"
    root_basename = os.path.basename(root_dir)  # 获取根目录名称
    md_content += f"- {root_basename}/\n"
    print(f"Markdown 结构查询: {os.walk(root_dir)}")

    for root, dirs, files in os.walk(root_dir):
        # 计算当前层级
        level = root.replace(root_dir, "").count(os.sep)
        indent = "  " * (level + 1)  # 增加缩进使其对齐
        print(f"Markdown 当前层级: {level}")
        print(f"Markdown 当前目录: {os.path.basename(root)}")
        # 获取相对路径，并确保保留根目录
        relative_root = os.path.relpath(root, os.path.dirname(root_dir)).replace("\\", "/")

        # 添加子目录
        if root != root_dir:
            md_content += f"{indent}- {os.path.basename(root)}/\n"

        # 添加文件，保持完整路径
        for f in files:
            file_path = os.path.join(root, f).replace("\\", "/")  # 兼容 Windows
            full_relative_path = os.path.relpath(file_path, os.path.dirname(root_dir)).replace("\\", "/")
            md_content += f"{indent}  - [{f}]({full_relative_path})\n"

    return md_content


# 生成 Markdown 并写入文件
with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
    f.write(generate_md_structure(ROOT_DIR))

print(f"Markdown 目录已生成: {OUTPUT_FILE}")