class HyperLinkToImageRatioSpecification:
    __min_ratio_variable = 'minimum_hyperlink_to_image_ratio'
    __max_ratio_variable = 'maximum_hyperlink_to_image_ratio'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        ratio = self.__get_hyperlink_to_image_ratio(html_tree)

        min_ratio = self.__configuration_provider.get_configuration(category_name, self.__min_ratio_variable)

        if min_ratio is None or ratio < float(min_ratio):
            self.__configuration_provider.set_configuration(category_name, self.__min_ratio_variable, ratio)

        max_ratio = self.__configuration_provider.get_configuration(category_name, self.__max_ratio_variable)

        if max_ratio is None or ratio > float(max_ratio):
            self.__configuration_provider.set_configuration(category_name, self.__max_ratio_variable, ratio)

    def is_specified_by(self, html_tree, category_name):
        ratio = self.__get_hyperlink_to_image_ratio(html_tree)

        min_ratio = self.__configuration_provider.get_configuration(category_name, self.__min_ratio_variable)
        max_ratio = self.__configuration_provider.get_configuration(category_name, self.__max_ratio_variable)

        if min_ratio is None or max_ratio is None or ratio < float(min_ratio) or ratio > float(max_ratio):
            return 0.0

        ratio_interval_length = float(max_ratio) - float(min_ratio)

        if ratio_interval_length == 0.0:
            return 0.0

        ratio_mean_value = (float(max_ratio) + float(min_ratio)) / 2.0
        ratio_mean_difference = 1.0 - abs(ratio - ratio_mean_value) / ratio_interval_length

        return ratio_mean_difference

    def __get_hyperlink_to_image_ratio(self, html_tree):
        images = 0
        hyperlinks = 0

        current_node = html_tree

        while current_node is not None:
            if current_node.get_tag() == 'a':
                hyperlinks += 1

            if current_node.get_tag() == 'img':
                images += 1

            current_node = current_node.next()

        if images == 0:
            return 0.0

        return float(hyperlinks) / float(images)
