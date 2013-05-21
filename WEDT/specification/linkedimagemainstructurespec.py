class LinkedImageMainStructureSpecification:
    __minimum_mean_variable = 'minimum_linked_image_mean_amount'
    __maximum_mean_variable = 'maximum_linked_image_mean_amount'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        mean_linked_images_amount = self.__get_mean_amount_of_linked_images_in_nodes(html_tree)

        minimum_mean_amount = self.__configuration_provider.get_configuration(category_name,
                                                                              self.__minimum_mean_variable)

        if minimum_mean_amount is None or mean_linked_images_amount < float(minimum_mean_amount):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_mean_variable,
                                                            mean_linked_images_amount)

        maximum_mean_amount = self.__configuration_provider.get_configuration(category_name,
                                                                              self.__maximum_mean_variable)

        if maximum_mean_amount is None or mean_linked_images_amount > float(maximum_mean_amount):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_mean_variable,
                                                            mean_linked_images_amount)

    def is_specified_by(self, html_tree, category_name):
        mean_linked_images_amount = self.__get_mean_amount_of_linked_images_in_nodes(html_tree)

        minimum_mean_amount = self.__configuration_provider.get_configuration(category_name,
                                                                              self.__minimum_mean_variable)

        maximum_mean_amount = self.__configuration_provider.get_configuration(category_name,
                                                                              self.__maximum_mean_variable)

        if minimum_mean_amount is None or maximum_mean_amount is None or \
                mean_linked_images_amount < float(minimum_mean_amount) or \
                mean_linked_images_amount > float(maximum_mean_amount):
            return 0.0

        mean_amount_interval_length = float(maximum_mean_amount) - float(minimum_mean_amount)
        mean_amount_mean_value = (float(maximum_mean_amount) + float(minimum_mean_amount)) / 2.0

        if mean_amount_interval_length == 0.0:
            return 0.0

        mean_amount_mean_difference = 1.0 - abs(mean_linked_images_amount - mean_amount_mean_value) / \
            mean_amount_interval_length

        return mean_amount_mean_difference

    def __get_mean_amount_of_linked_images_in_nodes(self, html_tree):
        valid_group_tags = self.__configuration_provider.get_group_valid_tags()
        valid_group_attributes = self.__configuration_provider.get_group_valid_attributes()
        nodes = self.__tree_browser.get_longest_nested_group_under_same_parent(html_tree, valid_group_tags,
                                                                               valid_group_attributes)

        if len(nodes) < 1:
            return 0.0

        total_nodes = 0.0
        total_images = 0.0

        for node in nodes:
            total_nodes += 1.0
            total_images += self.__get_linked_image_amount_in_node(node)

        return total_images / total_nodes

    def __get_linked_image_amount_in_node(self, node):
        images = 0.0

        for children in node.get_nodes():
            images += self.__get_linked_image_amount_in_node(children)

        if node.get_tag() == 'img' and node.get_parent().get_tag() == 'a':
            images += 1.0

        return images
