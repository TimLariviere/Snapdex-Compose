package com.kanoyatech.snapdex.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.Type
import com.kanoyatech.snapdex.theme.TypeColor

data class TypeUi(
    @StringRes val name: Int,
    val color: Color,
    @DrawableRes val image: Int
) {
    companion object {
        fun fromType(type: Type): TypeUi {
            val ui =
                when(type) {
                    Type.BUG -> TypeUi(
                        name = R.string.type_bug,
                        color = TypeColor.Bug,
                        image = R.drawable.type_bug
                    )
                    Type.DARK -> TypeUi(
                        name = R.string.type_dark,
                        color = TypeColor.Dark,
                        image = R.drawable.type_dark
                    )
                    Type.DRAGON -> TypeUi(
                        name = R.string.type_dragon,
                        color = TypeColor.Dragon,
                        image = R.drawable.type_dragon
                    )
                    Type.ELECTRIC -> TypeUi(
                        name = R.string.type_electric,
                        color = TypeColor.Electric,
                        image = R.drawable.type_electric
                    )
                    Type.FAIRY -> TypeUi(
                        name = R.string.type_fairy,
                        color = TypeColor.Fairy,
                        image = R.drawable.type_fairy
                    )
                    Type.FIGHTING -> TypeUi(
                        name = R.string.type_fighting,
                        color = TypeColor.Fighting,
                        image = R.drawable.type_fighting
                    )
                    Type.FIRE -> TypeUi(
                        name = R.string.type_fire,
                        color = TypeColor.Fire,
                        image = R.drawable.type_fire
                    )
                    Type.FLYING -> TypeUi(
                        name = R.string.type_flying,
                        color = TypeColor.Flying,
                        image = R.drawable.type_flying
                    )
                    Type.GHOST -> TypeUi(
                        name = R.string.type_ghost,
                        color = TypeColor.Ghost,
                        image = R.drawable.type_ghost
                    )
                    Type.GRASS -> TypeUi(
                        name = R.string.type_grass,
                        color = TypeColor.Grass,
                        image = R.drawable.type_grass
                    )
                    Type.GROUND -> TypeUi(
                        name = R.string.type_ground,
                        color = TypeColor.Ground,
                        image = R.drawable.type_ground
                    )
                    Type.ICE -> TypeUi(
                        name = R.string.type_ice,
                        color = TypeColor.Ice,
                        image = R.drawable.type_ice
                    )
                    Type.NORMAL -> TypeUi(
                        name = R.string.type_normal,
                        color = TypeColor.Normal,
                        image = R.drawable.type_normal
                    )
                    Type.POISON -> TypeUi(
                        name = R.string.type_poison,
                        color = TypeColor.Poison,
                        image = R.drawable.type_poison
                    )
                    Type.PSYCHIC -> TypeUi(
                        name = R.string.type_psychic,
                        color = TypeColor.Psychic,
                        image = R.drawable.type_psychic
                    )
                    Type.ROCK -> TypeUi(
                        name = R.string.type_rock,
                        color = TypeColor.Rock,
                        image = R.drawable.type_rock
                    )
                    Type.STEEL -> TypeUi(
                        name = R.string.type_steel,
                        color = TypeColor.Steel,
                        image = R.drawable.type_steel
                    )
                    Type.WATER -> TypeUi(
                        name = R.string.type_water,
                        color = TypeColor.Water,
                        image = R.drawable.type_water
                    )
                }

            return ui
        }
    }
}