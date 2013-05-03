"""
    Classifies web page.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
"""
import inspect
from algorithm.blogrecognizer import BlogRecognizer
from algorithm.imagebasedwebsiterecognizer import ImageBasedWebsiteRecognizer
from config.configurationprovider import ConfigurationProvider
from tree.htmltreebrowser import HTMLTreeBrowser
from tree.htmltreebuilder import HTMLTreeBuilder


class WebPageClassifier:
    __classifiers = {}
    __tree_builder = None
    __tree_browser = None
    __configuration_provider = None

    def __init__(self, config_file):
        self.__classifiers = {None: 0, BlogRecognizer(): 1, ImageBasedWebsiteRecognizer(): 2}
        self.__tree_builder = HTMLTreeBuilder()
        self.__tree_browser = HTMLTreeBrowser()
        self.__configuration_provider = ConfigurationProvider(config_file)

    def classify(self, html):
        for classifier in self.__classifiers:
            if classifier is None:
                continue

            for method_name, method in inspect.getmembers(classifier, predicate=inspect.ismethod):
                if self.__is_classifier_method(method_name, method):
                    if method(html, self.__tree_builder, self.__tree_browser, self.__configuration_provider):
                        return self.__classifiers[classifier]

        return self.__classifiers[None]

    def __is_classifier_method(self, method_name, method):
        arguments = inspect.getargspec(method)[0]

        return method_name.startswith('is_') and len(arguments) == 5 and arguments[0] == 'self' \
            and arguments[1] == 'html' and arguments[2] == 'tree_builder' and arguments[3] == 'tree_browser' \
            and arguments[4] == 'configuration_provider'
