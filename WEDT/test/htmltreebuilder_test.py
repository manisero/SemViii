'''
Created on 28-04-2013

@author: turekj
'''
import unittest
from tree.htmltreebuilder import HTMLTreeBuilder

class BaseHTMLTreeBuilderTest(unittest.TestCase):
    def setUp(self):
        self.tree_builder = HTMLTreeBuilder()

class BuildTreeHTMLTreeBuilderTest(BaseHTMLTreeBuilderTest):
    def setUp(self):
        BaseHTMLTreeBuilderTest.setUp(self)
        
        self.html = """<div class="BlogEntry">
                    <h2 id="EntryHeader">
                    <a href="entry_link.html">
                    entry_link
                    </a>
                    </h2>
                    <p class="EntryContent">
                    Content
                    </p>
                    </div>"""
    
    def runTest(self):
        html_tree = self.tree_builder.build_tree(self.html)
        
        div_blog_entry_node = html_tree
        
        self.assertEquals(div_blog_entry_node.get_tag(), "div")
        self.assertEquals(div_blog_entry_node.get_text(), "")
        self.assertEquals(div_blog_entry_node.get_parent(), div_blog_entry_node)
        self.assertEquals(len(div_blog_entry_node.get_attributes()), 1)
        self.assertTrue("class" in div_blog_entry_node.get_attributes())
        self.assertEquals(div_blog_entry_node.get_attributes()['class'], "BlogEntry")
        self.assertEquals(len(div_blog_entry_node.get_nodes()), 2)
        
        h2_entry_header_node = div_blog_entry_node.next()
        
        self.assertEquals(h2_entry_header_node.get_tag(), "h2")
        self.assertEquals(h2_entry_header_node.get_text(), "")
        self.assertEquals(h2_entry_header_node.get_parent(), div_blog_entry_node)
        self.assertEquals(len(h2_entry_header_node.get_attributes()), 1)
        self.assertTrue("id" in h2_entry_header_node.get_attributes())
        self.assertEquals(h2_entry_header_node.get_attributes()['id'], "EntryHeader")
        self.assertEquals(len(h2_entry_header_node.get_nodes()), 1)
        
        a_entry_link_node = h2_entry_header_node.next()
        
        self.assertEquals(a_entry_link_node.get_tag(), "a")
        self.assertEquals(a_entry_link_node.get_text(), "entry_link")
        self.assertEquals(a_entry_link_node.get_parent(), h2_entry_header_node)
        self.assertEquals(len(a_entry_link_node.get_attributes()), 1)
        self.assertTrue("href" in a_entry_link_node.get_attributes())
        self.assertEquals(a_entry_link_node.get_attributes()['href'], "entry_link.html")
        self.assertEquals(len(a_entry_link_node.get_nodes()), 0)
        
        p_entry_content_node = a_entry_link_node.next()
        
        self.assertEquals(p_entry_content_node.get_tag(), "p")
        self.assertEquals(p_entry_content_node.get_text(), "Content")
        self.assertEquals(p_entry_content_node.get_parent(), div_blog_entry_node)
        self.assertEquals(len(p_entry_content_node.get_attributes()), 1)
        self.assertTrue("class" in p_entry_content_node.get_attributes())
        self.assertEquals(p_entry_content_node.get_attributes()['class'], "EntryContent")
        self.assertEquals(len(p_entry_content_node.get_nodes()), 0)                     
        
        self.assertEquals(p_entry_content_node.next, None)
        