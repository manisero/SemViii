class MeanImageSizeSpecification:
    __minimum_width_variable = 'minimum_image_width'
    __maximum_width_variable = 'maximum_image_width'
    __minimum_height_variable = 'minimum_image_height'
    __maximum_height_variable = 'maximum_image_height'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        mean_size = self.__get_mean_image_size(html_tree)

        minimum_width = self.__configuration_provider.get_configuration(category_name, self.__minimum_width_variable)

        if minimum_width is None or mean_size[0] < float(minimum_width):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_width_variable, mean_size[0])

        maximum_width = self.__configuration_provider.get_configuration(category_name, self.__maximum_width_variable)

        if maximum_width is None or mean_size[0] > float(maximum_width):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_width_variable, mean_size[0])

        minimum_height = self.__configuration_provider.get_configuration(category_name, self.__minimum_height_variable)

        if minimum_height is None or mean_size[1] < float(minimum_height):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_height_variable, mean_size[1])

        maximum_height = self.__configuration_provider.get_configuration(category_name, self.__maximum_height_variable)

        if maximum_height is None or mean_size[1] > float(maximum_height):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_height_variable, mean_size[1])

        print "mean_size for category " + category_name + ":" + str(mean_size)

    def is_specified_by(self, html_tree, category_name):
        mean_size = self.__get_mean_image_size(html_tree)

        minimum_width = self.__configuration_provider.get_configuration(category_name, self.__minimum_width_variable)
        maximum_width = self.__configuration_provider.get_configuration(category_name, self.__maximum_width_variable)
        minimum_height = self.__configuration_provider.get_configuration(category_name, self.__minimum_height_variable)
        maximum_height = self.__configuration_provider.get_configuration(category_name, self.__maximum_height_variable)

        if minimum_width is None or maximum_width is None or minimum_height is None or maximum_height is None or \
                mean_size[0] < float(minimum_width) or mean_size[0] > float(maximum_width) or \
                mean_size[1] < float(minimum_height) or mean_size[1] > float(maximum_height):
            return 0.0

        width_interval_length = float(maximum_width) - float(minimum_width)
        height_interval_length = float(maximum_height) - float(minimum_height)

        if width_interval_length == 0.0 or height_interval_length == 0.0:
            return 1.0

        width_mean_value = (float(maximum_width) + float(minimum_width)) / 2.0
        height_mean_value = (float(maximum_height) + float(minimum_height)) / 2.0

        width_mean_difference = 1.0 - abs(mean_size[0] - width_mean_value) / width_interval_length
        height_mean_difference = 1.0 - abs(mean_size[1] - height_mean_value) / height_interval_length

        return (width_mean_difference + height_mean_difference) / 2.0

    def __get_mean_image_size(self, html_tree):
        mean_size = (0.0, 0.0)
        images = 0.0
        current_node = html_tree

        while current_node is not None:
            if current_node.get_tag() == 'img':
                images += 1.0
                mean_size = (mean_size[0] + current_node.get_size()[0], mean_size[1] + current_node.get_size()[1])

            current_node = current_node.next()

        if images > 0.0:
            return mean_size[0] / images, mean_size[1] / images

        return 0.0, 0.0