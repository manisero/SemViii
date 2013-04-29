'''
Created on 29-04-2013

@author: turekj
'''
import urllib2

class WebPageContentDownloader:
    __default_charset = 'utf8'
    
    def download(self, url):
            response = urllib2.urlopen(url)
            charset = response.headers.getparam('charset')
            
            if charset is None:
                charset = self.__default_charset
            
            return response.read().decode(charset, 'ignore').encode('ascii', 'ignore')
