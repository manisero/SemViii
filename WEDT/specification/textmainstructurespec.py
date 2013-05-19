class TextLengthMainStructureSpecification:
    __min_length_variable = 'minimum_text_length_amount'
    __max_length_variable = 'maximum_text_length_amount'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        current_text_length = self.__get_max_text_length_in_nodes(html_tree)

        min_text_length = self.__configuration_provider.get_configuration(category_name, self.__min_length_variable)

        if min_text_length is None or current_text_length < float(min_text_length):
            self.__configuration_provider.set_configuration(category_name, self.__min_length_variable,
                                                            current_text_length)

        max_text_length = self.__configuration_provider.get_configuration(category_name, self.__max_length_variable)

        if max_text_length is None or current_text_length > float(max_text_length):
            self.__configuration_provider.set_configuration(category_name, self.__max_length_variable,
                                                            current_text_length)

    def is_specified_by(self, html_tree, category_name):
        current_text_length = self.__get_max_text_length_in_nodes(html_tree)

        min_text_length = self.__configuration_provider.get_configuration(category_name, self.__min_length_variable)
        max_text_length = self.__configuration_provider.get_configuration(category_name, self.__max_length_variable)

        if min_text_length is None or max_text_length is None or current_text_length < float(min_text_length) or \
                current_text_length > float(max_text_length):
            return False

        return True

    def __get_max_text_length_in_nodes(self, html_tree):
        valid_group_tags = self.__configuration_provider.get_group_valid_tags()
        valid_group_attributes = self.__configuration_provider.get_group_valid_attributes()
        nodes = self.__tree_browser.get_longest_nested_group_under_same_parent(html_tree, valid_group_tags,
                                                                               valid_group_attributes)
        grouped_nodes = self.__tree_browser.get_grouped_nodes_under_same_parent(html_tree, valid_group_tags,
                                                                                valid_group_attributes)

        max_text_length = 0.0

        for group_entry in grouped_nodes:
            if len(grouped_nodes[group_entry]) != len(nodes):
                continue

            total_text_length = 0.0

            for node in grouped_nodes[group_entry]:
                total_text_length = self.__get_max_text_length_in_node(node)

            if total_text_length > max_text_length:
                max_text_length = total_text_length

        return max_text_length

    def __get_max_text_length_in_node(self, node):
        text_length = 0.0

        for children in node.get_nodes():
            if self.__get_max_text_length_in_node(children) > text_length:
                text_length = self.__get_max_text_length_in_node(children)

        if text_length < len(node.get_text()):
            return len(node.get_text())

        return text_length
