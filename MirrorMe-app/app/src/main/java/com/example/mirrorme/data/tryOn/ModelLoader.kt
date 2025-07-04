package com.example.mirrorme.data.tryOn

import android.content.Context
import android.util.Log
import io.github.sceneview.SceneView
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.math.Position
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.ModelNode
/**
 * Loads a 3D model into a SceneView and adds it to the scene.
 * This function is used to load a model from a given path and add it to the SceneView.
 * It also provides a callback to manipulate or store the loaded ModelNode.
 *
 */
fun loadModelIntoScene(
    context: Context,
    sceneView: SceneView,
    modelPath: String ,
    setLoading: (Boolean) -> Unit,
    onModelReady: ((ModelNode) -> Unit)? = null
) {
    val modelLoader = ModelLoader(sceneView.engine, context)
    setLoading(true)

    modelLoader.loadModelInstanceAsync(modelPath) { modelInstance: ModelInstance? ->
        modelInstance?.let { // Check if the modelInstance is not null
            val modelNode = ModelNode( // Create a ModelNode with the loaded modelInstance
                modelInstance = modelInstance,
                centerOrigin = Position(0f, 0f, 0f)
            ).apply {
                isPositionEditable = true
                isScaleEditable = true
                isRotationEditable = true
            }

            sceneView.addChildNode(modelNode)

            onModelReady?.invoke(modelNode)
            Log.d("ModelLoader", "Model loaded and added to scene!")
            setLoading(false)

        }
    }
}
