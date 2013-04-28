'''
Created on 28-04-2013

@author: turekj
'''
from HTMLParser import HTMLParser
from tree.htmltreenode import HTMLTreeNode

class HTMLTreeBuilder(HTMLParser):
    __root = None
    __valid_tags = None
    __valid_attributes = None
    __last_node = None
    
    def handle_starttag(self, tag, attrs):
        if self.__valid_tags is not None and tag not in self.__valid_tags:
            return
        
        attributes = { attribute: value for attribute, value in reversed(attrs)}
        
        if self.__valid_attributes is not None:
            attributes = { attribute: attributes[attribute] for attribute in filter(lambda x: x in attributes, self.__valid_attributes)}
        
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
        self.__valid_attributes = valid_attributes
        self.__last_node = None
        
        self.feed(html)
        
        return self.__root
