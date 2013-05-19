class HTML5TagAmountSpecification:
    __minimum_section_tag_repeats_variable = 'minimum_section_tag_repeats'
    __maximum_section_tag_repeats_variable = 'maximum_section_tag_repeats'
    __minimum_article_tag_repeats_variable = 'minimum_article_tag_repeats'
    __maximum_article_tag_repeats_variable = 'maximum_article_tag_repeats'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        section_tag_repeats = self.__get_tag_repeat_amount(html_tree, 'section')
        article_tag_repeats = self.__get_tag_repeat_amount(html_tree, 'article')

        minimum_section_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__minimum_section_tag_repeats_variable)

        if minimum_section_tag_repeats is None or section_tag_repeats < int(minimum_section_tag_repeats):
            self.__configuration_provider.set_configuration(
                category_name, self.__minimum_section_tag_repeats_variable, section_tag_repeats)

        maximum_section_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__maximum_section_tag_repeats_variable)

        if maximum_section_tag_repeats is None or section_tag_repeats > int(maximum_section_tag_repeats):
            self.__configuration_provider.set_configuration(
                category_name, self.__maximum_section_tag_repeats_variable, section_tag_repeats)

        minimum_article_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__minimum_article_tag_repeats_variable)

        if minimum_article_tag_repeats is None or article_tag_repeats < int(minimum_article_tag_repeats):
            self.__configuration_provider.set_configuration(
                category_name, self.__minimum_article_tag_repeats_variable, article_tag_repeats)

        maximum_article_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__maximum_article_tag_repeats_variable)

        if maximum_article_tag_repeats is None or article_tag_repeats > int(maximum_article_tag_repeats):
            self.__configuration_provider.set_configuration(
                category_name, self.__maximum_article_tag_repeats_variable, article_tag_repeats)

    def is_specified_by(self, html_tree, category_name):
        section_tag_repeats = self.__get_tag_repeat_amount(html_tree, 'section')
        article_tag_repeats = self.__get_tag_repeat_amount(html_tree, 'article')

        minimum_section_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__minimum_section_tag_repeats_variable)

        maximum_section_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__maximum_section_tag_repeats_variable)

        minimum_article_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__minimum_article_tag_repeats_variable)

        maximum_article_tag_repeats = self.__configuration_provider.get_configuration(
            category_name, self.__maximum_article_tag_repeats_variable)

        if minimum_section_tag_repeats is None or maximum_section_tag_repeats is None or \
                minimum_article_tag_repeats is None or maximum_article_tag_repeats is None or \
                section_tag_repeats < int(minimum_section_tag_repeats) or \
                section_tag_repeats > int(maximum_section_tag_repeats) or \
                article_tag_repeats < int(minimum_article_tag_repeats) or \
                article_tag_repeats > int(maximum_article_tag_repeats):
            return False

        return True

    def __get_tag_repeat_amount(self, html_tree, tag):
        total_repeats = 0

        current_node = html_tree

        while current_node is not None:
            if current_node.get_tag() == tag:
                total_repeats += 1

            current_node = current_node.next()

        return total_repeats
