import sys
from config.configurationprovider import ConfigurationProvider
from tree.htmltreebrowser import HTMLTreeBrowser
from web.webpagecontentdownloader import WebPageContentDownloader
from tree.htmltreebuilder import HTMLTreeBuilder
from algorithm.blogrecognizer import BlogRecognizer

if __name__ == "__main__":
    if len(sys.argv) > 1:
        try:
            content = WebPageContentDownloader().download(sys.argv[1])

            print 'Is this a blog? ' + str(BlogRecognizer().is_blog(content, HTMLTreeBuilder(),
                                                                    HTMLTreeBrowser(), ConfigurationProvider()))

        except Exception as ex:

            print >> sys.stderr, 'Failed to open URL: ' + sys.argv[1]
            print >> sys.stderr, str(ex)
            sys.exit(-1)

    else:
        print >> sys.stderr, 'Bad syntax. Use: ' + sys.argv[0] + ' url'
