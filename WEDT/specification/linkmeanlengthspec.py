class LinkMeanLengthSpecification:
    __min_length_variable = 'minimum_hyperlink_text_length'
    __max_length_variable = 'maximum_hyperlink_text_length'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        mean_length = self.__get_mean_link_length(html_tree)

        min_length = self.__configuration_provider.get_configuration(category_name, self.__min_length_variable)

        if min_length is None or mean_length < float(min_length):
            self.__configuration_provider.set_configuration(category_name, self.__min_length_variable, mean_length)

        max_length = self.__configuration_provider.get_configuration(category_name, self.__max_length_variable)

        if max_length is None or mean_length > float(max_length):
            self.__configuration_provider.set_configuration(category_name, self.__max_length_variable, mean_length)

    def is_specified_by(self, html_tree, category_name):
        mean_length = self.__get_mean_link_length(html_tree)

        min_length = self.__configuration_provider.get_configuration(category_name, self.__min_length_variable)
        max_length = self.__configuration_provider.get_configuration(category_name, self.__max_length_variable)

        if min_length is None or max_length is None or mean_length < float(min_length) or \
                mean_length > float(max_length):
            return 0.0

        interval_length = float(max_length) - float(min_length)

        if interval_length == 0.0:
            return 0.0

        mean_value = (float(max_length) + float(min_length)) / 2.0
        mean_difference = 1.0 - abs(mean_length - mean_value) / interval_length

        return mean_difference

    def __get_mean_link_length(self, html_tree):
        total_length = 0
        urls = 0

        current_node = html_tree

        while current_node is not None:
            if current_node.get_tag() == 'a' and 'href' in current_node.get_attributes():
                url = current_node.get_attributes()['href']

                if len(url) > total_length:
                    total_length = len(url)

                urls += 1

            current_node = current_node.next()

        return total_length
