"""
    HTML tree node structure. Stores following information:
    
        - C{__tag}: described HTML tag, for example DIV
        - C{__text}: plain text content in between start and end tag
        - C{__nodes}: list containing node's children
        - C{__attributes}: dictionary containing tag's attributes, for example class="sampleClass"
        - C{__parent}: node's parent (self if root)
    
    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 28-04-2013
    @version: 1.0
"""
import re


class HTMLTreeNode:
    __tag = ""
    __text = ""
    __nodes = []
    __attributes = []
    __parent = None
    __size = 0, 0
    
    def __init__(self, tag, text, attributes):
        self.__tag = tag
        self.__text = text
        self.__nodes = []
        self.__attributes = attributes
        self.__parent = self
        
    def get_tag(self):
        return self.__tag
    
    def get_text(self):
        return self.__text
    
    def get_nodes(self):
        return self.__nodes
    
    def get_attributes(self):
        return self.__attributes
    
    def get_parent(self):
        return self.__parent
        
    def set_parent(self, parent):
        self.__parent = parent

    def get_size(self):
        return self.__size

    def set_size(self, size):
        self.__size = size
        
    def append_text(self, text):
        self.__text += str(text)
        self.__text = re.sub('\s+', ' ', self.__text)
        
    def add_node(self, node):
        self.__nodes.append(node)
        node.set_parent(self)
        
    def add_nodes(self, nodes):
        for node in nodes:
            self.add_node(node)
            
    def is_root(self):
        return self.__parent == self
    
    def next(self):
        if self.__nodes:
            return self.__nodes[0]
        
        parent_node = self.__parent
        child_node = self
        
        while parent_node is not None and parent_node != child_node:
            if parent_node.get_nodes()[-1] != child_node:
                child_node_index = parent_node.get_nodes().index(child_node)
                return parent_node.get_nodes()[child_node_index + 1]
            else:
                child_node = parent_node
                parent_node = child_node.get_parent()
        
        return None
    
    def previous(self):
        parent_node = self.__parent
        child_node = self
        
        if parent_node == child_node:
            return None
        
        if parent_node.get_nodes()[0] == child_node:
            return parent_node
        
        child_node_index = parent_node.get_nodes().index(child_node)
        previous_node = parent_node.get_nodes()[child_node_index - 1]
        
        while previous_node.get_nodes():
            previous_node = previous_node.get_nodes()[-1]
        
        return previous_node

    def tag_attribute_equals(self, other_node, compared_attributes=None):

        stripped_self_attributes = self.get_attributes()
        stripped_other_attributes = other_node.get_attributes()

        if compared_attributes is not None:
            stripped_self_attributes = {attribute: self.get_attributes()[attribute]
                                        for attribute in compared_attributes if attribute in self.get_attributes()}
            stripped_other_attributes = {attribute: other_node.get_attributes()[attribute] for attribute
                                         in compared_attributes if attribute in other_node.get_attributes()}

        return self.get_tag() == other_node.get_tag() and stripped_self_attributes == stripped_other_attributes
