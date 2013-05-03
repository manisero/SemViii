"""
    Provides configuration for other modules.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
"""
import ConfigParser


class ConfigurationProvider:
    __config_file = ''

    def __init__(self, config_file):
        self.__config_file = config_file

    def get_configuration(self, object):
        config = ConfigParser.ConfigParser()
        config.read(self.__config_file)

        config_dictionary = {key: value for key, value in config.items(object.__class__.__name__)}

        return config_dictionary
