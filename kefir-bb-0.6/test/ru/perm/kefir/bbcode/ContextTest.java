package ru.perm.kefir.bbcode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ContextTest {
    @Test
    public void testTarget(){
        Context ctx  =new Context();
        Appendable target = new StringBuilder();
        ctx.setTarget(target);
        assertEquals(target, ctx.getTarget());
    }

    @Test
    public void testTerminator(){
        Context ctx = new Context();
        assertNull(ctx.getTerminator());
        PatternElement terminator = new Constant("test");
        ctx.setTerminator(terminator);
        assertEquals(terminator, ctx.getTerminator());
    }
}
