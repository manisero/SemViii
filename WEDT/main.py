import sys
from algorithm.webpageclassifier import WebPageClassifier
from web.webpagecontentdownloader import WebPageContentDownloader

if __name__ == "__main__":
    if len(sys.argv) > 1:
        try:
            content = WebPageContentDownloader().download(sys.argv[1])

            print 'Classification: ' + str(WebPageClassifier('config.ini').classify(content))

        except Exception as ex:

            print >> sys.stderr, 'Failed to open URL: ' + sys.argv[1]
            print >> sys.stderr, str(ex)
            sys.exit(-1)

    else:
        print >> sys.stderr, 'Bad syntax. Use: ' + sys.argv[0] + ' url'
