package pember.challenges.aoc.aoc2020

import pember.utils.FileReader

object PassportDesk {

    /**
     * Allow Passports without CID
     */
    fun porousCheck(fileName: String): Int =
        readPassports(fileName)
            .filter { it.isLooslyValid() }
            .count()

    fun slightlyMoreRigorousCheck(fileName: String): Int =
        readPassports(fileName)
            .filter { it.isSlightlyMoreValid() }
            .count()

    private fun readPassports(fileName: String): Sequence<Passport> {
        val lines = FileReader.readLines(fileName)
        var currentPassport = Passport()
        return sequence {
            lines.forEach {
                if (it.isEmpty()) {
                    yield(currentPassport)
                    currentPassport = Passport()
                } else {
                    currentPassport.apply(it)
                }
            }
            yield(currentPassport)
        }

    }

    class Passport() {
        var byr: Int? = null //(Birth Year)
        var iyr: Int? = null // (Issue Year)
        var eyr: Int? = null // (Expiration Year)
        var hgt: String? = null // (Height)
        var hcl: String? = null // (Hair Color)
        var ecl: String? = null // (Eye Color)
        var pid: String? = null // (Passport ID)
        var cid: String? = null // (Country ID)

        fun apply(line: String) {
            line.split(" ")
                .map { it.split(":") }
                .filter { it.size==2 }
                .map {it[0] to it[1]}
                .forEach { attribute ->
                    when(attribute.first) {
                        BIRTH_YEAR -> { byr = createIntFrom(attribute.second) }
                        ISSUE_YEAR -> { iyr = createIntFrom(attribute.second) }
                        EXPIRATION_YEAR -> { eyr = createIntFrom(attribute.second) }
                        HEIGHT -> { hgt = attribute.second }
                        HAIR_COLOR -> { hcl = attribute.second }
                        EYE_COLOR -> { ecl = attribute.second }
                        PASSPORT_ID -> { pid = attribute.second }
                        COUNTRY_ID -> { cid = attribute.second }
                        else -> {
                            println("error! unknown attr: ${attribute.first}")
                        }
                    }
                }
        }

        fun isLooslyValid(): Boolean =
            byr != null &&
                iyr != null  &&
                eyr != null  &&
                !hgt.isNullOrEmpty() &&
                !hcl.isNullOrEmpty() &&
                !ecl.isNullOrEmpty() &&
                !pid.isNullOrEmpty()
        // we don't care about cid here

        fun isSlightlyMoreValid(): Boolean =
            isBirthYearValid() &&
                isIssueYearValid() &&
                isExpirationYearValid() &&
                isHeightValid() &&
                isHairValid() &&
                isEyeValid() &&
                isPassportValid()

        // the remainder is a chaos of regex.

        /*
        byr (Birth Year) - four digits; at least 1920 and at most 2002.
iyr (Issue Year) - four digits; at least 2010 and at most 2020.
eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
hgt (Height) - a number followed by either cm or in:
If cm, the number must be at least 150 and at most 193.
If in, the number must be at least 59 and at most 76.
hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
pid (Passport ID) - a nine-digit number, including leading zeroes.
cid (Country ID) - ignored, missing or not.

         */

        private fun isBirthYearValid(): Boolean = checkYear(byr, 1920,2002)
        private fun isIssueYearValid(): Boolean = checkYear(iyr, 2010, 2020)
        private fun isExpirationYearValid(): Boolean = checkYear(eyr, 2020, 2030)

        private fun isHeightValid(): Boolean {
            if (hgt.isNullOrEmpty()) {
                return false
            }
            val result = heightRegex.matchEntire(hgt!!)
            if (result == null || result.groupValues.size != 3) {
                return false
            }

            val value = result.groupValues[1].toInt()
            if (result.groupValues[2] == "cm") {
                return value in 150..193
            } else {
                return value in 59..76
            }
        }

        private fun isHairValid(): Boolean = !hcl.isNullOrEmpty() && hairRegex.matches(hcl!!)

        private fun isEyeValid(): Boolean = !ecl.isNullOrEmpty() && eyeRegex.matches(ecl!!)

        private fun isPassportValid(): Boolean = !pid.isNullOrEmpty() && idRegex.matches(pid!!)

        private fun createIntFrom(raw: String): Int? {
            var value: Int? = null
            try {
                value = raw.toInt()
            } catch (e: Exception) {
                println("coud not cast ${raw} to int")
            }
            return value
        }



        private fun checkYear(value: Int?, lower: Int, upper:Int ) =
            value != null && value in lower..upper


        companion object {
            const val BIRTH_YEAR = "byr"
            const val ISSUE_YEAR = "iyr"
            const val EXPIRATION_YEAR = "eyr"
            const val HEIGHT = "hgt"
            const val HAIR_COLOR = "hcl"
            const val EYE_COLOR = "ecl"
            const val PASSPORT_ID = "pid"
            const val COUNTRY_ID = "cid"

            val heightRegex = "(\\d+)(cm|in)".toRegex()
            val hairRegex = "#([0-9]|[a-f]){6}".toRegex()
            val eyeRegex = "amb|blu|brn|gry|grn|hzl|oth".toRegex()
            val idRegex = "\\d{9}".toRegex()
        }
    }
}
