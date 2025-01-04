package com.innoveworkshop.plinko.assets

import com.innoveworkshop.plinko.engine.Rectangle
import com.innoveworkshop.plinko.engine.Vector

class DroppingRectangle(
    position: Vector?,
    width: Float,
    height: Float,
    dropRate: Float,
    color: Int
) : Rectangle(position, width, height, color) {
    var dropRate: Float = 0f

    init {
        this.dropRate = dropRate
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        if (!isFloored) position.y += dropRate
    }
}
