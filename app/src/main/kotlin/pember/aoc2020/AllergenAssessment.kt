package pember.aoc2020

import pember.utils.FileReader

/**
 *
 */
object AllergenAssessment {


    fun loadAllergenCache(input:String): Map<String, Set<String>> {
        val allergenCache = mutableMapOf<String, MutableSet<String>>()
//        val inputs:List<Pair<List<String>, List<String>>> = AocUtils.readNonEmptyLines(input)
//            .map {line ->
//                val (ingredientSection, allergenSection) = line.split(" (contains ", limit = 2)
//                ingredientSection.split(" ") to allergenSection.removeSuffix(")").split(", ")
//            }.toList()
//        inputs
//            .flatMap { (lhs, rhs) -> rhs.map { it to lhs } }
//            .groupingBy { it.first }
//            .aggregate { _, acc: MutableSet<String>?, (_, lhs), first ->
//                if (first) lhs.toMutableSet() else acc!!.apply { retainAll(lhs) }
//            }
        loadIngredientsAndAllergens(input)
            .forEach { (ingredients, allergens) ->
                allergens.forEach { allergen ->
                    if (!allergenCache.contains(allergen)) {
                        // the first time we see the allergen, initialize with its ingredients
                        allergenCache[allergen] = ingredients.toMutableSet()
                    } else {
                        // otherwise, only keep the common ones
                        allergenCache[allergen]!!.retainAll(ingredients)
                    }
                }
            }
        return allergenCache
    }

    fun countInertIngredients(input: String): Int {
        // find the cache of allergens to possible ingredients
        val allergenCache = loadAllergenCache(input)
        // run through our allergencache, looking for the foods that have ingredients not in any of the above mappings
        // as an exclusion list
        val exclusionIngredients = allergenCache.values.fold(mutableSetOf<String>()) { set, ingredients ->
            set.addAll(ingredients)
            set
        }
        allergenCache.forEach { allergen, ingredients ->
            println("$allergen -> ${ingredients}")
        }
        println("list of allergens = ${exclusionIngredients}")

        return loadIngredientsAndAllergens(input)
            .map { (ingredients, _) -> (ingredients-exclusionIngredients).size }
            .sum()
    }

    fun determineDangerousIngredients(input: String): String {
        val allergenCache = loadAllergenCache(input)
        val mapping = mutableListOf<Pair<String, String>>()
        while(mapping.size != allergenCache.keys.size) {
            // grow the list of mappings of the allergens to the ingredients
            allergenCache.map { (allergen, possibilities) ->
                // rather than maintaining a separate exclusion list, dynamically use the growing mapping list
                allergen to (possibilities-mapping.map {it.second})
            }
                .filter {(_, p) -> p.size == 1 }
                .forEach {(a, p) ->
                    println("${p.first()} contains ${a}")
                    mapping.add(a to p.first())
                }
        }
        mapping.sortBy { it.first }
        return mapping.map {it.second}.joinToString(",")
    }

    private fun loadIngredientsAndAllergens(file: String): Sequence<Pair<List<String>, List<String>>> = FileReader.readNonEmptyLines(file)
            .map {line ->
                val (ingredientSection, allergenSection) = line.split(" (contains ", limit = 2)
                ingredientSection.split(" ") to allergenSection.removeSuffix(")").split(", ")
            }
}
