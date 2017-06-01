# -*- coding: utf-8 -*-
import os
import sys
import shutil
import xlsconfig

def to_utf8(s):
	tp = type(s)
	if tp == unicode: return s.encode("utf-8")
	if tp == str: return s
	return s

def int_to_base26(value):
	asciiA = ord('A')

	value += 1

	ret = ""
	while value != 0:
		mod = value % 26
		value = value // 26
		if mod == 0:
			mod = 26
			value -= 1

		ret = chr(asciiA + mod - 1) + ret

	return ret

def base26_to_int(value):
	asciiA = ord('A')

	ret = 0
	for s in value:
		ret = ret * 26 + ord(s) - asciiA + 1

	return ret - 1


# 转换成类的名称格式。首字母大写驼峰法。
def to_class_name(name):
	strs = name.split('_')
	for i, s in enumerate(strs):
		if len(s) > 0:
			s = s[0].upper() + s[1:]
		strs[i] = s
	return "".join(strs)

# 根据python文件路径进行import。
# filename 可以是路径，也可以是'.'分割的串，但不能有后缀名。
def import_file(filename):
	module_name = filename.replace("/", '.')
	only_name = module_name.split('.')[-1]
	return __import__(module_name, globals(), locals(), [only_name, ])

# 打印错误日志。如果不是FORCE_FUN模式，会将错误日志以异常的形式抛出。
def log_error(msg, *args):
	if len(args) > 0: msg = msg % args

	if xlsconfig.FORCE_RUN:
		print "错误：", msg
	else:
		raise RuntimeError, msg

# 确保文件所在的路径存在。file_path是文件的路径。
def ensure_folder_exist(file_path):
	output_dir = os.path.dirname(file_path)
	if not os.path.isdir(output_dir):
		os.makedirs(output_dir)

	return

# 确保file_path所经过的目录存在，并且存在__init__.py
def ensure_package_exist(root, file_path):
	sub_path = os.path.dirname(file_path)
	if os.path.isdir(os.path.join(root, sub_path)):
		return

	full_path = root
	paths = sub_path.replace('\\', '/').split('/')
	for path in paths:
		full_path = os.path.join(full_path, path)
		if os.path.isdir(full_path): continue

		os.mkdir(full_path)
		init_path = os.path.join(full_path, "__init__.py")
		with open(init_path, "w") as f:
			f.close()

	return

# src文件是否比dst文件更新
def if_file_newer(src, dst):
	t1, t2 = 0, 0
	try:
		t1 = os.path.getmtime(src)
		t2 = os.path.getmtime(dst)
	except OSError:
		return False

	return t1 > t2

# 收集path目录下所有后缀符合exts的文件。
# 返回的路径是相对于path的相对路径。
def gather_all_files(path, exts):
	ret = []

	path_len = len(path)
	if path[-1] != '/': path_len += 1

	for root, dirs, files in os.walk(path):
		i = 0
		while i < len(dirs):
			if dirs[i][0] == '.':
				dirs.pop(i)
			else:
				i += 1

		relative_path = root[path_len : ]

		for fname in files:
			if fname.startswith("~$"): continue

			ext = os.path.splitext(fname)[1]
			if ext not in exts: continue

			file_path = os.path.join(relative_path, fname)
			ret.append(file_path.replace('\\', '/'))

	return ret

# 将src目录递归的拷贝到dst目录。
# 与shutil.copytree不同之处在于，如果目标文件存在，会直接进行覆盖，而不是抛异常。
def copytree(src, dst, symlinks=False, ignore=None):
	names = os.listdir(src)
	if ignore is not None:
		ignored_names = ignore(src, names)
	else:
		ignored_names = set()

	if not os.path.isdir(dst):
		os.makedirs(dst)

	errors = []
	for name in names:
		if name in ignored_names:
			continue

		srcname = os.path.join(src, name)
		dstname = os.path.join(dst, name)
		if os.path.isdir(srcname):
			copytree(srcname, dstname, symlinks, ignore)
		else:
			shutil.copy2(srcname, dstname)

	return

# 如果目标文件夹已经存在，则不进行任何操作
def safe_mkdir(path, recreate = False):
	if recreate and os.path.exists(path):
		shutil.rmtree(path)
		os.mkdir(path)
	elif not os.path.exists(path):
		os.mkdir(path)

# 如果目标文件夹已经存在，则不进行任何操作
def safe_makedirs(path, recreate = False):
	if recreate and os.path.exists(path):
		shutil.rmtree(path)
		os.makedirs(path)
	elif not os.path.exists(path):
		os.makedirs(path)


stdout = sys.stdout
stderr = sys.stderr

class Redirect(object):
	def write(self, msg):
		stdout.write(msg.decode("utf-8").encode(stdout.encoding))

	def flush(self):
		stdout.flush()

def redirect_iostream():
	if stdout != sys.stdout: return
	if not stdout.encoding or stdout.encoding.lower() == "utf-8": return

	stdout.write("redirect output stream.\n")
	stdout.flush()

	stream = Redirect()
	sys.stdout = stream
	sys.stderr = stream


def format_slash(path):
	return path.replace('\\', '/')
