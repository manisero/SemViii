'''
Created on 28-04-2013

@author: turekj
'''
import unittest

if __name__ == '__main__':
    suite = unittest.TestLoader().discover("test", "test_*.py")
    unittest.TextTestRunner().run(suite)
