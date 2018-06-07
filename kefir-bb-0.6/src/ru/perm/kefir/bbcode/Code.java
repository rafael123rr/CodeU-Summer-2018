package ru.perm.kefir.bbcode;

import java.io.IOException;

/**
 * The bbcode class
 *
 * @author Kefir
 */
public class Code extends AbstractCode {
    /**
     * Pattern for parsing code
     */
    private final Pattern pattern;

    /**
     * Create bb-code with default name and zero priority.
     *
     * @param pattern  parse pattern
     * @param template building tamplate
     */
    public Code(Pattern pattern, Template template) {
        super(template, Util.generateRandomName(), DEFAULT_PRIORITY);
        this.pattern = pattern;
    }

    /**
     * Create the bb-code with priority
     *
     * @param pattern  pattern to parse the source text
     * @param template template to build target text
     * @param name     name of code
     * @param priority priority. If priority higher then code be checking early.
     */
    public Code(Pattern pattern, Template template, String name, int priority) {
        super(template, name, priority);
        this.pattern = pattern;
    }

    /**
     * Parse bb-code
     *
     * Before invocation suspicious method must be call
     *
     * @param context the bb-processing context
     * @return true - if parse source
     *         false - if can't parse code
     * @throws java.io.IOException if can't append to target
     */
    public boolean process(Context context) throws IOException {
        Context codeContext = new Context(context);
        if (pattern.parse(codeContext)) {
            codeContext.mergeWithParent();
            template.generate(context);
            return true;
        }

        return false;
    }

    /**
     * Check if next sequence can be parsed with this code.
     * It's most called method in this project.
     *
     * @param source text source
     * @return true - if next sequence can be parsed with this code;
     *         false - only if next sequence can't be parsed with this code.
     */
    public boolean suspicious(Source source) {
        return pattern.suspicious(source);
    }
}
