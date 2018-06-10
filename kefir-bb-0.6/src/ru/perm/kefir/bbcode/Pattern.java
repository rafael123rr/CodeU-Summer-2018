package ru.perm.kefir.bbcode;

import java.util.Collections;
import java.util.List;

/**
 * Represents the pattern
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Pattern {
    /**
     * Pattern elements
     */
    private final List<? extends PatternElement> elements;

    // Performance optimization
    private final PatternElement firstElement;

    /**
     * Construct pattern.
     *
     * @param elements pattern elements
     */
    public Pattern(List<? extends PatternElement> elements) {
        this.elements = Collections.unmodifiableList(elements);

        // Performance optimization
        if (!this.elements.isEmpty()) {
            firstElement = this.elements.get(0);
        } else {
            throw new IllegalArgumentException("Parameter \"elements\" can't be empty.");
        }
    }

    /**
     * Указывает на то что следующая последовательность вполне может оказаться данным тэгом
     *
     * @param source источник
     * @return true - если следующие несколько символов совпадают с первой константой в коде
     *         false - означает, что это точно не тот код
     */
    public boolean suspicious(Source source) {
        return firstElement.isNextIn(source);
    }

    /**
     * Parse context with this pattern
     *
     * @param context current context
     * @return true if next subsequence is valid to this pattern,
     *         false others
     */
    public boolean parse(Context context) {
        boolean flag = true;
        int start = context.getSource().getOffset();
        int patternSize = elements.size();
        for (int i = 0; i < patternSize && flag; i++) {
            PatternElement current = elements.get(i);
            PatternElement next;
            if (i < patternSize - 1) {
                next = elements.get(i + 1);
            } else {
                next = context.getTerminator();
            }
            flag = context.getSource().hasNext() && current.parse(context, next);
        }

        if (!flag) {
            context.getSource().setOffset(start);
        }
        return flag;
    }
}
