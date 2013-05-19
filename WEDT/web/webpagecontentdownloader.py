"""
    Allows to download given web page content as HTML using C{download(url)} method.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @note: The class forces content's ASCII encoding.
    @version: 1.0
"""
import urllib2


class WebPageContentDownloader:
    __default_charset = 'utf8'
    
    def download(self, url):
            headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko)' +
                                     ' Chrome/28.0.1468.0 Safari/537.36'}
            request = urllib2.Request(url, None, headers)
            response = urllib2.urlopen(request)
            charset = response.headers.getparam('charset')
            
            if charset is None:
                charset = self.__default_charset
            
            return response.read().decode(charset, 'ignore').encode('ascii', 'ignore')
