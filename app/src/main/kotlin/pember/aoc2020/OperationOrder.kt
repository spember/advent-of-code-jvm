package pember.aoc2020

import pember.utils.FileReader
import java.lang.NullPointerException
import java.util.LinkedList
import java.util.Queue

/**
 * Day 18
 * https://adventofcode.com/2020/day/18
 *
 *
 */
object OperationOrder {
    // the basic idea here is to treat each line as a queue of queues, operating on a single 'token queue' at a time
    // a queue of queues, essentially

    fun evalulateExpressions(fileName: String, v1:Boolean=true): Long =
        FileReader.readNonEmptyLines(fileName)
            .map { evalulateSingleLine(it, v1)}
            .sum()

    fun evalulateSingleLine(line: String, v1:Boolean=true): Long {
        // not private, for testing
        val queue = LinkedList(line.split(" "))
        val initialToken = LinkedList(queue.poll().toList())
        return evalulateTokens(initialToken, queue, v1).first
    }

    private fun evalulateTokens(initialToken: Queue<Char>, remainingTokens: Queue<String>, v1: Boolean): Pair<Long, Queue<Char>> {
        // a recursive operation, which will 'branch' when detecting a parenthesis in the currentToken queue
        // for each token queue, accumulate a list of String tokens, representing numbers or operators for this 'scope'
        var currentToken = initialToken // current working token
        val scopedTokens = mutableListOf<String>() // a list of all 'actions' within this paren scope
        // todo: clean this up so that the list is not string, but some other object
        var currentNumber = mutableListOf<Char>()
        // if currently working on a number, accumulate it's chars

        while(true) {
//            println("currentToken is ${currentToken}")
            while(!currentToken.isEmpty()) {
                val char = currentToken.poll()
                if (char in OPERATORS) {
                    scopedTokens.add(char.toString())
                } else if (char == '(') {
                    val response = evalulateTokens(currentToken, remainingTokens, v1)
                    scopedTokens.add(response.first.toString())
                    currentToken.addAll(response.second)
                } else if (char == ')') {
                    scopedTokens.add(String(currentNumber.toCharArray()))
                    return Pair(mergeWithDecision(scopedTokens,v1), currentToken)
                } else {
                    currentNumber.add(char)
                }
            }
            // add the currentNumber after working on a token
            scopedTokens.add(String(currentNumber.toCharArray())) // this may be empty, thus the 'filter's below
            currentNumber = mutableListOf()

            try {
                currentToken = LinkedList(remainingTokens.poll().toList())
            } catch(npe: NullPointerException) {
                break
            }
        }
        return Pair(mergeWithDecision(scopedTokens, v1), currentToken)
    }

    /**
     * A quickly added on hack for part 2 that uses a boolean to determine the merging strategy
     */
    private fun mergeWithDecision(tokens:List<String>, version:Boolean): Long {
        if (version) {
            return mergeTokensV1(tokens)
        } else {
            return mergeTokensV2(tokens)
        }
    }

    /**
     * Merge a list of tokens. Assumes the input list is odd, following a pattern of Long, operator, Long, operator...
     *
     * Addition and multiplication are done in sequence, not following PEMDAS
     */
    private fun mergeTokensV1(tokens:List<String>): Long {
        var result = 0L
        var operator = PLUS
        tokens.filter {it.isNotEmpty()}. forEach { token ->
            if (token.first() in OPERATORS) {
                operator = token.first()
            } else {
                when(operator) {
                    PLUS -> result += token.toLong()
                    MULT -> result *= token.toLong()
                }
            }
        }
        return result
    }

    /**
     * Merge a list of tokens.
     *
     * Adds numbers before multipling
     */
    fun mergeTokensV2(tokens: List<String>): Long {
        val filteredTokens = LinkedList(tokens.filter {it.isNotEmpty()})
        val multTokens = mutableListOf<Long>()

        if (filteredTokens.size == 1) {
            return filteredTokens.first().toLong()
        }

        var currentValue = filteredTokens.poll().toLong()
        var nextValue: Long
        var currentOperator: String
        while (filteredTokens.isNotEmpty()) {
            // 'take two', we know the sequence will be operator, long until the end
            currentOperator = filteredTokens.poll()
            nextValue = filteredTokens.poll().toLong()

            if (currentOperator == PLUS.toString()) {
                currentValue += nextValue
            } else {
                multTokens.add(currentValue)
                currentValue = nextValue
            }
        }
        // don't forgot to add the last one!
        multTokens.add(currentValue)
        return multTokens.fold(1L, {acc, value -> acc*value})
    }


    private val PLUS = '+'
    private val MULT = '*'
    private val OPERATORS = listOf(PLUS, MULT)
}
