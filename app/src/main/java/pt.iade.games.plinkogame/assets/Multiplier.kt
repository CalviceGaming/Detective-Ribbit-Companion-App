package com.innoveworkshop.plinko.assets

import android.graphics.Color
import android.icu.text.ListFormatter.Width
import android.icu.text.Transliterator.Position
import com.innoveworkshop.plinko.engine.GameSurface
import com.innoveworkshop.plinko.engine.Rectangle
import com.innoveworkshop.plinko.engine.Vector

class Multiplier(
    position: Vector,
    width: Float,
    height: Float,
    colofOfMult: Int,
    multiplier: Float
):Rectangle(position, width, height, colofOfMult) {
    var multiplier = multiplier
}