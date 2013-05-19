class ImageAmountSpecification:
    __minimum_amount_variable = 'min_images'
    __maximum_amount_variable = 'max_images'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        total_images = self.__count_images(html_tree)

        minimum_images = self.__configuration_provider.get_configuration(category_name, self.__minimum_amount_variable)

        if minimum_images is None or total_images < int(minimum_images):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_amount_variable, total_images)

        maximum_images = self.__configuration_provider.get_configuration(category_name, self.__maximum_amount_variable)

        if maximum_images is None or total_images > int(maximum_images):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_amount_variable, total_images)

    def is_specified_by(self, html_tree, category_name):
        total_images = self.__count_images(html_tree)

        minimum_images = self.__configuration_provider.get_configuration(category_name, self.__minimum_amount_variable)
        maximum_images = self.__configuration_provider.get_configuration(category_name, self.__maximum_amount_variable)

        if minimum_images is None or maximum_images is None or \
                total_images < int(minimum_images) or total_images > int(maximum_images):
            return 0.0

        images_interval_length = float(maximum_images) - float(minimum_images)
        images_mean_value = (float(maximum_images) + float(minimum_images)) / 2.0

        if images_interval_length == 0.0:
            return 1.0

        images_mean_difference = 1.0 - abs(total_images - images_mean_value) / images_interval_length

        return images_mean_difference

    def __count_images(self, html_tree):
        total_images = 0

        current_node = html_tree

        while current_node is not None:
            if current_node.get_tag() == 'img':
                total_images += 1

            current_node = current_node.next()

        return total_images
