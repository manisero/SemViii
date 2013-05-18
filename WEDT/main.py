from classification.classifier import Classifier
from classification.configurationgenerator import ConfigurationGenerator
from config.configurationprovider import ConfigurationProvider
from config.filehandler import FileHandler
from specification.specificationregistry import SpecificationRegistry
from tree.htmltreebuilder import HTMLTreeBuilder
from web.webpagecontentdownloader import WebPageContentDownloader

configuration_file = 'config.ini'
classified_input_file = 'input_classified.txt'
unclassified_input_file = 'input_unclassified.txt'
output_file = 'output.txt'

if __name__ == "__main__":

    file_handler = FileHandler()

    url_map = file_handler.get_url_map(classified_input_file)

    configuration_provider = ConfigurationProvider(configuration_file)
    specification_registry = SpecificationRegistry(configuration_provider)
    content_downloader = WebPageContentDownloader()
    tree_builder = HTMLTreeBuilder()

    configuration_generator = ConfigurationGenerator(configuration_provider,
                                                     specification_registry,
                                                     content_downloader,
                                                     tree_builder)

    configuration_generator.generate_configuration(url_map)

    urls_to_classify = file_handler.get_urls(unclassified_input_file)

    classifier = Classifier(configuration_provider, specification_registry, content_downloader, tree_builder)

    file_handler.write_classification(output_file, classifier.classify(urls_to_classify))
