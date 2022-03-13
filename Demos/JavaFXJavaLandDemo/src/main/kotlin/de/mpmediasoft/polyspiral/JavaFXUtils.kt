package de.mpmediasoft.polyspiral

import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane

class SceneBuilder {
    var root: Parent? = null
    var width: Double = 0.0
    var height: Double = 0.0
    fun build(): Scene = Scene(root, width, height)
}

class ResizableCanvasPane : Pane() {
    val canvas: Canvas

    init {
        canvas = Canvas()
        children.add(canvas)
        canvas.widthProperty().bind(widthProperty())
        canvas.heightProperty().bind(heightProperty())
    }
}
