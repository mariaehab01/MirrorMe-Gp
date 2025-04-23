import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';

let maleModel = null, femaleModel = null;

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  75,
  window.innerWidth / window.innerHeight,
  0.1,
  1000
);
camera.position.set(0, 1.5, 3);

const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.outputEncoding = THREE.sRGBEncoding;
document.body.appendChild(renderer.domElement);

const controls = new OrbitControls(camera, renderer.domElement);
controls.enableDamping = true;

scene.add(new THREE.AmbientLight(0xffffff, 0.8));
const dirLight = new THREE.DirectionalLight(0xffffff, 1.5);
dirLight.position.set(2, 3, 2);
scene.add(dirLight);

const gltfLoader = new GLTFLoader();
const textureLoader = new THREE.TextureLoader();
const skinTexture = textureLoader.load('/assets/skins/skin_dark1.png');
skinTexture.encoding = THREE.sRGBEncoding;

function loadModel(path, isMale) {
  gltfLoader.load(
    path,
    gltf => {
      const model = gltf.scene;
      model.position.set(0, -1.0, 0);
      model.scale.set(1, 1, 1);
      model.visible = isMale;
      scene.add(model);

      model.traverse(child => {
        if (child.isMesh) {
          console.log(`${isMale ? 'Male' : 'Female'} Mesh name:`, child.name);
          child.material = new THREE.MeshStandardMaterial({
            map: skinTexture,
            metalness: 0.1,
            roughness: 0.9,
          });
          child.material.needsUpdate = true;
        }
        if (child.isBone) {
          console.log(`${isMale ? 'Male' : 'Female'} Bone name:`, child.name);
        }
      });

      if (isMale) maleModel = model;
      else femaleModel = model;
      console.log(`${isMale ? 'Male' : 'Female'} model loaded`);
      updateModelScale(); // Apply initial scale after loading
    },
    xhr => {
      console.log(`Model loading: ${(xhr.loaded / xhr.total * 100).toFixed(2)}%`);
    },
    err => {
      console.error('Model error:', err);
    }
  );
}

loadModel('/assets/male_scene.gltf', true);
loadModel('/assets/female_scene.gltf', false);

window.toggleModel = function() {
  if (maleModel && femaleModel) {
    maleModel.visible = !maleModel.visible;
    femaleModel.visible = !femaleModel.visible;
    document.getElementById('toggleButton').textContent = maleModel.visible ? 'Show Female' : 'Show Male';
    updateModelScale();
    console.log('Toggled: Male visible=', maleModel.visible);
  } else {
    console.warn('Toggle failed: Models not loaded');
  }
};

function calculateScale() {
  const weightInput = document.getElementById('weightInput');
  const heightInput = document.getElementById('heightInput');
  const shapeSelect = document.getElementById('shapeSelect');

  if (!weightInput || !heightInput || !shapeSelect) {
    console.error('Input elements not found');
    return { x: 1, y: 1, z: 1 };
  }

  const weight = parseFloat(weightInput.value) || 70; // Default 70 kg
  const height = parseFloat(heightInput.value) || 170; // Default 170 cm
  const shape = shapeSelect.value || 'Average';

  // Height scale: normalize to base height of 170 cm
  const heightScale = height / 170;

  // Width scale: approximate based on weight (sqrt for volume-like scaling)
  let widthScale = Math.sqrt(weight / 70);

  // Shape modifiers
  let shapeModifier = 1;
  if (shape === 'Slim') shapeModifier = 0.9;
  else if (shape === 'Muscular') shapeModifier = 1.2;
  else if (shape === 'Curvy') shapeModifier = 1.15;

  widthScale *= shapeModifier;

  console.log(`Calculated: weight=${weight}, height=${height}, shape=${shape}, scale=(${widthScale}, ${heightScale}, ${widthScale})`);

  return { x: widthScale, y: heightScale, z: widthScale };
}

function updateModelScale() {
  const scale = calculateScale();
  if (maleModel) {
    maleModel.scale.set(scale.x, scale.y, scale.z);
  }
  if (femaleModel) {
    femaleModel.scale.set(scale.x, scale.y, scale.z);
  }
  renderer.render(scene, camera); // Force render
  console.log('Applied scale:', scale);
}

window.addEventListener('DOMContentLoaded', () => {
  const inputs = ['weightInput', 'heightInput', 'shapeSelect'];
  inputs.forEach(id => {
    const element = document.getElementById(id);
    if (element) {
      element.addEventListener('input', () => {
        updateModelScale();
        console.log(`${id} changed`);
      });
    } else {
      console.error(`${id} not found`);
    }
  });
});

function animate() {
  requestAnimationFrame(animate);
  controls.update();
  renderer.render(scene, camera);
}
animate();

window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});