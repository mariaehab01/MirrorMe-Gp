package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import android.util.Log
import android.net.Uri
import com.example.myapplication.R
import com.example.myapplication.R.layout.activity_ar


class ARActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private var avatarNode: Node? = null
    private var clothingNode: Node? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_ar)

        // Initialize the ARFragment
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        // Set up listener to place avatar when a plane is tapped
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            if (avatarNode == null) {
                placeAvatar(hitResult)
            }
        }
    }

    private fun placeAvatar(hitResult: HitResult) {
        val anchor = hitResult.createAnchor()
        val anchorNode = Node().apply { setParent(arFragment.arSceneView.scene) }
        anchorNode.localPosition = Vector3(hitResult.hitPose.tx(), hitResult.hitPose.ty(), hitResult.hitPose.tz())

        ModelRenderable.builder()
            .setSource(this, Uri.parse("female_scene.gltf")) // or "female_avatar.glb"
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { avatarRenderable ->
                avatarNode = Node().apply {
                    renderable = avatarRenderable
                    setParent(anchorNode)
                }
                addClothingItem() // Attach clothing
            }
            .exceptionally {
                Log.e("ARCore", "Avatar load error: ${it.message}")
                null
            }
    }

    private fun addClothingItem() {
        ModelRenderable.builder()
            .setSource(this, Uri.parse("compo_shirt.gltf")) // or "shirt.glb"))
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { shirtRenderable ->
                clothingNode = Node().apply {
                    renderable = shirtRenderable
                    setParent(avatarNode)
                    localPosition = Vector3(0f, 1.0f, 0f) // Adjust position to align with avatar
                }
            }
            .exceptionally {
                Log.e("ARCore", "Clothing load error: ${it.message}")
                null
            }
    }
}
