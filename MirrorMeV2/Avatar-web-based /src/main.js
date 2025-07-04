import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';


//====================parsing URL values====================
const urlParams = new URLSearchParams(window.location.search);
const initialHeight = urlParams.get('height') || '165';
const initialWeight = urlParams.get('weight') || '50';
const initialGender = urlParams.get('gender') || 'male';
const initialBodyShape = urlParams.get('bodyShape') || 'Hourglass';
const initialSkinTone = urlParams.get('skinTone') || 'Light';
const initialSize = urlParams.get('size') || 'M';
const initialColor = urlParams.get('color') || 'white';

const userInputs = {
  height: parseFloat(initialHeight),
  weight: parseFloat(initialWeight),
  shape: initialBodyShape,
  gender: initialGender,
  size: initialSize,
  color: initialColor,
  skinTone: initialSkinTone
};

//=================initialise models holders and variables===============
let scene, camera, renderer, controls;
let maleModel = null, femaleModel = null;
let maleTShirt = null, femaleTShirt = null;  //before cloning
let malePants = null, femalePants = null;
let tshirtModel = null, pantsModel = null;  //after cloning
let skinTexture = null;

//==================setup scene============================
function initScene() {
  scene = new THREE.Scene();
  camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
  camera.position.set(0, 1.5, 3);

  renderer = new THREE.WebGLRenderer({ antialias: true });
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.outputEncoding = THREE.sRGBEncoding;
  renderer.setClearColor(0x333333);
  document.body.appendChild(renderer.domElement);

  controls = new OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;

  scene.add(new THREE.AmbientLight(0xffffff, 0.8));
  const light = new THREE.DirectionalLight(0xffffff, 1.5);
  light.position.set(2, 3, 2);
  scene.add(light);

  window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
  });
}

//=======================clothes positions=======================
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

//=========================size tables===========================
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


//===================circular indicator============================
const loaderOverlay = document.getElementById('loader');
const uiContainer = document.getElementById('ui');
let modelsLoaded = {
  male: false,
  female: false,
  tshirt: false,
  pants: false
};

function checkAllModelsLoaded() {
  const allDone = Object.values(modelsLoaded).every(Boolean);
  if (allDone) {
    loaderOverlay.style.display = 'none';  // Hide spinner
    uiContainer.style.display = 'block';   // Show UI
    updateAll();
  }
}

//====================loaders(skin and models)========================
const gltfLoader = new GLTFLoader();

function loadSkinTexture() {
  const textureLoader = new THREE.TextureLoader();
const skinTexturePath = `/assets/skins/${initialSkinTone}.png`;
 skinTexture = textureLoader.load(skinTexturePath);
skinTexture.encoding = THREE.sRGBEncoding;

}
loadSkinTexture();
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

    if (isMale){
	maleModel = model;
	modelsLoaded.male = true;  
    }
    else {
	femaleModel = model;
	modelsLoaded.female = true; 
    }

    if (tshirtModel) {
      attachTShirtToAvatar();
      applyTShirtColor();
    }
    if (pantsModel) {
      attachPantsToAvatar();
    }

    updateAll();
    checkAllModelsLoaded();

  });
}

loadModel('/assets/male_scene.gltf', true);
loadModel('/assets/female_scene.gltf', false);

gltfLoader.load('/assets/clothes/tshirt.glb', gltf => {
  tshirtModel = gltf.scene;
  modelsLoaded.tshirt = true; // ✅ This was missing!
  attachTShirtToAvatar();
  applyTShirtColor();
  updateAll();
checkAllModelsLoaded();
});

gltfLoader.load('/assets/clothes/pants.glb', gltf => {
  pantsModel = gltf.scene;
  modelsLoaded.pants = true;
  attachPantsToAvatar();
  updateAll();
checkAllModelsLoaded();
});

//=========================attaching clothes to avatar=======================

//attach t-shirt
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


//attach pants 
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

//=========================selected size scaling==================
//get the selected size data from the size table and approximate the scaling
function getSizeScale() {
  let { size } = userInputs;
  const isMale = maleModel && maleModel.visible;
  const sizeData = isMale ? menTshirtSizes[size] : womenTshirtSizes[size];
  if (!sizeData) return new THREE.Vector3(1, 1, 1);
  const chestScale = sizeData.chestCm / 100;
  const lengthScale = sizeData.lengthCm / 70;
  return new THREE.Vector3(chestScale, lengthScale, chestScale);
}


//======================update t-shirt position with the avatar position===============
function updateTShirtPosition() {
  const applyTransform = (avatar, clothing, boneName, offset, rotation, baseScale) => {
    if (!avatar || !clothing) return;
    const bone = avatar.getObjectByName(boneName);
    if (!bone) return;

    bone.updateWorldMatrix(true, false); //initialize so we can edit pos. and rotation
    bone.getWorldPosition(clothing.position); //copy avatar pos to t-shirt
    bone.getWorldQuaternion(clothing.quaternion);//copy avatar rotation to t-shirt

   //applies a small position offset (like shifting shirt down or forward)
    clothing.position.add(offset.clone().applyQuaternion(clothing.quaternion));
    clothing.rotation.x += rotation.x;
    clothing.rotation.y += rotation.y;
    clothing.rotation.z += rotation.z;

    const sizeScale = baseScale === tshirtBaseScale ? getSizeScale() : new THREE.Vector3(1, 1, 1);
    clothing.scale.copy(baseScale).multiply(sizeScale);
  };
//apply the transformations
  applyTransform(maleModel, maleTShirt, 'Chest_101', tshirtOffsetMale, tshirtRotationMale, tshirtBaseScale);
  applyTransform(femaleModel, femaleTShirt, 'Chest_40', tshirtOffsetFemale, tshirtRotationFemale, tshirtBaseScale);
  applyTransform(maleModel, malePants, 'Pelvis_107', pantsOffsetMale, pantsRotationMale, pantsBaseScale);
  applyTransform(femaleModel, femalePants, 'Pelvis_46', pantsOffsetFemale, pantsRotationFemale, pantsBaseScale);

  if (maleTShirt) maleTShirt.visible = maleModel && maleModel.visible;
  if (femaleTShirt) femaleTShirt.visible = femaleModel && femaleModel.visible;
  if (malePants) malePants.visible = maleModel && maleModel.visible;
  if (femalePants) femalePants.visible = femaleModel && femaleModel.visible;
}

//=====================scaling avatar with user data===============
function calculateScale() {
  let { height, weight, shape } = userInputs;

  if (isNaN(height)) height = 150;
  if (isNaN(weight)) weight = 50;

  const heightScale = height / 170;
  let widthScale = Math.sqrt(weight / 70);
//fine tune for body shapes
  if (shape === 'Hourglass') widthScale *= 1.1;
  if (shape === 'Rectangle') widthScale *= 1.0;
  if (shape === 'Pear') widthScale *= 1.15;
  if (shape === 'Inverted-Triangle') widthScale *= 1.2;
  if (shape === 'Rounded') widthScale *= 1.05;

//return the scaling
  return { x: widthScale, y: heightScale, z: widthScale };
}

//=========================get the scaling and apply to the model=====================
function updateModelScale() {
  const scale = calculateScale();
  if (maleModel) maleModel.scale.set(scale.x, scale.y, scale.z);
  if (femaleModel) femaleModel.scale.set(scale.x, scale.y, scale.z);
  renderer.render(scene, camera);
}

//================== update avatar y scaling for t-shirt scaling==================
function updateHumanPositionForSize() {
  let { size } = userInputs;  
  const isMale = maleModel && maleModel.visible;
  const sizeData = isMale ? menTshirtSizes[size] : womenTshirtSizes[size];
  if (!sizeData) return;

  const baseLength = isMale ? menTshirtSizes["M"].lengthCm : womenTshirtSizes["M"].lengthCm;
  const lengthDiff = sizeData.lengthCm - baseLength;  //This gets how much longer or shorter the selected size is compared to “M"

  let yOffset = lengthDiff * 0.005; //shifts model vertically
  yOffset = Math.min(Math.max(yOffset, -0.1), 0.1);

  if (maleModel) maleModel.position.y = -1.0 + yOffset;
  if (femaleModel) femaleModel.position.y = -1.0 + yOffset;
}

//====================apply color to tshirt==========================
function applyTShirtColor() {
  let { color } = userInputs;
  if (!color) return;

  const applyColor = (model) => {
    if (!model) return;
    model.traverse(child => {
      if (child.isMesh && child.material) {
        child.material.color = new THREE.Color(color);
        child.material.needsUpdate = true;
      }
    });
  };

  applyColor(maleTShirt);
  applyColor(femaleTShirt);
}

//====================recommendation size helpers========================

//get next size
function sizeUp(size) {
  const order = ["S", "M", "L", "XL", "XXL"];
  let idx = order.indexOf(size);
  return idx < order.length - 1 ? order[idx + 1] : size;
}

//get previous size
function sizeDown(size) {
  const order = ["S", "M", "L", "XL", "XXL"];
  let idx = order.indexOf(size);
  return idx > 0 ? order[idx - 1] : size;
}


//=====================recommendation size===========================
function recommendSize() {
  let { height, weight, shape } = userInputs;

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

// fine tune to shapes
  if (shape === 'Rectangle' && recommended !== 'S') {
    recommended = sizeDown(recommended);
  }

  if (['Pear', 'Hourglass', 'Rounded'].includes(shape) && recommended !== 'XXL') {
    recommended = sizeUp(recommended);
  }

  // Inverted-Triangle stays unchanged (or adjust later if needed)

//display
  document.getElementById('fitMessage').innerHTML = `Recommended size: <span style="color:#00f">${recommended}</span>`;
  return recommended;
}

//========================insights helper===================
//get the correct category according to the recommended size compared with the selected size
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

//===========================insights========================
function showFitInsights() {
  let { size } = userInputs;
  if (!size) return;

  const recommended = recommendSize();
  const chestFit = getFitCategory(size, recommended);
  const waistFit = getFitCategory(size, recommended);

  const insightsElement = document.getElementById('fitInsights');
  if (insightsElement) {
    insightsElement.innerHTML = `
      <span style="color:white">Fit Insights:</span><br>
      Chest: <span style="color:lightblue">${chestFit}</span><br>
      Waist: <span style="color:lightblue">${waistFit}</span>
    `;
  }
}

//=====================Calls all key update functions===================
function updateAll() {
  recommendSize();
  updateModelScale();
  updateHumanPositionForSize();
  applyTShirtColor();
  showFitInsights();
}

//=========================Set initial UI ============================
window.addEventListener('DOMContentLoaded', () => {
  document.getElementById('sizeSelect').value = userInputs.size;
  document.getElementById('colorSelect').value = userInputs.color;

  initScene(); 
  loadSkinTexture();
  
  ['sizeSelect', 'colorSelect'].forEach(id => {
  const el = document.getElementById(id);
  if (el) {
    el.addEventListener('input', () => {
      // Update global values
      if (id === 'sizeSelect') userInputs.size = el.value;
      if (id === 'colorSelect') userInputs.color = el.value;

      updateAll();
    });
  }
});

animate(); 
});

//========================animation and rotation loop=======================
function animate() {
  requestAnimationFrame(animate);
  controls.update();
  updateTShirtPosition();
  renderer.render(scene, camera);
}

//======================updates the camera and renderer size when the browser window is resized=====
window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});