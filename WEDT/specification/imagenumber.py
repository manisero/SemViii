class ImageNumberSpecification:
    __configuration_provider = None

    def __init__(self, configuration_provider):
        self.__configuration_provider = configuration_provider

    def generate_configuration(self, html_tree, category_name):
        print "[ImageNumberSpecification] Generating configuration for: " + category_name

    def is_specified_by(self, html_tree, category_name):
        print "[ImageNumberSpecification] Is specified by: " + category_name
