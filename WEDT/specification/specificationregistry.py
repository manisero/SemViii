from specification.imageamountspec import ImageAmountSpecification
from specification.structuralelementrepeatsspec import StructuralElementRepeatsSpecification


class SpecificationRegistry:
    __specifications = []

    def __init__(self, configuration_provider, tree_browser):
        self.__register_specifications(configuration_provider, tree_browser)

    def __register_specifications(self, configuration_provider, tree_browser):
        self.__specifications.append(ImageAmountSpecification(configuration_provider, tree_browser))
        self.__specifications.append(StructuralElementRepeatsSpecification(configuration_provider, tree_browser))

    def get_specifications(self):
        return self.__specifications
