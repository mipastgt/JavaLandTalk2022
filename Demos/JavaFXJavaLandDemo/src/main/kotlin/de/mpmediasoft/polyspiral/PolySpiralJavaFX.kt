package de.mpmediasoft.polyspiral

import javafx.application.Application
import javafx.stage.Stage
import kotlinx.coroutines.GlobalScope
import java.util.*

class PolySpiralJavaFX : Application() {

    private val view = PolySpiralView(GlobalScope)

    override fun init() {
        Locale.setDefault(Locale.US)
    }

    override fun start(primaryStage: Stage) {
        primaryStage.apply {
            title = "PolySpiral - JavaFX"
            scene = SceneBuilder().apply {
                root = view.root
                width = 640.0
                height = 700.0
            }.build()
            show()
        }
    }

}

fun main(args: Array<String>) {
    Application.launch(PolySpiralJavaFX::class.java)
}
