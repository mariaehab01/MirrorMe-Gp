# MirrorMe – Virtual Try-On App 

MirrorMe is an innovative Android app that offers a personalized, immersive virtual try-on experience using both real-time camera AR and 3D avatar simulations. It bridges the gap between online e-commerce and the physical shopping experience, helping users visualize outfits before buying while maintaining privacy and reducing return rates.

This project was developed as part of the **graduation project at FCAI-CU** (Faculty of Computers and Artificial Intelligence – Cairo University).

---

## Features

- **Dual Try-On Modes**  
  Try outfits via real-time **camera-based AR** or through a personalized **3D avatar**.


- **AI-Powered Recommendations**
  - Integrated **three custom-developed AI models** trained on a dataset of 5,000+ fashion items to provide highly personalized recommendations:
    - **Similarity Model** – Finds visually similar fashion items.
    - **Compatibility Model** – suggest items that match well together in an outfit.
    - **Outfit Suggestion Model** – Builds complete outfit suggestions tailored to the user.
      
  
- **Admin Dashboard**  
  A web-based admin panel for managing the inventory and users with real-time Supabase integration.
  
---

## Used Technologies

- **Android** – Mobile application for virtual try-on using Jetpack Compose and MVVM architecture.
- **Supabase** – Backend services including authentication, real-time database, and storage.
- **Three.js** – 3D rendering for web-based avatar visualization.
- **Next.js** – Frontend framework used to build the admin dashboard.
- **Milvus** – Vector database powering AI-based recommendations.
- **Flask** – Backend API to integrate and serve AI models.

---
