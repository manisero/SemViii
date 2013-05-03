"""
    C{HTMLTreeBrowser} unit tests. Function covered:

        - C{get_repeated_nodes(html_tree, minimum_repeats, valid_tags, valid_attributes)}
        - C{has_mutual_parent(nodes)}
        - C{get_grouped_text_length(html_tree, group_tags, group_attributes)}

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 28-04-2013
    @version: 1.0
"""
import unittest
from tree.htmltreebrowser import HTMLTreeBrowser
from tree.htmltreenode import HTMLTreeNode


class BaseHTMLTreeBrowserTest(unittest.TestCase):
    def setUp(self):
        self.tree_browser = HTMLTreeBrowser()

        self.root = HTMLTreeNode('div', 'Parent', {'class': 'BlogParent'})

        self.first_entry = HTMLTreeNode('p', 'FirstEntry', {'class': 'BlogEntry'})
        self.second_entry = HTMLTreeNode('p', 'SecondEntry', {'class': 'BlogEntry'})
        self.third_entry = HTMLTreeNode('p', 'ThirdEntry', {'class': 'BlogEntry'})
        self.footer = HTMLTreeNode('div', 'This is Footer!', {'class': 'Footer'})

        self.root.add_nodes([self.first_entry, self.second_entry, self.third_entry, self.footer])

        self.first_date = HTMLTreeNode('span', 'FirstDate', {'class': 'BlogDate'})
        self.second_date = HTMLTreeNode('span', 'SecondDate', {'class': 'BlogDate'})
        self.first_comments = HTMLTreeNode('a', 'FirstComment', {'class': 'BlogComments'})

        self.first_entry.add_node(self.first_date)
        self.second_entry.add_node(self.first_comments)
        self.third_entry.add_node(self.second_date)


class GetRepeatedNodesHTMLTreeBrowserTest(BaseHTMLTreeBrowserTest):
    def runTest(self):
        repeated_nodes = self.tree_browser.get_repeated_nodes(self.root, 2)

        self.assertEquals(len(repeated_nodes), 2)

        self.assertIn(self.first_entry, repeated_nodes)
        self.assertIn(self.first_date, repeated_nodes)

        self.assertEquals(len(repeated_nodes[self.first_entry]), 3)
        self.assertEquals(len(repeated_nodes[self.first_date]), 2)

        self.assertIn(self.first_entry, repeated_nodes[self.first_entry])
        self.assertIn(self.second_entry, repeated_nodes[self.first_entry])
        self.assertIn(self.third_entry, repeated_nodes[self.first_entry])

        self.assertIn(self.first_date, repeated_nodes[self.first_date])
        self.assertIn(self.second_date, repeated_nodes[self.first_date])


class HasMutualParentHTMLTreeBrowserTest(BaseHTMLTreeBrowserTest):
    def test_positive(self):
        self.assertTrue(self.tree_browser.has_mutual_parent([self.first_entry, self.second_entry, self.third_entry]))

    def test_negative(self):
        self.assertFalse(self.tree_browser.has_mutual_parent([self.first_date, self.second_date]))


class GetGroupedTextLengthHTMLTreeBrowserTest(BaseHTMLTreeBrowserTest):
    def runTest(self):
        grouped_text_length = self.tree_browser.get_grouped_text_length(self.root)

        self.assertEquals(len(grouped_text_length), 5)

        self.assertIn(self.root, grouped_text_length)
        self.assertIn(self.first_entry, grouped_text_length)
        self.assertIn(self.first_date, grouped_text_length)
        self.assertIn(self.footer, grouped_text_length)
        self.assertIn(self.first_comments, grouped_text_length)

        self.assertEquals(grouped_text_length[self.root], 6)
        self.assertEquals(grouped_text_length[self.first_entry], 31)
        self.assertEquals(grouped_text_length[self.first_date], 19)
        self.assertEquals(grouped_text_length[self.footer], 15)
        self.assertEquals(grouped_text_length[self.first_comments], 12)
