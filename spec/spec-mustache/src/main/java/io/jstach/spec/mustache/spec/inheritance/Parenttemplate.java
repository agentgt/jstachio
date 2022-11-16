package io.jstach.spec.mustache.spec.inheritance;

import io.jstach.jstache.JStache;
import io.jstach.jstache.JStachePartial;
import io.jstach.jstache.JStachePartials;
import io.jstach.spec.generator.SpecModel;

@JStache(path = "inheritance/Parenttemplate.mustache")
@JStachePartials({ @JStachePartial(name = "parent", template = "{{$foo}}default content{{/foo}}"), })
public class Parenttemplate extends SpecModel {

}
