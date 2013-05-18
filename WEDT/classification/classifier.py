import traceback
import sys


class Classifier:
    __configuration_provider = None
    __specification_registry = None
    __content_downloader = None
    __tree_builder = None

    def __init__(self, configuration_provider, specification_registry, content_downloader, tree_builder):
        self.__configuration_provider = configuration_provider
        self.__specification_registry = specification_registry
        self.__content_downloader = content_downloader
        self.__tree_builder = tree_builder

    def classify(self, urls):
        classification = {}

        for url in urls:
            classification[url] = self.__classify_single_url(url)

        return classification

    def __classify_single_url(self, url):
        try:
            content = self.__content_downloader.download(url)
            html_tree = self.__tree_builder.build_tree(content,
                                                       self.__configuration_provider.get_valid_tags(),
                                                       self.__configuration_provider.get_valid_attributes())

            classification = {}

            for specification in self.__specification_registry.get_specifications():
                for category in self.__configuration_provider.get_categories():
                    if specification.is_specified_by(html_tree, category):
                        if category not in classification:
                            classification[category] = 0

                        classification[category] += 1

            if classification:
                return sorted(classification, key=classification.get, reverse=True)[0]

        except Exception as ex:
            print >> sys.stderr, 'Failed to open URL: ' + url + ' because of: ' + str(ex)
            traceback.print_exc()

        return None
