"""
Created on 29-04-2013

@author: turekj
"""
from PIL import Image
import urllib2
from StringIO import StringIO


class ImageBasedWebsiteRecognizer:
    def is_image_based_website(self, html_tree):
        pattern = self.get_repeating_pattern(html_tree, ['div', 'img'], 'class', 5)
        
        filtered_pattern = self.has_mutual_parent(pattern)
        
        if filtered_pattern.keys() > 0:
            return self.has_image_larger_than(pattern, 5, 150, 150) 
        
        return False
    
    def get_repeating_pattern(self, html_tree, tags, attribute, minimum_repeats):
        current_node = html_tree
        
        attribute_sorted_nodes = {}
        
        while current_node is not None:
            if current_node.get_tag() in tags and attribute in current_node.get_attributes():
                attribute_value = current_node.get_attributes()[attribute]
                
                if attribute_value not in attribute_sorted_nodes:
                    attribute_sorted_nodes[attribute_value] = []
                    
                attribute_sorted_nodes[attribute_value].append(current_node) 
                
            current_node = current_node.next()
            
        repeated_nodes = { attribute: attribute_sorted_nodes[attribute] for attribute in attribute_sorted_nodes.keys() if len(attribute_sorted_nodes[attribute]) >= minimum_repeats }
        
        return repeated_nodes

    def has_mutual_parent(self, pattern):
        mutual_parent_pattern = {}
        
        for attribute_value in pattern:
            parent = None
            mutual_parent = True
            
            for node in pattern[attribute_value]:
                if parent is None:
                    parent = node.get_parent()
                elif node.get_parent() != parent:
                    mutual_parent = False
                    break
                
            if mutual_parent:
                mutual_parent_pattern[attribute_value] = pattern[attribute_value]
                
        return mutual_parent_pattern
    
    def has_image_larger_than(self, pattern, minimum_repeats, minimum_width, minimum_height):
        count = 0
        
        for attribute_value in pattern:
            for node in pattern[attribute_value]:
                has_image_larger_than = False
                
                nodes = [node]
                
                while len(nodes) > 0:
                    for node in nodes:
                        if node.get_tag() == 'img':
                            
                            if 'src' in node.get_attributes():
                                source = node.get_attributes()['src']
                                image_file = StringIO(urllib2.urlopen(source).read())
                                image = Image.open(image_file)
                                
                                if image.size[0] >= minimum_width and image.size[1] >= minimum_height:
                                    has_image_larger_than = True
                    
                    nodes = node.get_nodes()
                    
                if has_image_larger_than:
                    count += 1
        
        return count >= minimum_repeats
