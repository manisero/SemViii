import sys
import traceback


class ConfigurationGenerator:
    __configuration_provider = None
    __specification_registry = None
    __content_downloader = None
    __tree_builder = None

    def __init__(self, configuration_provider, specification_registry, content_downloader, tree_builder):
        self.__configuration_provider = configuration_provider
        self.__specification_registry = specification_registry
        self.__content_downloader = content_downloader
        self.__tree_builder = tree_builder

    def generate_configuration(self, url_map):
        """
        @type url_map: dict
        """
        for url in url_map:
            try:
                content = self.__content_downloader.download(url)
                html_tree = self.__tree_builder.build_tree(content,
                                                           self.__configuration_provider.get_valid_tags(),
                                                           self.__configuration_provider.get_valid_attributes())

                print "url: " + url

                for specification in self.__specification_registry.get_specifications():
                    specification.generate_configuration(html_tree, url_map[url])

            except Exception as ex:
                print >> sys.stderr, 'Failed to open URL: ' + url + ' because of: ' + str(ex)
                traceback.print_exc()

        self.__print_generation_summary()

        print "\n\n\n"

    def __print_generation_summary(self):
        print "Configuration summary"
        print "========================================"

        for category in self.__configuration_provider.get_categories():
            print "For category " + category + ":"

            configuration_dictionary = self.__configuration_provider.get_configuration(category)

            for variable_name, variable_value in configuration_dictionary:
                print " - " + variable_name + ": " + variable_value

        print "========================================"