'''
Created on 29-04-2013

@author: turekj
'''
class BlogRecognizer:
    def is_blog(self, html_tree):
        pattern = self.get_repeating_pattern(html_tree, 'div', 'class', 5)
        
        if self.has_mutual_parent(pattern):
            return self.has_text_longer_than(pattern, 100)
        
        return False
    
    def get_repeating_pattern(self, html_tree, tag, attribute, minimum_repeats):
        current_node = html_tree
        
        attribute_sorted_nodes = {}
        
        while current_node is not None:
            if current_node.get_tag() == tag and attribute in current_node.get_attributes():
                attribute_value = current_node.get_attributes()[attribute]
                
                if attribute_value not in attribute_sorted_nodes:
                    attribute_sorted_nodes[attribute_value] = []
                    
                attribute_sorted_nodes[attribute_value].append(current_node) 
                
            current_node = current_node.next()
            
        repeated_nodes = { attribute: attribute_sorted_nodes[attribute] for attribute in attribute_sorted_nodes.keys() if len(attribute_sorted_nodes[attribute]) >= minimum_repeats }
        
        return repeated_nodes

    def has_mutual_parent(self, pattern):
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
                return True
                
        return False
    
    def has_text_longer_than(self, pattern, min_length):
        for attribute_value in pattern:
            has_longer_text = True
            
            for node in pattern[attribute_value]:
                if len(node.get_text()) < min_length:
                    has_longer_text = False
                    
            if has_longer_text:
                return True
            
        return False
