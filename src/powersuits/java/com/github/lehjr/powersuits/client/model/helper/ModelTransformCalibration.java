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

package com.github.lehjr.powersuits.client.model.helper;

import com.github.lehjr.numina.util.client.model.helper.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ModelTransformCalibration {
    static float xOffest_original;
    static float yOffest_original;
    static float zOffest_original;

    public static float xOffest;
    public static float yOffest;
    public static float zOffest;

    static float angleX_original;
    static float angleY_original;
    static float angleZ_original;

    public static float angleX;
    public static float angleY;
    public static float angleZ;

    static float scalemodifier_original;

    public static float scalemodifier;

    public static boolean tap;

    public ModelTransformCalibration() {
        this(0,0,0,0,0,0,0.625f);
    }

    // unreliable
//    public ModelTransformCalibration(TransformationMatrix transform) {
//        this(
//                transform.getTranslation().x * 16,
//                transform.getTranslation().y * 16,
//                transform.getTranslation().z * 16,
//
//                TransformationMatrix.toYXZDegrees(transform.getLeftRot()).x,
//                TransformationMatrix.toYXZDegrees(transform.getLeftRot()).y,
//                TransformationMatrix.toYXZDegrees(transform.getLeftRot()).z,
//
//                transform.getScale().x
//        );
//    }

    public ModelTransformCalibration(float transformX, float transformY, float transformZ, float angleX, float angleY, float angleZ, float scale) {
        this.xOffest = this.xOffest_original = transformX;
        this.yOffest = this.yOffest_original = transformY;
        this.zOffest = this.zOffest_original = transformZ;

        this.angleX = this.angleX_original = angleX;
        this.angleY = this.angleY_original = angleY;
        this.angleZ = this.angleZ_original = angleZ;

        this.scalemodifier = this.scalemodifier_original = scale;

        this.tap = false;
    }

    static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    //----------------------------------
    /*
     * Only used for setting up scale, rotation, and relative placement coordinates
     */
    public static void transformCalibration() {
        int numsegments = 16;
        if (!tap) {

            if (isKeyPressed(GLFW.GLFW_KEY_INSERT)) {
                xOffest += 0.1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_DELETE)) {
                xOffest -= 0.1;
                tap = true;
            }

            if (isKeyPressed(GLFW.GLFW_KEY_HOME)) {
                yOffest += 0.1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_END)) {
                yOffest -= 0.1;
                tap = true;
            }

            if (isKeyPressed(GLFW.GLFW_KEY_PAGE_UP )) {
                zOffest += 0.1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_PAGE_DOWN)) {
                zOffest -= 0.1;
                tap = true;
            }

            if (isKeyPressed(GLFW.GLFW_KEY_KP_4)) {
                angleX += 1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_KP_5)) {
                angleY += 1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_KP_6)) {
                angleZ += 1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_KP_1)) {
                angleX -= 1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_KP_2)) {
                angleY -= 1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_KP_3)) {
                angleZ -= 1;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_KP_8)) {
                xOffest = xOffest_original;
                yOffest = yOffest_original;
                zOffest = zOffest_original;

                angleX = angleX_original;
                angleY = angleY_original;
                angleZ = angleZ_original;

                scalemodifier = scalemodifier_original;

                tap = true;
            }
            // this probably needs a bit more work, int's are too big.
            if (isKeyPressed(GLFW.GLFW_KEY_SCROLL_LOCK)) {
                scalemodifier += 0.01f;
                tap = true;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_PAUSE)) {
                scalemodifier -= 0.01f;
                tap = true;
            }

            if (isKeyPressed(GLFW.GLFW_KEY_KP_0)) {

                System.out.println("FIXME: check consistency between using every coinstructor");

                System.out.println("xOffest: " + xOffest + ", yOffest: " + yOffest + ", zOffest: " + zOffest);
                System.out.println("xrot: " + angleX + ", yrot: " + angleY + ", zrot: " + angleZ);
                System.out.println("scaleModifier: " + scalemodifier);

                System.out.println("ModelHelper.get(" + xOffest +", " + yOffest + ", " + zOffest + ", " + angleX + ", " + angleY+ ", " + angleZ + ", " + scalemodifier + ")" );


                tap = true;
            }
        } else {
            if (!isKeyPressed(GLFW.GLFW_KEY_KP_0) && !isKeyPressed(GLFW.GLFW_KEY_KP_1) && !isKeyPressed(GLFW.GLFW_KEY_KP_2)
                    && !isKeyPressed(GLFW.GLFW_KEY_KP_3) && !isKeyPressed(GLFW.GLFW_KEY_KP_4)
                    && !isKeyPressed(GLFW.GLFW_KEY_KP_5) && !isKeyPressed(GLFW.GLFW_KEY_KP_6)) {
                tap = false;
            }
            if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                tap = false;
            }
        }
    }

    public TransformationMatrix getTransform() {
        transformCalibration();
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