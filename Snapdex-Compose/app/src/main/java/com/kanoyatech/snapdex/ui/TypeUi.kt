package com.kanoyatech.snapdex.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.theme.TypeColor

data class TypeUi(
    @StringRes val name: Int,
    val color: Color,
    @DrawableRes val image: Int
) {
    companion object {
        fun fromType(type: PokemonType): TypeUi {
            val ui =
                when(type) {
                    PokemonType.BUG -> TypeUi(
                        name = R.string.type_bug,
                        color = TypeColor.Bug,
                        image = R.drawable.type_bug
                    )
                    PokemonType.DRAGON -> TypeUi(
                        name = R.string.type_dragon,
                        color = TypeColor.Dragon,
                        image = R.drawable.type_dragon
                    )
                    PokemonType.ELECTRIC -> TypeUi(
                        name = R.string.type_electric,
                        color = TypeColor.Electric,
                        image = R.drawable.type_electric
                    )
                    PokemonType.FAIRY -> TypeUi(
                        name = R.string.type_fairy,
                        color = TypeColor.Fairy,
                        image = R.drawable.type_fairy
                    )
                    PokemonType.FIGHTING -> TypeUi(
                        name = R.string.type_fighting,
                        color = TypeColor.Fighting,
                        image = R.drawable.type_fighting
                    )
                    PokemonType.FIRE -> TypeUi(
                        name = R.string.type_fire,
                        color = TypeColor.Fire,
                        image = R.drawable.type_fire
                    )
                    PokemonType.FLYING -> TypeUi(
                        name = R.string.type_flying,
                        color = TypeColor.Flying,
                        image = R.drawable.type_flying
                    )
                    PokemonType.GHOST -> TypeUi(
                        name = R.string.type_ghost,
                        color = TypeColor.Ghost,
                        image = R.drawable.type_ghost
                    )
                    PokemonType.GRASS -> TypeUi(
                        name = R.string.type_grass,
                        color = TypeColor.Grass,
                        image = R.drawable.type_grass
                    )
                    PokemonType.GROUND -> TypeUi(
                        name = R.string.type_ground,
                        color = TypeColor.Ground,
                        image = R.drawable.type_ground
                    )
                    PokemonType.ICE -> TypeUi(
                        name = R.string.type_ice,
                        color = TypeColor.Ice,
                        image = R.drawable.type_ice
                    )
                    PokemonType.NORMAL -> TypeUi(
                        name = R.string.type_normal,
                        color = TypeColor.Normal,
                        image = R.drawable.type_normal
                    )
                    PokemonType.POISON -> TypeUi(
                        name = R.string.type_poison,
                        color = TypeColor.Poison,
                        image = R.drawable.type_poison
                    )
                    PokemonType.PSYCHIC -> TypeUi(
                        name = R.string.type_psychic,
                        color = TypeColor.Psychic,
                        image = R.drawable.type_psychic
                    )
                    PokemonType.ROCK -> TypeUi(
                        name = R.string.type_rock,
                        color = TypeColor.Rock,
                        image = R.drawable.type_rock
                    )
                    PokemonType.STEEL -> TypeUi(
                        name = R.string.type_steel,
                        color = TypeColor.Steel,
                        image = R.drawable.type_steel
                    )
                    PokemonType.WATER -> TypeUi(
                        name = R.string.type_water,
                        color = TypeColor.Water,
                        image = R.drawable.type_water
                    )
                }

            return ui
        }
    }
}