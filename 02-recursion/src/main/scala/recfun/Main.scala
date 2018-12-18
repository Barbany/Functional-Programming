package recfun

object Main {
  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = 
    if(c > r) 0 else{
      if(c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
    }

  
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def findNextPar(chars: List[Char], openPar: Int): Boolean = 
      if(chars.isEmpty || openPar < 0) {
        if(openPar == 0) true else false
      } else{
        if(chars.head == '(') findNextPar(chars.tail, openPar + 1) else{
          if(chars.head == ')') findNextPar(chars.tail, openPar - 1)
          else findNextPar(chars.tail, openPar)
        }
      }

    findNextPar(chars, 0)
  }
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def recCount(money: Int, coins: List[Int]): Int = 
      if(coins.isEmpty) 0 else{
        if(money == 0) 1 else{
          if(money < 0) 0 
          else recCount(money, coins.tail) + recCount(money - coins.head, coins)
      }
      }

    if(coins.isEmpty) 0 else{
      if(money == 0) Int.MaxValue else recCount(money, coins.sorted.reverse)
    }
  }
}
