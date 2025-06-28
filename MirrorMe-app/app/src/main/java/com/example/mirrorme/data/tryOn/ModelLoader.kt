package com.example.mirrorme.data.tryOn

import android.content.Context
import android.util.Log
import io.github.sceneview.SceneView
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.math.Position
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.ModelNode

fun loadModelIntoScene(
    context: Context,
    sceneView: SceneView,
    modelPath: String = "models/pants.glb",
    onModelReady: ((ModelNode) -> Unit)? = null
) {
    val modelLoader = ModelLoader(sceneView.engine, context)

    modelLoader.loadModelInstanceAsync(modelPath) { modelInstance: ModelInstance? ->
        modelInstance?.let {
            val modelNode = ModelNode(
                modelInstance = modelInstance,
                centerOrigin = Position(0f, 0f, 0f) // 👈 No offset at all
            ).apply {
                isPositionEditable = true
                isScaleEditable = true
                isRotationEditable = true
            }

            sceneView.addChildNode(modelNode)

            onModelReady?.invoke(modelNode) // 👈 callback to manipulate or store the node
            Log.d("ModelLoader", "Model loaded and added to scene!")

        }
    }
}
