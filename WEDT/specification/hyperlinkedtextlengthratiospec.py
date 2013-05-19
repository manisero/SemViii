class HyperlinkedTextLengthSpecification:
    __minimum_ratio_variable = 'minimum_hyperlinked_text_length_ratio'
    __maximum_ratio_variable = 'maximum_hyperlinked_text_length_ratio'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        hyperlinked_text_ratio = self.__get_hyperlinked_text_ratio(html_tree)

        minimum_ratio = self.__configuration_provider.get_configuration(category_name, self.__minimum_ratio_variable)

        if minimum_ratio is None or hyperlinked_text_ratio < float(minimum_ratio):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_ratio_variable,
                                                            hyperlinked_text_ratio)

        maximum_ratio = self.__configuration_provider.get_configuration(category_name, self.__maximum_ratio_variable)

        if maximum_ratio is None or hyperlinked_text_ratio > float(maximum_ratio):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_ratio_variable,
                                                            hyperlinked_text_ratio)

    def is_specified_by(self, html_tree, category_name):
        hyperlinked_text_ratio = self.__get_hyperlinked_text_ratio(html_tree)

        minimum_ratio = self.__configuration_provider.get_configuration(category_name, self.__minimum_ratio_variable)
        maximum_ratio = self.__configuration_provider.get_configuration(category_name, self.__maximum_ratio_variable)

        if minimum_ratio is None or maximum_ratio is None or hyperlinked_text_ratio < float(minimum_ratio) or \
                hyperlinked_text_ratio > float(maximum_ratio):
            return False

        return True

    def __get_hyperlinked_text_ratio(self, html_tree):
        hyperlinked_text_length = 0.0
        text_length = 0.0

        current_node = html_tree

        while current_node is not None:
            text_length += len(current_node.get_text())

            if current_node.get_tag() == 'a':
                hyperlinked_text_length += len(current_node.get_text())

            current_node = current_node.next()

        return hyperlinked_text_length / text_length
