package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.ScenesMenager;

public abstract class Controller{
    public void setScenesMenager(ScenesMenager scenesMenager){
        HelloApplication.scenesMenager = scenesMenager;
    }
}
