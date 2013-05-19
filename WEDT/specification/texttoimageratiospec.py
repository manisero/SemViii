class TextToImageRatioSpecification:
    __minimum_ratio_variable = 'minimum_text_to_image_ratio'
    __maximum_ratio_variable = 'maximum_text_to_image_ratio'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        current_ratio = self.__get_text_to_image_ratio(html_tree)

        min_ratio = self.__configuration_provider.get_configuration(category_name, self.__minimum_ratio_variable)

        if min_ratio is None or current_ratio < float(min_ratio):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_ratio_variable,
                                                            current_ratio)

        max_ratio = self.__configuration_provider.get_configuration(category_name, self.__maximum_ratio_variable)

        if max_ratio is None or current_ratio > float(max_ratio):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_ratio_variable,
                                                            current_ratio)

    def is_specified_by(self, html_tree, category_name):
        current_ratio = self.__get_text_to_image_ratio(html_tree)

        min_ratio = self.__configuration_provider.get_configuration(category_name, self.__minimum_ratio_variable)
        max_ratio = self.__configuration_provider.get_configuration(category_name, self.__maximum_ratio_variable)

        if min_ratio is None or max_ratio is None or current_ratio < float(min_ratio) or \
                current_ratio > float(max_ratio):
            return 0.0

        ratio_interval_length = float(max_ratio) - float(min_ratio)
        ratio_mean_value = (float(max_ratio) + float(min_ratio)) / 2.0

        if ratio_interval_length == 0.0:
            return 1.0

        ratio_mean_difference = 1.0 - abs(current_ratio - ratio_mean_value) / \
            ratio_interval_length

        return ratio_mean_difference

    def __get_text_to_image_ratio(self, html_tree):
        current_node = html_tree

        text_length = 0.0
        images = 0.0

        while current_node is not None:

            text_length += float(len(current_node.get_text()))

            if current_node.get_tag() == 'img':
                images += 1.0

            current_node = current_node.next()

        if text_length > 0.0:
            return images / text_length

        return 0.0
