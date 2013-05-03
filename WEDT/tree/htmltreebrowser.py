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

            repeated_nodes_entry = self.__get_repeated_nodes_entry(current_node, repeated_nodes, valid_attributes)

            if repeated_nodes_entry is not None:
                repeated_nodes[repeated_nodes_entry].append(current_node)
            else:
                repeated_nodes[current_node] = []
                repeated_nodes[current_node].append(current_node)

            current_node = current_node.next()

        repeated_nodes = {node: repeated_nodes[node] for node in repeated_nodes.keys()
                          if len(repeated_nodes[node]) >= minimum_repeats}

        return repeated_nodes

    def __get_repeated_nodes_entry(self, node, repeated_nodes, valid_attributes):
        for key in repeated_nodes.keys():
            if key.tag_attribute_equals(node, valid_attributes):
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
