from classification.configurationgenerator import ConfigurationGenerator
from config.configurationprovider import ConfigurationProvider
from specification.specificationregistry import SpecificationRegistry
from tree.htmltreebuilder import HTMLTreeBuilder
from web.webpagecontentdownloader import WebPageContentDownloader

if __name__ == "__main__":

    url_map = {'http://kwejk.pl': 'Kwejk'}

    configuration_provider = ConfigurationProvider('config.ini')
    specification_registry = SpecificationRegistry(configuration_provider)
    content_downloader = WebPageContentDownloader()
    tree_builder = HTMLTreeBuilder()

    configuration_generator = ConfigurationGenerator(configuration_provider,
                                                     specification_registry,
                                                     content_downloader,
                                                     tree_builder)

    configuration_generator.generate_configuration(url_map)
