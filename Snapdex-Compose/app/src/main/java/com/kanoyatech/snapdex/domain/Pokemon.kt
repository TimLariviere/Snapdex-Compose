package com.kanoyatech.snapdex.domain

typealias PokemonId = Int
typealias Level = Int

data class Pokemon(
    val id: PokemonId,
    val type: List<Type>,
    val weakness: List<Type>,
    val weight: Weight,
    val height: Length,
    val category: Category,
    val abilities: Abilities,
    val maleToFemaleRatio: Percentage,
    val evolutionTree: EvolutionTree
) {
    companion object {
        val Charmander = Pokemon(
            id = 4,
            type = listOf(
                Type.FIRE
            ),
            weakness = listOf(
                Type.WATER,
                Type.GROUND,
                Type.ROCK
            ),
            weight = 8.5.kg,
            height = 0.6.m,
            category = Category.LIZARD,
            abilities = Abilities.BLAZE,
            maleToFemaleRatio = 87.5.percent,
            evolutionTree = EvolutionTree.Charmander
        )

        val Charmeleon = Pokemon(
            id = 5,
            type = listOf(
                Type.FIRE
            ),
            weakness = listOf(
                Type.WATER,
                Type.GROUND,
                Type.ROCK
            ),
            weight = 19.0.kg,
            height = 1.1.m,
            category = Category.FLAME,
            abilities = Abilities.BLAZE,
            maleToFemaleRatio = 87.5.percent,
            evolutionTree = EvolutionTree.Charmander
        )

        val Charizard = Pokemon(
            id = 6,
            type = listOf(
                Type.FIRE,
                Type.FLYING
            ),
            weakness = listOf(
                Type.WATER,
                Type.GROUND,
                Type.ROCK
            ),
            weight = 90.5.kg,
            height = 1.7.m,
            category = Category.FLAME,
            abilities = Abilities.BLAZE,
            maleToFemaleRatio = 87.5.percent,
            evolutionTree = EvolutionTree.Charmander
        )
    }
}
