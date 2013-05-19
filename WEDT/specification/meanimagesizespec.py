from StringIO import StringIO
import traceback
import urllib2
from PIL import Image
import sys


class MeanImageSizeSpecification:
    __minimum_width_variable = 'minimum_image_width'
    __maximum_width_variable = 'maximum_image_width'
    __minimum_height_variable = 'minimum_image_height'
    __maximum_height_variable = 'maximum_image_height'
    __configuration_provider = None
    __tree_browser = None

    def __init__(self, configuration_provider, tree_browser):
        self.__configuration_provider = configuration_provider
        self.__tree_browser = tree_browser

    def generate_configuration(self, html_tree, category_name):
        mean_size = self.__get_mean_image_size(html_tree)

        print "mean_size for category " + category_name + ":" + str(mean_size)

    def is_specified_by(self, html_tree, category_name):
        return 0.0

    def __get_mean_image_size(self, html_tree):
        mean_size = (0.0, 0.0)
        images = 0.0
        current_node = html_tree

        while current_node is not None:
            if current_node.get_tag() == 'img' and 'src' in current_node.get_attributes():
                try:
                    src = current_node.get_attributes()['src']

                    if src.startswith('/'):
                        src = url + src

                    image_file = StringIO(urllib2.urlopen(src).read())
                    image = Image.open(image_file)
                    mean_size = (mean_size[0] + float(image.size[0]), mean_size[1] + float(image.size[1]))
                    images += 1.0
                except Exception as ex:
                    print >> sys.stderr, 'Failed to open image: ' + url + ' because of: ' + str(ex)
                    traceback.print_exc()

            current_node = current_node.next()

        if images > 0.0:
            mean_size = (mean_size[0] / images, mean_size[1] / images)

            return mean_size

        return 0.0, 0.0