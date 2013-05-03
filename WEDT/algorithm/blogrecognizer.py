"""
    Blog recognizer.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
"""


class BlogRecognizer:
    def is_blog(self, html, tree_builder, tree_browser, configuration_provider):
        """
        @type tree_builder: HTMLTreeBuilder
        @type tree_browser: HTMLTreeBrowser
        @type configuration_provider: ConfigurationProvider
        """
        configuration = configuration_provider.get_configuration(self)

        html_tree = tree_builder.build_tree(html, configuration['parsed_tree_attributes'].split(';'))

        repeating_patterns = tree_browser.get_repeated_nodes(html_tree,
                                                             int(configuration['min_notes']),
                                                             configuration['repeated_patterns_tags'].split(';'),
                                                             configuration['repeated_patterns_attributes'].split(';'))

        for pattern in repeating_patterns:
            if tree_browser.has_mutual_parent(repeating_patterns[pattern]):
                mutual_text_lengths = None

                for node in repeating_patterns[pattern]:
                    node.set_parent(node)
                    text_lengths = \
                        tree_browser.get_grouped_text_length(node, configuration['grouped_texts_tags'].split(';'),
                                                             configuration['grouped_texts_attributes'].split(';'))

                    if mutual_text_lengths is None:
                        mutual_text_lengths = text_lengths
                    else:
                        mutual_text_lengths = \
                            self.__get_grouped_text_lengths_mean_intersection(text_lengths, mutual_text_lengths,
                                                                              configuration['grouped_texts_attributes']
                                                                              .split(';'))

                mutual_text_lengths = {key: mutual_text_lengths[key] for key in mutual_text_lengths
                                       if mutual_text_lengths[key] >= int(configuration['min_average_text_length'])}

                if len(mutual_text_lengths) > 0:
                    return True

        return False

    def __get_grouped_text_lengths_mean_intersection(self, first, second, group_attributes):
        intersection = {}

        for first_key in first:
            for second_key in second:
                if first_key.tag_attribute_equals(second_key, group_attributes):
                    intersection[first_key] = (first[first_key] + second[second_key]) / 2

        return intersection
