package ru.perm.kefir.bbcode;

import java.io.IOException;

/**
 * Класс текста, который подлежит парсингу
 *
 * @author Kefir
 */
public class Text extends NamedElement implements PatternElement {
    /**
     * Scope defina the codeset for parsing this text
     */
    private final Scope scope;

    /**
     * Mark that variables getted in element context will be put into parent context
     */
    private final boolean transparent;

    /**
     * Создает именованный элемент
     *
     * @param name        имя переменной
     * @param transparent mark that scope variable must be accessible from parent context
     */
    public Text(String name, boolean transparent) {
        super(name);
        scope = null;
        this.transparent = transparent;
    }

    public Text(String name, Scope scope, boolean transparent) {
        super(name);
        this.scope = scope;
        this.transparent = transparent;
    }

    /**
     * Парсит элемент
     *
     * @param context контекст
     * @return true - если удалось распарсить константу
     *         false - если не удалось
     */
    public boolean parse(Context context, PatternElement terminator) {
        Context child = new Context(context);
        StringBuilder target = new StringBuilder();
        child.setTarget(target);
        if (scope != null) {
            child.setScope(scope);
        }
        child.setTerminator(terminator);
        try {
            child.parse();
        } catch (IOException e) {
            // Never because StringBuilder don't throw IOException
        }
        if(transparent){
            child.mergeWithParent();
        }
        setAttribute(context, target);
        return true;
    }

    /**
     * Определяет, что дальше в разбираемой строке находится нужная последовательность
     *
     * @param source source text
     * @return true если следующие символы в строке совпадают с pattern
     *         false если не совпадают или строка кончилась
     */
    public boolean isNextIn(Source source) {
        return false;
    }

    /**
     * Find this element
     *
     * @param source text source
     * @return start offset
     */
    public int findIn(Source source) {
        return -1;
    }

    @Override
    public String toString() {
        return "text:" + getName();
    }
}
