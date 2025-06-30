import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';

const urlParams = new URLSearchParams(window.location.search);
const initialHeight = urlParams.get('height') || '165';
const initialWeight = urlParams.get('weight') || '50';
const initialGender = urlParams.get('gender') || 'male';
const initialBodyShape = urlParams.get('bodyShape') || 'Hourglass';
const initialSkinTone = urlParams.get('skinTone') || 'Light';
const initialSize = urlParams.get('size') || 'M';
const initialColor = urlParams.get('color') || 'white';

let maleModel = null, femaleModel = null;
let maleTShirt = null, femaleTShirt = null;
let malePants = null, femalePants = null;
let tshirtModel = null, pantsModel = null;

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
camera.position.set(0, 1.5, 3);

const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.outputEncoding = THREE.sRGBEncoding;
renderer.setClearColor(0x333333);
document.body.appendChild(renderer.domElement);

const controls = new OrbitControls(camera, renderer.domElement);
controls.enableDamping = true;

scene.add(new THREE.AmbientLight(0xffffff, 0.8));
const dirLight = new THREE.DirectionalLight(0xffffff, 1.5);
dirLight.position.set(2, 3, 2);
scene.add(dirLight);

const gltfLoader = new GLTFLoader();
const textureLoader = new THREE.TextureLoader();
const skinTexturePath = `/assets/skins/${initialSkinTone}.png`;
const skinTexture = textureLoader.load(skinTexturePath);
skinTexture.encoding = THREE.sRGBEncoding;

const tshirtOffsetMale = new THREE.Vector3(0, -1.45, 0);
const tshirtRotationMale = new THREE.Euler(Math.PI / 150, Math.PI, 0);
const tshirtOffsetFemale = new THREE.Vector3(0, -1.25, 0);
const tshirtRotationFemale = new THREE.Euler(0.04, Math.PI, 0);
const tshirtBaseScale = new THREE.Vector3(1.3, 1.1, 1.5);

const pantsOffsetMale = new THREE.Vector3(0, -1.0, -0.32);
const pantsRotationMale = new THREE.Euler(-0.3, Math.PI, 0);
const pantsOffsetFemale = new THREE.Vector3(0, -1.0, -0.3);
const pantsRotationFemale = new THREE.Euler(-0.3, Math.PI, 0);

const pantsBaseScale = new THREE.Vector3(1.2, 1.0, 1.2);

const womenTshirtSizes = {
  "S": { chestCm: 84, lengthCm: 60, waistCm: 70 },
  "M": { chestCm: 89, lengthCm: 62, waistCm: 75 },
  "L": { chestCm: 94, lengthCm: 64, waistCm: 80 },
  "XL": { chestCm: 99, lengthCm: 66, waistCm: 85 },
  "XXL": { chestCm: 104, lengthCm: 68, waistCm: 90 }
};

const menTshirtSizes = {
  "S": { chestCm: 92, lengthCm: 68, waistCm: 78 },
  "M": { chestCm: 97, lengthCm: 70, waistCm: 83 },
  "L": { chestCm: 102, lengthCm: 72, waistCm: 88 },
  "XL": { chestCm: 107, lengthCm: 74, waistCm: 93 },
  "XXL": { chestCm: 112, lengthCm: 76, waistCm: 98 }
};

function loadModel(path, isMale) {
  gltfLoader.load(path, gltf => {
    const model = gltf.scene;
    model.position.set(0, -1.0, 0);
    model.scale.set(1, 1, 1);
    model.visible = isMale === (initialGender === 'male');
    scene.add(model);

    model.traverse(child => {
      if (child.isMesh) {
        child.material = new THREE.MeshStandardMaterial({
          map: skinTexture,
          metalness: 0.1,
          roughness: 0.9,
        });
      }
    });

    if (isMale) maleModel = model;
    else femaleModel = model;

    if (tshirtModel) {
      attachTShirtToAvatar();
      applyTShirtColor();
    }
    if (pantsModel) {
      attachPantsToAvatar();
    }

    updateAll();
  });
}

loadModel('/assets/male_scene.gltf', true);
loadModel('/assets/female_scene.gltf', false);

gltfLoader.load('/assets/clothes/tshirt.glb', gltf => {
  tshirtModel = gltf.scene;
  attachTShirtToAvatar();
  applyTShirtColor();
  updateAll();
});

gltfLoader.load('/assets/clothes/pants.glb', gltf => {
  pantsModel = gltf.scene;
  attachPantsToAvatar();
  updateAll();
});

function attachTShirtToAvatar() {
  if (!tshirtModel) return;

  if (maleModel && !maleTShirt) {
    maleTShirt = tshirtModel.clone();
    scene.add(maleTShirt);
  }

  if (femaleModel && !femaleTShirt) {
    femaleTShirt = tshirtModel.clone();
    scene.add(femaleTShirt);
  }
}

function attachPantsToAvatar() {
  if (!pantsModel) return;

  if (maleModel && !malePants) {
    malePants = pantsModel.clone();
    scene.add(malePants);
  }

  if (femaleModel && !femalePants) {
    femalePants = pantsModel.clone();
    scene.add(femalePants);
  }
}

function getSizeScale() {
  const sizeKey = document.getElementById('sizeSelect')?.value || initialSize;
  const isMale = maleModel && maleModel.visible;
  const sizeData = isMale ? menTshirtSizes[sizeKey] : womenTshirtSizes[sizeKey];
  if (!sizeData) return new THREE.Vector3(1, 1, 1);
  const chestScale = sizeData.chestCm / 100;
  const lengthScale = sizeData.lengthCm / 70;
  return new THREE.Vector3(chestScale, lengthScale, chestScale);
}

function updateTShirtPosition() {
  const applyTransform = (avatar, clothing, boneName, offset, rotation, baseScale) => {
    if (!avatar || !clothing) return;
    const bone = avatar.getObjectByName(boneName);
    if (!bone) return;

    bone.updateWorldMatrix(true, false);
    bone.getWorldPosition(clothing.position);
    bone.getWorldQuaternion(clothing.quaternion);

    clothing.position.add(offset.clone().applyQuaternion(clothing.quaternion));
    clothing.rotation.x += rotation.x;
    clothing.rotation.y += rotation.y;
    clothing.rotation.z += rotation.z;

    const sizeScale = baseScale === tshirtBaseScale ? getSizeScale() : new THREE.Vector3(1, 1, 1);
    clothing.scale.copy(baseScale).multiply(sizeScale);
  };

  applyTransform(maleModel, maleTShirt, 'Chest_101', tshirtOffsetMale, tshirtRotationMale, tshirtBaseScale);
  applyTransform(femaleModel, femaleTShirt, 'Chest_40', tshirtOffsetFemale, tshirtRotationFemale, tshirtBaseScale);
  applyTransform(maleModel, malePants, 'Pelvis_107', pantsOffsetMale, pantsRotationMale, pantsBaseScale);
  applyTransform(femaleModel, femalePants, 'Pelvis_46', pantsOffsetFemale, pantsRotationFemale, pantsBaseScale);

  if (maleTShirt) maleTShirt.visible = maleModel && maleModel.visible;
  if (femaleTShirt) femaleTShirt.visible = femaleModel && femaleModel.visible;
  if (malePants) malePants.visible = maleModel && maleModel.visible;
  if (femalePants) femalePants.visible = femaleModel && femaleModel.visible;
}

function calculateScale() {
  let weight = parseFloat(document.getElementById('weightInput')?.value) || parseFloat(initialWeight);
  let height = parseFloat(document.getElementById('heightInput')?.value) || parseFloat(initialHeight);
  const shape = document.getElementById('shapeSelect')?.value || initialBodyShape || 'Average';

  if (isNaN(height)) height = 150;
  if (isNaN(weight)) weight = 50;

  const heightScale = height / 170;
  let widthScale = Math.sqrt(weight / 70);

  if (shape === 'Hourglass') widthScale *= 1.1;
  if (shape === 'Rectangle') widthScale *= 1.0;
  if (shape === 'Pear') widthScale *= 1.15;
  if (shape === 'Inverted-Triangle') widthScale *= 1.2;
  if (shape === 'Rounded') widthScale *= 1.05;


  return { x: widthScale, y: heightScale, z: widthScale };
}

function updateModelScale() {
  const scale = calculateScale();
  if (maleModel) maleModel.scale.set(scale.x, scale.y, scale.z);
  if (femaleModel) femaleModel.scale.set(scale.x, scale.y, scale.z);
  renderer.render(scene, camera);
}

function updateHumanPositionForSize() {
  const sizeKey = document.getElementById('sizeSelect')?.value || initialSize;
  const isMale = maleModel && maleModel.visible;
  const sizeData = isMale ? menTshirtSizes[sizeKey] : womenTshirtSizes[sizeKey];
  if (!sizeData) return;

  const baseLength = isMale ? menTshirtSizes["M"].lengthCm : womenTshirtSizes["M"].lengthCm;
  const lengthDiff = sizeData.lengthCm - baseLength;
  let yOffset = lengthDiff * 0.005;
  yOffset = Math.min(Math.max(yOffset, -0.1), 0.1);

  if (maleModel) maleModel.position.y = -1.0 + yOffset;
  if (femaleModel) femaleModel.position.y = -1.0 + yOffset;
}

function applyTShirtColor() {
  const selectedColor = document.getElementById('colorSelect')?.value || initialColor;
  if (!selectedColor) return;

  const applyColor = (model) => {
    if (!model) return;
    model.traverse(child => {
      if (child.isMesh && child.material) {
        child.material.color = new THREE.Color(selectedColor);
        child.material.needsUpdate = true;
      }
    });
  };

  applyColor(maleTShirt);
  applyColor(femaleTShirt);
}

function sizeUp(size) {
  const order = ["S", "M", "L", "XL", "XXL"];
  let idx = order.indexOf(size);
  return idx < order.length - 1 ? order[idx + 1] : size;
}

function sizeDown(size) {
  const order = ["S", "M", "L", "XL", "XXL"];
  let idx = order.indexOf(size);
  return idx > 0 ? order[idx - 1] : size;
}

function recommendSize() {
  let height = parseFloat(document.getElementById('heightInput')?.value) || parseFloat(initialHeight);
  let weight = parseFloat(document.getElementById('weightInput')?.value) || parseFloat(initialWeight);
  const shape = document.getElementById('shapeSelect')?.value || initialBodyShape || 'Average';

  if (isNaN(height)) height = 140;
  if (isNaN(weight)) weight = 40;

  let recommended = "";

  if (height < 150 && weight < 45) {
    recommended = "S";
  } else if (height >= 150 && height <= 160 && weight < 50) {
    recommended = "S";
  } else if (height >= 150 && height <= 160 && weight >= 50 && weight <= 60) {
    recommended = "M";
  } else if (height >= 150 && height <= 160 && weight > 60 && weight <= 70) {
    recommended = "L";
  } else if (height >= 150 && height <= 160 && weight > 70 && weight <= 80) {
    recommended = "XL";
  } else if (height >= 150 && height <= 160 && weight > 80) {
    recommended = "XXL";
  } else if (height > 160 && height <= 170 && weight < 55) {
    recommended = "S";
  } else if (height > 160 && height <= 170 && weight >= 55 && weight <= 65) {
    recommended = "M";
  } else if (height > 160 && height <= 170 && weight > 65 && weight <= 75) {
    recommended = "L";
  } else if (height > 170 && height <= 180 && weight < 65) {
    recommended = "M";
  } else if (height > 170 && height <= 180 && weight >= 65 && weight <= 80) {
    recommended = "L";
  } else if (height > 170 && height <= 180 && weight > 80) {
    recommended = "XL";
  } else if (height > 180 && weight < 75) {
    recommended = "L";
  } else if (height > 180 && weight >= 75 && weight <= 90) {
    recommended = "XL";
  } else {
    recommended = "XXL";
  }


  if (shape === 'Rectangle' && recommended !== 'S') {
    recommended = sizeDown(recommended);
  }

  if (['Pear', 'Hourglass', 'Rounded'].includes(shape) && recommended !== 'XXL') {
    recommended = sizeUp(recommended);
  }

  // Inverted-Triangle stays unchanged (or adjust later if needed)

  document.getElementById('fitMessage').innerHTML = `Recommended size: <span style="color:#00f">${recommended}</span>`;
  return recommended;
}

function getFitCategory(selectedSizeKey, recommendedSizeKey) {
  const order = ["S", "M", "L", "XL", "XXL"];
  const selectedIndex = order.indexOf(selectedSizeKey);
  const recommendedIndex = order.indexOf(recommendedSizeKey);

  const diff = selectedIndex - recommendedIndex;

  if (diff <= -2) return "too tight";
  if (diff === -1) return "tight";
  if (diff === 0) return "perfect";
  if (diff === 1) return "slightly loose";
  return "loose";
}

function showFitInsights() {
  const sizeKey = document.getElementById('sizeSelect')?.value || initialSize;
  if (!sizeKey) return;

  const recommended = recommendSize();
  const chestFit = getFitCategory(sizeKey, recommended);
  const waistFit = getFitCategory(sizeKey, recommended);

  const insightsElement = document.getElementById('fitInsights');
  if (insightsElement) {
    insightsElement.innerHTML = `
      <span style="color:white">Fit Insights:</span><br>
      Chest: <span style="color:lightblue">${chestFit}</span><br>
      Waist: <span style="color:lightblue">${waistFit}</span>
    `;
  }
}

function updateAll() {
  recommendSize();
  updateModelScale();
  updateHumanPositionForSize();
  applyTShirtColor();
  showFitInsights();
}

window.toggleModel = function() {
  if (maleModel && femaleModel) {
    maleModel.visible = !maleModel.visible;
    femaleModel.visible = !femaleModel.visible;
    document.getElementById('toggleButton').textContent = maleModel.visible ? 'Show Female' : 'Show Male';
    updateAll();
  }
};

window.addEventListener('DOMContentLoaded', () => {
  document.getElementById('heightInput').value = initialHeight;
  document.getElementById('weightInput').value = initialWeight;
  document.getElementById('shapeSelect').value = initialBodyShape;
  document.getElementById('sizeSelect').value = initialSize;
  document.getElementById('colorSelect').value = initialColor;

  if (maleModel && femaleModel) {
    maleModel.visible = (initialGender === 'male');
    femaleModel.visible = (initialGender === 'female');
  }

  ['weightInput', 'heightInput', 'shapeSelect', 'sizeSelect', 'colorSelect'].forEach(id => {
    const el = document.getElementById(id);
    if (el) el.addEventListener('input', updateAll);
  });
});

function animate() {
  requestAnimationFrame(animate);
  controls.update();
  updateTShirtPosition();
  renderer.render(scene, camera);
}
animate();

window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});