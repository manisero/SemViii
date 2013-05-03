"""
    C{HTMLTreeNode} unit tests. Functions covered:
    
        - C{add_nodes(nodes)}
        - C{next()}
        - C{previous()}
        
    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
"""

import unittest
from tree.htmltreenode import HTMLTreeNode


class BaseTreeNodeTest(unittest.TestCase):
    def setUp(self):
            self.root = HTMLTreeNode("root", "rootText", [])
            
            self.firstRootChild = HTMLTreeNode("firstRootChild", "firstRootChildText", [])
            self.secondRootChild = HTMLTreeNode("secondRootChild", "secondRootChildText", [])
            
            self.root.add_nodes([self.firstRootChild, self.secondRootChild])
            
            self.firstFirstRootChildChild = HTMLTreeNode("firstFirstRootChildChild",
                                                         "firstFirstRootChildChildText",
                                                         [])
            self.secondFirstRootChildChild = HTMLTreeNode("secondFirstRootChildChild",
                                                          "secondFirstRootChildChildText",
                                                          [])
            self.thirdFirstRootChildChild = HTMLTreeNode("thirdFirstRootChildChild",
                                                         "thirdFirstRootChildChildText",
                                                         [])
            
            self.firstRootChild.add_nodes([self.firstFirstRootChildChild,
                                           self.secondFirstRootChildChild,
                                           self.thirdFirstRootChildChild])
            
            self.firstSecondRootChildChild = HTMLTreeNode("firstSecondRootChildChild",
                                                          "firstSecondRootChildChildText",
                                                          [])
            self.secondSecondRootChildChild = HTMLTreeNode("secondSecondRootChildChild",
                                                           "secondSecondRootChildChildText",
                                                           [])
            
            self.secondRootChild.add_nodes([self.firstSecondRootChildChild, self.secondSecondRootChildChild])


class AddNodesHTMLTreeNodeTest(BaseTreeNodeTest):
    def runTest(self):
        self.assertEquals(self.root.get_nodes()[0], self.firstRootChild)
        self.assertEquals(self.firstRootChild.get_parent(), self.root)
        
        self.assertEquals(self.root.get_nodes()[1], self.secondRootChild)
        self.assertEquals(self.secondRootChild.get_parent(), self.root)
        
        self.assertEquals(self.firstRootChild.get_nodes()[0], self.firstFirstRootChildChild)
        self.assertEquals(self.firstFirstRootChildChild.get_parent(), self.firstRootChild)
        
        self.assertEquals(self.firstRootChild.get_nodes()[1], self.secondFirstRootChildChild)
        self.assertEquals(self.secondFirstRootChildChild.get_parent(), self.firstRootChild)
        
        self.assertEquals(self.firstRootChild.get_nodes()[2], self.thirdFirstRootChildChild)
        self.assertEquals(self.thirdFirstRootChildChild.get_parent(), self.firstRootChild)
        
        self.assertEquals(self.secondRootChild.get_nodes()[0], self.firstSecondRootChildChild)
        self.assertEquals(self.firstSecondRootChildChild.get_parent(), self.secondRootChild)
        
        self.assertEquals(self.secondRootChild.get_nodes()[1], self.secondSecondRootChildChild)
        self.assertEquals(self.secondSecondRootChildChild.get_parent(), self.secondRootChild)


class NextHTMLTreeNodeTest(BaseTreeNodeTest):
    def runTest(self):
        current_node = self.root
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.firstRootChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.firstFirstRootChildChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.secondFirstRootChildChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.thirdFirstRootChildChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.secondRootChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.firstSecondRootChildChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, self.secondSecondRootChildChild)
        
        current_node = current_node.next()
        self.assertEquals(current_node, None)


class PreviousHTMLTreeNodeTest(BaseTreeNodeTest):
    def runTest(self):
        current_node = self.secondSecondRootChildChild
        
        current_node = current_node.previous()
        self.assertEquals(current_node, self.firstSecondRootChildChild)
        
        current_node = current_node.previous()
        self.assertEquals(current_node, self.secondRootChild)

        current_node = current_node.previous()
        self.assertEquals(current_node, self.thirdFirstRootChildChild)
        
        current_node = current_node.previous()
        self.assertEquals(current_node, self.secondFirstRootChildChild)
        
        current_node = current_node.previous()
        self.assertEquals(current_node, self.firstFirstRootChildChild)
        
        current_node = current_node.previous()
        self.assertEquals(current_node, self.firstRootChild)
        
        current_node = current_node.previous()
        self.assertEquals(current_node, self.root)
        
        current_node = current_node.previous()
        self.assertEquals(current_node, None)


class TagAttributeEqualsHTMLTreeNodeTest(unittest.TestCase):
    def setUp(self):
        self.p_blog_entry = HTMLTreeNode('p', 'BlogEntry not bold text', {'class': 'BlogEntry'})

        self.div_blog_entry = HTMLTreeNode('div', 'BlogEntry not bold text', {'class': 'BlogEntry'})

        self.div_blog_entry_bold = HTMLTreeNode('div', 'BlogEntry text', {'class': 'BlogEntry',
                                                                          'style': 'font-weight:bold'})

        self.div_blog_first_menu_entry = HTMLTreeNode('div', 'First MenuEntry', {'class': 'MenuEntry',
                                                                                 'font': '1'})

        self.div_blog_second_menu_entry = HTMLTreeNode('div', 'Second MenuEntry', {'class': 'MenuEntry',
                                                                                   'font': '1'})

    def test_full_positive_match(self):
        self.assertTrue(self.div_blog_first_menu_entry.tag_attribute_equals(self.div_blog_second_menu_entry))

    def test_partial_positive_match(self):
        self.assertFalse(self.div_blog_entry.tag_attribute_equals(self.div_blog_entry_bold))
        self.assertTrue(self.div_blog_entry.tag_attribute_equals(self.div_blog_entry_bold, ['class']))

    def test_negative_tag_match(self):
        self.assertFalse(self.p_blog_entry.tag_attribute_equals(self.div_blog_entry))

    def test_negative_match(self):
        self.assertFalse(self.div_blog_entry.tag_attribute_equals(self.div_blog_first_menu_entry))
