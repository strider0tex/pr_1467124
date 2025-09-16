import kotlin.random.Random

fun main() {
    println("Добро пожаловать в игру 'Быки и коровы'!")
    println("Компьютер загадал 4-значное число с неповторяющимися цифрами.")
    println("Число может начинаться с нуля!")
    println("Введите вашу догадку (4 цифры без повторений):")

    val secretNumber = generateSecretNumber()
    var attempts = 0

    while (true) {
        attempts++
        print("Попытка $attempts: ")
        val guess = readlnOrNull()?.trim()

        if (guess == null || guess.length != 4) {
            println("Ошибка! Введите ровно 4 цифры.")
            continue
        }

        if (!guess.all { it.isDigit() }) {
            println("Ошибка! Вводите только цифры.")
            continue
        }

        if (guess.toSet().size != 4) {
            println("Ошибка! Цифры не должны повторяться.")
            continue
        }

        val result = checkGuess(secretNumber, guess)
        println("Результат: ${result.bulls} бык(ов), ${result.cows} корова(ы)")

        if (result.bulls == 4) {
            println("\n🎉 Поздравляем! Вы угадали число $secretNumber за $attempts попыток!")
            break
        }
    }
}

fun generateSecretNumber(): String {
    val digits = mutableListOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    digits.shuffle()
    return digits.subList(0, 4).joinToString("")
}

fun checkGuess(secret: String, guess: String): GuessResult {
    var bulls = 0
    var cows = 0

    for (i in secret.indices) {
        if (secret[i] == guess[i]) {
            bulls++
        } else if (guess[i] in secret) {
            cows++
        }
    }

    return GuessResult(bulls, cows)
}

data class GuessResult(val bulls: Int, val cows: Int)