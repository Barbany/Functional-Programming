package interpreter

object Main {
  import java.io.{BufferedReader, InputStreamReader}
  val in = new BufferedReader(new InputStreamReader(System.in))

  def main(args: Array[String]): Unit = {
    print("lisp>")
    val cmd = in.readLine()
    if(cmd == "exit")
      println("Exiting Lisp interpreter")
    else{
      try {
        println(Lisp.evaluate(Lisp.string2lisp(cmd)))
      } catch {
        case e: Exception => try {
          println(Lisp.evaluate(LispCode.withDifferences(cmd)))
        } catch {
          case e: Exception => println("Command not recognized")
        }
      }
      main(args)
    }
  }
}

object LispCode {
    // TODO: implement the function `reverse` in Lisp.
  // From a list (a, b, c, d) it should compute (d, c, b, a)
  // Write it as a String, and test it in your REPL
  val reverse = """
  def (reverse L acc) (
    if (null? L)
      acc
      (reverse (cdr L) (cons (car L) acc))
    )
  """

    // TODO: implement the function `differences` in Lisp.
  // From a list (a, b, c, d ...) it should compute (a, b-a, c-b, d-c ...)
  // You might find useful to define an inner loop def
  val differences = """
  def (differences L) (
    def (loop ls elem acc) (
      if (null? ls)
        acc
        (loop (cdr ls) (car ls) (cons (- (car ls) elem) acc))
    )
    (reverse (loop L 0 nil) nil)
  )
  """

  val rebuildList = """
  def (rebuildList L) (
    def (loop ls elem acc) (
      if (null? ls)
        acc
        (loop (cdr ls) (+ (car ls) elem) (cons (+ (car ls) elem) acc))
    )
    (reverse (loop L 0 nil) nil)
  )
  """

  val withDifferences: String => String =
    (code: String) => "(" + reverse + " (" + differences + " (" + rebuildList + " " + code + ")))"
}
