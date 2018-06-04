package ru.perm.kefir.bbcode;

/**
 * Named element are variable and text PatternElement or TemplateElement
 */
public class NamedElement {
    /**
     * Variable name
     */
    private final String name;

    /**
     * Create named element
     *
     * @param name name of element
     */
    public NamedElement(String name) {
        this.name = name;
    }

    /**
     * Get element name
     *
     * @return element name
     */
    public String getName() {
        return name;
    }

    /**
     * Add attribute with name of this element name and value <code>value</code> to <code>context</code>.
     *
     * @param context context
     * @param value   variable value
     */
    protected void setAttribute(Context context, CharSequence value) {
        context.setAttribute(name, value);
    }
}
