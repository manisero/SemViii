"""
    HTML tree builder. Constructs C{HTMLTreeNode} out of any given HTML.
    
    Usage: invoke C{build_tree(html, valid_tags, valid_attributes)} function. 
    
    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 28-04-2013
    @version: 1.0
"""
from HTMLParser import HTMLParser
from StringIO import StringIO
import urllib2
import Image
from tree.htmltreenode import HTMLTreeNode


class HTMLTreeBuilder(HTMLParser):
    __root = None
    __valid_tags = None
    __valid_attributes = None
    __blacklisted_tags = None
    __last_node = None
    __append_data = True
    __url = ''
    
    def handle_starttag(self, tag, attributes):
        if self.__blacklisted_tags and tag in self.__blacklisted_tags:
            self.__append_data = False

        if self.__valid_tags is not None and tag not in self.__valid_tags:
            return
        
        attributes = {attribute: value for attribute, value in reversed(attributes)}
        
        if self.__valid_attributes is not None:
            attributes = {attribute: attributes[attribute] for attribute in
                          filter(lambda x: x in attributes, self.__valid_attributes)}
        
        node = HTMLTreeNode(tag, "", attributes)
        
        if self.__root is None:
            self.__root = node
            self.__last_node = self.__root
        else:
            self.__last_node.add_node(node)
            self.__last_node = node

        node.set_size(self.__get_image_size(tag, attributes))
    
    def handle_endtag(self, tag):
        if self.__blacklisted_tags and tag in self.__blacklisted_tags:
            self.__append_data = True

        if self.__valid_tags is not None and tag not in self.__valid_tags:
            return
        
        self.__last_node = self.__last_node.get_parent()
    
    def handle_data(self, data):
        if not self.__append_data:
            return

        if self.__last_node is not None:
            self.__last_node.append_text(data)
    
    def build_tree(self, url, html, valid_tags=None, valid_attributes=None, blacklisted_tags=None):
        """
            Builds C{HTMLTreeNode} out of C{html}.
            
            @param html: HTML source code to process
            @param valid_tags: List of HTML tags converted to tree nodes. If C{None} constructs tree of all tags.
            @param valid_attributes: List of HTML attributes stored in tree nodes. If C{None} stores all the attributes.
            
            @rtype: HTMLTreeNode
        """

        self.__url = url
        self.__root = None
        self.__valid_tags = valid_tags
        self.__valid_attributes = valid_attributes
        self.__blacklisted_tags = blacklisted_tags
        self.__last_node = None
        
        self.feed(html)
        
        return self.__root

    def __get_image_size(self, tag, attributes):
        try:
            if tag == 'img' and 'src' in attributes:
                src = attributes['src']

                if src.startswith('/'):
                    src = self.__url + src

                image_file = StringIO(urllib2.urlopen(src).read())
                image = Image.open(image_file)

                return image.size

        except Exception as ex:
            return 0, 0

        return 0, 0