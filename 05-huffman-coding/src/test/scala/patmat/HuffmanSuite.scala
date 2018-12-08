package patmat

import org.junit._
import org.junit.Assert.assertEquals

class HuffmanSuite {
  import Huffman._

  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
    val t3 = Fork(Leaf('a',8),Fork(Fork(Leaf('b',3),Fork(Leaf('c',1),Leaf('d',2),List('c','d'),2),
      List('b','c','d'),5),Fork(Fork(Leaf('e',1),Leaf('f',1),List('e','f'),2),Fork(Leaf('g',1),Leaf('h',1),List('g','h'),2),
      List('e','f','g','h'),4),List('b','c','d','e','f','g','h'),9),List('a','b','c','d','e','f','g','h'),17)
  }


  @Test def `weight of a larger tree`: Unit =
    new TestTrees {
      assertEquals(5, weight(t1))
    }

  @Test def `chars of a larger tree`: Unit =
    new TestTrees {
      assertEquals(List('a','b','d'), chars(t2))
    }

  @Test def `string2chars hello world`: Unit =
    assertEquals(List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'), string2Chars("hello, world"))


  @Test def `make ordered leaf list for some frequency table`: Unit =
    assertEquals(List(Leaf('e',1), Leaf('t',2), Leaf('x',3)), makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))))


  @Test def `combine of some leaf list`: Unit = {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)), combine(leaflist))
  }

  @Test def `appearance of letters`: Unit = {
    assertEquals(List(('a',2), ('b',2)),times("abab".toList))
  }

  @Test def `decode and encode a very short text should be identity`: Unit =
    new TestTrees {
      assertEquals("ab".toList, decode(t1, encode(t1)("ab".toList)))
    }

  @Test def `decode trial`: Unit =
    new TestTrees {
      assertEquals("bac".toList, decode(t3, encode(t3)("bac".toList)))
    }
  
  @Test def `decode french`: Unit =
    new TestTrees {
      assertEquals("huffmanestcool".toList, decodedSecret)
    }
  
  @Test def `code tables`: Unit =
    new TestTrees {
      assertEquals(List(('a',List(0)), ('b',List(1, 0, 0)), ('c',List(1, 0, 1, 0)), ('d',List(1, 0, 1, 1)), ('e',List(1, 1, 0, 0)),
        ('f',List(1, 1, 0, 1)), ('g',List(1, 1, 1, 0)), ('h',List(1, 1, 1, 1))), convert(t3))
    }
  
  @Test def `test quick encoding`: Unit =
    new TestTrees {
      assertEquals("huffmanestcool".toList, decode(frenchCode, quickEncode(frenchCode)("huffmanestcool".toList)))
    }
  
  @Test def `code tree`: Unit =
    new TestTrees {
      createCodeTree("someText".toList)
    }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
