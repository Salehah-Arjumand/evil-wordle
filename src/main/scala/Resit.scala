import java.io.File
import scala.::
import scala.annotation.tailrec

object Resit extends App {

  import io.Source

  def get_wordle_list(filePath: String): List[String] = {
    val source = Source.fromFile(new File(filePath))
    try {
      val wordsList = source.getLines().toList
      if (wordsList.isEmpty) List.empty[String]
      else wordsList
    } finally {
      source.close()
    }
  }


  val secrets = get_wordle_list("src/main/resources/wordle.txt")
    println(secrets)
    println(secrets(0))
    println((secrets.head).getClass)

  def removeOne[A](xs: List[A], elem: A): List[A] = {
    val index = xs.indexOf(elem)
    if (index < 0) {
      xs
    } else if (index == 0) {
      xs.tail
    } else {
      xs.slice(0, index) ++ xs.slice(index + 1, xs.length)
    }

  }

   println(removeOne(List(1,2,3,2,1), 2))
   println(removeOne(List(1,2,3,2,1), 3))
   println(removeOne(List(1,2,3,2,1), 2))
   println(removeOne(List(1,2,3,2,1), 1))
   println(removeOne(List(1,2,3,2,1), 0))

  abstract class Tip

  case object Absent extends Tip

  case object Present extends Tip

  case object Correct extends Tip

  def pool(secret: String, word: String): List[Char] = {
    @tailrec
    def innerPool(sec: String, wor: String, res: List[Char]): List[Char] = {
      if (sec.length == 0) {
        res
      }
      else if (sec.head != wor.head) {
        innerPool(sec.tail, wor.tail, sec.head :: res)
      }
      else {
        innerPool(sec.tail, wor.tail, res)
      }
    }

    innerPool(secret, word, List.empty[Char])
  }

   println(pool("chess", "caves"))

  def score(secret: String, word: String): List[Tip] = {

    def aux(secret: List[Char], word: List[Char], pool: List[Char]): List[Tip] = {
      @tailrec
      def innerAux(sec: List[Char], wor: List[Char], saved: List[Char], res: List[Tip]): List[Tip] = {
        if (sec.length == 0) {
          res
        }
        else if (sec.head == wor.head) {
          innerAux(sec.tail, wor.tail, saved, Correct :: res)
        }
        else {
          if (saved.contains(wor.head)) {
            innerAux(sec.tail, wor.tail, saved.filter(_ != sec.head), Present :: res)
          }
          else {
            innerAux(sec.tail, wor.tail, saved, Absent :: res)
          }
        }
      }

      innerAux(secret, word, pool, List.empty[Tip]).reverse
    }

    aux(secret.toList, word.toList, pool(secret, word))
  }


   println(score("chess", "caves"))
   println(score("doses", "slide"))
   println(score("chess", "swiss"))
   println(score("chess", "eexss"))

  def eval(t: Tip): Int = {
    if (t == Correct) {
      10
    }
    else if (t == Present) {
      1
    }
    else {
      0
    }
  }

  def iscore(secret: String, word: String): Int = {
    val s = score(secret, word)
    s.map(x => eval(x)).sum
  }

   println(iscore("chess", "caves"))
   println(iscore("chess", "swiss"))

  def evil(secrets: List[String], word: String): List[String] = {
    def lowest(secrets: List[String], word: String, current: Int, acc: List[String]): List[String] = {
      if (secrets.length > 0) {
        if (iscore(secrets.head, word) < current) {
          lowest(secrets.tail, word, iscore(secrets.head, word), secrets.head :: List.empty[String])
        }
        else if (iscore(secrets.head, word) > current) {
          lowest(secrets.tail, word, current, acc)
        }
        else {
          lowest(secrets.tail, word, current, secrets.head :: acc)
        }
      }
      else {
        acc
      }
    }

    lowest(secrets, word, Int.MaxValue, List.empty[String])
  }
   println(evil(secrets, "stent").length)
   println(evil(secrets, "hexes").length)
   println(evil(secrets, "horse").length)
   println(evil(secrets, "hoise").length)
   println(evil(secrets, "house").length)

  def frequencies(secrets: List[String]): Map[Char, Double] = {
    val lower_secrets = secrets.map(_.toLowerCase)
    val characters = lower_secrets.flatten
    characters.groupBy(el => el).map(e => (e._1, (1 - (e._2.length.toDouble / characters.length.toDouble).toDouble)))
  }

   println(frequencies(secrets))


  def rank(frqs: Map[Char, Double], s: String): Double = {

    def innerRank(freq: Map[Char, Double], str: String, res: Double): Double = {

      if (str.length > 0) {
        innerRank(freq, str.tail, res + freq(str.head))
      }
      else {
        res
      }
    }

    innerRank(frqs, s, 0.0)
  }

  def ranked_evil(secrets: List[String], word: String): List[String] = {

    val all_evils = evil(secrets, word)

    def innerRanked(evils: List[String], curr: Double, res: List[String]): List[String] = {
      if (evils.length > 0) {
        if (rank(frequencies(secrets), evils.head) < curr) {
          innerRanked(evils.tail, rank(frequencies(secrets), evils.head), evils.head :: List.empty[String])
        }
        else if (rank(frequencies(secrets), evils.head) == curr) {
          innerRanked(evils.tail, curr, evils.head :: res)
        }
        else {
          innerRanked(evils.tail, curr, res)
        }
      }
      else {
        res
      }
    }

    innerRanked(all_evils, Double.MaxValue, List.empty[String])
  }

   println(ranked_evil(secrets, "beats"))
   println(ranked_evil(secrets, "bento"))
   println(ranked_evil(secrets, "belts"))
   println(ranked_evil(secrets, "vitae"))

}