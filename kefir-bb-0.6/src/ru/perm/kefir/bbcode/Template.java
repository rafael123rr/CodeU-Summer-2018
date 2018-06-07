package ru.perm.kefir.bbcode;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Code template
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Template {
    /**
     * Empty template
     */
    @SuppressWarnings({"unchecked"})
    public static final Template EMPTY = new Template(Collections.EMPTY_LIST);

    /**
     * Template elemnts
     */
    private final List<? extends TemplateElement> elements;

    /**
     * Create neq template with elements.
     *
     * @param elements template elements.
     */
    public Template(List<? extends TemplateElement> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    /**
     * Append to result string processed text.
     *
     * @param context current context.
     * @throws IOException if can't append.
     */
    public void generate(Context context) throws IOException {
        for (TemplateElement element : elements) {
            context.getTarget().append(element.generate(context));
        }
    }
}
