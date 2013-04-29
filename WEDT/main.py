import sys
from web.webpagecontentdownloader import WebPageContentDownloader
from tree.htmltreebuilder import HTMLTreeBuilder
from algorithm.blogrecognizer import BlogRecognizer

if __name__ == "__main__":
	
	if len(sys.argv) > 1:
		try:
			content = WebPageContentDownloader().download(sys.argv[1])
			
		except:
			print >> sys.stderr, 'Failed to open URL: ' + sys.argv[1]
			sys.exit(-1)
			
		html_tree = HTMLTreeBuilder().build_tree(content, ['div'], ['class'])

		print 'Is this a blog? ' + str(BlogRecognizer().is_blog(html_tree))
			

	else:
		print >> sys.stderr, 'Bad syntax. Use: ' + sys.argv[0] + ' url'
