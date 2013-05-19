class TextLengthMainStructureSpecification:
    __minimum_mean_variable = 'minimum_text_length_mean_amount'
    __maximum_mean_variable = 'maximum_text_length_mean_amount'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        mean_text_length = self.__get_mean_text_length_in_nodes(html_tree)

        minimum_mean = self.__configuration_provider.get_configuration(category_name, self.__minimum_mean_variable)

        if minimum_mean is None or mean_text_length < float(minimum_mean):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_mean_variable,
                                                            mean_text_length)

        maximum_mean = self.__configuration_provider.get_configuration(category_name, self.__maximum_mean_variable)

        if maximum_mean is None or mean_text_length > float(maximum_mean):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_mean_variable,
                                                            mean_text_length)

    def is_specified_by(self, html_tree, category_name):
        mean_text_length = self.__get_mean_text_length_in_nodes(html_tree)

        minimum_mean = self.__configuration_provider.get_configuration(category_name, self.__minimum_mean_variable)
        maximum_mean = self.__configuration_provider.get_configuration(category_name, self.__maximum_mean_variable)

        if minimum_mean is None or maximum_mean is None or mean_text_length < float(minimum_mean) or \
                mean_text_length > float(maximum_mean):
            return False

        return True

    def __get_mean_text_length_in_nodes(self, html_tree):
        valid_group_tags = self.__configuration_provider.get_group_valid_tags()
        valid_group_attributes = self.__configuration_provider.get_group_valid_attributes()
        nodes = self.__tree_browser.get_longest_nested_group_under_same_parent(html_tree, valid_group_tags,
                                                                               valid_group_attributes)
        grouped_nodes = self.__tree_browser.get_grouped_nodes_under_same_parent(html_tree, valid_group_tags,
                                                                                valid_group_attributes)

        maximum_mean_length = 0.0

        for group_entry in grouped_nodes:
            if len(grouped_nodes[group_entry]) != len(nodes):
                continue

            total_nodes = 0.0
            total_text_length = 0.0

            for node in grouped_nodes[group_entry]:
                total_nodes += 1.0
                total_text_length += self.__get_text_length_in_node(node)

            if total_text_length / total_nodes > maximum_mean_length:
                maximum_mean_length = total_text_length / total_nodes

        return maximum_mean_length

    def __get_text_length_in_node(self, node):
        text_length = 0.0

        for children in node.get_nodes():
            text_length += self.__get_text_length_in_node(children)

        return text_length + len(node.get_text())
