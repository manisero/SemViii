'''
Created on 29-04-2013

@author: turekj
'''
import urllib2

class WebPageContentDownloader:
    def download(self, url):
            response = urllib2.urlopen(url)
            encoding = response.headers.getparam('charset')

            return response.read().decode(encoding)