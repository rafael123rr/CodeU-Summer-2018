package ru.perm.kefir.bbcode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * Create the text processor configuration
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class ConfigurationFactory {
    // Helper constants
    private static final String DEFAULT_CONFIGURATION = "ru/perm/kefir/bbcode/default";
    private static final String DEFAULT_USER_CONFIGURATION = "kefirbb";
    private static final String CONFIGURATION_EXTENSION = ".xml";

    // Configuration paths
    public static final String DEFAULT_USER_CONFIGURATION_FILE = DEFAULT_USER_CONFIGURATION + CONFIGURATION_EXTENSION;
    public static final String DEFAULT_CONFIGURATION_FILE = DEFAULT_CONFIGURATION + CONFIGURATION_EXTENSION;
    public static final String DEFAULT_PROPERTIES_FILE = "kefirbb.properties";
    public static final String DEFAULT_PROPERTIES_XML_FILE = "kefirbb.properties.xml";

    /**
     * Schema location
     */
    private static final String SCHEMA_LOCATION = "http://kefir-bb.sourceforge.net/schemas";

    /**
     * Constants wich uses when parse XML-configuration
     */
    private static final String TAG_CODE = "code";
    private static final String TAG_CODE_ATTR_NAME = "name";
    private static final String TAG_CODE_ATTR_PRIORITY = "priority";
    private static final String TAG_PATTERN = "pattern";
    private static final String TAG_VAR = "var";
    private static final String TAG_VAR_ATTR_NAME = "name";
    private static final String DEFAULT_VARIABLE_NAME = "variable";
    private static final String TAG_VAR_ATTR_PARSE = "parse";
    private static final boolean DEFAULT_PARSE_VALUE = true;
    private static final String TAG_VAR_ATTR_INHERIT = "inherit";
    private static final boolean DEFAULT_INHERIT_VALUE = false;
    private static final String TAG_VAR_ATTR_REGEX = "regex";
    private static final String TAG_VAR_ATTR_TRANSPARENT = "transparent";
    private static final String TAG_TEMPLATE = "template";
    private static final String TAG_SCOPE = "scope";
    private static final String TAG_SCOPE_ATTR_NAME = "name";
    private static final String TAG_SCOPE_ATTR_PARENT = "parent";
    private static final String TAG_SCOPE_ATTR_IGNORE_TEXT = "ignoreText";
    private static final boolean DEFAULT_IGNORE_TEXT = false;
    private static final String TAG_CODEREF = "coderef";
    private static final String TAG_CODEREF_ATTR_NAME = TAG_CODE_ATTR_NAME;
    private static final String TAG_PREFIX = "prefix";
    private static final String TAG_SUFFIX = "suffix";
    private static final String TAG_PARAMS = "params";
    private static final String TAG_PARAM = "param";
    private static final String TAG_PARAM_ATTR_NAME = "name";
    private static final String TAG_PARAM_ATTR_VALUE = "value";

    /**
     * Singletone class instance
     */
    private static final ConfigurationFactory instance = new ConfigurationFactory();

    /**
     * private constructor
     */
    private ConfigurationFactory() {
    }

    /**
     * Return instance of class ConfigurationFactory
     *
     * @return configuration factory
     */
    public static ConfigurationFactory getInstance() {
        return instance;
    }


    /**
     * Create the default bb-code processor.
     *
     * @return Default bb-code processor
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          when can't read the default code set resource
     */
    public Configuration create() {
        Configuration configuration;
        try {
            InputStream stream = null;
            try {
                // Search the user configuration
                stream = Util.openResourceStream(DEFAULT_USER_CONFIGURATION_FILE);

                // If user configuration not found then use default
                if (stream == null) {
                    stream = Util.openResourceStream(DEFAULT_CONFIGURATION_FILE);
                }

                if (stream != null) {
                    configuration = create(stream);
                } else {
                    throw new TextProcessorFactoryException("Can't find or open resource.");
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }

            Properties properties = new Properties();

            // Load properties from .property file
            InputStream propertiesStream = null;
            try {
                propertiesStream = Util.openResourceStream(DEFAULT_PROPERTIES_FILE);
                if (propertiesStream != null) {
                    properties.load(propertiesStream);
                }
            } finally {
                if (propertiesStream != null) {
                    propertiesStream.close();
                }
            }

            // Load properties from xml file
            InputStream xmlPropertiesStream = null;
            try {
                xmlPropertiesStream = Util.openResourceStream(DEFAULT_PROPERTIES_XML_FILE);
                if (xmlPropertiesStream != null) {
                    properties.loadFromXML(xmlPropertiesStream);
                }
            } finally {
                if (xmlPropertiesStream != null) {
                    xmlPropertiesStream.close();
                }
            }

            configuration.addParams(properties);
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        }
        return configuration;
    }

    /**
     * Create the bb-processor using xml-configuration resource
     *
     * @param resourceName name of resource file
     * @return bb-code processor
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          when can't find or read the resource or illegal config file
     */
    public Configuration createFromResource(String resourceName) {
        if (resourceName == null) {
            throw new IllegalArgumentException("The resource name is not setted.");
        }

        Configuration configuration;
        try {
            InputStream stream = null;
            try {
                stream = Util.openResourceStream(resourceName);

                if (stream != null) {
                    configuration = create(stream);
                } else {
                    throw new TextProcessorFactoryException("Can't find or open resource \"" + resourceName + "\".");
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        }

        return configuration;
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param fileName name of file with configuration
     * @return bb-code processor
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          any problems
     */
    public Configuration create(String fileName) {
        return create(new File(fileName));
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param file file with configuration
     * @return bb-code processor
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          any problems
     */
    public Configuration create(File file) {
        try {
            Configuration configuration;
            InputStream stream = new BufferedInputStream(new FileInputStream(file));
            try {
                configuration = create(stream);
            } finally {
                stream.close();
            }
            return configuration;
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        }
    }

    /**
     * Create the bb-processor from XML InputStream
     *
     * @param stream the input stream with XML
     * @return bb-code processor
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          when can't build Document
     */
    public Configuration create(InputStream stream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(stream);
            return create(document);
        } catch (ParserConfigurationException e) {
            throw new TextProcessorFactoryException(e);
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        } catch (SAXException e) {
            throw new TextProcessorFactoryException(e);
        }
    }

    /**
     * Create the bb-code processor from DOM Document
     *
     * @param dc document
     * @return bb-code processor
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          If invalid Document
     */
    private Configuration create(Document dc) {
        // Create configuration
        Configuration configuration = new Configuration();

        // Parse parameters
        configuration.addParams(parseParams(dc));

        // Parse prefix and suffix
        configuration.setPrefix(parseFix(dc, TAG_PREFIX));
        configuration.setSuffix(parseFix(dc, TAG_SUFFIX));

        // Parse codes and scope and set this to configuration
        // Parse scopes
        NodeList scopeNodeList = dc.getDocumentElement().getElementsByTagNameNS(SCHEMA_LOCATION, TAG_SCOPE);
        Map<String, Scope> scopes = parseScopes(scopeNodeList);

        boolean fillRoot = false;
        Scope root;
        if (!scopes.containsKey(Scope.ROOT)) {
            root = new Scope(Scope.ROOT);
            scopes.put(Scope.ROOT, root);
            fillRoot = true;
        } else {
            root = scopes.get(Scope.ROOT);
        }

        // Parse codes
        Map<String, AbstractCode> codes = parseCodes(dc, scopes);

        // include codes in scopes
        fillScopeCodes(scopeNodeList, scopes, codes);

        // If root scope not defined in configuration file, then root scope fills all codes
        if (fillRoot) {
            root.setScopeCodes(new HashSet<AbstractCode>(codes.values()));
        }

        // set root scope
        configuration.setScope(root);

        // return configuration
        return configuration;
    }

    private Map<String, String> parseParams(Document dc) {
        Map<String, String> params = new HashMap<String, String>();
        NodeList paramsElements = dc.getElementsByTagName(TAG_PARAMS);
        if (paramsElements.getLength() > 0) {
            Element paramsElement = (Element) paramsElements.item(0);
            NodeList paramElements = paramsElement.getElementsByTagName(TAG_PARAM);
            for (int i = 0; i < paramElements.getLength(); i++) {
                Node paramElement = paramElements.item(i);
                String name = nodeAttribute(paramElement, TAG_PARAM_ATTR_NAME, "");
                String value = nodeAttribute(paramElement, TAG_PARAM_ATTR_VALUE, "");
                if (name != null && name.length()>0) {
                    params.put(name, value);
                }
            }
        }
        return params;
    }

    @SuppressWarnings({"unchecked"})
    private Template parseFix(Document dc, String tagname) {
        Template fix;
        NodeList prefixElementList = dc.getElementsByTagName(tagname);
        if (prefixElementList.getLength() > 0) {
            fix = parseTemplate(prefixElementList.item(0));
        } else {
            fix = Template.EMPTY;
        }
        return fix;
    }

    /**
     * Fill codes of scopes.
     *
     * @param scopeNodeList node list with scopes definitions
     * @param scopes        scopes
     * @param codes         codes
     * @throws TextProcessorFactoryException any problem
     */
    private void fillScopeCodes(NodeList scopeNodeList, Map<String, Scope> scopes, Map<String, AbstractCode> codes) {
        int initCount;
        int notInitCount;
        do {
            initCount = 0;
            notInitCount = 0;
            for (int i = 0; i < scopeNodeList.getLength(); i++) {
                Element scopeElement = (Element) scopeNodeList.item(i);
                Scope scope = scopes.get(scopeElement.getAttribute(TAG_SCOPE_ATTR_NAME));
                if (!scope.isInitialized()) {
                    // parent
                    boolean canBeInit = true;
                    if (scopeElement.hasAttribute(TAG_SCOPE_ATTR_PARENT)) {
                        String parentName = scopeElement.getAttribute(TAG_SCOPE_ATTR_PARENT);
                        Scope parent = scopes.get(parentName);
                        if (parent == null) {
                            throw new TextProcessorFactoryException("Can't find parent scope \"" + parentName + "\".");
                        }
                        if (parent.isInitialized()) {
                            scope.setParent(parent);
                        } else {
                            canBeInit = false;
                        }
                    }

                    if (canBeInit) {
                        // Add codes to scope
                        Set<AbstractCode> scopeCodes = new HashSet<AbstractCode>();

                        // bind exists codes
                        NodeList coderefs = scopeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_CODEREF);
                        for (int j = 0; j < coderefs.getLength(); j++) {
                            Element ref = (Element) coderefs.item(j);
                            String codeName = ref.getAttribute(TAG_CODEREF_ATTR_NAME);
                            AbstractCode code = codes.get(codeName);
                            if (code == null) {
                                throw new TextProcessorFactoryException("Can't find code \"" + codeName + "\".");
                            }
                            scopeCodes.add(code);
                        }

                        // Add inline codes
                        NodeList inlineCodes = scopeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_CODE);
                        for (int j = 0; j < inlineCodes.getLength(); j++) {
                            // Inline element code
                            Element ice = (Element) inlineCodes.item(j);
                            scopeCodes.add(parseCode(ice, scopes));
                        }

                        // Set codes to scope
                        scope.setScopeCodes(scopeCodes);
                        initCount++;
                    } else {
                        notInitCount++;
                    }
                }
            }
        } while (initCount > 0 && notInitCount > 0);

        if (notInitCount > 0) {
            throw new TextProcessorFactoryException("Can't init scopes.");
        }
    }

    /**
     * Parse scopes from XML
     *
     * @param scopeNodeList list with scopes definitions
     * @return scopes
     * @throws TextProcessorFactoryException any problems
     */
    private Map<String, Scope> parseScopes(NodeList scopeNodeList) {
        Map<String, Scope> scopes = new HashMap<String, Scope>();
        for (int i = 0; i < scopeNodeList.getLength(); i++) {
            Element scopeElement = (Element) scopeNodeList.item(i);
            String name = scopeElement.getAttribute(TAG_SCOPE_ATTR_NAME);
            if (name.length()==0) {
                throw new TextProcessorFactoryException("Illegal scope name.");
            }
            Scope scope = new Scope(name);
            scope.setIgnoreText(nodeAttribute(scopeElement, TAG_SCOPE_ATTR_IGNORE_TEXT, DEFAULT_IGNORE_TEXT));
            scopes.put(scope.getName(), scope);
        }
        return scopes;
    }

    /**
     * Parse codes from XML
     *
     * @param dc     DOM document with configuration
     * @param scopes scope set
     * @return codes
     * @throws TextProcessorFactoryException any problem
     */
    private Map<String, AbstractCode> parseCodes(Document dc, Map<String, Scope> scopes) {
        Map<String, AbstractCode> codes = new HashMap<String, AbstractCode>();
        NodeList codeNodeList = dc.getDocumentElement().getElementsByTagNameNS(SCHEMA_LOCATION, TAG_CODE);
        for (int i = 0; i < codeNodeList.getLength(); i++) {
            AbstractCode code = parseCode((Element) codeNodeList.item(i), scopes);
            codes.put(code.getName(), code);
        }
        return codes;
    }

    /**
     * Parse bb-code from DOM Node
     *
     * @param codeElement node, represent code wich
     * @param scopes      mapping names of scope to scope
     * @return bb-code
     * @throws ru.perm.kefir.bbcode.TextProcessorFactoryException
     *          if error format
     */
    private AbstractCode parseCode(Element codeElement, Map<String, Scope> scopes) {
        // Code name
        String name = nodeAttribute(codeElement, TAG_CODE_ATTR_NAME, Util.generateRandomName());

        // Code priority
        int priority = nodeAttribute(codeElement, TAG_CODE_ATTR_PRIORITY, AbstractCode.DEFAULT_PRIORITY);

        // Template to building
        Template template;
        NodeList templateElements = codeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_TEMPLATE);
        if (templateElements.getLength() > 0) {
            template = parseTemplate(templateElements.item(0));
        } else {
            throw new TextProcessorFactoryException("Illegal configuration. Can't find template of code.");
        }


        // Pattern to parsing
        NodeList patternElements = codeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_PATTERN);
        if (patternElements.getLength() <= 0) {
            throw new TextProcessorFactoryException("Illegal configuration. Can't find pattern of code.");
        }

        AbstractCode code = null;

        // Attempt parse constant code
        NodeList patternList = patternElements.item(0).getChildNodes();
        if (patternList.getLength() == 1) {
            Node node = patternList.item(0);
            if (node.getNodeType() == Node.TEXT_NODE) {
                code = new ConstantCode(node.getNodeValue(), template, name, priority);
            }
        }

        if (code == null) {
            // Create code
            code = new Code(parsePattern(patternElements.item(0), scopes), template, name, priority);
        }

        // return code
        return code;
    }

    /**
     * Parse code pattern for parse text.
     *
     * @param node   pattern node with pattern description
     * @param scopes mapping names of scope to scope
     * @return list of pattern elements
     * @throws TextProcessorFactoryException If invalid pattern format
     */
    private Pattern parsePattern(Node node, Map<String, Scope> scopes) {
        List<PatternElement> elements = new LinkedList<PatternElement>();
        NodeList patternList = node.getChildNodes();
        int patternLength = patternList.getLength();
        if (patternLength <= 0) {
            throw new TextProcessorFactoryException("Invalid pattern");
        }
        for (int k = 0; k < patternLength; k++) {
            Node el = patternList.item(k);
            if (el.getNodeType() == Node.TEXT_NODE) {
                elements.add(new Constant(el.getNodeValue()));
            } else if (
                    el.getNodeType() == Node.ELEMENT_NODE
                            && el.getLocalName().equals(TAG_VAR)
                            && (k != 0 || nodeHasAttribute(el, TAG_VAR_ATTR_REGEX))) {
                elements.add(parseNamedElement(el, scopes));
            } else {
                throw new TextProcessorFactoryException("Invalid pattern");
            }
        }
        return new Pattern(elements);
    }

    private PatternElement parseNamedElement(Node el, Map<String, Scope> scopes) {
        String name = nodeAttribute(el, TAG_VAR_ATTR_NAME, DEFAULT_VARIABLE_NAME);
        PatternElement namedElement;
        if (
                nodeAttribute(el, TAG_VAR_ATTR_PARSE, DEFAULT_PARSE_VALUE)
                        && !nodeHasAttribute(el, TAG_VAR_ATTR_REGEX)
                ) {
            namedElement = parseText(el, name, scopes);
        } else {
            namedElement = parseVariable(el, name);
        }
        return namedElement;
    }

    private Text parseText(Node el, String name, Map<String, Scope> scopes) {
        Text text;
        if (nodeAttribute(el, TAG_VAR_ATTR_INHERIT, DEFAULT_INHERIT_VALUE)) {
            text = new Text(name, nodeAttribute(el, TAG_VAR_ATTR_TRANSPARENT, false));
        } else {
            text = new Text(
                    name,
                    scopes.get(nodeAttribute(el, TAG_SCOPE, Scope.ROOT)),
                    nodeAttribute(el, TAG_VAR_ATTR_TRANSPARENT, false)
            );
        }
        return text;
    }

    private Variable parseVariable(Node el, String name) {
        Variable variable;
        if (nodeHasAttribute(el, TAG_VAR_ATTR_REGEX)) {
            variable = new Variable(
                    name,
                    java.util.regex.Pattern.compile(
                            nodeAttribute(el, TAG_VAR_ATTR_REGEX)
                    )
            );
        } else {
            variable = new Variable(name);
        }
        return variable;
    }

    /**
     * Parse template fo generate text.
     *
     * @param node template node
     * @return list of template elements
     */
    private Template parseTemplate(Node node) {
        List<TemplateElement> elements = new LinkedList<TemplateElement>();
        NodeList templateList = node.getChildNodes();
        for (int k = 0; k < templateList.getLength(); k++) {
            Node el = templateList.item(k);
            if (el.getNodeType() == Node.ELEMENT_NODE && el.getLocalName().equals(TAG_VAR)) {
                elements.add(new NamedValue(nodeAttribute(el, TAG_VAR_ATTR_NAME, DEFAULT_VARIABLE_NAME)));
            } else {
                elements.add(new Constant(el.getNodeValue()));
            }
        }
        return new Template(elements);
    }

    /**
     * Return node attribute value, if exists or default attibute value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @param defaultValue  attribute default value
     * @return attribute value or default value
     */
    private boolean nodeAttribute(Node node, String attributeName, boolean defaultValue) {
        boolean value = defaultValue;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = Boolean.valueOf(attribute.getNodeValue());
            }
        }
        return value;
    }

    /**
     * Return node attribute value, if exists or default attibute value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @param defaultValue  attribute default value
     * @return attribute value or default value
     */
    private String nodeAttribute(Node node, String attributeName, String defaultValue) {
        String value = defaultValue;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = attribute.getNodeValue();
            }
        }
        return value;
    }

    /**
     * Return node attribute value, if exists or null value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @return attribute value or default value
     */
    private String nodeAttribute(Node node, String attributeName) {
        String value = null;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = attribute.getNodeValue();
            }
        }
        return value;
    }

    /**
     * Return node attribute value, if exists or default attibute value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @param defaultValue  attribute default value
     * @return attribute value or default value
     */
    private int nodeAttribute(Node node, String attributeName, int defaultValue) {
        int value = defaultValue;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = Integer.decode(attribute.getNodeValue());
            }
        }
        return value;
    }

    /**
     * Check node attribute.
     *
     * @param node          XML-node
     * @param attributeName name of attribute
     * @return true if node has attribute with specified name
     *         false if has not
     */
    private boolean nodeHasAttribute(Node node, String attributeName) {
        return node.hasAttributes() && node.getAttributes().getNamedItem(attributeName) != null;
    }
}
