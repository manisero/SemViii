"""
    HTML tree builder. Constructs C{HTMLTreeNode} out of any given HTML.
    
    Usage: invoke C{build_tree(html, valid_tags, valid_attributes)} function. 
    
    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 28-04-2013
    @version: 1.0
"""
from HTMLParser import HTMLParser
from tree.htmltreenode import HTMLTreeNode


class HTMLTreeBuilder(HTMLParser):
    __root = None
    __valid_tags = None
    __valid_attributes = None
    __last_node = None
    __append_data = True
    
    def handle_starttag(self, tag, attributes):
        if tag == 'script':
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
    
    def handle_endtag(self, tag):
        if tag == 'script':
            self.__append_data = True

        if self.__valid_tags is not None and tag not in self.__valid_tags:
            return
        
        self.__last_node = self.__last_node.get_parent()
    
    def handle_data(self, data):
        if not self.__append_data:
            return

        if self.__last_node is not None:
            self.__last_node.append_text(data)
    
    def build_tree(self, html, valid_tags=None, valid_attributes=None):
        """
            Builds C{HTMLTreeNode} out of C{html}.
            
            @param html: HTML source code to process
            @param valid_tags: List of HTML tags converted to tree nodes. If C{None} constructs tree of all tags.
            @param valid_attributes: List of HTML attributes stored in tree nodes. If C{None} stores all the attributes.
            
            @rtype: HTMLTreeNode
        """
        
        self.__root = None
        self.__valid_tags = valid_tags
        self.__valid_attributes = valid_attributes
        self.__last_node = None
        
        self.feed(html)
        
        return self.__root
