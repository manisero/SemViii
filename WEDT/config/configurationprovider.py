"""
    Provides configuration for other modules.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
"""
import ConfigParser


class ConfigurationProvider:
    __config = ConfigParser.ConfigParser()
    __main_section = 'Main'
    __category_section_prefix = 'Specification'
    __valid_tags_variable = 'valid_tags'
    __valid_attributes_variable = 'valid_attributes'

    def __init__(self, config_file):
        self.__config.read(config_file)

    def get_valid_tags(self):
        self.__config.get(self.__main_section, self.__valid_tags_variable).split(';')

    def get_valid_attributes(self):
        self.__config.get(self.__main_section, self.__valid_attributes_variable).split(';')

    def get_configuration(self, category_name, variable_name=None):
        section_name = self.__get_section_for_category(category_name)

        if variable_name is None:
            if not self.__config.has_section(section_name):
                return {}

            return self.__config.items(section_name)
        else:
            if not self.__config.has_section(section_name) or not self.__config.has_option(section_name, variable_name):
                return None

            return self.__config.get(section_name, variable_name)

    def set_configuration(self, category_name, variable_name, variable_value):
        section_name = self.__get_section_for_category(category_name)

        if not self.__config.has_section(section_name):
            self.__config.add_section(section_name)

        self.__config.set(self.__get_section_for_category(category_name), variable_name, str(variable_value))

    def get_categories(self):
        categories = self.__config.sections()

        return filter(lambda x: x.startswith(self.__category_section_prefix), categories)

    def __get_section_for_category(self, category_name):
        section_name = self.__category_section_prefix

        split_category_name = category_name.split(' ')

        for partial_category_name in split_category_name:
            section_name += partial_category_name[:1].upper() + partial_category_name[1:]

        return section_name