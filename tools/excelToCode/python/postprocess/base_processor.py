# -*- coding: utf-8 -*-

class BaseProcessor(object):

	def __init__(self, exporter, generator_info):
		super(BaseProcessor, self).__init__()

		self.exporter = exporter
		self.generator_info = generator_info

	def run(self):
		pass
