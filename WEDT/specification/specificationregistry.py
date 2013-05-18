from specification.imageamountspec import ImageAmountSpecification


class SpecificationRegistry:
    __specifications = []

    def __init__(self, configuration_provider):
        self.__register_specifications(configuration_provider)

    def __register_specifications(self, configuration_provider):
        self.__specifications.append(ImageAmountSpecification(configuration_provider))

    def get_specifications(self):
        return self.__specifications
