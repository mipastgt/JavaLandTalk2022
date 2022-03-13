package de.mpmediasoft.polyspiral

import de.mpmediasoft.polyspiral.model.VirtualDrawScope
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class JavaFXDrawScope(val gc: GraphicsContext): VirtualDrawScope {

    override val height: Double
        get() = gc.canvas.height

    override val width: Double
        get() = gc.canvas.width

    override fun drawLine(startX: Double, startY: Double, endX: Double, endY: Double) {
        gc.strokeLine(startX, startY, endX, endY)
    }

    override fun strokeColorHsv(hue: Double, saturation: Double, value: Double) {
        gc.stroke = Color.hsb(hue, saturation, value)
    }

    override fun strokeWidth(strokeWidth: Double) {
        gc.lineWidth = strokeWidth
    }

}