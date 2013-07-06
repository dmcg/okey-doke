/**
 * 
 */
package org.rococoa.okeydoke.internal;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexDumpTest
{
    @Test
    public void testOneByte() {
        assertEquals("00", HexDump.format((byte) 0x00));
        assertEquals("0F", HexDump.format((byte) 0x0F));
        assertEquals("F0", HexDump.format((byte) 0xF0));
        assertEquals("FF", HexDump.format((byte) 0xFF));

        assertEquals("0x80", HexDump.format((byte) 0x80, true));
    }

    @Test
    public void testByteArray() {
        assertEquals("00 01 FE FF",
            HexDump.format(new byte[] {0x00, 0x01, (byte) 0xFE, (byte) 0xFF}));
        assertEquals("0001FEFF",
            HexDump.formatCompact(new byte[] {0x00, 0x01, (byte) 0xFE, (byte) 0xFF}));
        assertEquals("0x00 0xFF",
            HexDump.format(new byte[] {0x00, (byte) 0xFF}, true, true));
    }

    @Test
    public void testRead() {
        assertArrayEquals(
                new byte[]{0x00, 0x01, (byte) 0xFE, (byte) 0xFF},
                HexDump.readCompact("0001FEFF"));
        try {
            HexDump.readCompact("0001FEF");
            fail();
        } catch (NumberFormatException x) {
        }
    }
    
}