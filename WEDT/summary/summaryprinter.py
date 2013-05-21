class SummaryPrinter:
    def print_summary(self, result, expected_result):
        inverted_result = self.__sort_by_category(result)

        categories = len(inverted_result)

        if None in inverted_result:
            categories -= 1

        print "Summary"
        print "========================================"
        print "Analyzed " + str(categories) + " categories."
        print "========================================"

        for category in inverted_result:
            if category is None:
                continue

            summary = self.__get_category_summary(category, result, expected_result)

            print "For category " + category + ":"
            print " - accuracy: " + str((summary[0] * 100.0)) + "%"
            print " - specificity: " + str((summary[1] * 100.0)) + "%"
            print " - fallout: " + str((summary[2] * 100.0)) + "%"
            print " - precision: " + str((summary[3] * 100.0)) + "%"
            print "========================================"

    def __get_category_summary(self, category, result, expected_result):
        split_urls = self.__split_urls_by_category_adhesion(category, result)

        urls_in_category = split_urls[0]
        urls_not_in_category = split_urls[1]

        true_positives = 0.0
        true_negatives = 0.0
        false_positives = 0.0
        false_negatives = 0.0

        for url in urls_in_category:
            if expected_result[url] == category:
                true_positives += 1
            else:
                false_positives += 1

        for url in urls_not_in_category:
            if expected_result[url] == category:
                false_negatives += 1
            else:
                true_negatives += 1

        accuracy = 0

        if true_positives + true_negatives + false_positives + false_negatives > 0:
            accuracy = (true_positives + true_negatives) / \
                       (true_positives + true_negatives + false_positives + false_negatives)

        specificity = 0

        if true_positives + false_negatives > 0:
            specificity = true_positives / (true_positives + false_negatives)

        fallout = 0

        if false_positives + true_negatives > 0:
            fallout = false_positives / (false_positives + true_negatives)

        precision = 0

        if true_positives + false_positives > 0:
            precision = true_positives / (true_positives + false_positives)

        return accuracy, specificity, fallout, precision

    def __split_urls_by_category_adhesion(self, category, url_dictionary):
        urls_in_category = []
        urls_not_in_category = []

        for url in url_dictionary:
            if url_dictionary[url] == category:
                urls_in_category.append(url)
            else:
                urls_not_in_category.append(url)

        return urls_in_category, urls_not_in_category

    def __sort_by_category(self, url_category_dictionary):
        inverted_url_category_dictionary = {}

        for url in url_category_dictionary:
            if url_category_dictionary[url] not in inverted_url_category_dictionary:
                inverted_url_category_dictionary[url_category_dictionary[url]] = []

            inverted_url_category_dictionary[url_category_dictionary[url]].append(url)

        return inverted_url_category_dictionary
