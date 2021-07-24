package com.example.inventorymanagement
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class GameActivity :AppCompatActivity(){
    private lateinit var one: Button
    private lateinit var two: Button
    private lateinit var three: Button
    private lateinit var four: Button
    private lateinit var five: Button
    private lateinit var six: Button
    private lateinit var seven: Button
    private lateinit var eight: Button
    private lateinit var nine: Button

    private var turn = true
    private var scoreArr:Array<IntArray> = arrayOf(intArrayOf(-1,-1,-1),intArrayOf(-1,-1,-1),intArrayOf(-1,-1,-1))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        //Retrieve all indexes in tic tac toe board
        one = findViewById(R.id.button11)
        two = findViewById(R.id.button12)
        three = findViewById(R.id.button13)
        four = findViewById(R.id.button21)
        five = findViewById(R.id.button22)
        six = findViewById(R.id.button23)
        seven = findViewById(R.id.button31)
        eight = findViewById(R.id.button32)
        nine = findViewById(R.id.button33)


        one.setOnClickListener{playerTurn(one,  intArrayOf(0,0))}
        two.setOnClickListener{playerTurn(two,  intArrayOf(0,1))}
        three.setOnClickListener{playerTurn(three,  intArrayOf(0,2))}
        four.setOnClickListener{playerTurn(four,  intArrayOf(1,0))}
        five.setOnClickListener{playerTurn(five,  intArrayOf(1,1))}
        six.setOnClickListener{playerTurn(six,  intArrayOf(1,2))}
        seven.setOnClickListener{playerTurn(seven,  intArrayOf(2,0))}
        eight.setOnClickListener{playerTurn(eight,  intArrayOf(2,1))}
        nine.setOnClickListener{playerTurn(nine,  intArrayOf(2,2))}
    }


    fun playerTurn(button: Button, coord: IntArray) {
        if(button.text != "") return

        if(turn) {
            button.text = "X"
            scoreArr[coord[0]][coord[1]] = 1
        }
        else{
            button.text = "O"
            scoreArr[coord[0]][coord[1]] = 0
        }
        turn = !turn

        checkScore()
    }
    private fun checkScore(){
        for(row in scoreArr){
            //disregard if row contains -1 (empty box)
            if(row.contains(-1)) continue

            if(row.sum() == 3 || row.sum() == 0){
                gameOver(row.sum())
            }
        }
        var list: List<Int>
        for(x in 0..2){
            list = emptyList()
            for(y in 0..2){
                if (scoreArr[y][x] == -1) break
                list+= scoreArr[y][x]
            }
            if((list.sum() == 0 || list.sum() == 3) && list.size == 3){
                gameOver(list.sum())
            }
        }

        list = listOf(scoreArr[0][0],scoreArr[1][1],scoreArr[2][2])
        if(list.sum() == 0 || list.sum() == 3){
            Log.i("---------------", list.joinToString())
            if (!list.contains(-1)) gameOver(list.sum())
        }

        val secondSum: List<Int> = listOf(scoreArr[0][2], scoreArr[1][1], scoreArr[2][0])
        if (secondSum.sum() == 0 || secondSum.sum() == 3){
            if (!secondSum.contains(-1)) gameOver(secondSum.sum())
        }
    }
    private fun gameOver(player: Int){
        val end = findViewById<TextView>(R.id.endGame)
        end.visibility = View.VISIBLE
        if(turn) end.text = "Player 1(X) Won!"
        else end.text = "Player 2(O) Won!"

        //restart or main menu?
        val restart = findViewById<Button>(R.id.restart)
        val menu = findViewById<Button>(R.id.menu)
        restart.visibility = View.VISIBLE
        menu.visibility = View.VISIBLE
        restart.setOnClickListener{
            one.text = ""
            two.text = ""
            three.text = ""
            four.text = ""
            five.text = ""
            six.text = ""
            seven.text = ""
            eight.text = ""
            nine.text = ""
            scoreArr = arrayOf(intArrayOf(-1,-1,-1),intArrayOf(-1,-1,-1),intArrayOf(-1,-1,-1))
            restart.visibility = View.INVISIBLE
            menu.visibility = View.INVISIBLE
            end.visibility = View.INVISIBLE
        }
        menu.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}