package com.example.beginning.FPToMax

import scala.io.StdIn.readLine
import scala.util.Random

object App1 extends App{

  print("Enter you name = ")
  val name = readLine()

  println(s"Hello $name, Welcome to the game!!")

  var exec = true

  while(exec){
    val num = Random.nextInt(5) + 1

    println(s"Dear $name, please guess the number 1 to 5:-")

    val guess = readLine().toInt

    if(guess == num) {
      println(s"You guessed right, $name!")
      println("Congratulation you won the game")
      exec = false
    }else{
      println(s"You guessed wrong, $name")
      println(s"Do you want to continue the game, $name?")
      print("Press 'y' for yes, Press 'n' for No = ")

      readLine() match {
        case "y" => exec = true
        case "n" => exec = false
      }
    }
  }
}

/**
 * Above program is very simple but it has some side effects like there will be some
 * run time exception if the user put some invalid inputs also it directly contacting with the console
 *
 * So, with the help of FP we will solve these side effects considering these points in mind for the functions
 * 1. Total -> For every input they return an output
 * 2. Deterministic -> For same input, they return the same output
 * 3. Pure -> Their only effect computing the return values
 */
