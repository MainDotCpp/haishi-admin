# -*- coding: utf-8 -*-
from pathlib import Path
import jinja2

tasks = [
    {
        "package": "system",
        "biz_name": "user",
        "biz_name_upper": "User",
        "comment": "用户",
    },
    {
        "package": "system",
        "biz_name": "role",
        "biz_name_upper": "Role",
        "comment": "角色",
    },
    {
        "package": "system",
        "biz_name": "permission",
        "biz_name_upper": "Permission",
        "comment": "权限",
    }
]
# 更改当前工作目录
work_dir = Path(__file__).parent.parent
output_dir = work_dir.joinpath("generate")
template_dir = work_dir.joinpath("script/templates")


def render_all_templates(data):
    """
    将模板目录下的所有模板渲染到输出目录
    :return:
    """
    for template_path in Path.rglob(template_dir, "*.j2"):
        print(f"Rendering {template_path}...")
        out_path = output_dir / Path.relative_to(template_path, template_dir)
        out_path = str(out_path)
        out_path = out_path.replace(".j2", "")
        for key in data:
            out_path = out_path.replace("{{" + key + "}}", data[key])
        # 读取模板文件
        with open(template_path, "r", encoding="utf-8") as f:
            template = jinja2.Template(f.read())
        # 渲染模板
        rendered = template.render(data)
        print(out_path)
        # 写入文件
        Path(out_path).parent.mkdir(parents=True, exist_ok=True)
        with open(out_path, "w", encoding='utf-8') as f:
            f.write(rendered)


if __name__ == '__main__':
    for task in tasks:
        render_all_templates(task)
