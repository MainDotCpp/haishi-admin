# -*- coding: utf-8 -*-
from pathlib import Path
import jinja2
import re

tasks = [
    {
        "package": "system",
        "biz": "dept",
        "comment": "部门",
    }
]
# 更改当前工作目录
work_dir = Path(__file__).parent.parent
output_dir = work_dir.joinpath("generate")
template_dir = work_dir.joinpath("script/templates")

def first_upper(s):
    return s[0].upper() + s[1:]

def covert_to_camel(s):
    return "".join([first_upper(x) for x in s.split("_")])

# 驼峰转常亮命名
def covert_to_const(s):
    return re.sub(r'([A-Z])', r'_\1', s).upper()


def render_all_templates(data):
    """
    将模板目录下的所有模板渲染到输出目录
    :return:
    """
    data["Biz"] = data["biz"].capitalize()
    data["BIZ"] = data["biz"].upper()
    data["B_I_Z"] = covert_to_const(data["biz"])
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
