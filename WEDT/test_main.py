'''
    Entry point for application's unit tests.

    @author: Jakub Turek
    @contact: jkbturek(at)gmail(dot)com
    @date: 03-05-2013
    @version: 1.0
'''
import unittest

if __name__ == '__main__':
    suite = unittest.TestLoader().discover("test", "test_*.py")
    unittest.TextTestRunner().run(suite)
