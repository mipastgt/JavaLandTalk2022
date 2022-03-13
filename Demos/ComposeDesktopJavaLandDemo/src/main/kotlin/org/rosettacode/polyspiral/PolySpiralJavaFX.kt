package org.rosettacode.polyspiral

import javafx.application.Application
import javafx.application.Application.launch
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javax.swing.Timer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PolySpiralJavaFX : Application() {
    private lateinit var graphics: CanvasPane
    private lateinit var timer: Timer
    private var inc = 0.0

    override fun start(primaryStage: Stage) {
        graphics = CanvasPane(640.0, 640.0)
        val layout = BorderPane().apply {
            center = graphics
        }
        primaryStage.apply {
            title = "PolySpiral - JavaFX"
            scene = Scene(layout)
            show()
        }
        timer = Timer(40) {
            Platform.runLater {
                inc = (inc + 0.05) % 360.0
                with(graphics.canvas.graphicsContext2D) {
                    fill = Color.WHITE
                    fillRect(0.0, 0.0, canvas.width, canvas.height)
                    drawSpiral(this, 5, Math.toRadians(inc))
                }
            }
        }
        timer.start()
    }

    override fun stop() {
        timer.stop()
        Platform.exit()
    }

    private fun drawSpiral(g: GraphicsContext, length: Int, angleIncrement: Double) {
        var x1 = g.canvas.width / 2.0f
        var y1 = g.canvas.height / 2.0f
        var len = length
        var angle = angleIncrement
        for (i in 0 until 150) {
            g.stroke = Color.hsb(360.0 * (i / 150.0f), 1.0, 1.0)
            g.lineWidth = 1.0
            val x2 = x1 + cos(angle) * len
            val y2 = y1 - sin(angle) * len
            g.strokeLine(x1, y1, x2, y2)
            x1 = x2
            y1 = y2
            len += 3
            angle = (angle + angleIncrement) % (PI * 2.0)
        }
    }

}

fun main(args: Array<String>) {
    launch(PolySpiralJavaFX::class.java)
}

private class CanvasPane internal constructor(width: Double, height: Double) : Pane() {
    val canvas: Canvas

    init {
        setWidth(width)
        setHeight(height)
        canvas = Canvas(width, height)
        children.add(canvas)
        canvas.widthProperty().bind(widthProperty())
        canvas.heightProperty().bind(heightProperty())
    }
}