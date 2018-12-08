package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      num <- arbitrary[Int]
      heap <- oneOf(const(empty), genHeap)
    } yield insert(num, heap)
  )

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min2") = forAll { a: Int =>
    val h = insert(a, empty)
    forAll {
      b: Int =>
      val h2 = insert(b, h)
      findMin(h2) == (a min b)
    }
  }

  property("delete1") = forAll { a: Int =>
    val h = insert(a, empty)
    isEmpty(deleteMin(h))
  }

  property("sortedSeq") = forAll { (h: H) =>
    def deleteRec(h: H, seq: List[Int]): List[Int] = {
      if (isEmpty(h)) seq
      else deleteRec(deleteMin(h), findMin(h) :: seq)
    }
    def isSorted(seq: List[Int]): Boolean = seq match {
      case Nil => true
      case x :: xs =>
        if (x >= xs.head) isSorted(xs)
        else false
    }
    isSorted(deleteRec(h, Nil))
  }

  property("meld") = forAll { (h1: H) =>
    val min1 = findMin(h1)
    forAll {
      (h2: H) =>
      val min2 = findMin(h2)
      findMin(meld(h1, h2)) == (min1 min min2)
    }
  }

}
