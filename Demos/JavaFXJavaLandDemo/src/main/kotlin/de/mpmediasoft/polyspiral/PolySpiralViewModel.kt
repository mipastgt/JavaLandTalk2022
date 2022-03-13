package de.mpmediasoft.polyspiral

import de.mpmediasoft.polyspiral.model.PolySpiralManager
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javafx.beans.property.SimpleDoubleProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PolySpiralViewModel(val coroutineScope: CoroutineScope) {

    private val polySpiralManager = PolySpiralManager(coroutineScope)

    private val _polySpiralManagerState = ReadOnlyObjectWrapper(polySpiralManager.polySpiralManagerState.value)
    val polySpiralManagerStateProperty = _polySpiralManagerState.readOnlyProperty

    private val _startStopStringProperty = ReadOnlyStringWrapper()
    val startStopStringProperty = _startStopStringProperty.readOnlyProperty

    private val _angleIncrementDegStringProperty = ReadOnlyStringWrapper()
    val angleIncrementDegStringProperty = _angleIncrementDegStringProperty.readOnlyProperty

    val angleIncrementDegProperty = SimpleDoubleProperty(0.0)

    init {
        coroutineScope.launch(Dispatchers.Main) { // This will be Dispatchers.JavaFx here
            polySpiralManager.polySpiralManagerState.collect {
                _polySpiralManagerState.set(it)
                _startStopStringProperty.set(if (it.isRendering) "Stop" else "Start")
                angleIncrementDegProperty.set(it.angleIncrementDeg)
                _angleIncrementDegStringProperty.set("%.2fº".format(it.angleIncrementDeg))
            }
        }
        angleIncrementDegProperty.addListener { _, _, n ->
            with (polySpiralManager) { n.toDouble().also { if (angleIncrementDeg != it) angleIncrementDeg = it } }
        }
    }

    fun toggleRendering() = polySpiralManager.toggleRendering()

    fun reset() = polySpiralManager.reset()

}