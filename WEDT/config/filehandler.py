import sys
import traceback


class FileHandler:
    def get_url_map(self, file_name):
        try:
            url_map = {}

            with open(file_name, "r") as file:
                for line in file:
                    index = line.index(' ')

                    url_map[line[:index]] = line[index+1:].strip()

                return url_map

        except Exception as ex:
            print >> sys.stderr, 'Failed to open file: ' + file_name + ' because of: ' + str(ex)
            traceback.print_exc()

    def get_urls(self, file_name):
        try:
            urls = []

            with open(file_name, "r") as file:
                for line in file:
                    urls.append(line.strip())

                return urls

        except Exception as ex:
            print >> sys.stderr, 'Failed to open file: ' + file_name + ' because of: ' + str(ex)
            traceback.print_exc()
