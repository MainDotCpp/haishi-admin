# -*- coding: utf-8 -*-
from pathlib import Path
import jinja2
import re

tasks = [
    {
        "package": "resource",
        "biz": "server",
        "comment": "服务器",
    },
    {
        "package": "resource",
        "biz": "domain",
        "comment": "域名",
    }
]
# 更改当前工作目录
work_dir = Path(__file__).parent.parent
output_dir = work_dir.joinpath("src/main/java/com/haishi/admin")
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


def reverse_replace(s, data):
    for key in data:
        s = s.replace(data[key], "{{" + key + "}}")
    return s


def eject_template(data):
    """
    将生成目录的文件提取到模板目录, 并替换模板变量
    :return:
    """
    for it in output_dir.rglob("*"):
        if it.is_dir():
            continue
        output_path = Path.relative_to(it, output_dir)

        output_path = reverse_replace(str(output_path) + ".j2", data)
        output_path = work_dir / f"{template_dir}__{data.get('biz')}" / output_path
        if "{{Biz}}" not in str(output_path):
            continue
        print(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        with open(it, "r", encoding="utf-8") as f:
            content = f.read()
            content = reverse_replace(content, data)
            with open(output_path, "w", encoding="utf-8") as f:
                f.write(content)


def convert_dict(data):
    data["Biz"] = data["biz"].capitalize()
    data["BIZ"] = data["biz"].upper()
    data["B_I_Z"] = covert_to_const(data["biz"])


if __name__ == '__main__':
    for task in tasks:
        convert_dict(task)
        render_all_templates(task)
        # eject_template(task)
