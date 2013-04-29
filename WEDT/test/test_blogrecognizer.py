'''
Created on 29-04-2013

@author: turekj
'''
from algorithm.blogrecognizer import BlogRecognizer
import unittest
from tree.htmltreenode import HTMLTreeNode

class BaseBlogRecognizerTest(unittest.TestCase):
    def setUp(self):
        self.blog_recognizer = BlogRecognizer()
        
class GetRepeatingPatternMethodTest(BaseBlogRecognizerTest):
    def setUp(self):
        BaseBlogRecognizerTest.setUp(self)
        
        self.root = HTMLTreeNode("div", "", {"class": "BlogContent"})
        self.header = HTMLTreeNode("div", "Header", {"class": "BlogHeader"})
        self.firstEntry = HTMLTreeNode("div", "FirstEntryContent", {"class": "BlogEntry"})
        self.firstDate = HTMLTreeNode("div", "FirstDate", {"class": "BlogEntryDate"})
        self.secondEntry = HTMLTreeNode("div", "SecondEntryContent", {"class": "BlogEntry"})
        self.thirdEntry = HTMLTreeNode("div", "ThirdEntryContent", {"class": "BlogEntry"})
        self.secondDate = HTMLTreeNode("div", "SecondDate", {"class": "BlogEntryDate"})
        self.footer = HTMLTreeNode("div", "Footer", {"class": "footer"})
        
        self.root.add_nodes([self.header, self.firstEntry, self.firstDate, self.secondEntry, self.thirdEntry, self.secondDate, self.footer])
        
    def runTest(self):
        pattern = self.blog_recognizer.get_repeating_pattern(self.root, "div", "class", 2)
        
        self.assertEquals(len(pattern), 2)
        
        self.assertIn("BlogEntry", pattern)
        self.assertIn("BlogEntryDate", pattern)
        
        self.assertEquals(len(pattern['BlogEntry']), 3)
        self.assertEquals(len(pattern['BlogEntryDate']), 2)
        
        self.assertIn(self.firstEntry, pattern['BlogEntry'])
        self.assertIn(self.secondEntry, pattern['BlogEntry'])
        self.assertIn(self.thirdEntry, pattern['BlogEntry'])
        
        self.assertIn(self.firstDate, pattern['BlogEntryDate'])
        self.assertIn(self.secondDate, pattern['BlogEntryDate'])

class HasMutualParentMethodPositiveTest(BaseBlogRecognizerTest):
    def setUp(self):
        BaseBlogRecognizerTest.setUp(self)
        
        self.mutual_parent = HTMLTreeNode("div", "", {"class": "BlogContent"})
        self.header = HTMLTreeNode("div", "Header", {"class": "BlogClass"})
        self.entry = HTMLTreeNode("div", "Entry", {"class": "BlogClass"})
        self.footer = HTMLTreeNode("div", "Footer", {"class": "BlogClass"})
        
        self.mutual_parent.add_nodes([self.header, self.entry, self.footer])
        
        self.pattern = {"BlogClass": [self.header, self.entry, self.footer]}
        
    def runTest(self):    
        self.assertTrue(self.blog_recognizer.has_mutual_parent(self.pattern))

class HasMutualParentMethodNegativeTest(BaseBlogRecognizerTest):
    def setUp(self):
        BaseBlogRecognizerTest.setUp(self)
        
        self.mutual_parent = HTMLTreeNode("div", "", {"class": "BlogContent"})
        self.header = HTMLTreeNode("div", "Header", {"class": "BlogClass"})
        self.entry = HTMLTreeNode("div", "Entry", {"class": "BlogClass"})
        self.footer = HTMLTreeNode("div", "Footer", {"class": "BlogClass"})
        
        self.mutual_parent.add_nodes([self.entry])
        self.entry.add_nodes([self.header, self.footer])
        
        self.pattern = {"BlogClass": [self.header, self.entry, self.footer]}
        
    def runTest(self):
        self.assertFalse(self.blog_recognizer.has_mutual_parent(self.pattern))
