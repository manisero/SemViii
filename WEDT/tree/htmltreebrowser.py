'''
    HTML tree browser. Implements searching algorithms in C{HTMLTreeNode} based structure.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
'''


class HTMLTreeBrowser:
    def get_grouped_nodes(self, html_tree, valid_tags=None, valid_attributes=None):
        grouped_nodes = {}
        current_node = html_tree

        while current_node is not None:
            if valid_tags is not None and current_node.get_tag() not in valid_tags:
                current_node = current_node.next()
                continue

            group_entry = self.__get_grouped_nodes_dictionary_entry(current_node, grouped_nodes, valid_attributes)

            if group_entry is None:
                group_entry = current_node
                grouped_nodes[group_entry] = []

            grouped_nodes[group_entry].append(current_node)

            current_node = current_node.next()

        return grouped_nodes

    def get_grouped_nodes_under_same_parent(self, html_tree, valid_tags=None, valid_attributes=None):
        grouped_nodes_under_same_parent = {}
        grouped_nodes = self.get_grouped_nodes(html_tree, valid_tags, valid_attributes)

        for group_entry in grouped_nodes:
            if len(grouped_nodes[group_entry]) < 1:
                continue

            mutual_parent = True

            first_parent = group_entry.get_parent()

            while valid_tags is not None and first_parent.get_tag() not in valid_tags:
                first_parent = first_parent.get_parent()

                if first_parent is first_parent.get_parent():
                    mutual_parent = False
                    break

            for node in grouped_nodes[group_entry]:
                parent = node.get_parent()

                while valid_tags is not None and parent.get_tag() not in valid_tags:
                    parent = parent.get_parent()

                    if parent is parent.get_parent():
                        mutual_parent = False
                        break

                if parent is not first_parent:
                    mutual_parent = False
                    break

            if mutual_parent:
                grouped_nodes_under_same_parent[group_entry] = grouped_nodes[group_entry]

        return grouped_nodes_under_same_parent

    def get_longest_group_under_same_parent(self, html_tree, valid_tags=None, valid_attributes=None):
        longest_group = []
        grouped_under_same_parent = self.get_grouped_nodes_under_same_parent(html_tree, valid_tags, valid_attributes)

        for group_entry in grouped_under_same_parent:
            if len(grouped_under_same_parent[group_entry]) > len(longest_group):
                longest_group = grouped_under_same_parent[group_entry]

        return longest_group

    def get_longest_nested_group_under_same_parent(self, html_tree, valid_tags=None, valid_attributes=None):
        longest_group = []
        grouped_under_same_parent = self.get_grouped_nodes_under_same_parent(html_tree, valid_tags, valid_attributes)

        for group_entry in grouped_under_same_parent:
            if len(grouped_under_same_parent[group_entry]) > len(longest_group) and \
                    self.__check_if_nested_structure(grouped_under_same_parent[group_entry]):
                longest_group = grouped_under_same_parent[group_entry]

        return longest_group

    def __check_if_nested_structure(self, nodes):
        for node in nodes:
            if node.get_nodes():
                if len(node.get_nodes()) > 1 or node.get_nodes()[0].get_nodes():
                    return True

        return False

    def get_repeated_nodes(self, html_tree, minimum_repeats, valid_tags=None, valid_attributes=None):
        repeated_nodes = {}
        current_node = html_tree

        while current_node is not None:
            if valid_tags is not None and current_node.get_tag() not in valid_tags:
                current_node = current_node.next()
                continue

            repeated_nodes_entry = self.__get_grouped_nodes_dictionary_entry(current_node, repeated_nodes,
                                                                             valid_attributes)

            if repeated_nodes_entry is not None:
                repeated_nodes[repeated_nodes_entry].append(current_node)
            else:
                repeated_nodes[current_node] = []
                repeated_nodes[current_node].append(current_node)

            current_node = current_node.next()

        repeated_nodes = {node: repeated_nodes[node] for node in repeated_nodes.keys()
                          if len(repeated_nodes[node]) >= minimum_repeats}

        return repeated_nodes

    def __get_grouped_nodes_dictionary_entry(self, key_node, grouped_nodes, group_attributes):
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
                current_node = current_node.next()
                continue

            grouped_length_entry = self.__get_grouped_nodes_dictionary_entry(current_node, grouped_text_length,
                                                                             group_attributes)

            if grouped_length_entry is None:
                grouped_length_entry = current_node
                grouped_text_length[grouped_length_entry] = 0

            grouped_text_length[grouped_length_entry] += len(current_node.get_text())

            current_node = current_node.next()

        return grouped_text_length

    def get_mutual_children(self, repeated_nodes, group_tags=None, group_attributes=None):
        grouped_mutual_children = self.__get_grouped_children(repeated_nodes[0], group_tags, group_attributes)

        for node in repeated_nodes:
            node_grouped_mutual_children = self.__get_grouped_children(node, group_tags, group_attributes)

            common_grouped_mutual_children = {}

            for mutual_children in grouped_mutual_children:
                for node_mutual_children in node_grouped_mutual_children:
                    if mutual_children.tag_attribute_equals(node_mutual_children, group_attributes):
                        common_grouped_mutual_children[mutual_children] = grouped_mutual_children[mutual_children]
                        common_grouped_mutual_children[mutual_children].append(
                            node_grouped_mutual_children[node_mutual_children])

            grouped_mutual_children = common_grouped_mutual_children

    def __get_grouped_children(self, html_tree, group_tags=None, group_attributes=None, grouped_children={}):
        """
        @type html_tree: HTMLTreeNode
        """
        if html_tree in group_tags:
            grouped_children_entry = self.__get_grouped_nodes_dictionary_entry(html_tree, grouped_children, group_attributes)

            if grouped_children_entry is None:
                grouped_children[html_tree] = [html_tree]
            else:
                grouped_children[grouped_children_entry].append(html_tree)

        for children in html_tree.get_nodes():
            self.__get_grouped_children(children, group_tags, group_attributes, grouped_children)

        return grouped_children

    def has_direct_children(self, nodes, children_tag):
        for node in nodes:
            has_direct_children = False

            for children in node.get_nodes():
                if children.get_tag() == children_tag:
                    has_direct_children = True
                    break

            if not has_direct_children:
                return False

        return True
