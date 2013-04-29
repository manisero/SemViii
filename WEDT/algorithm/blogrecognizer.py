'''
Created on 29-04-2013

@author: turekj
'''
class BlogRecognizer:
    def is_blog(self, html_tree):
        pattern = self.get_repeating_pattern(html_tree, ['div'], 'class', 5)
        
        filtered_pattern = self.has_mutual_parent(pattern)
        
        if filtered_pattern.keys() > 0:
            return self.has_text_longer_than(pattern, 'div', 'class', 5, 150)
        
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
    
    def has_text_longer_than(self, pattern, group_tag, group_attribute, minimum_repeats, min_length):
        text_length_dictionary = {}
        
        for attribute_value in pattern:
            for node in pattern[attribute_value]:
                nodes = [node]
            
                while len(nodes) > 0:
                    for node in nodes:
                        if node.get_tag() == group_tag:
                            if group_attribute in node.get_attributes():
                                group_attribute_value = node.get_attributes()[group_attribute]
    
                                if group_attribute_value not in text_length_dictionary:
                                    text_length_dictionary[group_attribute_value] = 0
                                    
                                if len(node.get_text()) > min_length:
                                    text_length_dictionary[group_attribute_value] = text_length_dictionary[group_attribute_value] + 1
                
                    nodes = node.get_nodes()
                    
        for group_attribute_value in text_length_dictionary:
            if text_length_dictionary[group_attribute_value] >= minimum_repeats:
                return True
        
        return False
