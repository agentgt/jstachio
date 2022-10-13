package com.snaphop.staticmustache.spec.partials;

import com.snaphop.staticmustache.spec.SpecModel;

import io.jstach.GenerateRenderableAdapter;
import io.jstach.Template;
import io.jstach.TemplateMapping;

@GenerateRenderableAdapter(template = "partials/StandaloneIndentation.mustache")
@TemplateMapping({
@Template(name="partial", template="|\n{{{content}}}\n|\n"),
})
public class StandaloneIndentation extends SpecModel {
}
