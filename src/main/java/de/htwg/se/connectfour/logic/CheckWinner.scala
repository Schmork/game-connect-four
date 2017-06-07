package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.Cell

class CheckWinner(val gridController: GridController) {

  val NUMBER_OF_CELLS_TO_WIN = 4
  val CELLS_AROUND_TO_WIN: Int = NUMBER_OF_CELLS_TO_WIN - 1

  def checkForWinner(col: Int, row: Int): Boolean = {
    val cell = gridController.cell(col, row)
    val checkRound = CheckTurnWinner(cell)
    checkRound.checkLine(CheckType.VERTICAL) || checkRound.checkLine(CheckType.HORIZONTAL) ||
      checkRound.checkLine(CheckType.DIAGONAL_\) || checkRound.checkLine(CheckType.DIAGONAL_/)
  }

  private object CheckType extends Enumeration {
    val VERTICAL, HORIZONTAL, DIAGONAL_/, DIAGONAL_\ = Value
  }

  private case class CheckTurnWinner(cell: Cell) {

    def checkLine(checkType: CheckType.Value): Boolean = {
      var winCounter = 0
      var isCellValidAndSame = false
      for (i <- -CELLS_AROUND_TO_WIN to +CELLS_AROUND_TO_WIN) {
        isCellValidAndSame = processCheckType(i, checkType)
        winCounter = increaseOrResetCounter(winCounter, isCellValidAndSame)
        if (winCounter == NUMBER_OF_CELLS_TO_WIN) return true
      }
      false
    }

    def processCheckType(i: Int, checkType: CheckType.Value): Boolean = {
      checkType match {
        case CheckType.HORIZONTAL =>
          isValidAndSameType(cell.x + i, cell.y)
        case CheckType.VERTICAL =>
          isValidAndSameType(cell.x, cell.y + i)
        case CheckType.DIAGONAL_\ =>
          isValidAndSameType(cell.x + i, cell.y + i)
        case CheckType.DIAGONAL_/ =>
          isValidAndSameType(cell.x - i, cell.y + i)
      }
    }

    def isValidAndSameType(x: Int, y: Int): Boolean = {
      gridController.isCellValid(x, y) && isCellSameType(x, y)
    }

    private def isCellSameType(x: Int, y: Int): Boolean = {
      gridController.cell(x, y).cellType == cell.cellType
    }

    def increaseOrResetCounter(oldValue: Int, shouldIncrease: Boolean): Int = {
      if (shouldIncrease) oldValue + 1 else 0
    }
  }

}