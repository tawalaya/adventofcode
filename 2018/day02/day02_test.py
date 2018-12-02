#! /bin/env python3

import unittest
import day02

class Testing(unittest.TestCase):
    def test_read(self):
        clean = ["abcdef","bababc","abbcde","abcccd","aabcdd","abcdee","ababab"]
        stu = list(day02.read("test.txt"))
        self.assertEqual(clean,stu)

    def test_checksum(self):
        ids = ["abcdef","bababc","abbcde","abcccd","aabcdd","abcdee","ababab"]
        stu = day02.checksum(ids)
        self.assertEqual(stu,12)

    def test_full(self):
        stu = day02.checksum(day02.read("test.txt"))
        self.assertEqual(stu,12)
    
    def test_findPair(self):
        stu = day02.findPair(day02.read("test2.txt"))
        self.assertEqual(stu,('fghij','fguij'))
    
    def test_solution(self):
        stu = day02.findPair(day02.read("test2.txt"))
        word = day02.solution(stu)
        self.assertEqual(word,'fgij')

if __name__ == "__main__":
    unittest.main()