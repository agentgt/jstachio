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
package com.github.sviperll.staticmustache.context;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
class DeclaredTypeRenderingContext implements RenderingContext {
    private final JavaExpression expression;
    private final TypeElement definitionElement;
    private final RenderingContext parent;

    DeclaredTypeRenderingContext(JavaExpression expression, TypeElement element, RenderingContext parent) {
        this.expression = expression;
        this.definitionElement = element;
        this.parent = parent;
    }

    @Override
    public String beginSectionRenderingCode() {
        return parent.beginSectionRenderingCode();
    }

    @Override
    public String endSectionRenderingCode() {
        return parent.endSectionRenderingCode();
    }

    @Override
    public JavaExpression getDataOrDefault(String name, JavaExpression defaultValue) {
        List<? extends Element> enclosedElements = definitionElement.getEnclosedElements();
        for (Element element: enclosedElements) {
            if (element.getKind() == ElementKind.METHOD && element.getSimpleName().contentEquals(name)) {
                return getMethodEntryOrDefault(enclosedElements, name, defaultValue);
            }
        }
        String getterName = getterName(name);
        for (Element element: enclosedElements) {
            if (element.getKind() == ElementKind.METHOD && element.getSimpleName().contentEquals(getterName)) {
                return getMethodEntryOrDefault(enclosedElements, getterName, defaultValue);
            }
        }
        for (Element element: enclosedElements) {
            if (element.getKind() == ElementKind.FIELD && element.getSimpleName().contentEquals(name)) {
                return expression.fieldAccess(element);
            }
        }
        return parent.getDataOrDefault(name, defaultValue);
    }

    private JavaExpression getMethodEntryOrDefault(List<? extends Element> elements, String methodName, JavaExpression defaultValue) {
        for (Element element: elements) {
            if (element.getKind() == ElementKind.METHOD
                && element.getSimpleName().contentEquals(methodName)
                && !element.getModifiers().contains(Modifier.STATIC)) {
                ExecutableType method = expression.methodSignature(element);
                if (method.getParameterTypes().isEmpty() && areUnchecked(method.getThrownTypes())) {
                    return expression.methodCall(element);
                }
            }
        }
        return defaultValue;
    }

    private String getterName(String name) {
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private boolean areUnchecked(List<? extends TypeMirror> thrownTypes) {
        for (TypeMirror thrownType: thrownTypes) {
            if (!expression.model().isUncheckedException(thrownType))
                return false;
        }
        return true;
    }

    @Override
    public JavaExpression currentExpression() {
        return expression;
    }

    @Override
    public VariableContext createEnclosedVariableContext() {
        return parent.createEnclosedVariableContext();
    }
}