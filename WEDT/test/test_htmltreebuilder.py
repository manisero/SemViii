"""
    C{HTMLTreeBuilder} unit tests. Function covered:
    
        - C{build_tree(html, valid_tags, valid_attributes)}
        
    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 28-04-2013
    @version: 1.0
"""

import unittest
from tree.htmltreebuilder import HTMLTreeBuilder

class BaseHTMLTreeBuilderTest(unittest.TestCase):
    def setUp(self):
        self.tree_builder = HTMLTreeBuilder()

class BuildTreeHTMLTreeBuilderTest(BaseHTMLTreeBuilderTest):
    def setUp(self):
        BaseHTMLTreeBuilderTest.setUp(self)
        
        self.html = ("<div class=\"BlogEntry\">"
                    "<h2 id=\"EntryHeader\">"
                    "<a href=\"entry_link.html\">"
                    "entry_link"
                    "</a>"
                    "</h2>"
                    "<p class=\"EntryContent\">"
                    "Content"
                    "</p>"
                    "</div>")
    
    def runTest(self):
        html_tree = self.tree_builder.build_tree(self.html)
        
        self.assertIsNotNone(html_tree)
        
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
        
        self.assertIsNone(p_entry_content_node.next())

class BuildTreeWithExclusionHTMLTreeBuilderTest(BaseHTMLTreeBuilderTest):
    def setUp(self):
        BaseHTMLTreeBuilderTest.setUp(self)
        
        self.html = ("<div class=\"BlogEntry\" style=\"font-weight:bold\">"
                     "<p class=\"EntryContent\">"
                     "It's just <b>very important</b> blog entry with <i>linked image</i>: "
                     "<a href=\"image_link.jpg\">"
                     "located on the right "
                     "<img class=\"BlogIconImage\" style=\"width: 150px; height: 150px\" src=\"image_link.jpg\" alt=\"Link to the image!\">"
                     "</a>"
                     "</p>"
                     "<div class=\"SubEntry\" dir=\"rtl\">"
                     "SubEntry"
                     "<div class=\"SubEntryText\" lang=\"en\">"
                     "Text"
                     "</div>"
                     "</div>"
                     "</div>")
        
        self.valid_tags = ['div', 'a', 'img']
        self.valid_attributes = ['class', 'href', 'src', 'alt', 'lang']
        
    def runTest(self):
        html_tree = self.tree_builder.build_tree(self.html, self.valid_tags, self.valid_attributes)
         
        self.assertIsNotNone(html_tree)
        
        div_blog_entry_node = html_tree
        
        self.assertEquals(div_blog_entry_node.get_tag(), "div")
        self.assertEquals(div_blog_entry_node.get_text(), "It's just very important blog entry with linked image: ")
        self.assertEquals(div_blog_entry_node.get_parent(), html_tree)
        self.assertEquals(len(div_blog_entry_node.get_attributes()), 1)
        self.assertTrue("class" in div_blog_entry_node.get_attributes())
        self.assertEquals(div_blog_entry_node.get_attributes()['class'], "BlogEntry")
        self.assertEquals(len(div_blog_entry_node.get_nodes()), 2)
        
        a_image_link_node = div_blog_entry_node.next()
        
        self.assertEquals(a_image_link_node.get_tag(), "a")
        self.assertEquals(a_image_link_node.get_text(), "located on the right ")
        self.assertEquals(a_image_link_node.get_parent(), div_blog_entry_node)
        self.assertEquals(len(a_image_link_node.get_attributes()), 1)
        self.assertTrue("href" in a_image_link_node.get_attributes())
        self.assertEquals(a_image_link_node.get_attributes()['href'], "image_link.jpg")
        self.assertEquals(len(a_image_link_node.get_nodes()), 1)
        
        img_blog_icon_image_node = a_image_link_node.next()
        
        self.assertEquals(img_blog_icon_image_node.get_tag(), "img")
        self.assertEquals(img_blog_icon_image_node.get_text(), "")
        self.assertEquals(img_blog_icon_image_node.get_parent(), a_image_link_node)
        self.assertEquals(len(img_blog_icon_image_node.get_attributes()), 3)
        self.assertTrue("class" in img_blog_icon_image_node.get_attributes())
        self.assertEquals(img_blog_icon_image_node.get_attributes()['class'], "BlogIconImage")
        self.assertTrue("src" in img_blog_icon_image_node.get_attributes())
        self.assertEquals(img_blog_icon_image_node.get_attributes()['src'], "image_link.jpg")
        self.assertTrue("alt" in img_blog_icon_image_node.get_attributes())
        self.assertEquals(img_blog_icon_image_node.get_attributes()['alt'], "Link to the image!")
        self.assertEquals(len(img_blog_icon_image_node.get_nodes()), 0)                
         
        
        div_sub_entry_note = img_blog_icon_image_node.next()
        
        self.assertEquals(div_sub_entry_note.get_tag(), "div")
        self.assertEquals(div_sub_entry_note.get_text(), "SubEntry")
        self.assertEquals(div_sub_entry_note.get_parent(), div_blog_entry_node)
        self.assertEquals(len(div_sub_entry_note.get_attributes()), 1)
        self.assertTrue("class" in div_sub_entry_note.get_attributes())
        self.assertEquals(div_sub_entry_note.get_attributes()['class'], "SubEntry")
        self.assertEquals(len(div_sub_entry_note.get_nodes()), 1)     
        
        div_sub_entry_text_node = div_sub_entry_note.next()
        
        self.assertEquals(div_sub_entry_text_node.get_tag(), "div")
        self.assertEquals(div_sub_entry_text_node.get_text(), "Text")
        self.assertEquals(div_sub_entry_text_node.get_parent(), div_sub_entry_note)
        self.assertEquals(len(div_sub_entry_text_node.get_attributes()), 2)
        self.assertTrue("class" in div_sub_entry_text_node.get_attributes())
        self.assertEquals(div_sub_entry_text_node.get_attributes()['class'], "SubEntryText")
        self.assertTrue("lang" in div_sub_entry_text_node.get_attributes())
        self.assertEquals(div_sub_entry_text_node.get_attributes()['lang'], "en")
        self.assertEquals(len(div_sub_entry_text_node.get_nodes()), 0)
        
        self.assertIsNone(div_sub_entry_text_node.next())   

class HTMLTreeBuilderTestSuite(unittest.TestSuite):
    def __init__(self):
        self.addTests([BuildTreeHTMLTreeBuilderTest(), BuildTreeWithExclusionHTMLTreeBuilderTest()])
