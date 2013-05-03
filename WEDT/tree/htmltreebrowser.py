'''
    HTML tree browser. Implements searching algorithms in C{HTMLTreeNode} based structure.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
'''


class HTMLTreeBrowser:
    def get_repeated_nodes(self, html_tree, minimum_repeats, valid_tags=None, valid_attributes=None):
        repeated_nodes = {}
        current_node = html_tree

        while current_node is not None:
            if valid_tags is not None and current_node.get_tag() not in valid_tags:
                continue

            repeated_nodes_entry = self.__get_nodes_dictionary_entry(current_node, repeated_nodes, valid_attributes)

            if repeated_nodes_entry is not None:
                repeated_nodes[repeated_nodes_entry].append(current_node)
            else:
                repeated_nodes[current_node] = []
                repeated_nodes[current_node].append(current_node)

            current_node = current_node.next()

        repeated_nodes = {node: repeated_nodes[node] for node in repeated_nodes.keys()
                          if len(repeated_nodes[node]) >= minimum_repeats}

        return repeated_nodes

    def __get_nodes_dictionary_entry(self, key_node, grouped_nodes, group_attributes):
        for key in grouped_nodes.keys():
            if key.tag_attribute_equals(key_node, group_attributes):
                return key

        return None

    def has_mutual_parent(self, nodes):
        if len(nodes) < 1:
            return False

        first_parent = nodes[0].get_parent()

        for node in nodes:
            if node.get_parent() is not first_parent:
                return False

        return True

    def get_grouped_text_length(self, html_tree, group_tags=None, group_attributes=None):
        grouped_text_length = {}

        current_node = html_tree

        while current_node is not None:
            if group_tags is not None and current_node.get_tag() not in group_tags:
                continue

            grouped_length_entry = self.__get_nodes_dictionary_entry(current_node, grouped_text_length, group_attributes)

            if grouped_length_entry is None:
                grouped_length_entry = current_node
                grouped_text_length[grouped_length_entry] = 0

            grouped_text_length[grouped_length_entry] += len(current_node.get_text())

            current_node = current_node.next()

        return grouped_text_length
