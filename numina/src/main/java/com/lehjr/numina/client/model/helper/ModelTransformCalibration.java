/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.lehjr.numina.client.model.helper;

import com.lehjr.numina.common.base.NuminaLogger;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public enum ModelTransformCalibration {
    CALIBRATION;
    // TODO: ItemDisplayContext cameraTransformType default values as starting point for item transforms
    static float xOffest_original = 0;
    static float yOffest_original = 0;
    static float zOffest_original = 0;

    public static float xOffest = 0;
    public static float yOffest = 0;
    public static float zOffest = 0;

    static float angleX_original = 0;
    static float angleY_original = 0;
    static float angleZ_original = 0;

    public static float angleX = 0;
    public static float angleY = 0;
    public static float angleZ = 0;

    static float scalemodifier_original = 1;

    public static float scalemodifier = 1;

    public static boolean tap = false;

    boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    //----------------------------------
    /*
     * Only used for setting up scale, rotation, and relative placement coordinates
     */
    public void transformCalibration(InputEvent.Key event) {
        int key = event.getKey();

          /*
            modifiers (there are more but these are the important ones):
            ----------
            0-none
            1-shift
            2-ctrl
            4-alt
         */
        double delta = switch(event.getModifiers()) {
            case 1 -> 10;
            case 2 -> 0.1;
            case 4 -> 5;
            default -> 1;
        };

         switch (key) {
            case GLFW.GLFW_KEY_INSERT ->{
                xOffest += delta;
            }

            case GLFW.GLFW_KEY_DELETE ->{
                xOffest -= delta;
            }

            case GLFW.GLFW_KEY_HOME -> {
                yOffest += delta;
            }

            case GLFW.GLFW_KEY_END -> {
                yOffest -= delta;
            }

            case GLFW.GLFW_KEY_PAGE_UP -> {
                zOffest += delta;
            }

            case GLFW.GLFW_KEY_PAGE_DOWN -> {
                zOffest -= delta;
            }

            case GLFW.GLFW_KEY_KP_4 -> {
                angleX += delta;
            }

            case GLFW.GLFW_KEY_KP_5 -> {
                angleY += delta;
            }
            case GLFW.GLFW_KEY_KP_6 -> {
                angleZ += delta;
            }

            case GLFW.GLFW_KEY_KP_1 -> {
                angleX -= delta;
            }

            case GLFW.GLFW_KEY_KP_2 -> {
                angleY -= delta;
            }

            case GLFW.GLFW_KEY_KP_3 -> {
                angleZ -= delta;
            }

            case GLFW.GLFW_KEY_KP_8 -> {
                xOffest = xOffest_original;
                yOffest = yOffest_original;
                zOffest = zOffest_original;

                angleX = angleX_original;
                angleY = angleY_original;
                angleZ = angleZ_original;

                scalemodifier = scalemodifier_original;
            }

            // this probably needs a bit more work, int's are too big.
            case GLFW.GLFW_KEY_SCROLL_LOCK -> {
                scalemodifier += delta * 0.1;
            }

            case GLFW.GLFW_KEY_PAUSE -> {
                scalemodifier -= delta * 0.1;
            }

            case GLFW.GLFW_KEY_KP_0 -> {
                NuminaLogger.logError("xOffest: " + xOffest + ", yOffest: " + yOffest + ", zOffest: " + zOffest);
                NuminaLogger.logError("xrot: " + angleX + ", yrot: " + angleY + ", zrot: " + angleZ);
                NuminaLogger.logError("scaleModifier: " + scalemodifier);
                NuminaLogger.logError("MuseModelHelper.get(" + xOffest +", " + yOffest + ", " + zOffest + ", " + angleX + ", " + angleY+ ", " + angleZ + ", " + scalemodifier + ")" );
            }
        }

//        if (!tap) {
//        } else {
//            if (!isKeyPressed(GLFW.GLFW_KEY_KP_0) && !isKeyPressed(GLFW.GLFW_KEY_KP_1) && !isKeyPressed(GLFW.GLFW_KEY_KP_2)
//                    && !isKeyPressed(GLFW.GLFW_KEY_KP_3) && !isKeyPressed(GLFW.GLFW_KEY_KP_4)
//                    && !isKeyPressed(GLFW.GLFW_KEY_KP_5) && !isKeyPressed(GLFW.GLFW_KEY_KP_6)) {
//                tap = false;
//            }
//            if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
//                tap = false;
//            }
//        }
    }

    public Transformation getTransform() {
//        transformCalibration();
        return ModelHelper.get(xOffest, yOffest, zOffest, angleX, angleY, angleZ, scalemodifier);
    }


    /**

     OFFSET
     --------------------------------------------------------------------------
     |                       |
     INSERT + xOffest   |    HOME + yOffest     |   PAGE_UP + zOffest
     DELETE - xOffest   |    END - yOffest      |   PAGE_DOWN -zOffest
     |                       |
     --------------------------------------------------------------------------
     ROTATION
     --------------------------------------------------------------------------
     |                       |
     NUM_PAD_4 + xRot   |   NUM_PAD_5 + yRot    |   NUM_PAD_6 + zRot
     NUM_PAD_1 - xRot   |   NUM_PAD_2 - yRot    |   NUM_PAD_3 - zRot
     |                       |
     --------------------------------------------------------------------------
     |                       |
     SCROLL_LOCK +scale | NUM_PAD_8 = reset     | L_SHIFT tap = false
     PAUSE - scale   | NUM_PAD_0 = print

     */


}
