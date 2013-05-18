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

    def get_configuration(self, category_name, variable_name):
        self.__config.get(self.__category_section_prefix + category_name, variable_name)

    def set_configuration(self, category_name, variable_name, variable_value):
        self.__config.set(self.__category_section_prefix + category_name, variable_name, variable_value)

    def get_categories(self):
        categories = self.__config.sections()

        return filter(lambda x: x.startswith(self.__category_section_prefix), categories)
