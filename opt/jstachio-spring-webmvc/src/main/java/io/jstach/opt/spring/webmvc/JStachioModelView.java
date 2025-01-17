package io.jstach.opt.spring.webmvc;

import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import io.jstach.jstache.JStache;
import io.jstach.jstache.JStacheInterfaces;
import io.jstach.jstachio.JStachio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Another way to use JStachio with Spring MVC is to have models implement Springs
 * {@link View} interface. You can enforce that your models implement this interface with
 * {@link JStacheInterfaces}.
 * <p>
 * The model will use the static jstachio singleton that will be the spring one.
 * <p>
 * This approach has pros and cons. It makes your models slightly coupled to Spring MVC
 * but allows you to return different views if say you had to redirect on some inputs
 * ({@link RedirectView}).
 *
 * @author agentgt
 *
 */
public interface JStachioModelView extends View {

	@SuppressWarnings("exports")
	@Override
	default void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String contentType = getContentType();
		if (contentType != null) {
			response.setContentType(contentType);
		}
		try (var w = response.getWriter()) {
			jstachio().execute(model(), w);
		}

	}

	/**
	 * Returns the jstachio singleton by default.
	 * @return stachio singleton by default.
	 * @see JStachio#setStatic(java.util.function.Supplier)
	 */
	default JStachio jstachio() {
		return JStachio.of();
	}

	/**
	 * The model to be rendered by {@link #jstachio()}.
	 * @return model defaulting to <code>this</code> instance.
	 */
	default Object model() {
		return this;
	}

	/**
	 * Creates a spring view from a model with content type:
	 * "<code>text/html; charset=utf-8</code>".
	 * @param model an instance of a class annotated with {@link JStache}.
	 * @return view ready for rendering
	 */
	public static JStachioModelView of(Object model) {
		return of(model, "text/html; charset=utf-8");
	}

	/**
	 * Creates a spring view from a model.
	 * @param model an instance of a class annotated with {@link JStache}.
	 * @param contentType See {@link #getContentType()}
	 * @return view ready for rendering
	 */
	public static JStachioModelView of(Object model, String contentType) {
		return new JStachioModelView() {
			@Override
			public Object model() {
				return model;
			}

			@Override
			public String getContentType() {
				return contentType;
			}
		};
	}

}
