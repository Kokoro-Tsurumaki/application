from pathlib import Path

# 项目根目录
project_path = Path(__file__).resolve().parent

# 资源文件路径
assets_dir = project_path / "assets"

template_dir = assets_dir / "templates"
static_dir = assets_dir / "static"
# apk文件路径
apk_dir = assets_dir / "apk"
# log路径
log_dir = assets_dir / "config" / "log"
log_path = assets_dir / "config" / "log" / "data.log"
# 控制文件路径
config_path = assets_dir / "config" / "config.ini"
