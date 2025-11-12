import os


def merge_files_to_all(folder_path, output_file='all.md'):
    """
    将指定文件夹内所有文件内容合并到单个all文件中

    :param folder_path: 要读取的文件夹路径
    :param output_file: 输出的合并文件名
    """
    # 确保输出文件在根目录
    output_path = os.path.join(os.getcwd(), output_file)

    try:
        with open(output_path, 'w', encoding='utf-8') as all_file:
            # 遍历文件夹
            for root, _, files in os.walk(folder_path):
                for filename in files:
                    file_path = os.path.join(root, filename)

                    # 跳过all文件自身（避免循环写入）
                    if os.path.abspath(file_path) == os.path.abspath(output_path):
                        continue

                    try:
                        # 写入文件名作为分隔标记
                        all_file.write(f"\n\n===== {filename} =====\n\n")

                        # 读取并写入内容
                        with open(file_path, 'r', encoding='utf-8') as f:
                            all_file.write(f.read())

                    except UnicodeDecodeError:
                        print(f"警告：跳过二进制文件 {filename}")
                    except Exception as e:
                        print(f"处理 {filename} 时出错: {str(e)}")

        print(f"所有文件内容已合并到 {output_path}")

    except Exception as e:
        print(f"创建输出文件失败: {str(e)}")


# 使用示例（假设要合并folder_to_scan文件夹）
merge_files_to_all('CS')


