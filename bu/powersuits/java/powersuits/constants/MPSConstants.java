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

package lehjr.powersuits.constants;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslatableComponent;

public class MPSConstants {

    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final String RESOURCE_DOMAIN = MOD_ID.toLowerCase();

    // temporary locations until model spec system up and running
    public static final String SEBK_AMROR_PANTS = TEXTURE_PREFIX + "item/armor/sebkarmorpants.png";
    public static final String SEBK_AMROR = TEXTURE_PREFIX + "item/armor/sebkarmor.png";
    public static final String CITIZEN_JOE_ARMOR_PANTS = TEXTURE_PREFIX + "item/armor/joearmorpants.png";
    public static final String CITIZEN_JOE_ARMOR = TEXTURE_PREFIX + "item/armor/joearmor.png";

    public static final ResourceLocation POWER_FIST_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "models/powerfist.png");

    public static final TranslatableComponent CRAFTING_TABLE_CONTAINER_NAME  = new TranslatableComponent("container.crafting");







    /** Recipes ------------------------------------------------------------------------------------- */
    public static final String CONFIG_PREFIX_RECIPES = CONFIG_PREFIX + "recipes.";
    public static final String CONFIG_RECIPES_USE_VANILLA = CONFIG_PREFIX_RECIPES + "useVanilla";





}
