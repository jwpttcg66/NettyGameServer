# -*- coding: utf-8 -*-
import sys
import os

out = sys.stdout
ENCODING = out.encoding
print "enviroment encoding:", ENCODING

def native_str_with_encoding(v):
	tp = type(v)
	if v == str:
		v = v.decode("utf-8")
	elif v != unicode:
		v = str(v).decode("utf-8")
	return v.encode(ENCODING)

def native_str_utf8(v):
	tp = type(v)
	if v == unicode:
		v = v.encode("utf-8")
	elif v != str:
		v = str(v)
	return v

def log_with_encoding(*args):
	for i, v in enumerate(args):
		args[i] = native_str_with_encoding(v)

	args.append("\n")
	out.write(" ".join(args))

def log_utf8(*args):
	for i, v in enumerate(args):
		args[i] = native_str_utf8(v)

	args.append("\n")
	out.write(" ".join(args))

log = log_utf8
native_str = native_str_utf8

if ENCODING and ENCODING.lower() != "utf-8":
	log = log_with_encoding
	native_str = native_str_with_encoding
