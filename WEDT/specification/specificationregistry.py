from specification.hyperlinkedtextlengthratiospec import HyperlinkedTextLengthSpecification
from specification.imageamountspec import ImageAmountSpecification
from specification.html5tagamountspec import HTML5TagAmountSpecification
from specification.meanimagesizespec import MeanImageSizeSpecification
from specification.structuralelementrepeatsspec import StructuralElementRepeatsSpecification
from specification.linkedimagemainstructurespec import LinkedImageMainStructureSpecification
from specification.textmainstructurespec import TextLengthMainStructureSpecification
from specification.texttoimageratiospec import TextToImageRatioSpecification


class SpecificationRegistry:
    __specifications = []

    def __init__(self, configuration_provider, tree_browser):
        self.__register_specifications(configuration_provider, tree_browser)

    def __register_specifications(self, configuration_provider, tree_browser):
        self.__specifications.append(ImageAmountSpecification(configuration_provider, tree_browser))
        self.__specifications.append(StructuralElementRepeatsSpecification(configuration_provider, tree_browser))
        self.__specifications.append(HyperlinkedTextLengthSpecification(configuration_provider, tree_browser))
        self.__specifications.append(HTML5TagAmountSpecification(configuration_provider, tree_browser))
        self.__specifications.append(LinkedImageMainStructureSpecification(configuration_provider, tree_browser))
        self.__specifications.append(TextLengthMainStructureSpecification(configuration_provider, tree_browser))
        self.__specifications.append(TextToImageRatioSpecification(configuration_provider, tree_browser))
        self.__specifications.append(MeanImageSizeSpecification(configuration_provider, tree_browser))

    def get_specifications(self):
        return self.__specifications
