package ru.perm.kefir.bbcode.blackbox;

import org.junit.Test;
import ru.perm.kefir.bbcode.BBProcessorFactory;
import ru.perm.kefir.bbcode.Configuration;
import ru.perm.kefir.bbcode.ConfigurationFactory;
import ru.perm.kefir.bbcode.TextProcessor;

import static ru.perm.kefir.bbcode.Assert.assertProcess;

/**
 * Класс для тестирования обработчика BB-кодов
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class BBProcessorTest {
    @Test
    public void testPrefix() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-prefix.xml");
        assertProcess(processor, "^text^", "prefixtext");
        assertProcess(processor, "(^text^)", "[prefixtext]");
        assertProcess(processor, "text", "text");
    }

    @Test
    public void testPrefixAndSuffix() {
        assertProcess(
            BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-prefixsuffix.xml"),
            "<!-- bbcodes begin -->Test<!-- bbcodes end -->",
            "Test"
        );
    }

    @Test
    public void testContextRollback() {
        assertProcess(
            BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-context-rollback.xml"),
            "1",
            "[1](2)[/]"
        );
    }

    @Test
    public void testVariable() {
        assertProcess(
            BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-variable.xml"),
            "Oi!",
            "def Oi!;print;"
        );
    }

    @Test
    public void testNotClosed() {
        assertProcess(
            BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-variable.xml"),
            "def Oi!",
            "def Oi!"
        );
    }

    @Test
    public void testParameters() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-parameters.xml");
        assertProcess(processor, "Oi!", "print;");
        assertProcess(processor, "PunkOi!", "print punk;print;");

        Configuration configuration = ConfigurationFactory.getInstance().create();
        configuration.setParam("var4", 4);
        configuration.setParam("var5", 4);
        TextProcessor defaultProcessor = configuration.create();
        assertProcess(defaultProcessor, "12345", "def 5;");
        assertProcess(defaultProcessor, "12344", "print;");
    }

    @Test
    public void testRegex() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-regex.xml");
        assertProcess(processor, "v:3", "def v=3;");
        assertProcess(processor, "var:33", "def var=33;");
        assertProcess(processor, "def var= 33;", "def var= 33;");
        assertProcess(processor, "def var = 33 ;", "def var = 33 ;");
        assertProcess(processor, "def 3=v;", "def 3=v;");
        assertProcess(processor, "def 33=var;", "def 33=var;");
    }

    @Test
    public void testTransparent(){
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-transparent.xml");
        assertProcess(processor, "tag=tag,a=2,b=1,c=3", "<tag b=\"1\" a=\"2\" c=\"3\" />");
        assertProcess(processor, "tag=tag,a=2,b=null,c=3", "<tag a=\"2\" c=\"3\" />");
    }

    @Test
    public void testOpenCodes(){
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-open.xml");
        assertProcess(processor, "Set value to name.", "name=value");
    }
}
