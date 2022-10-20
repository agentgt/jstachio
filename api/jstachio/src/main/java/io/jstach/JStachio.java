package io.jstach;

import java.io.IOException;

import io.jstach.spi.JStacheServices;

public enum JStachio {
	;
    public static <T> Renderer<T> renderer(Class<T> modelType) {
    	return JStacheServices.renderer(modelType);
    }

    @SuppressWarnings("unchecked")
	public static void render(Object model, Appendable a) throws IOException {
    	@SuppressWarnings("rawtypes")
		Renderer r = JStacheServices.renderer(model.getClass());
    	r.render(model, a);
    }
    
    @SuppressWarnings("unchecked")
	public static String render(Object model) throws IOException {
    	@SuppressWarnings("rawtypes")
		Renderer r = JStacheServices.renderer(model.getClass());
    	return r.render(model);
    }

}