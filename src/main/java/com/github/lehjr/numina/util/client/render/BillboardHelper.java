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

package com.github.lehjr.numina.util.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:21 AM, 11/13/13
 * <p>
 * Ported to Java by lehjr on 10/25/16.
 */
public final class BillboardHelper {
    private static final FloatBuffer matrix = BufferUtils.createFloatBuffer(16);

    static {
        new BillboardHelper();
    }

    private BillboardHelper() {
    }

    private static void unrotate() {
        matrix.clear();
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, matrix);
        float scalex = pythag(matrix.get(0), matrix.get(1), matrix.get(2));
        float scaley = pythag(matrix.get(4), matrix.get(5), matrix.get(6));
        float scalez = pythag(matrix.get(8), matrix.get(9), matrix.get(10));
        for (int i = 0; i < 12; i++) {
            matrix.put(i, 0);
        }
        matrix.put(0, scalex);
        matrix.put(5, scaley);
        matrix.put(10, scalez);
        GL11.glLoadMatrixf(matrix);
    }

    public static void unRotate() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        unrotate();
    }

    private static float pythag(final float x, final float y, final float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}