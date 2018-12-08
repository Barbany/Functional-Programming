// Note: "Run this worksheet" does not recompile other files in this project,
// You should run "~compile" in sbt to automatically recompile the project.

import interpreter._
import Lisp._


val reverse = """
  (def (reverse L acc) (
    if (null? L)
      acc
      (reverse (cdr L) (cons (car L) acc))
    )
  (reverse (cons 1 (cons 2 (cons 3 nil))) nil))
  """
evaluate(reverse)