package com.example.beginning.FPToMax

import scala.io.StdIn.readLine
import scala.util.{Random, Try}


object App2 extends App{
  /**
   * here is the copy of the first/above application in which we will remove the side effects.
   *
   * So, the first one is that we are using the ".toInt" partial function to convert the input into the Integer,
   * basically it can throw a runtime exception if user put an alphabet.
   * So, we simply put it in the error handling definition by using Option => parseInt
   *
   * another one is we are taking input as 'y' and 'n' so have to take care of this
   *
   * note: It still has some side effect like mutability, foreign value changing and interaction with outer world i.e println,
   * but now its exception free
   */

  /**
   * So, for println like statement create a datastructure that hold everything description/definition
   * currently we are making everything purely functional.
   */

  //step-1
  def parseInt(s: String): Option[Int] = Try(s.toInt).toOption

  //step-2
  case class IO[A](unsafeRun:() => A) { self =>

    def map[B](f: A => B):IO[B] = IO(() => f(self.unsafeRun()))

    def flatMap[B](f: A => IO[B]): IO[B] = IO(() => f(self.unsafeRun()).unsafeRun())

  }
  object IO {
    def point[A](a: => A):IO[A] = IO(() => a)
  }

  def putStrLn(line: String): IO[Unit] = IO(() => println(line))
  def getStrLn: IO[String] = IO(() => readLine())

  def nextInt(upper: Int): IO[Int] = IO(() => Random.nextInt(upper))

  def checkContinue(name: String): IO[Boolean] =
    for{
      _ <- putStrLn(s"Do you want to continue the game, $name?")
      input <- getStrLn.map(_.toLowerCase)
      cont <- input match {
        case "y" => IO.point(true)
        case "n" => IO.point(false)
        case _ => checkContinue(name)
      }
    }yield cont

  def gameLoop(name: String): IO[Unit] = {
    for {
      _ <- putStrLn(s"Dear $name, please guess the number 1 to 5:-")
      num <- nextInt(5).map(_ + 1)
      input <- getStrLn
      _ <- parseInt(input).fold(
        putStrLn("You did not enter a number")
      ) {
        guess =>
          if (guess == num) {
            putStrLn(s"You guessed right, $name!")
          }
          else {
            putStrLn(s"You guessed wrong, $name")
          }
      }
      cont <- checkContinue(name)
      _ <- if(cont) gameLoop(name) else IO.point(())

    }yield()
  }

  val res = for{
    _ <- putStrLn("Enter you name = ")
    name <- getStrLn
    _ <- gameLoop(name)
  }yield ()

  res.unsafeRun()

}