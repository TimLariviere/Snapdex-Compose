package com.kanoyatech.snapdex.domain

typealias PokemonId = Int
typealias Level = Int

data class Pokemon(
    val id: PokemonId,
    val types: List<Type>,
    val weaknesses: List<Type>,
    val weight: Weight,
    val height: Length,
    val category: Category,
    val abilities: Abilities,
    val maleToFemaleRatio: Percentage
) {
    companion object {
        private val all = mapOf(
            Pair(1, Pokemon(
                id = 1,
                types = listOf(
                    Type.GRASS,
                    Type.POISON
                ),
                weaknesses = listOf(
                    Type.FIRE,
                    Type.ICE,
                    Type.FLYING,
                    Type.PSYCHIC
                ),
                weight = 6.9.kg,
                height = 0.7.m,
                category = Category.SEED,
                abilities = Abilities.OVERGROW,
                maleToFemaleRatio = 87.5.percent
            )),
            Pair(2, Pokemon(
                id = 2,
                types = listOf(
                    Type.GRASS,
                    Type.POISON
                ),
                weaknesses = listOf(
                    Type.FIRE,
                    Type.ICE,
                    Type.FLYING,
                    Type.PSYCHIC
                ),
                weight = 13.0.kg,
                height = 1.0.m,
                category = Category.SEED,
                abilities = Abilities.OVERGROW,
                maleToFemaleRatio = 87.5.percent
            )),
            Pair(3, Pokemon(
                id = 3,
                types = listOf(
                    Type.GRASS,
                    Type.POISON
                ),
                weaknesses = listOf(
                    Type.FIRE,
                    Type.ICE,
                    Type.FLYING,
                    Type.PSYCHIC
                ),
                weight = 100.0.kg,
                height = 2.0.m,
                category = Category.SEED,
                abilities = Abilities.OVERGROW,
                maleToFemaleRatio = 87.5.percent
            )),
            Pair(4, Pokemon(
                id = 4,
                types = listOf(
                    Type.FIRE
                ),
                weaknesses = listOf(
                    Type.WATER,
                    Type.GROUND,
                    Type.ROCK
                ),
                weight = 8.5.kg,
                height = 0.6.m,
                category = Category.LIZARD,
                abilities = Abilities.BLAZE,
                maleToFemaleRatio = 87.5.percent
            )),
            Pair(5, Pokemon(
                id = 5,
                types = listOf(
                    Type.FIRE
                ),
                weaknesses = listOf(
                    Type.WATER,
                    Type.GROUND,
                    Type.ROCK
                ),
                weight = 19.0.kg,
                height = 1.1.m,
                category = Category.FLAME,
                abilities = Abilities.BLAZE,
                maleToFemaleRatio = 87.5.percent
            )),
            Pair(6, Pokemon(
                id = 6,
                types = listOf(
                    Type.FIRE,
                    Type.FLYING
                ),
                weaknesses = listOf(
                    Type.WATER,
                    Type.GROUND,
                    Type.ROCK
                ),
                weight = 90.5.kg,
                height = 1.7.m,
                category = Category.FLAME,
                abilities = Abilities.BLAZE,
                maleToFemaleRatio = 87.5.percent
            ))
        )

        fun find(id: PokemonId): Pokemon {
            return all[id]!!
        }

        fun tryFind(id: PokemonId): Pokemon? {
            return all[id]
        }
    }
}
