from classification.classifier import Classifier
from classification.configurationgenerator import ConfigurationGenerator
from config.configurationprovider import ConfigurationProvider
from config.filehandler import FileHandler
from specification.specificationregistry import SpecificationRegistry
from summary.summaryprinter import SummaryPrinter
from tree.htmltreebrowser import HTMLTreeBrowser
from tree.htmltreebuilder import HTMLTreeBuilder
from web.webpagecontentdownloader import WebPageContentDownloader

configuration_file = 'config.ini'

if __name__ == "__main__":

    file_handler = FileHandler()

    configuration_provider = ConfigurationProvider(configuration_file)
    tree_browser = HTMLTreeBrowser()

    specification_registry = SpecificationRegistry(configuration_provider, tree_browser)
    content_downloader = WebPageContentDownloader()
    tree_builder = HTMLTreeBuilder()

    configuration_generator = ConfigurationGenerator(configuration_provider,
                                                     specification_registry,
                                                     content_downloader,
                                                     tree_builder)

    url_map = file_handler.get_url_map(configuration_provider.get_classified_input_file_name())

    configuration_generator.generate_configuration(url_map)

    url_map_to_classify = file_handler.get_url_map(configuration_provider.get_unclassified_input_file_name())

    classifier = Classifier(configuration_provider, specification_registry, content_downloader, tree_builder)

    classification = classifier.classify(url_map_to_classify.keys())

    file_handler.write_classification(configuration_provider.get_output_file_name(), classification)

    SummaryPrinter().print_summary(classification, url_map_to_classify)

    while input != 'quit':
        input = raw_input("URL: ")

        if input.startswith('http://'):
            classifier.classify([input])
