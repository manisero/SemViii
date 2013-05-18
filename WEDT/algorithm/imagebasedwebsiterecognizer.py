"""
    Image-based social media website recognizer.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
"""


class ImageBasedWebsiteRecognizer:
    def is_image_based_website(self, html, tree_builder, tree_browser, configuration_provider):
        """
        @type tree_builder: HTMLTreeBuilder
        @type tree_browser: HTMLTreeBrowser
        @type configuration_provider: ConfigurationProvider
        """

        configuration = configuration_provider.get_configuration(self)

        html_tree = tree_builder.build_tree(html, configuration['parsed_tree_attributes'].split(';'))

        repeating_patterns = tree_browser.get_repeated_nodes(html_tree,
                                                             int(configuration['min_images']),
                                                             configuration['repeated_patterns_tags'].split(';'),
                                                             configuration['repeated_patterns_attributes'].split(';'))

        for pattern in repeating_patterns:
            if tree_browser.has_mutual_parent(repeating_patterns[pattern]):
                grouped_mutual_children = tree_browser.get_mutual_children(repeating_patterns[pattern],
                                                                           configuration['grouped_children_tags'].
                                                                           split(';'),
                                                                           configuration['grouped_children_attributes'].
                                                                           split(';'))

                if grouped_mutual_children:
                    for group in grouped_mutual_children:
                        if tree_browser.has_direct_children(grouped_mutual_children[group],
                                                            configuration['direct_children_tag']):
                            return True

        return False
