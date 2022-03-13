package de.mpmediasoft.polyspiral

import de.mpmediasoft.polyspiral.model.PolySpiralManagerState
import de.mpmediasoft.polyspiral.model.drawSpiral
import javafx.beans.value.ChangeListener
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import kotlinx.coroutines.CoroutineScope

class PolySpiralView(coroutineScope: CoroutineScope) {

    val root: Parent

    private val viewModel = PolySpiralViewModel(coroutineScope)

    private val canvasSizeChangeListener = ChangeListener<Number> { _, _, _ -> renderCanvas() }
    private val polySpiralManagerStateChangeListener = ChangeListener<PolySpiralManagerState> { _, _, _ -> renderCanvas() }
    private val startStopEventHandler = EventHandler<ActionEvent> { viewModel.toggleRendering() }
    private val resetEventHandler = EventHandler<ActionEvent> { viewModel.reset() }

    private val graphicsContext: GraphicsContext
    private val drawScope: JavaFXDrawScope

    init {
        root = BorderPane().apply {
            top = ToolBar(
                Button().apply {
                    textProperty().bind(viewModel.startStopStringProperty)
                    onAction = startStopEventHandler
                },
                TextField().apply {
                    textProperty().bind(viewModel.angleIncrementDegStringProperty)
                    alignment = Pos.BASELINE_RIGHT
                    style = "-fx-font-family: 'monospaced';"
                    prefColumnCount = 8
                },
                Slider(0.0, 360.0, 0.0).apply {
                    HBox.setHgrow(this, Priority.ALWAYS)
                    valueProperty().bindBidirectional(viewModel.angleIncrementDegProperty)
                },
                Button().apply {
                    text = "Reset"
                    onAction = resetEventHandler
                }
            )
            center = ResizableCanvasPane().apply {
                graphicsContext = this.canvas.graphicsContext2D
                drawScope = JavaFXDrawScope(graphicsContext)
                widthProperty().addListener(canvasSizeChangeListener)
                heightProperty().addListener(canvasSizeChangeListener)
            }
        }

        viewModel.polySpiralManagerStateProperty.addListener(polySpiralManagerStateChangeListener)
    }

    private fun renderCanvas() {
        with(graphicsContext) {
            fill = Color.WHITE
            fillRect(0.0, 0.0, canvas.width, canvas.height)
            with (viewModel.polySpiralManagerStateProperty.get()) {
                drawSpiral(drawScope, length, lengthIncrement, angleIncrementDeg, strokeWidth)
            }
        }
    }

}