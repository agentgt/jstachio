/*
 * Copyright (c) 2014, Victor Nazarov <asviraspossible@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation and/or
 *     other materials provided with the distribution.
 *
 *  3. Neither the name of the copyright holder nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.sviperll.staticmustache.examples;

import com.github.sviperll.staticmustache.Html;
import com.github.sviperll.staticmustache.Renderable;
import com.github.sviperll.staticmustache.Renderer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int [][] array = new int[][] {new int[] {1,2,3,4,5},new int[] {1,2,3,4,5},new int[] {1,2,3,4,5},new int[] {1,2,3,4,5},new int[] {1,2,3,4,5}};
        List<User.Item<String>> list1 = new ArrayList<User.Item<String>>();
        list1.add(new User.Item<String>("abc"));
        list1.add(new User.Item<String>("def"));

        User user = new User("Victor", 29, new String[] {"aaa", "bbb", "ccc"}, array, list1);
        Renderable<Text> renderable = new RenderableTextUserAdapter(user);
        Renderer renderer = renderable.createRenderer(System.out);
        renderer.render();
        User user1 = new User("Victor <asviraspossible@gmail.com>", 29, new String[] {}, array, list1);
        Renderable<Html> renderable1 = new RenderableHtmlUserAdapter(user1);
        Renderer renderer1 = renderable1.createRenderer(System.out);
        renderer1.render();
        Settings settings = new Settings(renderable1, true);
        Renderable<Html> renderable2 = new RenderableSettingsAdapter(settings);
        Renderer renderer2 = renderable2.createRenderer(System.out);
        renderer2.render();
    }
}