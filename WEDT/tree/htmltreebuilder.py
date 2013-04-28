'''
Created on 28-04-2013

@author: turekj
'''
from HTMLParser import HTMLParser
from tree.htmltreenode import HTMLTreeNode

class HTMLTreeBuilder(HTMLParser):
    __root = None
    __valid_tags = None
    __valid_attibutes = None
    __last_node = None
    
    def handle_starttag(self, tag, attrs):
        attributes = { attribute: value for attribute, value in reversed(attrs)}
        
        node = HTMLTreeNode(tag, "", attributes)
        
        if self.__root is None:
            self.__root = node
            self.__last_node = self.__root
        else:
            self.__last_node.add_node(node)
            self.__last_node = node
    
    def handle_endtag(self, tag):
        self.__last_node = self.__last_node.get_parent()
    
    def handle_data(self, data):
        self.__last_node.append_text(data)
    
    def build_tree(self, html, valid_tags = None, valid_attributes = None):
        self.__root = None
        self.__valid_tags = valid_tags
        self.__valid_attibutes = valid_attributes
        self.__last_node = None
        
        self.feed(html)
        
        return self.__root
