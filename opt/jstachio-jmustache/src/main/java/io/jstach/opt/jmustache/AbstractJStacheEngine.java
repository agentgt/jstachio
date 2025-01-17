package io.jstach.opt.jmustache;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNull;

import io.jstach.jstachio.TemplateInfo;
import io.jstach.jstachio.spi.JStachioExtensionProvider;
import io.jstach.jstachio.spi.JStachioFilter;

/**
 * Adapts a filter to ease adding a fallback template engine such as JMustache.
 *
 * @author agentgt
 */
abstract class AbstractJStacheEngine implements JStachioFilter, JStachioExtensionProvider {

	/**
	 * Do nothing constructor
	 */
	public AbstractJStacheEngine() {
	}

	@Override
	public FilterChain filter(TemplateInfo template, FilterChain previous) {
		return (model, a) -> {
			boolean answer = execute(model, a, template, previous.isBroken(model));
			if (answer) {
				return;
			}
			previous.process(model, a);
		};
	}

	/**
	 * {@inheritDoc} The implementation is a filter and provides itself.
	 */
	@Override
	public final @NonNull JStachioFilter provideFilter() {
		return this;
	}

	/**
	 * Execute the template engine. If the engine chooses not to participate
	 * <code>false</code> should be returned.
	 * @param context the model
	 * @param a the appendable to write to
	 * @param template template info
	 * @param broken whether or no the previous filter (usually jstachio itself) is
	 * broken.
	 * @return <code>true</code> if the engine has written to the appendable
	 * @throws IOException error writing to the appendable
	 */
	protected abstract boolean execute(Object context, Appendable a, TemplateInfo template, boolean broken)
			throws IOException;

}