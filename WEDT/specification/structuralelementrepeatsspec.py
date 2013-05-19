class StructuralElementRepeatsSpecification:
    __minimum_structure_repeats_variable = 'minimum_structure_repeats'
    __maximum_structure_repeats_variable = 'maximum_structure_repeats'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        total_structure_repeats = self.__get_maximum_structure_repeats(html_tree)

        minimum_structure_repeats = \
            self.__configuration_provider.get_configuration(category_name, self.__minimum_structure_repeats_variable)

        if minimum_structure_repeats is None or total_structure_repeats < int(minimum_structure_repeats):
            self.__configuration_provider.set_configuration(category_name, self.__minimum_structure_repeats_variable,
                                                            total_structure_repeats)

        maximum_structure_repeats = \
            self.__configuration_provider.get_configuration(category_name, self.__maximum_structure_repeats_variable)

        if maximum_structure_repeats is None or total_structure_repeats > int(maximum_structure_repeats):
            self.__configuration_provider.set_configuration(category_name, self.__maximum_structure_repeats_variable,
                                                            total_structure_repeats)

    def is_specified_by(self, html_tree, category_name):
        total_structure_repeats = self.__get_maximum_structure_repeats(html_tree)

        print "total structure repeats: " + str(total_structure_repeats)

        minimum_structure_repeats = \
            self.__configuration_provider.get_configuration(category_name, self.__minimum_structure_repeats_variable)

        maximum_structure_repeats = \
            self.__configuration_provider.get_configuration(category_name, self.__maximum_structure_repeats_variable)

        if minimum_structure_repeats is None or maximum_structure_repeats is None or \
                total_structure_repeats > int(maximum_structure_repeats) or \
                total_structure_repeats < int(minimum_structure_repeats):
            return 0.0

        structure_repeats_interval_length = float(maximum_structure_repeats) - float(minimum_structure_repeats)
        structure_repeats_mean_value = (float(maximum_structure_repeats) + float(minimum_structure_repeats)) / 2.0

        if structure_repeats_interval_length == 0.0:
            return 0.0

        structure_repeats_mean_difference = 1.0 - abs(total_structure_repeats - structure_repeats_mean_value) / \
            structure_repeats_interval_length

        return structure_repeats_mean_difference

    def __get_maximum_structure_repeats(self, html_tree):
        valid_group_tags = self.__configuration_provider.get_group_valid_tags()
        valid_group_attributes = self.__configuration_provider.get_group_valid_attributes()
        longest_group_of_nodes = self.__tree_browser.get_longest_nested_group_under_same_parent(html_tree,
                                                                                                valid_group_tags,
                                                                                                valid_group_attributes)

        return len(longest_group_of_nodes)
