package ru.perm.kefir.bbcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration of bbcode processor.
 * It's thread safe class.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Configuration implements TextProcessorFactory {
    private Scope scope = null;
    private Template prefix = Template.EMPTY;
    private Template suffix = Template.EMPTY;
    private final Map<String, Object> params = new HashMap<String, Object>();

    /**
     * Create the configuration
     */
    public Configuration() {
    }

    /**
     * Create text processor.
     *
     * @return text processor
     * @throws IllegalStateException if scope not setted
     */
    public synchronized TextProcessor create() throws IllegalStateException {
        if (scope == null) {
            throw new IllegalStateException("Scope is null.");
        }
        BBProcessor processor = new BBProcessor();
        processor.setScope(scope);
        processor.setPrefix(prefix);
        processor.setSuffix(suffix);
        processor.setParams(params);
        return processor;
    }

    /**
     * Set root scope for text processor.
     *
     * @param scope root scope, text will be parse with this scope
     */
    public synchronized void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Set prefix template. Prefix append to start of processed text.
     *
     * @param prefix template for prefix
     */
    public synchronized void setPrefix(Template prefix) {
        if (prefix != null) {
            this.prefix = prefix;
        } else {
            this.prefix = Template.EMPTY;
        }
    }

    /**
     * Set suffix template. Suffix append to end of processed text.
     *
     * @param suffix template for suffix
     */
    public synchronized void setSuffix(Template suffix) {
        if (suffix != null) {
            this.suffix = suffix;
        } else {
            this.suffix = Template.EMPTY;
        }
    }

    /**
     * Add param with name <code>name</code> and value <code>value</code> to root context.
     * Call addParam(String, object)
     *
     * @param name  name of context parameter
     * @param value value of context parameter
     * @see #addParam(String, Object)
     */
    public synchronized void setParam(String name, Object value) {
        addParam(name, value);
    }

    /**
     * Add param with name <code>name</code> and value <code>value</code> to root context.
     *
     * @param name  name of context parameter
     * @param value value of context parameter
     */
    public synchronized void addParam(String name, Object value) {
        params.put(name, value);
    }

    /**
     * Add param from map to root context.
     *
     * @param params Map contained params
     */
    public synchronized void addParams(Map<String, ?> params) {
        this.params.putAll(params);
    }

    /**
     * Add param from properties to root context.
     *
     * @param properties Properties object
     */
    public synchronized void addParams(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Object key = entry.getKey();
            if (key != null) {
                this.params.put(key.toString(), entry.getValue());
            }
        }
    }

    /**
     * Remove parameter with name <code>name</code> from context.
     *
     * @param name name of parameter
     */
    public synchronized void removeParam(String name) {
        this.params.remove(name);
    }

    /**
     * Remove all parameters from context.
     */
    public synchronized void clearParams() {
        this.params.clear();
    }
}
