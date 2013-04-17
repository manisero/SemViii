import re
import sys
import urllib2
from HTMLParser import HTMLParser

class WebContentsAnalyzer:
	
	def print_html_tree(self, url):

		contents = self.__get_web_contents(url)

		parser = HTMLTreePrinter()
		parser.feed(contents)

	def __get_web_contents(self, url):

		try:
			response = urllib2.urlopen(url)
			encoding = response.headers.getparam('charset')

			return response.read().decode(encoding)

		except:
			print >> sys.stderr, 'Failed to open url: ' + url

			return ''

class HTMLTreePrinter(HTMLParser):

	__indent = 0

	def handle_starttag(self, tag, attrs):

		if tag == "div" or tag == "td":
			indentLeft = self.__indent

			treeLine = ''

			for x in range(0, self.__indent):

				if x == self.__indent - 1:
					treeLine += '+- '
				else:
					treeLine += '|  '

			treeLine += tag

			print treeLine
				
			self.__indent += 1

	def handle_endtag(self, tag):

		if tag == "div" or tag == "td":
			self.__indent -= 1

